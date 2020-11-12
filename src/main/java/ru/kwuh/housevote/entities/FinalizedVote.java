package ru.kwuh.housevote.entities;

import com.google.common.hash.Hashing;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FinalizedVote {
    @Id
    String id;
    @NotNull
    final Vote vote;
    String voteBodyHash;
    public FinalizedVote(Vote vote) {
        this.vote = vote;
        voteBodyHash = Hashing.sha256().hashString(
                this.vote.getMetadataHash()+this.vote.getQuestionsHash()+this.vote.getCombinedParticipantsHash(),
                StandardCharsets.UTF_8).toString();
    }
}
