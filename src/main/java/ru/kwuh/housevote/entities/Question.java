package ru.kwuh.housevote.entities;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Question {
    @NotNull
    final String questionText;
    @NotNull
    final boolean needsTwoThirds;
    enum Answers { YES, NO, ABSTAINED }
}
