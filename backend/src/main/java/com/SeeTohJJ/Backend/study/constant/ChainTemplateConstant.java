package com.SeeTohJJ.Backend.study.constant;

public class ChainTemplateConstant {

    public static final String GET_CHAIN_TEMPLATE = """
            SELECT order_in_chain, node_type, content_sequence
            FROM study_chain_template
            WHERE chain_type = ? and is_active = true
            ORDER BY order_in_chain
            """;

    public static final String GET_ALL_CHAIN_TEMPLATES = """
            SELECT template_id, chain_type, order_in_chain, node_type, content_sequence, last_updated, is_active
            FROM study_chain_template
            WHERE is_active = true
            ORDER BY chain_type, order_in_chain
            """;

    public static final String GET_CHAIN_TEMPLATES_BY_ID = """
            SELECT template_id, chain_type, order_in_chain, node_type, content_sequence, last_updated, is_active
            FROM study_chain_template
            WHERE template_id = ?
            """;

    public static final String INSERT_CHAIN_TEMPLATE = """
            INSERT INTO study_chain_template (template_id, chain_type, order_in_chain, node_type, content_sequence, last_updated, is_active)
            VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, true)
            """;

    public static final String UPDATE_CHAIN_TEMPLATE_BY_ID = """
            UPDATE study_chain_template
            SET chain_type = ?, order_in_chain = ?, node_type = ?, content_sequence = ?, last_updated = CURRENT_TIMESTAMP
            WHERE template_id = ?
            """;

    public static final String SET_CHAIN_TEMPLATE_INACTIVE = """
            UPDATE study_chain_template
            SET is_active = false
            WHERE template_id = ?
            """;

    public static final String SET_CHAIN_TEMPLATE_ACTIVE = """
            UPDATE study_chain_template
            SET is_active = true
            WHERE template_id = ?
            """;

    public static final String GET_ACTIVE_TEMPLATE_COUNT = """
            SELECT COUNT(DISTINCT chain_type)
            FROM study_chain_template
            WHERE is_active = true
            """;
}
