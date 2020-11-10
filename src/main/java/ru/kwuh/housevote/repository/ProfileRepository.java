package ru.kwuh.housevote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kwuh.housevote.entities.Profile;

import java.math.BigInteger;

public interface ProfileRepository extends MongoRepository<Profile, BigInteger> {
    Profile findUserByEmailAddress(String email);
}
