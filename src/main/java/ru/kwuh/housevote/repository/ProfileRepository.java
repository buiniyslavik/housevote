package ru.kwuh.housevote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kwuh.housevote.entities.Profile;

import java.math.BigInteger;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    Profile findUserByEmailAddress(String email);
    Boolean existsUserByEmailAddress(String email);
}
