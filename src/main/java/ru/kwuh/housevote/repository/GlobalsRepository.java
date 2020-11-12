package ru.kwuh.housevote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kwuh.housevote.entities.Globals;

public interface GlobalsRepository extends MongoRepository<Globals, String> {
}
