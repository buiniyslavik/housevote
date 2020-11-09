package ru.kwuh.housevote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kwuh.housevote.entities.User;

import java.math.BigInteger;

public interface ProfileRepository extends MongoRepository<User, BigInteger> {
}
