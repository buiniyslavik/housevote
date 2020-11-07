package ru.kwuh.housevote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kwuh.housevote.entities.Vote;

public interface VoteRepository extends MongoRepository<Vote, Long> {
}
