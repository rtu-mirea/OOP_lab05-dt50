package com.company;

import java.io.Serializable;

public class Elector extends User implements Serializable {
    private Boolean voted;

    public Elector(String name, String login, String password){
        add(name, login, password);
        voted = false;
    }
    public Boolean isVoted(){return voted;}
    public void vote(){voted = true;}
}
