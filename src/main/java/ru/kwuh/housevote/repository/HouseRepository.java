package ru.kwuh.housevote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kwuh.housevote.entities.House;

import java.math.BigInteger;

public interface HouseRepository extends MongoRepository <House, String> {
}
