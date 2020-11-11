package ru.kwuh.housevote.entities;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SignupRequest {
    @NotNull
    String email;
    @NotNull
    String rawPassword;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    String paternal;
}
