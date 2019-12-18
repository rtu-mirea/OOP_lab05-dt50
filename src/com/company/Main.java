package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            File f = new File("dataBase.txt");
            if (!f.exists()) {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dataBase.txt"));
                out.writeObject(new VotingSystem());
                out.close();
            }
        }
        catch (Exception e){}

        //Main window
        JFrame frame = new JFrame("Main window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel nameLabel = new JLabel("Имя");
        JTextField nameText = new JTextField();
        JLabel loginLabel = new JLabel("Логин");
        JTextField loginText = new JTextField();
        JLabel passwordLabel = new JLabel("Пароль");
        JTextField passwordText = new JTextField();
        JLabel repPasswordLabel = new JLabel("Повтор пароля");
        JTextField repPasswordText = new JTextField();
        JButton logButton = new JButton("Войти");
        JButton regButton = new JButton("Зарегистрироваться");

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridLayout(10, 1));
        panel.add(nameLabel);
        panel.add(nameText);
        panel.add(loginLabel);
        panel.add(loginText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(repPasswordLabel);
        panel.add(repPasswordText);
        panel.add(logButton);
        panel.add(regButton);

        nameLabel.setVisible(false);
        nameText.setVisible(false);
        repPasswordLabel.setVisible(false);
        repPasswordText.setVisible(false);

        frame.setPreferredSize(new Dimension(200, 300));
        frame.pack();
        frame.setVisible(true);

        //окно админа

        JFrame adminWindow = new JFrame("Админ");
        JTextArea list = new JTextArea("Список кандидатов:\n");
        JScrollPane scrollList = new JScrollPane(list,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JLabel candidateNumberLabel = new JLabel("Номер кандидата");
        JTextField candidateNamber = new JTextField();
        JLabel candidateLabel = new JLabel("Кандидат");
        JTextField candidateText = new JTextField();
        JButton addCandidate = new JButton("Добавить кандидата");
        JButton changeCandidate = new JButton("Заменить кандидата");
        JButton delete = new JButton("Удалить кандидата");
        JButton end = new JButton("Завершение голосования");
        JButton exite = new JButton("Выйти из системы");

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(10, 1));
        adminWindow.add(adminPanel);
        adminPanel.add(scrollList);
        adminPanel.add(candidateNumberLabel);
        adminPanel.add(candidateNamber);
        adminPanel.add(candidateLabel);
        adminPanel.add(candidateText);
        adminPanel.add(addCandidate);
        adminPanel.add(changeCandidate);
        adminPanel.add(delete);

        adminPanel.add(end);
        adminPanel.add(exite);

        adminWindow.pack();

        // окно пользователя

        JFrame userWindow = new JFrame("Пользователь");
        //userWindow.setSize(200, 500);
        JTextArea candidateList = new JTextArea("Список кандидатов:\n");
        JScrollPane scroll = new JScrollPane(candidateList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JLabel voiceLabel = new JLabel("Номер кандидата");
        JTextField voiceText = new JTextField();
        JButton addVoise = new JButton("Отдать голос");
        JButton exite1 = new JButton("Выйти из системы");

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(5, 1));
        userWindow.add(userPanel);
        //userPanel.add(candidateList);
        userPanel.add(scroll);
        userPanel.add(voiceLabel);
        userPanel.add(voiceText);
        userPanel.add(addVoise);
        userPanel.add(exite1);

        userWindow.pack();


        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameLabel.isVisible()){
                    try {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream("dataBase.txt"));
                        VotingSystem votingSystem = (VotingSystem)in.readObject();
                        in.close();

                        votingSystem.addUser(nameText.getText(), loginText.getText(),
                                passwordText.getText(), repPasswordText.getText());
                        JOptionPane.showMessageDialog(frame, "Успешная регистрация");

                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dataBase.txt"));
                        out.writeObject(votingSystem);
                        out.close();

                        nameLabel.setVisible(false);
                        nameText.setVisible(false);
                        repPasswordLabel.setVisible(false);
                        repPasswordText.setVisible(false);
                        nameText.setText("");
                        loginText.setText("");
                        passwordText.setText("");
                        repPasswordText.setText("");
                    }
                    catch (Exception ex){
                        JOptionPane.showMessageDialog(frame, ex.getMessage());
                    }
                }
                else {
                    nameLabel.setVisible(true);
                    nameText.setVisible(true);
                    repPasswordLabel.setVisible(true);
                    repPasswordText.setVisible(true);
                }
            }
        });
        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameLabel.isVisible()){
                    nameLabel.setVisible(false);
                    nameText.setVisible(false);
                    repPasswordLabel.setVisible(false);
                    repPasswordText.setVisible(false);
                }
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("dataBase.txt"));
                    VotingSystem votingSystem = (VotingSystem)in.readObject();
                    in.close();

                    votingSystem.enter(loginText.getText(), passwordText.getText());

                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dataBase.txt"));
                    out.writeObject(votingSystem);
                    out.close();

                    if (votingSystem.getCurrentUser() == -1) {
                        adminWindow.setVisible(true);
                        frame.setVisible(false);
                        list.setText(votingSystem.getCandidateList());
                    }
                    else if (votingSystem.getCurrentUser() > -1){
                        candidateList.setText(votingSystem.getCandidateList());
                        userWindow.setVisible(true);
                        frame.setVisible(false);
                    }

                    loginText.setText("");
                    passwordText.setText("");
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        addCandidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("dataBase.txt"));
                VotingSystem votingSystem = (VotingSystem)in.readObject();
                in.close();

                votingSystem.addCandidate(candidateText.getText());
                JOptionPane.showMessageDialog(adminWindow, "Кандидат успешно добавлен");
                list.setText(votingSystem.getCandidateList());


                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dataBase.txt"));
                out.writeObject(votingSystem);
                out.close();

                candidateText.setText("");
            }
                catch (Exception ex){
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
        });
        changeCandidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("dataBase.txt"));
                    VotingSystem votingSystem = (VotingSystem)in.readObject();
                    in.close();

                    votingSystem.changeCandidate(Integer.parseInt(candidateNamber.getText()), candidateText.getText());
                    JOptionPane.showMessageDialog(adminWindow, "Кандидат успешно изменен");

                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dataBase.txt"));
                    out.writeObject(votingSystem);
                    out.close();

                    list.setText(votingSystem.getCandidateList());
                    candidateText.setText("");
                    candidateNamber.setText("");
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("dataBase.txt"));
                    VotingSystem votingSystem = (VotingSystem)in.readObject();
                    in.close();

                    votingSystem.deleteCandidate(Integer.parseInt(candidateNamber.getText()));
                    JOptionPane.showMessageDialog(adminWindow, "Кандидат успешно удален");

                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dataBase.txt"));
                    out.writeObject(votingSystem);
                    out.close();

                    list.setText(votingSystem.getCandidateList());
                    candidateText.setText("");
                    candidateNamber.setText("");
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("dataBase.txt"));
                    VotingSystem votingSystem = (VotingSystem)in.readObject();
                    in.close();

                    JOptionPane.showMessageDialog(adminWindow, "Голосование завершено, победитель:" +
                            votingSystem.getWinner());

                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dataBase.txt"));
                    out.writeObject(votingSystem);
                    out.close();

                    candidateText.setText("");
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
        exite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("dataBase.txt"));
                    VotingSystem votingSystem = (VotingSystem)in.readObject();
                    in.close();

                    votingSystem.exit();

                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dataBase.txt"));
                    out.writeObject(votingSystem);
                    out.close();

                    frame.setVisible(true);
                    adminWindow.setVisible(false);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        addVoise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("dataBase.txt"));
                    VotingSystem votingSystem = (VotingSystem)in.readObject();
                    in.close();

                    votingSystem.giveVoice(Integer.parseInt(voiceText.getText()));

                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dataBase.txt"));
                    out.writeObject(votingSystem);
                    out.close();

                    voiceText.setText("");
                    candidateText.setText("");
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
        exite1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("dataBase.txt"));
                    VotingSystem votingSystem = (VotingSystem)in.readObject();
                    in.close();

                    votingSystem.exit();

                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dataBase.txt"));
                    out.writeObject(votingSystem);
                    out.close();

                    frame.setVisible(true);
                    userWindow.setVisible(false);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });


    }
}
