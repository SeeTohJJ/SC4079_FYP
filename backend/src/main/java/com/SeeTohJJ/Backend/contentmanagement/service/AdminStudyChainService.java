package com.SeeTohJJ.Backend.contentmanagement.service;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.ChainTemplateRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.request.QuizRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.ChainTemplateResponseDTO;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.QuizResponseDTO;

import java.util.List;

public interface AdminStudyChainService {
    List<ChainTemplateResponseDTO> getAllActiveChainTemplates();
    List<ChainTemplateResponseDTO> getAllInactiveChainTemplates();

    ChainTemplateResponseDTO getChainTemplate(int templateId);
    ChainTemplateResponseDTO createChainTemplate(ChainTemplateRequest request);
    ChainTemplateResponseDTO updateChainTemplate(int templateId, ChainTemplateRequest request);
    void setChainTemplateInactive(int templateId);
    void setChainTemplateActive(int templateId);

    int getActiveCount();
}
