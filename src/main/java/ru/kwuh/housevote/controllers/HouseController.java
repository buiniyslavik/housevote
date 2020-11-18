package ru.kwuh.housevote.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.House;
import ru.kwuh.housevote.entities.Profile;
import ru.kwuh.housevote.repository.HouseRepository;
import ru.kwuh.housevote.repository.ProfileRepository;
import ru.kwuh.housevote.services.HouseService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Objects;

@RestController
@RequestMapping(path = "/house", produces = "application/json")
public class HouseController {
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    HouseService houseService;

    @PostMapping(path = "/add", consumes = "application/json;charset=UTF-8")
    public House addHouse(@RequestBody @Valid House house) {
        return houseService.addHouse(house);
    }

    @GetMapping(value = {"/all", "/all/{page}"})
    public Iterable<House> showAllHouses(@PathVariable(name = "page", required = false) Integer pageNumber) {
        return houseService.showAllHouses(pageNumber);
    }

    @PostMapping(value = "/id/{houseId}/adduser")
    public House addProfileToHouse(
            @PathVariable(name = "houseId") String houseId,
            @RequestBody @NotNull String userId
    ) throws House.DuplicateProfileException {
        return houseService.addProfileToHouse(houseId, userId);
    }
}
