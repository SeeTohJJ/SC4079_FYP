package com.SeeTohJJ.Backend.study.service.progress.impl;

import com.SeeTohJJ.Backend.common.exception.ChainGenerationException;
import com.SeeTohJJ.Backend.study.constant.EnergyConstant;
import com.SeeTohJJ.Backend.study.dao.ChainTemplateDao;
import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.dto.*;
import com.SeeTohJJ.Backend.study.model.GeneratedNode;
import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.model.UserNodeProgress;
import com.SeeTohJJ.Backend.study.model.chain.ChainTemplate;
import com.SeeTohJJ.Backend.study.service.adaptive.SpacedRepetitionService;
import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.UserStudyPathService;
import com.SeeTohJJ.Backend.study.service.progress.UserSubtopicService;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
import com.SeeTohJJ.Backend.topic.service.TopicService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class NodeGenerationServiceImpl implements NodeGenerationService {

    private static final Logger logger = LoggerFactory.getLogger(NodeGenerationServiceImpl.class);

    private final StudyDao studyDao;
    private final TopicService topicService;
    private final UserStudyPathService userStudyPathService;
    private final SubTopicService subTopicService;
    private final ChainTemplateDao chainTemplateDao;
    private final UserSubtopicService userSubtopicService;
    private final SpacedRepetitionService spacedRepetitionService;

    @Autowired
    public NodeGenerationServiceImpl(StudyDao studyDao,
                                     TopicService topicService,
                                     UserStudyPathService userStudyPathService,
                                     SubTopicService subTopicService,
                                     ChainTemplateDao chainTemplateDao,
                                     UserSubtopicService userSubtopicService,
                                     SpacedRepetitionService spacedRepetitionService) {
        this.studyDao = studyDao;
        this.topicService = topicService;
        this.userStudyPathService = userStudyPathService;
        this.subTopicService = subTopicService;
        this.chainTemplateDao = chainTemplateDao;
        this.userSubtopicService = userSubtopicService;
        this.spacedRepetitionService = spacedRepetitionService;
    }

    public enum ChainType {
        STANDARD,
        PRACTICE,
        TUTORIAL,
        REVIEW
    }

    @Transactional
    @Override
    public List<StudyNodePathDTO> getStudyPathNodes(Long userId){
        logger.info("Starting getStudyPathNodes");

        // Check and get nodes from user_node_progress if there is one that is unlocked AND not completed
        if (studyDao.hasActiveNodes(userId)) {
            return getExistingNodePath(userId);
        }

        // Need to find if there is an user interested topic with uncompleted tutorial
        // Need to complete tutorial once the last quiz node in tutorial is completed
        // If no user interested topic with uncompleted tutorial, generate a special interest decision node
        // for user to add a topic to their interest (choose between 2 option)

        String topicId = topicService.getUncompletedTutorialTopic(userId);

        if (topicId != null) {
            // Generate tutorial nodes if user has interest in topic and tutorial not completed
            generateNewTopicTutorial(userId, topicId);
        }
        else {
            generateInterestDecisionNode(userId);
        }

        return getExistingNodePath(userId);
    }

    @Override
    public List<StudyNodePathDTO> getExistingNodePath(Long userId){
        logger.info("Starting getExistingNodePath");

        List<UserNodeProgress> nodePaths = studyDao.getExistingNodePath(userId);

        return nodePaths.stream()
                .map(this::convertToStudyNodePathDTO)
                .toList();
    }

    private StudyNodePathDTO convertToStudyNodePathDTO(
            UserNodeProgress nodeProgress) {

        StudyNodePathDTO dto = new StudyNodePathDTO();

        String topicName = topicService.getTopicName(topicService.getTopicId(nodeProgress.getNodeId()));

        dto.setNodeId(nodeProgress.getNodeId());
        dto.setNodeTopic(topicName);
        dto.setNodeType(nodeProgress.getNodeType());
        dto.setPositionIndex(nodeProgress.getPositionIndex());
        dto.setUnlocked(nodeProgress.isUnlocked());
        dto.setCompleted(nodeProgress.isCompleted());
        dto.setEnergyCost(getNodeEnergyCost(nodeProgress.getNodeType()));

        return dto;
    }

    private int getNodeEnergyCost(StudyNode.NodeType nodeType) {
        return switch (nodeType) {
            case LESSON -> EnergyConstant.ENERGY_COST_LESSON;
            case QUIZ -> EnergyConstant.ENERGY_COST_QUIZ;
            default -> 0;
        };

    }


    private void insertAdaptiveNodesForUser(Long userId) {
        logger.info("Starting insertAdaptiveNodesForUser");

    }

    @Transactional
    public void generateNewTopicTutorial(Long userId, String topicId) {
        logger.info("Starting generateNewTopicTutorial");

        String subtopicId = topicId.concat("S001");

        double p_know = subTopicService.getPInit(subtopicId);
        userSubtopicService.insertNewSubtopicMastery(userId, subtopicId, p_know);
        generateChain(userId, subtopicId, ChainType.TUTORIAL);
    }

    private List<StudyNode> fetchTutorialNodes(String TopicId) {
        logger.info("Starting fetchTutorialNodes");

        return studyDao.getTutorialNodes(TopicId);
    }

    private void insertNodesIntoNodeProgress(List<StudyNode> nodes, Long userId) {
        logger.info("Starting insertNodesIntoNodeProgress");

        int position = 0;

        for (StudyNode node : nodes) {

            boolean unlocked = (position == 0);

            studyDao.insertNodeIntoUserProgress(
                    userId,
                    node.getNodeId(),
                    node.getType().toString(),
                    position,
                    unlocked,
                    false
            );

            position++;
        }
    }

    @Transactional
    @Override
    public void generateNewChain(Long userId){
        logger.info("Starting generateNewChain");

        List<String> dueReviews = spacedRepetitionService.getDueReviews(userId);

        if (!dueReviews.isEmpty()) {
            generateReviewChain(userId, dueReviews.getFirst());
            return;
        }

        String currentSubtopic = userStudyPathService.getCurrentSubtopic(userId);

        if (userSubtopicService.isSubtopicMastered(userId, currentSubtopic)){
            String nextSubtopic = subTopicService.getNextSubtopic(currentSubtopic);

            if(nextSubtopic != null) {
                generateStandardChain(userId, nextSubtopic);
            }
            else {
                nextSubtopic = userSubtopicService.getUserLowestPKnowSubtopicNotMastered(userId);
                if(nextSubtopic != null) {
                    generateStandardChain(userId, nextSubtopic);
                }
                else {
                    generateInterestDecisionNode(userId);
                }
            }
        }
        else if (subTopicService.moreLessonExists(userId, currentSubtopic)){
            generateStandardChain(userId, currentSubtopic);
        }
        else {
            generatePracticeChain(userId, currentSubtopic);
        }

    }

    @Transactional
    public void generateStandardChain(Long userId, String subtopicId) {
        logger.info("Starting generateStandardChain");

        generateChain(userId, subtopicId, ChainType.STANDARD);
    }

    @Transactional
    public void generatePracticeChain(Long userId, String subtopicId) {
        logger.info("Starting generatePracticeChain");

        generateChain(userId, subtopicId, ChainType.PRACTICE);
    }

    @Transactional
    public void generateReviewChain(Long userId, String subtopicId) {
        logger.info("Starting generateReviewChain");

        generateChain(userId, subtopicId, ChainType.REVIEW);
    }

    // NodeId S-T000-S000-TI-001
    private void generateInterestDecisionNode(Long userId){
        logger.info("Starting generateInterestDecisionNode");

        String nodeId = "S-T000-S000-TI-001";
        int currentPathPositionIndex = studyDao.getUserLastPositionIndex(userId) + 1;
        boolean unlock = true;
        String nodeType = "TOPIC_INTEREST";

        userStudyPathService.insertNodeIntoUserProgress(
                userId,
                nodeId,
                currentPathPositionIndex,
                unlock,
                nodeType
        );
    }

    @Transactional
    public void generateChain(Long userId, String subtopicId, ChainType chainType) {
        logger.info("Starting generateChain");

        List<ChainTemplate> template = chainTemplateDao.getChainTemplate(chainType);

        if (template.isEmpty()) {
            throw new ChainGenerationException(
                    "No chain template found for " + chainType);
        }

        int currentChain = userSubtopicService.getCurrentChain(userId, subtopicId);
        int startPosition = studyDao.getUserLastPositionIndex(userId);

        List<GeneratedNode> generatedNodes = resolveNodes(userId, subtopicId, template, currentChain, startPosition);

        insertNodes(userId, generatedNodes);
    }

    private List<GeneratedNode> resolveNodes(Long userId, String subtopicId, List<ChainTemplate> template, int currentChain, int startPosition) {
        logger.info("Starting resolveNodes");

        List<GeneratedNode> nodes = new ArrayList<>();
        int position = startPosition;

        // Count how many REVIEW slots exist
        int requiredReviewNodeCount = (int) template.stream()
                .filter(step -> StudyNode.NodeType.REVIEW.toString().equals(step.getNodeType()))
                .count();

        List<String> reviewNodes = userStudyPathService.getIncorrectNodes(
                userId,
                subtopicId,
                requiredReviewNodeCount);

        int reviewIndex = 0;
        int fallbackQuizSequence = 1;

        for (ChainTemplate step : template) {

            String actualNodeType = step.getNodeType();
            String nodeId;

            if (StudyNode.NodeType.REVIEW.toString().equals(actualNodeType)) {

                // Use an incorrect question first
                if (reviewIndex < reviewNodes.size()) {

                    nodeId = reviewNodes.get(reviewIndex++);

                } else {

                    // Not enough incorrect questions, use a normal quiz
                    nodeId = subTopicService.getNodeId(
                            subtopicId,
                            StudyNode.NodeType.QUIZ.toString(),
                            fallbackQuizSequence++,
                            currentChain);

                    if (nodeId == null) {
                        throw new ChainGenerationException(
                                "Unable to find fallback quiz for subtopic "
                                        + subtopicId);
                    }

                }
                actualNodeType = StudyNode.NodeType.QUIZ.toString();

            } else {

                nodeId = subTopicService.getNodeId(
                        subtopicId,
                        actualNodeType,
                        step.getContentSequence(),
                        currentChain);

                if (nodeId == null) {
                    throw new ChainGenerationException(
                            "Missing node. " +
                                    "Subtopic=" + subtopicId +
                                    ", Type=" + actualNodeType +
                                    ", Sequence=" + step.getContentSequence());
                }
            }

            position++;

            nodes.add(new GeneratedNode(
                    nodeId,
                    actualNodeType,
                    position,
                    nodes.isEmpty()));
        }

        return nodes;
    }

    private void insertNodes(Long userId, List<GeneratedNode> nodes) {

        logger.info("Starting insertNodes");

        for (GeneratedNode node : nodes) {

            userStudyPathService.insertNodeIntoUserProgress(
                    userId,
                    node.getNodeId(),
                    node.getPosition(),
                    node.isUnlocked(),
                    node.getNodeType());
        }
    }
}
