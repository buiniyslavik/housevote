package ru.kwuh.housevote.entities;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

// Описание пользователя системы
@Data
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
