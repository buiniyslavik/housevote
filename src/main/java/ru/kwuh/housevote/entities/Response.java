package ru.kwuh.housevote.entities;

import lombok.Data;

@Data
public class Response {
    long userId;
    int questionNumber;
    Question.Answers answer;
}
