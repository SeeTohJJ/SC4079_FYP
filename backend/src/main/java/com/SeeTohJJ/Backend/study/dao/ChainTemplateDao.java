package com.SeeTohJJ.Backend.study.dao;

import com.SeeTohJJ.Backend.study.model.chain.ChainTemplate;
import com.SeeTohJJ.Backend.study.service.progress.impl.NodeGenerationServiceImpl;

import java.util.List;

public interface ChainTemplateDao {

    List<ChainTemplate> getChainTemplate(NodeGenerationServiceImpl.ChainType chainType);
}
