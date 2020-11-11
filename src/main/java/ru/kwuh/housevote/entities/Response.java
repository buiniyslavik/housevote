package ru.kwuh.housevote.entities;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class Response {
    @NotNull
    String profileId;
    @NotNull
    int questionNumber;
    Question.Answers answer;
}
