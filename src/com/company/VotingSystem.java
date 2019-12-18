package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class VotingSystem implements Serializable {
    private ArrayList<Elector> electors = new ArrayList<Elector>();
    private Admin admin = new Admin();
    private Voting currentVoting = new Voting();
    private int currentUser = -2;

    public int getCurrentUser(){ return currentUser; }
    public void enter(String login, String password) throws Exception{
        if (admin.enter(login, password)) {
            currentUser = -1;
            return;
        }
        if (electors.isEmpty()) throw new Exception("В системе нет ни одного зарегистрированного пользователя");
        for (Elector elector: electors){
            if (elector.enter(login, password))
            {
                currentUser = electors.indexOf(elector);
                break;
            }
            else currentUser = -2;
        }
        if (currentUser == -2) throw new Exception("Логин или пароль были введены неверно");
    }
    public void addUser(String name, String login, String password, String repeatPass) throws Exception {
        if (login == "" || password == "") throw new Exception("Логин или пароль не могут быть пустыми");
        if (password.compareTo(repeatPass) != 0) throw new Exception("Пароли не совпадают");
        if (login.compareTo(admin.getLogin()) == 0) throw new Exception("Данный логин уже занят");
        if (electors.isEmpty()){
            electors.add(new Elector(name, login, password));
        }
        else{
            for (Elector elector: electors){
                if (elector.getLogin().compareTo(login) == 0) throw new Exception("Данный логин уже занят");
            }
            electors.add(new Elector(name, login, password));
        }
    }
    public void exit(){ currentUser = -2; }
    public void addCandidate(String name) throws Exception{
        if (name.compareTo("") == 0) throw new Exception("Введите имя кандидата");
        if (currentUser != -1) throw new Exception("Вы не можете добавлять кандидатов");
        currentVoting.addCandidate(name);
    }
    public void changeCandidate(int i, String name) throws Exception{
        currentVoting.changeCandidate(i, name);
    }
    public void deleteCandidate(int i) throws Exception{
        currentVoting.deleteCandidate(i);
    }
    public String getCandidateList(){
        return "Список кондидатов:\n" + currentVoting.getTitle();
    }
    public void giveVoice(int voice) throws Exception{
        //Scanner in = new Scanner(System.in);
        if (currentVoting.isNoCandidates()) throw new Exception("В списках еще нет кандидатов");
        if (currentUser < 0) throw new Exception("Вы не можете голосовать");
        if (electors.get(currentUser).isVoted()) throw new Exception("Вы уже отдали свой голос");
        //System.out.print("Список кондидатов:\n" + currentVoting.getTitle() + "Выберите кондидата: ");
        currentVoting.addVoise(voice);
        electors.get(currentUser).vote();
    }
    public String getWinner() throws Exception {
        return currentVoting.getWinner();
    }
}
