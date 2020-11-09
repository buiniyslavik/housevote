package ru.kwuh.housevote.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.House;
import ru.kwuh.housevote.entities.Vote;
import ru.kwuh.housevote.repository.HouseRepository;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping(path = "/house", produces = "application/json")
public class HouseController {
    @Autowired
    HouseRepository houseRepository;

    @PostMapping(path = "/add", consumes = "application/json")
    public House addHouse(@RequestBody @Valid House house) {
        return houseRepository.save(house);
    }

    @GetMapping(value = {"/all", "/all/{page}"})
    public Iterable<House> showAllHouses(@PathVariable(name = "page", required = false) Integer pageNumber) {
        PageRequest page = PageRequest.of(
                Objects.requireNonNullElse(
                        pageNumber, 0),
                25,
                Sort.by("postingDate").descending());
        return houseRepository.findAll(page).getContent();
    }
}
