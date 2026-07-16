package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.study.constant.ChainTemplateConstant;
import com.SeeTohJJ.Backend.study.dao.ChainTemplateDao;
import com.SeeTohJJ.Backend.study.model.chain.ChainTemplate;
import com.SeeTohJJ.Backend.study.service.progress.impl.NodeGenerationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository
public class ChainTemplateDaoImpl implements ChainTemplateDao {

    private static final Logger logger = LoggerFactory.getLogger(ChainTemplateDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChainTemplateDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ChainTemplate> getChainTemplate(NodeGenerationServiceImpl.ChainType chainType){
        logger.info("Getting Chain Template");

        return jdbcTemplate.query(
                ChainTemplateConstant.GET_CHAIN_TEMPLATE,
                (rs, rowNum) -> {
                    ChainTemplate chainTemplate = new ChainTemplate();
                    chainTemplate.setOrderInChain(rs.getInt("order_in_chain"));
                    chainTemplate.setNodeType(rs.getString("node_type"));
                    chainTemplate.setContentSequence(rs.getInt("content_sequence"));
                    return chainTemplate;
                },
                chainType.name()
        );
    }


}
