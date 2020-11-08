package ru.kwuh.housevote.entities;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonRootName("question")
public class Question {
    @NotNull
    final String questionText;
    @NotNull
    final boolean needsTwoThirds;
    enum Answers { YES, NO, ABSTAINED }
}
