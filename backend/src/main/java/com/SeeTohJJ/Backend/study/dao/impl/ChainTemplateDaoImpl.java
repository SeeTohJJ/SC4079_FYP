package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.contentmanagement.dto.response.ChainTemplateResponseDTO;
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
        logger.info("Starting getChainTemplate");

        try {
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
        catch (Exception e) {
            logger.error("Error fetching chain template for chain type {}: {}", chainType, e.getMessage());
            return Collections.emptyList();
        }
    }


    @Override
    public List<ChainTemplateResponseDTO> getAllChainTemplates(){
        logger.info("Starting getAllChainTemplates");

        return jdbcTemplate.query(
                ChainTemplateConstant.GET_ALL_CHAIN_TEMPLATES,
                (rs, rowNum) -> {
                    ChainTemplateResponseDTO dto = new ChainTemplateResponseDTO();
                    dto.setChainTemplateId(rs.getInt("template_id"));
                    dto.setChainType(rs.getString("chain_type"));
                    dto.setOrderInChain(rs.getInt("order_in_chain"));
                    dto.setNodeType(rs.getString("node_type"));
                    dto.setContentSequence(rs.getInt("content_sequence"));
                    java.sql.Timestamp timestamp = rs.getTimestamp("last_updated");
                    if (timestamp != null) {
                        dto.setLastUpdated(timestamp.toLocalDateTime());
                    }
                    dto.setActive(rs.getBoolean("is_active"));
                    return dto;
                }
        );
    }

    @Override
    public ChainTemplateResponseDTO findById(int templateId){
        logger.info("Starting findById");

        return jdbcTemplate.queryForObject(
                ChainTemplateConstant.GET_CHAIN_TEMPLATES_BY_ID,
                (rs, rowNum) -> {
                    ChainTemplateResponseDTO dto = new ChainTemplateResponseDTO();
                    dto.setChainTemplateId(rs.getInt("template_id"));
                    dto.setChainType(rs.getString("chain_type"));
                    dto.setOrderInChain(rs.getInt("order_in_chain"));
                    dto.setNodeType(rs.getString("node_type"));
                    dto.setContentSequence(rs.getInt("content_sequence"));
                    java.sql.Timestamp timestamp = rs.getTimestamp("last_updated");
                    if (timestamp != null) {
                        dto.setLastUpdated(timestamp.toLocalDateTime());
                    }
                    dto.setActive(rs.getBoolean("is_active"));
                    return dto;
                },
                templateId
        );
    }

    @Override
    public void createChainTemplate(int templateId, String chainType, int orderInChain, String nodeType, int contentSequence){
        logger.info("Starting createChainTemplate");

        jdbcTemplate.update(
                ChainTemplateConstant.INSERT_CHAIN_TEMPLATE,
                templateId,
                chainType,
                orderInChain,
                nodeType,
                contentSequence
        );
    }

    @Override
    public void updateChainTemplate(int templateId, String chainType, int orderInChain, String nodeType, int contentSequence){
        logger.info("Starting updateChainTemplate");

        jdbcTemplate.update(
                ChainTemplateConstant.UPDATE_CHAIN_TEMPLATE_BY_ID,
                chainType,
                orderInChain,
                nodeType,
                contentSequence,
                templateId
        );
    }

    @Override
    public void setChainTemplateInactive(int templateId){
        logger.info("Starting setChainTemplateInactive");

        jdbcTemplate.update(
                ChainTemplateConstant.SET_CHAIN_TEMPLATE_INACTIVE,
                templateId
        );
    }

    @Override
    public void setChainTemplateActive(int templateId){
        logger.info("Starting setChainTemplateActive");

        jdbcTemplate.update(
                ChainTemplateConstant.SET_CHAIN_TEMPLATE_ACTIVE,
                templateId
        );
    }

    @Override
    public int getActiveCount(){
        logger.info("Starting getActiveCount");

        Integer count = jdbcTemplate.queryForObject(
                ChainTemplateConstant.GET_ACTIVE_TEMPLATE_COUNT,
                Integer.class
        );

        return (count != null) ? count : 0;
    }

}
