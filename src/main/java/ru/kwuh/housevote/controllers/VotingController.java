package ru.kwuh.housevote.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.*;
import ru.kwuh.housevote.repository.HouseRepository;
import ru.kwuh.housevote.repository.VoteRepository;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
//@CrossOrigin("*")
@RequestMapping(path = "/voting", produces = "application/json")
public class VotingController {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    HouseRepository houseRepository;

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
        List<OnlineVoter> registeredHouseVoters = new ArrayList<>();
        if(houseRepository.findById(vote.getHouseId()).isPresent()) {
            House houseOnVote = houseRepository.findById(vote.getHouseId()).get();
            houseOnVote.getRegisteredProfiles().forEach(user -> registeredHouseVoters.add(new OnlineVoter(user.getId())));
            vote.setOnlineParticipants(registeredHouseVoters);
            return voteRepository.save(vote);
        }
        return null;
    }

    @DeleteMapping("/{voteId}")
    public Vote deleteVote(@PathVariable(name = "voteId") BigInteger voteId) {
        if(voteRepository.findById(voteId).isPresent()){
            Vote v = voteRepository.findById(voteId).get();
            voteRepository.delete(v);
            return v;
        }
        else return null;
    }

    // -------------------------------------------------


    @GetMapping("/{voteId}")
    public Iterable<Question> showVoteQuestions(@PathVariable(name = "voteId") BigInteger voteId) {
        if (voteRepository.findById(voteId).isPresent())
            return voteRepository.findById(voteId).get().getQuestionList();
        else return null;
    }

    @PostMapping("/{voteId}/finish")
    public Vote endVote(@PathVariable(name = "voteId") BigInteger voteId) {
        if (voteRepository.findById(voteId).isPresent()) {
            Vote vote = voteRepository.findById(voteId).get();
            vote.finalizeAnswers();
            voteRepository.save(vote);
            return vote;
        }
        return null;
    }

    @PutMapping(value = "/{voteId}", consumes = "application/json")
    public Iterable<Response> respondToQuestion(
            @PathVariable(name="voteId") BigInteger voteId,
            @RequestBody Response response
    ) {
        Vote vote;
        if (voteRepository.findById(voteId).isPresent())
            vote = voteRepository.findById(voteId).get();
        else return null;
        OnlineVoter currentVoter;
        try {
                    currentVoter = vote.getOnlineParticipants()
                    .stream()
                    .filter(onlineVoter ->
                            onlineVoter.getProfileId().equals(response.getProfileId()))
                    .findFirst().get(); //TODO make it less horrible
        }
        catch(NoSuchElementException nsex) {
            return null;
        }
        currentVoter.getResponses().add(response);
        voteRepository.save(vote);
        return currentVoter.getResponses();
    }
}
