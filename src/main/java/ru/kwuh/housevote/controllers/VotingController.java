package ru.kwuh.housevote.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kwuh.housevote.entities.Vote;
import ru.kwuh.housevote.repository.VoteRepository;

import java.util.List;

@RestController
@RequestMapping(path="/voting", produces = "application/json")
public class VotingController {

    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/all")
    public Iterable<Vote> showAllVoting() {
        PageRequest page = PageRequest.of(0, 25, Sort.by("postingDate").descending());
        return voteRepository.findAll(page).getContent();
    }

    @GetMapping("/current")
    public Iterable<Vote> ShowCurrentVoting() {
        PageRequest page = PageRequest.of(0, 25, Sort.by("postingDate").descending());
        List<Vote> voteList = voteRepository.findAll(page).getContent();
        voteList.removeIf(v -> !v.isCurrentlyUsed()); // remove inactive votes
        return voteList;
    }
}
