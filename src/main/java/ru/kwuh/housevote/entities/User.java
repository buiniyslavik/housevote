package ru.kwuh.housevote.entities;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
// Описание пользователя системы
@Data
public class User {
    @Id
    final long userId;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    String paternal;
    boolean isConfirmed = true; // наличие подтверждённой учётки на госуслугах
}
