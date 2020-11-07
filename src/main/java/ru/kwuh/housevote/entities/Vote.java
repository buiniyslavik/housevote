package ru.kwuh.housevote.entities;

import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

// Описывает собрание собственников
@Data
public class Vote {
    @Id
    final long voteId;
    final long houseId; // дом, в котором проходит голосование
    final LocalDateTime postingDate; // дата создания голосования
    final LocalDateTime voteStartDate = postingDate.plusDays(7);
    final LocalDateTime voteEndDate = voteStartDate.plusHours(24);
    List<Question> questionList;
    List<User> onlineParticipants;
    List<OfflineVoter> offlineVoters;

    boolean isCurrentlyUsed = false;

    public void addQuestion(String question) {
        questionList.add(new Question(question));
    }

    public void removeQuestion(int questionIndex) throws VoteIsInUseException {
        if(!isCurrentlyUsed)
        questionList.remove(questionIndex);
        else throw new VoteIsInUseException();
    }

    private static class VoteIsInUseException extends Exception {
    }
}
