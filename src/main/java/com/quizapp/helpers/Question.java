package com.quizapp.helpers;

public class Question {
    public String question;
    public String[] options = new String[4];
    public String answer;

    public Question(String question , String[] options , String answer){
        this.question = question;
        this.options = options;
        this.answer = answer;
    }
}