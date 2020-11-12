package ru.kwuh.housevote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kwuh.housevote.entities.Vote;

import java.math.BigInteger;
import java.util.List;

public interface VoteRepository extends MongoRepository<Vote, String> {
    Iterable<Vote> findByHouseIdIn(Iterable<String> iterable);
}
