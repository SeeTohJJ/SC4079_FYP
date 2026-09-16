package com.SeeTohJJ.Backend.ai.dto;

public class QuizGenerationContent {

    private String employment_status;
    private Integer age;
    private String income;
    private String country;

    private String topic;
    private String subtopic;

    private Double masteryScore;
    private Double eloRating;

    public String getEmployment_status() {
        return employment_status;
    }

    public void setEmployment_status(String employment_status) {
        this.employment_status = employment_status;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubtopic() {
        return subtopic;
    }

    public void setSubtopic(String subtopic) {
        this.subtopic = subtopic;
    }

    public Double getMasteryScore() {
        return masteryScore;
    }

    public void setMasteryScore(Double masteryScore) {
        this.masteryScore = masteryScore;
    }

    public Double getEloRating() {
        return eloRating;
    }

    public void setEloRating(Double eloRating) {
        this.eloRating = eloRating;
    }
}
