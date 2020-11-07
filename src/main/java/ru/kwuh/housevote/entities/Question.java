package ru.kwuh.housevote.entities;

import lombok.Data;

@Data
public class Question {
    final String questionText;
    enum Answers { YES, NO, ABSTAINED }
}
