package ru.kwuh.housevote.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.House;
import ru.kwuh.housevote.entities.Profile;
import ru.kwuh.housevote.repository.HouseRepository;
import ru.kwuh.housevote.repository.ProfileRepository;

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

    @PostMapping(path = "/add", consumes = "application/json;charset=UTF-8")
    public House addHouse(@RequestBody @Valid House house) {
//        House house1 = house;
        return houseRepository.save(house);
    }

    @GetMapping(value = {"/all", "/all/{page}"})
    public Iterable<House> showAllHouses(@PathVariable(name = "page", required = false) Integer pageNumber) {

        PageRequest page;
        if(pageNumber != null)
            page = PageRequest.of(pageNumber, 25, Sort.by("postingDate").descending());
        else
            page = PageRequest.of(0,25, Sort.by("postingDate").descending());
        return houseRepository.findAll(page).getContent();
    }

    @PostMapping(value = "/id/{houseId}/adduser")
    public House addProfileToHouse(
            @PathVariable(name = "houseId") String houseId,
            @RequestBody @NotNull String userId
    ) throws House.DuplicateProfileException {
        if (houseRepository.findById(houseId).isPresent() && profileRepository.findById(userId).isPresent()) {
            House currentHouse = houseRepository.findById(houseId).get();
            Profile currentProfile = profileRepository.findById(userId).get();

            currentHouse.addRegisteredProfile(currentProfile);
            currentProfile.addProperty(currentHouse);
            profileRepository.save(currentProfile);
            return houseRepository.save(currentHouse);
        }
        return null;
    }
}
