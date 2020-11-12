package ru.kwuh.housevote.controllers;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.*;
import ru.kwuh.housevote.repository.FinalizedVoteRepository;
import ru.kwuh.housevote.repository.HouseRepository;
import ru.kwuh.housevote.repository.ProfileRepository;
import ru.kwuh.housevote.repository.VoteRepository;

import javax.validation.Valid;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
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

    @GetMapping(value = {"/all", "/all/{page}"})
    public Iterable<Vote> showAllVoting(@PathVariable(name = "page", required = false) Integer pageNumber) {
        PageRequest page;
        if (pageNumber != null)
            page = PageRequest.of(pageNumber, 25, Sort.by("postingDate").descending());
        else
            page = PageRequest.of(0, 25, Sort.by("postingDate").descending());
        return voteRepository.findAll(page).getContent();
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
        String currentUserEmail = principal.getName();
        List<String> currentUserProperty = profileRepository.findUserByEmailAddress(currentUserEmail).getOwnedProperty();
        return voteRepository.findByHouseIdIn(currentUserProperty);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Vote addNewVoting(@RequestBody @Valid Vote vote) {
        List<OnlineVoter> registeredHouseVoters = new ArrayList<>();
        if (houseRepository.findById(vote.getHouseId()).isPresent()) {
            House houseOnVote = houseRepository.findById(vote.getHouseId()).get();
            houseOnVote.getRegisteredProfiles().forEach(user -> registeredHouseVoters.add(new OnlineVoter(user.getId())));
            vote.setOnlineParticipants(registeredHouseVoters);
            vote.setOfflineVoters(new ArrayList<>());
            //       Vote voteWithId = voteRepository.save(vote);
            return voteRepository.save(vote);
        }
        return null;
    }

    @DeleteMapping("/id/{voteId}")
    public Vote deleteVote(@PathVariable(name = "voteId") String voteId) {
        if (voteRepository.findById(voteId).isPresent()) {
            Vote v = voteRepository.findById(voteId).get();
            voteRepository.delete(v);
            return v;
        } else return null;
    }

    // -------------------------------------------------


    @GetMapping("/id/{voteId}")
    public Iterable<Question> showVoteQuestions(@PathVariable(name = "voteId") String voteId) {
        if (voteRepository.findById(voteId).isPresent())
            return voteRepository.findById(voteId).get().getQuestionList();
        else return null;
    }

    @PutMapping("/id/{voteId}/activate")
    public Vote activateVote(@PathVariable(name = "voteId") String voteId) {
        if (voteRepository.findById(voteId).isPresent()) {
            Vote v = voteRepository.findById(voteId).get();
            v.activateVote();
            return voteRepository.save(v);
        }
        return null;
    }

    @PostMapping("/id/{voteId}/finish")
    public FinalizedVote endVote(@PathVariable(name = "voteId") String voteId) {
        if (voteRepository.findById(voteId).isPresent()) {
            Vote vote = voteRepository.findById(voteId).get();
            vote.finalizeAnswers();

            FinalizedVote lastVote = finalizedVoteRepository.findFinalizedVoteByIdIsLessThan(vote.getId());
            if (lastVote != null) {
                vote.setPrevBlockHash(lastVote.getVoteBodyHash());
            } else {
                vote.setPrevBlockHash(Hashing.sha256().hashString("START", StandardCharsets.UTF_8).toString());
            }

            voteRepository.save(vote);
            FinalizedVote finalizedVote = new FinalizedVote(vote);
            return finalizedVoteRepository.save(finalizedVote);
        }
        return null;
    }

    @PutMapping(value = "/id/{voteId}", consumes = "application/json")
    public Iterable<Response> respondToQuestion(
            @PathVariable(name = "voteId") String voteId,
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
        } catch (NoSuchElementException nsex) {
            return null;
        }
        currentVoter.getResponses().add(response);
        voteRepository.save(vote);
        return currentVoter.getResponses();
    }
}
