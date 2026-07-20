package com.SeeTohJJ.Backend.study.constant;

public class ChainTemplateConstant {

    public static final String GET_CHAIN_TEMPLATE = """
            SELECT order_in_chain, node_type, content_sequence
            FROM study_chain_template
            WHERE chain_type = ? and is_active = true
            ORDER BY order_in_chain
            """;
}
