package com.mansourahmed.stablemarriage.domain;

public class Acceptor extends Participant {

    public Acceptor(Integer id) {
        setId(id);
    }

    @Override
    public String toString() {
        return "Acceptor{" +
                "id=" + getId() +
                ", preferences=" + getPreferences() +
                ", engaged=" + isEngaged() +
                '}';
    }

}