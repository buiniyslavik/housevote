package ru.kwuh.housevote.entities;

import lombok.Data;

import java.util.List;

@Data
public class VoteResults {
    Integer totalVotersRegistered;
    List<Question> questionList;
}
