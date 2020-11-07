package ru.kwuh.housevote.entities;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

// Описывает собрание собственников
@Data
public class Vote {
    @Id
    final long voteId;
    @NotNull
    final long houseId; // дом, в котором проходит голосование
    @NotNull
    final LocalDateTime postingDate; // дата создания голосования
    final LocalDateTime voteStartDate = postingDate.plusDays(7);
    final LocalDateTime voteEndDate = voteStartDate.plusHours(24);
    List<Question> questionList;
    List<User> onlineParticipants;
    List<OfflineVoter> offlineVoters;

    boolean isCurrentlyUsed = false;

    public void addQuestion(String question, boolean needsTwoThirds) {
        questionList.add(new Question(question, needsTwoThirds));
    }

    public void removeQuestion(int questionIndex) throws VoteIsInUseException {
        if(!isCurrentlyUsed)
        questionList.remove(questionIndex);
        else throw new VoteIsInUseException(questionIndex);
    }

    @RequiredArgsConstructor
    private static class VoteIsInUseException extends Exception {
        private final int questionIndex;
        @Getter private final String message = String.format("Attempt to delete question %d that is being voted on", questionIndex);
    }
}
