package ru.kwuh.housevote.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

// Описание пользователя системы
@Data
@Document
public class Profile {
    @Id
    String id;
    @Email @NotNull @JsonIgnore
    String emailAddress;
    @NotNull @JsonIgnore
    String passwordHash;

//    SimpleGrantedAuthority accessLevel;

    @NotNull
    String firstName;
    @NotNull
    String lastName;
    String paternal;
    boolean isConfirmed = true; // наличие подтверждённой учётки на госуслугах

    public Profile(String emailAddress, String passwordHash, String firstName, String lastName, String paternal) {
        this.emailAddress = emailAddress;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.paternal = paternal;
    }

    //    @DBRef(lazy = true)
    @JsonIgnore
    List<String> ownedProperty;

    public List<String> getOwnedProperty() {
        if (ownedProperty == null) {
            ownedProperty = new ArrayList<>();
        }
        return ownedProperty;
    }

    public void addProperty(House house) throws House.DuplicateProfileException {
        if (!getOwnedProperty().contains(house.getId())) {
            ownedProperty.add(house.getId());
        } else throw new House.DuplicateProfileException();
    }
}
