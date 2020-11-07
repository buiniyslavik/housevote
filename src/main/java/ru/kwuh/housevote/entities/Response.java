package ru.kwuh.housevote.entities;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Response {
    @NotNull
    long userId;
    @NotNull
    int questionNumber;
    Question.Answers answer;
}
