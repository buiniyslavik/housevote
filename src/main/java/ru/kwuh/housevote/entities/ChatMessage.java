package ru.kwuh.housevote.entities;

import lombok.Data;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Document
public class ChatMessage {
    @Id
    String id;
    @NotNull
    String profileName;
    @NotNull
    String message;
    @NotNull
    LocalDateTime postingDate;

    private ChatMessage(){
        postingDate = LocalDateTime.now();
    }

    @PersistenceConstructor
    public ChatMessage(String message, String profileName, LocalDateTime postingDate){
        this.postingDate = LocalDateTime.now();
        this.message = message;
        this.profileName = profileName;
    }
}
