package com.SeeTohJJ.Backend.study.dto;

import com.SeeTohJJ.Backend.study.dto.option.DecisionOptionDTO;

import java.util.List;

public class DecisionNodeDTO extends StudyNodeDTO {

    private String scenario;

    private List<DecisionOptionDTO> options;

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public List<DecisionOptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<DecisionOptionDTO> options) {
        this.options = options;
    }
}
