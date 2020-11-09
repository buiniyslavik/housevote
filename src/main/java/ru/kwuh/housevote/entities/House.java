package ru.kwuh.housevote.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Document
public class House {
    @Id
    BigInteger id;
    @NotNull
    String address;
    @DBRef(lazy = true)
    List<User> registeredUsers;

    public List<User> getRegisteredUsers() {
        if(registeredUsers == null) {
            registeredUsers = new ArrayList<>();
        }
        return registeredUsers;
    }
}
