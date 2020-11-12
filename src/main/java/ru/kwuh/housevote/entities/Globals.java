package ru.kwuh.housevote.entities;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Globals {
    @Id
    String id = "config";
    Boolean initialized = false;
    String lastFinalizedVoteId;
}
