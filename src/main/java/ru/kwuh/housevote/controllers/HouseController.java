package ru.kwuh.housevote.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.House;
import ru.kwuh.housevote.entities.User;
import ru.kwuh.housevote.entities.Vote;
import ru.kwuh.housevote.repository.HouseRepository;
import ru.kwuh.housevote.repository.ProfileRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/house", produces = "application/json")
public class HouseController {
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    ProfileRepository profileRepository;

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

    @PostMapping(value = "/{houseId}/adduser")
    public House addUserToHouse(@PathVariable(name = "houseId") BigInteger houseId, @RequestBody @NotNull BigInteger userId) {
        if (houseRepository.findById(houseId).isPresent() && profileRepository.findById(userId).isPresent()) {
            House currentHouse = houseRepository.findById(houseId).get();
            User currentUser = profileRepository.findById(userId).get();
            if (currentHouse.getRegisteredUsers().stream().noneMatch(u -> u.equals(currentUser))) {
                currentHouse.getRegisteredUsers().add(currentUser);
                currentUser.getOwnedProperty().add(houseId);
                profileRepository.save(currentUser);
                return houseRepository.save(currentHouse);
            } else {
                return null; // need to throw an ex here
            }
        }
        return null;
    }
}
