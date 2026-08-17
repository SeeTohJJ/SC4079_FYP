package com.SeeTohJJ.Backend.contentmanagement.service.impl;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.ChainTemplateRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.request.LessonRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.ChainTemplateResponseDTO;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.LessonResponseDTO;
import com.SeeTohJJ.Backend.contentmanagement.service.AdminStudyChainService;
import com.SeeTohJJ.Backend.study.dao.ChainTemplateDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminStudyChainServiceImpl implements AdminStudyChainService {

    private static final Logger logger = LoggerFactory.getLogger(AdminStudyChainServiceImpl.class);
    private final ChainTemplateDao chainTemplateDao;

    @Autowired
    public AdminStudyChainServiceImpl(ChainTemplateDao chainTemplateDao) {
        this.chainTemplateDao = chainTemplateDao;
    }

    @Override
    public List<ChainTemplateResponseDTO> getAllActiveChainTemplates(){
        logger.info("Starting getAllActiveChainTemplates");

        return chainTemplateDao.getAllActiveChainTemplates();
    }

    @Override
    public List<ChainTemplateResponseDTO> getAllInactiveChainTemplates(){
        logger.info("Starting getAllInactiveChainTemplates");

        return chainTemplateDao.getAllInactiveChainTemplates();
    }

    @Override
    public ChainTemplateResponseDTO getChainTemplate(int templateId){
        logger.info("Starting getChainTemplate");

        return chainTemplateDao.findById(templateId);
    }

    @Override
    public ChainTemplateResponseDTO createChainTemplate(ChainTemplateRequest request){
        logger.info("Starting createChainTemplate");

        String chainType = request.getChainType();
        int orderInChain = request.getOrderInChain();
        String nodeType = request.getNodeType();
        int contentSequence = request.getContentSequence();

        int templateId = chainTemplateDao.getNextTemplateId();
        chainTemplateDao.createChainTemplate(templateId, chainType, orderInChain, nodeType, contentSequence);

        return chainTemplateDao.findById(templateId);
    }

    @Override
    public ChainTemplateResponseDTO updateChainTemplate(int templateId, ChainTemplateRequest request){
        logger.info("Starting updateChainTemplate");

        String chainType = request.getChainType();
        int orderInChain = request.getOrderInChain();
        String nodeTpe = request.getNodeType();
        int contentSequence = request.getContentSequence();

        chainTemplateDao.updateChainTemplate(templateId, chainType, orderInChain, nodeTpe, contentSequence);

        return chainTemplateDao.findById(templateId);
    }

    @Override
    public void setChainTemplateInactive(int templateId){
        logger.info("Starting setChainTemplateInactive");

        chainTemplateDao.setChainTemplateInactive(templateId);
    }

    @Override
    public void setChainTemplateActive(int templateId){
        logger.info("Starting setChainTemplateActive");

        chainTemplateDao.setChainTemplateActive(templateId);
    }

    @Override
    public int getActiveCount(){
        logger.info("Starting getActiveCount");

        return chainTemplateDao.getActiveCount();
    }
}
