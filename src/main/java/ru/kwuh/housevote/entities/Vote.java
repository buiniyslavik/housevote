package ru.kwuh.housevote.entities;

import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

// Описывает собрание собственников
@Data
public class Vote {
    @Id
    long voteId;
    long houseId; // дом, в котором проходит голосование
    LocalDateTime postingDate; // дата создания голосования
    LocalDateTime voteStartDate = postingDate.plusDays(7);
    LocalDateTime voteEndDate = voteStartDate.plusHours(24);
    List<Question> questionList;
    List<User> onlineParticipants;
    List<OfflineVoter> offlineVoters;
}
