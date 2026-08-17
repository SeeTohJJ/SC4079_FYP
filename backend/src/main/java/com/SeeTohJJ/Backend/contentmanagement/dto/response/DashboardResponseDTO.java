package com.SeeTohJJ.Backend.contentmanagement.dto.response;

public class DashboardResponseDTO {

    private int userCount;
    private int adminCount;
    private int topicCount;
    private int subtopicCount;
    private int lessonCount;
    private int quizCount;
    private int studyChainCount;

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getAdminCount() {
        return adminCount;
    }

    public void setAdminCount(int adminCount) {
        this.adminCount = adminCount;
    }

    public int getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
    }

    public int getSubtopicCount() {
        return subtopicCount;
    }

    public void setSubtopicCount(int subtopicCount) {
        this.subtopicCount = subtopicCount;
    }

    public int getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(int lessonCount) {
        this.lessonCount = lessonCount;
    }

    public int getQuizCount() {
        return quizCount;
    }

    public void setQuizCount(int quizCount) {
        this.quizCount = quizCount;
    }

    public int getStudyChainCount() {
        return studyChainCount;
    }

    public void setStudyChainCount(int studyChainCount) {
        this.studyChainCount = studyChainCount;
    }
}
