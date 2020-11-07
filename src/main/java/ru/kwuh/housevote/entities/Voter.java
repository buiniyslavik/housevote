package ru.kwuh.housevote.entities;

import java.util.List;

interface Voter {
    List<Response> getResponses();
}
