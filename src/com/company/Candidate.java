package com.company;

import java.io.Serializable;

public class Candidate implements Serializable {
    private String name = "";
    private int votes = 0;

    public Candidate(String name){
        this.name = name;
    }
    public void setName(String name){this.name = name;}
    public String getName(){ return name; }
    public void addVoice(){votes++;}
    public int getVotes(){ return votes; }
}
