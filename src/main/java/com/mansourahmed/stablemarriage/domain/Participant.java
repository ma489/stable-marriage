package com.mansourahmed.stablemarriage.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Participant {

    private Integer id;
    private List<Integer> preferences = new ArrayList<>();
    private boolean engaged = false;

    public List<Integer> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Integer> preferences) {
        this.preferences = preferences;
    }

    public boolean isEngaged() {
        return engaged;
    }

    public void setEngaged(boolean engaged) {
        this.engaged = engaged;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}