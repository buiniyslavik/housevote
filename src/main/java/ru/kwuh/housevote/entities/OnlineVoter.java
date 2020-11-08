package ru.kwuh.housevote.entities;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Data
public class OnlineVoter implements Voter {
    @NotNull
    BigInteger userId;
    List<Response> responses;
    HashCode responseHash = null;
    @Override
    public List<Response> getResponses() {
        return responses;
    }
    public void hashResponses() {
        responseHash = Hashing.sha256().hashString(
                responses.toString(), StandardCharsets.UTF_8
        );
    }
}
