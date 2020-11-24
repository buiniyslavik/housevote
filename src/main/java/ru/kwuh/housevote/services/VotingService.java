package ru.kwuh.housevote.services;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kwuh.housevote.entities.*;
import ru.kwuh.housevote.repository.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class VotingService {
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

    public Iterable<Vote> getAvailableVotesForUser(Principal principal) {
        String currentUserEmail = principal.getName();
        List<String> currentUserProperty = profileRepository.findUserByEmailAddress(currentUserEmail).getOwnedProperty();
        List<Vote> availableVotes = Lists.newArrayList(voteRepository.findByHouseIdIn(currentUserProperty))
                .stream().filter(Vote::isCurrentlyUsed).collect(Collectors.toList());
        return availableVotes;
    }

    public Vote addNewVoting(Vote vote) {
        List<OnlineVoter> registeredHouseVoters = new ArrayList<>();
        if (houseRepository.findById(vote.getHouseId()).isPresent()) {
            House houseOnVote = houseRepository.findById(vote.getHouseId()).get();
            houseOnVote.getRegisteredProfiles().forEach(user -> registeredHouseVoters.add(new OnlineVoter(user.getId())));
            vote.setOnlineParticipants(registeredHouseVoters);
            vote.setOfflineVoters(new ArrayList<>());
            //       Vote voteWithId = voteRepository.save(vote);
            return voteRepository.save(vote);
        }
        return null; // todo NoHouseFoundException
    }

    public Vote deleteVote(String voteId) {
        if (voteRepository.findById(voteId).isPresent()) {
            Vote v = voteRepository.findById(voteId).get();
            voteRepository.delete(v);
            return v;
        }
        return null; // todo NoSuchVoteException
    }

    public Iterable<Question> showVoteQuestions(String voteId) {
        if (voteRepository.findById(voteId).isPresent())
            return voteRepository.findById(voteId).get().getQuestionList();
        else return null; // todo NoSuchVoteException
    }

    public Vote activateVote(String voteId) {
        if (voteRepository.findById(voteId).isPresent()) {
            Vote v = voteRepository.findById(voteId).get();
            v.activateVote();
            return voteRepository.save(v);
        }
        return null; // todo NoSuchVoteException
    }

    public FinalizedVote endVote(String voteId) {
        if (voteRepository.findById(voteId).isPresent()) {
            Vote vote = voteRepository.findById(voteId).get();
            vote.finalizeAnswers();
            Globals globals;
            if (globalsRepository.findById("config").isPresent()) {
                globals = globalsRepository.findById("config").get();
                if (globals.getLastFinalizedVoteId() != null) {
                    vote.setPrevBlockHash(finalizedVoteRepository
                            .findById(globals.getLastFinalizedVoteId())
                            .get()
                            .getVoteBodyHash());
                } else {
                    vote.setPrevBlockHash(Hashing.sha256().hashString("START", StandardCharsets.UTF_8).toString());
                }
            } else {
                vote.setPrevBlockHash(Hashing.sha256().hashString("START", StandardCharsets.UTF_8).toString());
                globals = new Globals();
            }


            FinalizedVote finalizedVote = new FinalizedVote(vote);
            voteRepository.delete(vote);

            FinalizedVote finalizedVote1 = finalizedVoteRepository.save(finalizedVote);
            globals.setLastFinalizedVoteId(finalizedVote1.getId());
            globalsRepository.save(globals);
            return finalizedVote1;
        }
        return null; // todo NoSuchVoteException
    }

    public Iterable<Response> respondToQuestion(
            String voteId,
            List<Response> responses
    ) throws Exception {
        Vote vote; // find current vote
        if (voteRepository.findById(voteId).isPresent())
            vote = voteRepository.findById(voteId).get();
        else return null;


        OnlineVoter currentVoter;
        try { // match current user with a registered voter
            currentVoter = vote.getOnlineParticipants()
                    .stream()
                    .filter(onlineVoter ->
                            onlineVoter.getProfileId().equals(responses.get(0).getProfileId()))
                    .findFirst().get(); //TODO make it less horrible
        } catch (NoSuchElementException nsex) {
            return null;
        }

        if (currentVoter.getResponses().isEmpty()) {
            currentVoter.setResponses(responses);
        } else throw new Exception("Already voted here!"); // todo swap for AlreadyVotedException
        voteRepository.save(vote);
        return currentVoter.getResponses();
    }

    // ----- finalized votes -----

    public FinalizedVote showCompletedVote(String voteId) {
        if (finalizedVoteRepository.findById(voteId).isPresent())
            return finalizedVoteRepository.findById(voteId).get();
        else return null; // todo NoSuchFinalizedVoteException
    }

    public Object getVoteResults(@PathVariable(name = "voteId") String voteId) {
        if (finalizedVoteRepository.findById(voteId).isPresent()) {
            FinalizedVote finalizedVote = finalizedVoteRepository.findById(voteId).get();
            Vote vote = finalizedVote.getVote();
            List<Question> questions = vote.getQuestionList();
            List<OnlineVoter> onlineVoters = vote.getOnlineParticipants();
            List<ResponseCounter> rc = new ArrayList<>();
            // indian mode on
            for (int currentQuestion = 0; currentQuestion < questions.size(); currentQuestion++) {
                rc.add(new ResponseCounter(currentQuestion));
                for (int currentVoter = 0; currentVoter < onlineVoters.size(); currentVoter++) {
                    for (
                            int currentResp = 0;
                            currentResp < onlineVoters
                                    .get(currentVoter)
                                    .getResponses()
                                    .size();
                            currentResp++) {
                        Response resp = onlineVoters.get(currentVoter).getResponses().get(currentResp);
                        if (resp.getQuestionNumber() != currentQuestion)
                            continue;
                        else {

                            if (resp.getAnswer().equals(Question.Answers.YES))
                                rc.get(currentQuestion).yes++;
                            if (resp.getAnswer().equals(Question.Answers.NO))
                                rc.get(currentQuestion).no++;
                            if (resp.getAnswer().equals(Question.Answers.ABSTAINED))
                                rc.get(currentQuestion).abstained++;

                        }
                    }
                }
            }
            Integer totalVoters = onlineVoters.size();
            rc.forEach(responseCounter -> {
                if(responseCounter.yes > totalVoters/2)
                    responseCounter.setHasPassed(true);
                if(responseCounter.no > totalVoters/2)
                    responseCounter.setHasPassed(false);
            });
            return rc;
        }

        return null;
    }
}