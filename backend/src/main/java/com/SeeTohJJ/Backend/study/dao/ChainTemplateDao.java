package com.SeeTohJJ.Backend.study.dao;

import com.SeeTohJJ.Backend.contentmanagement.dto.response.ChainTemplateResponseDTO;
import com.SeeTohJJ.Backend.study.model.chain.ChainTemplate;
import com.SeeTohJJ.Backend.study.service.progress.impl.NodeGenerationServiceImpl;

import java.util.List;

public interface ChainTemplateDao {

    List<ChainTemplate> getChainTemplate(NodeGenerationServiceImpl.ChainType chainType);
    List<ChainTemplateResponseDTO> getAllChainTemplates();
    ChainTemplateResponseDTO findById(int templateId);
    void createChainTemplate(int templateId, String chainType, int orderInChain, String nodeType, int contentSequence);
    void updateChainTemplate(int templateId, String chainType, int orderInChain, String nodeType, int contentSequence);
    void setChainTemplateInactive(int templateId);
    void setChainTemplateActive(int templateId);
    int getActiveCount();
}
