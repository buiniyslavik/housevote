package ru.kwuh.housevote.entities;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Data
public class OnlineVoter implements Voter {
    @NotNull
    final String profileId;
    List<Response> responses;
    String responseHash;

    @Override
    public List<Response> getResponses() {
        if(responses==null) responses = new ArrayList<>();
        return responses;
    }

    public void hashResponses(String questionsHash) {
        if(responses != null)
        responseHash = Hashing.sha256().hashString(
                responses.toString(), StandardCharsets.UTF_8
        ).toString();
    }
}
