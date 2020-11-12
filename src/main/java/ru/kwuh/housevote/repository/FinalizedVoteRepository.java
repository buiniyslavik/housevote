package ru.kwuh.housevote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kwuh.housevote.entities.FinalizedVote;

public interface FinalizedVoteRepository extends MongoRepository<FinalizedVote, String> {
    public FinalizedVote findFinalizedVoteByIdIsLessThan(String id);
}
