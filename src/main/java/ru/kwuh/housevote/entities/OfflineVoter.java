package ru.kwuh.housevote.entities;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

// Описывает собственника, голосующего "на бумаге"
@Data
public class OfflineVoter implements Voter {
    @NotNull
    String fullName;
    @NotNull
    String passportNumber;
    List<Response> responses;

    public List<Response> getResponses() {
        return responses;
    }
}
