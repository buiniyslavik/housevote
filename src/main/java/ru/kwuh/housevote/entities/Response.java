package ru.kwuh.housevote.entities;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class Response {
    @NotNull
    BigInteger userId;
    @NotNull
    int questionNumber;
    Question.Answers answer;
}
