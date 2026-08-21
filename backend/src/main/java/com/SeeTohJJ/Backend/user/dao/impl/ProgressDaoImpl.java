package com.SeeTohJJ.Backend.user.dao.impl;

import com.SeeTohJJ.Backend.study.constant.StudyPathConstant;
import com.SeeTohJJ.Backend.user.dao.ProgressDao;
import com.SeeTohJJ.Backend.user.dto.CompletedLessonResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProgressDaoImpl implements ProgressDao {

    private static final Logger logger = LoggerFactory.getLogger(ProgressDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public ProgressDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int getCompletedLessons(Long userId, String topicId) {
        logger.info("Starting getCompletedLessons");

        try {
            Integer count = jdbcTemplate.queryForObject(
                    StudyPathConstant.GET_COMPLETED_LESSONS_COUNT,
                    Integer.class,
                    userId,
                    topicId
            );

            return count != null ? count : 0;
        } catch (Exception e) {
            logger.error("Error retrieving completed lessons count: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public int getTotalLessons(String topicId) {
        logger.info("Starting getTotalLessons");

        try {
            Integer count = jdbcTemplate.queryForObject(
                    StudyPathConstant.GET_TOTAL_LESSONS_COUNT,
                    Integer.class,
                    topicId
            );
            return count != null ? count : 0;
        } catch (Exception e) {
            logger.error("Error retrieving total lessons count: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<CompletedLessonResponseDTO> getAllCompletedLessonsInfo(Long userId){
        logger.info("Starting getAllCompletedLessonsInfo for userId: {}", userId);

        try {
            return jdbcTemplate.query(
                    StudyPathConstant.GET_ALL_COMPLETED_LESSONS_INFO,
                    (rs, rowNum) -> new CompletedLessonResponseDTO(
                            rs.getString("node_id"),
                            rs.getString("title"),
                            rs.getString("topic_id")
                    ),
                    userId
            );
        } catch (Exception e) {
            logger.error("Error retrieving all completed lessons info for userId {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }

}
