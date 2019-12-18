package com.company;

import java.io.Serializable;
import java.security.spec.NamedParameterSpec;

public class User implements Serializable {
    private String name;
    private String login;
    private String password;
    public User(){
        name = "";
        login = "";
        password = "";
    }
    public void add(String name, String login, String password){
        this.name = name;
        this.login = login;
        this.password = password;
    }
    public boolean enter(String log, String pas){
        return (log.compareTo(login) == 0 && pas.compareTo(password) == 0);
    }
    public String getName(){ return name; }
    public String getLogin(){return login;}
}
