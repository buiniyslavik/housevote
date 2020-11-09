package ru.kwuh.housevote.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

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
    List<BigInteger> ownedProperty;
}
