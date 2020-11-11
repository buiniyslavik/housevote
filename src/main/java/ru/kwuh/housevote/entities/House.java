package ru.kwuh.housevote.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class House {
    @Id
    String id;
//    String jsonId;
    @NotNull
    String address;
    @DBRef(lazy = true)
    List<Profile> registeredProfiles;
    Integer totalOwners = 0;

    public void addRegisteredProfile(Profile profile) throws DuplicateProfileException {
        getRegisteredProfiles(); //make sure its not null
        if(!getRegisteredProfiles().contains(profile)) {
            getRegisteredProfiles().add(profile);
            totalOwners++;
        }
        else throw new DuplicateProfileException();
    }

    public List<Profile> getRegisteredProfiles() {
        if(registeredProfiles == null) {
            registeredProfiles = new ArrayList<>();
        }
        return registeredProfiles;
    }

    @NoArgsConstructor
    public static class DuplicateProfileException extends Exception {

    }
}
