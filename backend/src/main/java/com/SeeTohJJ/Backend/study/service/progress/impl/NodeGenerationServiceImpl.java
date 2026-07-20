package com.SeeTohJJ.Backend.study.service.progress.impl;

import com.SeeTohJJ.Backend.study.dao.ChainTemplateDao;
import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.dto.*;
import com.SeeTohJJ.Backend.study.dto.node.DecisionNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizNodeDTO;
import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.model.UserNodeProgress;
import com.SeeTohJJ.Backend.study.model.chain.ChainTemplate;
import com.SeeTohJJ.Backend.study.service.adaptive.BktService;
import com.SeeTohJJ.Backend.study.service.adaptive.EloService;
import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.ProgressService;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
import com.SeeTohJJ.Backend.topic.service.TopicService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NodeGenerationServiceImpl implements NodeGenerationService {

    private static final Logger logger = LoggerFactory.getLogger(NodeGenerationServiceImpl.class);

    private final StudyDao studyDao;
    private final TopicService topicService;
    private final BktService bktService;
    private final EloService eloService;
    private final ProgressService progressService;
    private final SubTopicService subTopicService;
    private final ChainTemplateDao chainTemplateDao;

    @Autowired
    public NodeGenerationServiceImpl(StudyDao studyDao,
                                     TopicService topicService,
                                     BktService bktService,
                                     EloService eloService,
                                     ProgressService progressService,
                                     SubTopicService subTopicService,
                                     ChainTemplateDao chainTemplateDao) {
        this.studyDao = studyDao;
        this.topicService = topicService;
        this.bktService = bktService;
        this.eloService = eloService;
        this.progressService = progressService;
        this.subTopicService = subTopicService;
        this.chainTemplateDao = chainTemplateDao;
    }

    public enum ChainType {
        STANDARD,
        PRACTICE,
        TUTORIAL
    }

    @Override
    public List<StudyNodePathDTO> getStudyPathNodes(Long userId){
        logger.info("Starting getStudyPathNodes");

        // Check and get nodes from user_node_progress if there is one that is unlocked AND not completed
        if (studyDao.hasActiveNodes(userId)) {
            return getExistingNodePath(userId);
        }

        String uncompletedTopicId = topicService.getUncompletedTutorialTopic(userId);
        String uncompletedSubtopicId = uncompletedTopicId.concat("S001");

        // Generate study nodes based on user topic interest
//        if(uncompletedTopicId == null) {
//            insertAdaptiveNodesForUser(userId);
//
//            return getExistingNodePath(userId);
//        }

        // Generate tutorial nodes if user has interest in topic and tutorial not completed
        insertNewSubtopicMastery(userId, uncompletedSubtopicId);
        insertTutorialNodesForUser(userId, uncompletedSubtopicId);

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

        dto.setNodeId(nodeProgress.getNodeId());
        dto.setNodeType(nodeProgress.getNodeType());
        dto.setPositionIndex(nodeProgress.getPositionIndex());
        dto.setUnlocked(nodeProgress.isUnlocked());
        dto.setCompleted(nodeProgress.isCompleted());

        return dto;
    }


    private void insertAdaptiveNodesForUser(Long userId) {
        logger.info("Starting insertAdaptiveNodesForUser");

    }

    private void insertTutorialNodesForUser(Long userId, String TopicId) {
        logger.info("Starting insertTutorialNodesForUser");

//        List<StudyNode> nodes = fetchTutorialNodes(TopicId);
//        insertNodesIntoNodeProgress(nodes, userId);

        generateChain(userId, TopicId, ChainType.TUTORIAL);
    }

    private void insertNewSubtopicMastery(Long userId, String subtopicId) {
        logger.info("Starting insertNewSubtopicMastery");

        subTopicService.insertNewSubtopicMastery(userId, subtopicId);
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

    @Override
    public LessonNodeDTO getLessonNodeContent(String nodeId){
        logger.info("Starting getLessonNodeContent");

        return studyDao.getLessonNodeContent(nodeId);
    }

    @Override
    public QuizNodeDTO getQuizContent(String nodeId){
        logger.info("Starting getQuizContent");

        return studyDao.getQuizNodeContent(nodeId);
    }

    @Override
    public DecisionNodeDTO getDecisionNodeContent(String nodeId){
        logger.info("Starting getDecisionNodeContent");

        return studyDao.getDecisionNodeContent(nodeId);
    }

    @Override
    public EventNodeDTO getEventNodeContent(String nodeId){
        logger.info("Starting getEventNodeContent");

        return studyDao.getEventNodeContent(nodeId);
    }

    @Override
    public void generateNewChain(Long userId){
        logger.info("Starting generateNewChain");

        String currentSubtopic = progressService.getCurrentSubtopic(userId);

        if (subTopicService.isSubtopicMastered(userId, currentSubtopic)){
            String nextSubtopic = progressService.getUserLowestPKnowSubtopicNotMastered(userId);

            if(nextSubtopic != null) {
                generateStandardChain(userId, nextSubtopic);
            }
            else {
                nextSubtopic = subTopicService.createNewUserInterestedSubtopic(userId, currentSubtopic);
                generateStandardChain(userId, nextSubtopic);
            }
        }
        else if (subTopicService.moreLessonExists(userId, currentSubtopic)){
            generateStandardChain(userId, currentSubtopic);
        }
        else {
            generatePracticeChain(userId, currentSubtopic);
        }

    }

    public void generateStandardChain(Long userId, String subtopicId) {
        logger.info("Starting generateStandardChain");

        generateChain(userId, subtopicId, ChainType.STANDARD);
    }

    public void generatePracticeChain(Long userId, String subtopicId) {
        logger.info("Starting generatePracticeChain");

        generateChain(userId, subtopicId, ChainType.PRACTICE);
    }

    private void generateChain(Long userId, String subtopicId, ChainType chainType) {
        logger.info("Starting generateChain");

        List<ChainTemplate> template = chainTemplateDao.getChainTemplate(chainType);
        int currentChain = progressService.getCurrentChain(userId, subtopicId);

        if (template.isEmpty()) {
            throw new RuntimeException("No chain template found for " + chainType);
        }

        int currentPathPositionIndex = studyDao.getUserLastPositionIndex(userId) + 1;

        boolean unlock = true;

        for (ChainTemplate step : template) {
            String nodeId = subTopicService.getNodeId(
                            subtopicId,
                            step.getNodeType(),
                            step.getContentSequence(),
                            currentChain);

            if (nodeId == null) {
                throw new RuntimeException(
                        "Missing study node. " +
                                "Subtopic=" + subtopicId +
                                ", Type=" + step.getNodeType() +
                                ", Sequence=" + step.getContentSequence());
            }

            currentPathPositionIndex++;

            progressService.insertNodeIntoUserProgress(
                    userId,
                    nodeId,
                    currentPathPositionIndex,
                    unlock,
                    step.getNodeType()
            );

            unlock = false;
        }

    }
}
