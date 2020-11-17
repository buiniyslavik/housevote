package ru.kwuh.housevote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kwuh.housevote.entities.ChatMessage;

public interface ChatRepository extends MongoRepository<ChatMessage, String> {
}
