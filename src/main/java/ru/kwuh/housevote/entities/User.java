package ru.kwuh.housevote.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Описание пользователя системы
@Data
@Document
public class User {
    @Id
    BigInteger id;
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
}
