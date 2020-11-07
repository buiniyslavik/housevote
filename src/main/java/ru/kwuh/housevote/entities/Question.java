package ru.kwuh.housevote.entities;

import lombok.Data;

@Data
public class Question {
    final String questionText;
    final boolean needsTwoThirds;
    enum Answers { YES, NO, ABSTAINED }
}
