package ru.kwuh.housevote.entities;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Описывает собрание собственников
@Document
@Data
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Vote {
    @Id // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    String id;
//    String jsonId;
    //BigInteger voteId;
    @NotNull
    //@Indexed
    BigInteger houseId; // дом, в котором проходит голосование
    LocalDateTime postingDate; // дата создания голосования
    LocalDateTime voteStartDate;
    LocalDateTime voteEndDate;
    List<Question> questionList;
    //   String question;
    List<OnlineVoter> onlineParticipants;
    List<OfflineVoter> offlineVoters;

    private Vote() {
        postingDate = LocalDateTime.now();
        voteStartDate = postingDate.plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        voteEndDate = voteStartDate.plusHours(24);
        //voteId = id;
    }

    boolean isCurrentlyUsed = false;
/*
    public Vote(Long houseId) {
        this.houseId = houseId;
        postingDate = LocalDateTime.now();
        voteStartDate = postingDate.plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        voteEndDate = voteStartDate.plusHours(24);
    }
    public Vote(Long houseId, String question) {
        this.houseId = houseId;
        this.questionList = new ArrayList<>();
        this.questionList.add(new Question(question, false));
        postingDate = LocalDateTime.now();
        voteStartDate = postingDate.plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        voteEndDate = voteStartDate.plusHours(24);
    } */

    @PersistenceConstructor
    public Vote(BigInteger houseId, LocalDateTime postingDate, LocalDateTime voteStartDate,
                LocalDateTime voteEndDate, boolean isCurrentlyUsed, List<Question> questionList) {
        this.houseId = houseId;
        this.postingDate = postingDate;
        this.voteStartDate = voteStartDate;
        this.voteEndDate = voteEndDate;
        this.isCurrentlyUsed = isCurrentlyUsed;
        this.questionList = questionList;
    }

    public void addQuestion(String question, boolean needsTwoThirds) {
        questionList.add(new Question(question, needsTwoThirds));
    }

    public void removeQuestion(int questionIndex) throws VoteIsInUseException {
        if (!isCurrentlyUsed)
            questionList.remove(questionIndex);
        else throw new VoteIsInUseException(questionIndex);
    }

    public void finalizeAnswers() {
        onlineParticipants.forEach(onlineParticipant ->
                onlineParticipant.hashResponses());
    }

    private static class VoteIsInUseException extends Exception {
        private final int questionIndex;
        @Getter
        private final String message;

        public VoteIsInUseException(int QuestionIndex) {
            questionIndex = QuestionIndex;
            message = String.format("Attempt to delete question %d that is being voted on", questionIndex);
        }
    }
}
