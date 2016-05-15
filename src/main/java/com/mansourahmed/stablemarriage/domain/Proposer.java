package com.mansourahmed.stablemarriage.domain;

public class Proposer extends Participant {

    public Proposer(Integer id) {
        setId(id);
    }

    @Override
    public String toString() {
        return "Proposer{" +
                "id=" + getId() +
                ", preferences=" + getPreferences() +
                ", engaged=" + isEngaged() +
                '}';
    }

}