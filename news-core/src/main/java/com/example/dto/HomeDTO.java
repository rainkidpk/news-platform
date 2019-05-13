package com.example.dto;

import java.io.Serializable;
import java.util.List;

public class HomeDTO implements Serializable {

    private static final long serialVersionUID = 9124021155169472422L;

    private List<NewDTO> topViews;
    private List<NewDTO> topNewDates;

    public List<NewDTO> getTopViews() {
        return topViews;
    }

    public void setTopViews(List<NewDTO> topViews) {
        this.topViews = topViews;
    }

    public List<NewDTO> getTopNewDates() {
        return topNewDates;
    }

    public void setTopNewDates(List<NewDTO> topNewDates) {
        this.topNewDates = topNewDates;
    }
}
