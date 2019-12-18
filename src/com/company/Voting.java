package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Voting implements Serializable {
    private String title = "";
    private ArrayList<Candidate> candidates = new ArrayList<Candidate>();

    //public void setTitle(String title){ this.title = title + '\n';}
    public String getTitle(){return title;}
    public void addCandidate(String name){
        candidates.add(new Candidate(name));
        title += "[" + (candidates.size() - 1) + "] " +  name + '\n';
    }
    public void changeCandidate(int i, String name) throws Exception{
        if (i > candidates.size()) throw new Exception("В списке нет кандидата с таким номером");
        candidates.get(i).setName(name);
        //title = "Список кандидатов:\n";
        title = "";
        for (int index = 0; index < candidates.size(); index++){
            title += "[" + index + "] " +  candidates.get(index).getName() + '\n';
        }
    }
    public void deleteCandidate(int i) throws Exception{
        if (i > candidates.size()) throw new Exception("В списке нет кандидата с таким номером");
        candidates.remove(i);
        //title = "Список кандидатов:\n";
        title = "";

        for (int index = 0; index < candidates.size(); index++){
            title += "[" + index + "] " +  candidates.get(index).getName() + '\n';
        }
    }
    public void addVoise(int indexOfCandidate) throws Exception{
        if (indexOfCandidate > candidates.size())
            throw new Exception("Кандидата под таким номером нет");
        candidates.get(indexOfCandidate).addVoice();
    }
    public String getWinner() throws Exception{
        int max = 0;
        String winner = "No one";
        for (Candidate candidate: candidates) {
            if (max < candidate.getVotes()){
                max = candidate.getVotes();
                winner = candidate.getName();
            }
        }
        if (winner.compareTo("No one") == 0) throw new Exception("Голосование еще не было проведено");
        return winner;
    }
    public Boolean isNoCandidates(){return candidates.isEmpty();}

}
