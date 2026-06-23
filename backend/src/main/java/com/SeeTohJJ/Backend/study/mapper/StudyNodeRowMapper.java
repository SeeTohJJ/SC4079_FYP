package com.SeeTohJJ.Backend.study.mapper;

import com.SeeTohJJ.Backend.study.model.StudyNode;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudyNodeRowMapper implements RowMapper<StudyNode> {

    @Override
    public StudyNode mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {

        StudyNode node = new StudyNode();

        node.setNodeId(
                rs.getString("node_id")
        );

        node.setType(
                StudyNode.NodeType.valueOf(
                        rs.getString("type").toUpperCase()
                )
        );

        node.setOrderIndex(
                rs.getInt("order_index")
        );

        return node;
    }


}
