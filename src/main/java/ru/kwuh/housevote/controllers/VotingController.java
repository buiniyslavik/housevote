package ru.kwuh.housevote.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.Vote;
import ru.kwuh.housevote.repository.VoteRepository;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RestController
//@CrossOrigin("*")
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
    public Iterable<Vote> showCurrentVoting() {
        PageRequest page = PageRequest.of(0, 25, Sort.by("postingDate").descending());
        return voteRepository.findAll(page).getContent().stream().filter(Vote::isCurrentlyUsed).collect(Collectors.toList());
    }

    @PostMapping(value = "/add",  consumes = "application/json")
    public Vote addNewVoting(@RequestBody Vote vote) {
        return voteRepository.save(vote);
    }
}
