package ru.kwuh.housevote.controllers;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.*;
import ru.kwuh.housevote.repository.*;
import ru.kwuh.housevote.services.VotingService;

import javax.validation.Valid;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
//@CrossOrigin("*")
@RequestMapping(path = "/voting", produces = "application/json;charset=UTF-8")
public class VotingController {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    FinalizedVoteRepository finalizedVoteRepository;

    @Autowired
    GlobalsRepository globalsRepository;

    @Autowired
    VotingService votingService;

    @GetMapping(value = {"/all", "/all/{page}"})
    public Iterable<Vote> showAllVoting(@PathVariable(name = "page", required = false) Integer pageNumber) {
        PageRequest page;
        if (pageNumber != null)
            page = PageRequest.of(pageNumber, 25, Sort.by("postingDate").descending());
        else
            page = PageRequest.of(0, 25, Sort.by("postingDate").descending());
        return voteRepository.findAll(page).getContent();
    }

    @GetMapping(value = {"/results/all", "/results/all/{page}"})
    public Iterable<FinalizedVote> showAllFinalizedVotes(@PathVariable(name = "page", required = false) Integer pageNumber) {
        PageRequest page;
        if (pageNumber != null)
            page = PageRequest.of(pageNumber, 25, Sort.by("postingDate").descending());
        else
            page = PageRequest.of(0, 25, Sort.by("postingDate").descending());
        return finalizedVoteRepository.findAll(page).getContent();
    }

    @GetMapping(value = {"/all/current", "/all/current/{page}"})
    public Iterable<Vote> showCurrentVoting(@PathVariable(name = "page", required = false) Integer pageNumber) {
        PageRequest page;
        if (pageNumber != null)
            page = PageRequest.of(pageNumber, 25, Sort.by("postingDate").descending());
        else
            page = PageRequest.of(0, 25, Sort.by("postingDate").descending());
        return voteRepository.findAll(page)
                .getContent()
                .stream()
                .filter(Vote::isCurrentlyUsed)
                .collect(Collectors.toList());
    }

    @GetMapping("/available")
    public Iterable<Vote> showAvailableVoting(Principal principal) {
        return votingService.getAvailableVotesForUser(principal);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Vote addNewVoting(@RequestBody @Valid Vote vote) {
        return votingService.addNewVoting(vote);
    }

    @DeleteMapping("/id/{voteId}")
    public Vote deleteVote(@PathVariable(name = "voteId") String voteId) {
        return votingService.deleteVote(voteId);
    }


    @GetMapping("/results/id/{voteId}")
    public FinalizedVote showCompletedVote(@PathVariable(name = "voteId") String voteId) {
        return votingService.showCompletedVote(voteId);
    }

    @GetMapping("/id/{voteId}")
    public Iterable<Question> showVoteQuestions(@PathVariable(name = "voteId") String voteId) {
        return votingService.showVoteQuestions(voteId);
    }

    @PutMapping("/id/{voteId}/activate")
    public Vote activateVote(@PathVariable(name = "voteId") String voteId) {
        return votingService.activateVote(voteId);
    }

    @PostMapping("/id/{voteId}/finish")
    public FinalizedVote endVote(@PathVariable(name = "voteId") String voteId) {
        return votingService.endVote(voteId);
    }

    @PutMapping(value = "/id/{voteId}", consumes = "application/json")
    public Iterable<Response> respondToQuestion(
            @PathVariable(name = "voteId") String voteId,
            @RequestBody List<Response> response
    ) throws Exception {
        return votingService.respondToQuestion(voteId, response);
    }

    @GetMapping("/results/report/id/{voteId}")
    public Object getVoteResults(@PathVariable(name = "voteId") String voteId) {
        return votingService.getVoteResults(voteId);
    }
}
