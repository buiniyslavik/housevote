package ru.kwuh.housevote.entities;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

// Описывает собрание собственников
@Document
@Data
public class Vote {
    @Id //@GeneratedValue(strategy = GenerationType.AUTO)
    Long voteId;
    @NotNull @Indexed
    final long houseId; // дом, в котором проходит голосование
    @NotNull
    final LocalDateTime postingDate; // дата создания голосования
    final LocalDateTime voteStartDate;
    final LocalDateTime voteEndDate;
    List<Question> questionList;
    List<User> onlineParticipants;
    List<OfflineVoter> offlineVoters;

    boolean isCurrentlyUsed = false;

    public Vote(long HouseId, LocalDateTime PostingDate) {
        houseId = HouseId;
        postingDate = PostingDate;
        voteStartDate = postingDate.plusDays(7);
        voteEndDate = voteStartDate.plusHours(24);
    }
    public void addQuestion(String question, boolean needsTwoThirds) {
        questionList.add(new Question(question, needsTwoThirds));
    }

    public void removeQuestion(int questionIndex) throws VoteIsInUseException {
        if(!isCurrentlyUsed)
        questionList.remove(questionIndex);
        else throw new VoteIsInUseException(questionIndex);
    }

    private static class VoteIsInUseException extends Exception {
        private final int questionIndex;
        @Getter private final String message;
        public VoteIsInUseException(int QuestionIndex) {
            questionIndex = QuestionIndex;
            message = String.format("Attempt to delete question %d that is being voted on", questionIndex);
        }
    }
}
