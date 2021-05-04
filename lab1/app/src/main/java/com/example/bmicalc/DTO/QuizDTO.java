package com.example.bmicalc.DTO;

import java.util.ArrayList;

public class QuizDTO {

    private final String question;
    private final ArrayList<String> answers;
    private final String correctAnswer;

    public QuizDTO(String question, ArrayList<String> answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
