package ru.kwuh.housevote.entities;

import lombok.Data;

@Data
public class ResponseCounter {
    public Integer yes = 0;
    public Integer no = 0;
    public Integer abstained = 0;
    public Boolean hasPassed = false;
}
