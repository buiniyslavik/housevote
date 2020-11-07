package ru.kwuh.housevote.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kwuh.housevote.entities.Vote;
import ru.kwuh.housevote.repository.VoteRepository;

@RestController
@RequestMapping(path="/voting", produces = "application/json")
public class VotingController {

    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/all")
    public Iterable<Vote> showCurrentVoting() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("postingDate").descending());
        return voteRepository.findAll(page).getContent();
    }
}
