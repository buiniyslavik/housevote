package ru.kwuh.housevote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kwuh.housevote.entities.Vote;

import java.math.BigInteger;

public interface VoteRepository extends MongoRepository<Vote, BigInteger> {
}
