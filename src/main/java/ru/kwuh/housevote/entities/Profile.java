package ru.kwuh.housevote.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

// Описание пользователя системы
@Data
@Document
public class Profile {
    @Id
    BigInteger id;

    @Email
    String emailAddress;
    String passwordHash;

    SimpleGrantedAuthority accessLevel;

    @NotNull
    String firstName;
    @NotNull
    String lastName;
    String paternal;
    boolean isConfirmed = true; // наличие подтверждённой учётки на госуслугах
//    @DBRef(lazy = true)
    @JsonIgnore
    List<BigInteger> ownedProperty;

    public List<BigInteger> getOwnedProperty() {
        if(ownedProperty==null) {
            ownedProperty = new ArrayList<>();
        }
        return ownedProperty;
    }

    public void addProperty(House house) throws House.DuplicateProfileException {
        if(!getOwnedProperty().contains(house.getId())) {
            ownedProperty.add(house.getId());
        }
        else throw new House.DuplicateProfileException();
    }
}
