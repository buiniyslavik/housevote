package ru.kwuh.housevote.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

@Data
@Document
public class House {
    @Id
    BigInteger id;
    @NotNull
    String address;
    @DBRef
    List<User> registeredUsers;
}
