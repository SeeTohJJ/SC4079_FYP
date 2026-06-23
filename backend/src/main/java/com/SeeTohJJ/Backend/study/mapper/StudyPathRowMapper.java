package com.SeeTohJJ.Backend.study.mapper;


import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.model.UserNodeProgress;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudyPathRowMapper implements RowMapper<UserNodeProgress> {

    @Override
    public UserNodeProgress mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {

        UserNodeProgress progress = new UserNodeProgress();

        progress.setNodeId(
                rs.getString("node_id")
        );

        progress.setNodeType(
                StudyNode.NodeType.valueOf(rs.getString("node_type"))
        );

        progress.setPositionIndex(
                rs.getInt("position_index")
        );

        progress.setCompleted(
                rs.getBoolean("is_completed")
        );

        progress.setUnlocked(
                rs.getBoolean("is_unlocked")
        );

        return progress;
    }
}

