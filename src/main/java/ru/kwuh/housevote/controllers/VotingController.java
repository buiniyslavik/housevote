package ru.kwuh.housevote.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.Question;
import ru.kwuh.housevote.entities.Vote;
import ru.kwuh.housevote.repository.VoteRepository;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
//@CrossOrigin("*")
@RequestMapping(path = "/voting", produces = "application/json")
public class VotingController {

    @Autowired
    VoteRepository voteRepository;

    @GetMapping(value = {"/all", "/all/{page}"})
    public Iterable<Vote> showAllVoting(@PathVariable(name = "page", required = false) Integer pageNumber) {
        PageRequest page = PageRequest.of(
                Objects.requireNonNullElse(
                        pageNumber, 0),
                25,
                Sort.by("postingDate").descending());
        return voteRepository.findAll(page).getContent();
    }

    @GetMapping(value = {"/current", "/current/{page}"})
    public Iterable<Vote> showCurrentVoting(@PathVariable(name = "page", required = false) Integer pageNumber) {
        PageRequest page = PageRequest.of(
                Objects.requireNonNullElse(
                        pageNumber, 0),
                25,
                Sort.by("postingDate").descending());
        return voteRepository.findAll(page)
                .getContent()
                .stream()
                .filter(Vote::isCurrentlyUsed)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Vote addNewVoting(@RequestBody @Valid Vote vote) {
        return voteRepository.save(vote);
    }

    // -------------------------------------------------

    @GetMapping("/{voteId}")
    public Iterable<Question> showVoteQuestions(@PathVariable(name = "voteId") BigInteger voteId) {
        if (voteRepository.findById(voteId).isPresent())
            return voteRepository.findById(voteId).get().getQuestionList();
        else return null;
    }
}
