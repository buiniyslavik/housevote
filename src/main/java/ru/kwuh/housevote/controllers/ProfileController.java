package ru.kwuh.housevote.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.House;
import ru.kwuh.housevote.entities.Profile;
import ru.kwuh.housevote.entities.SignupRequest;
import ru.kwuh.housevote.repository.HouseRepository;
import ru.kwuh.housevote.repository.ProfileRepository;
import ru.kwuh.housevote.services.ProfileService;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(path = "/profile", produces = "application/json;charset=UTF-8")
public class ProfileController {
    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    ProfileService profileService;

    @PostMapping(path = "/create", consumes = "application/json")
    public Profile createNewProfile(@RequestBody @Valid SignupRequest signupRequest) {
       return profileService.createNewProfile(signupRequest);
    }

    @GetMapping(value = {"/all", "/all/{page}"})
    public Iterable<Profile> showAllProfiles(@PathVariable(name = "page", required = false) Integer pageNumber) {
        return profileService.showAllProfiles(pageNumber);
    }

    @GetMapping("/me")
    public Profile getMyInfo() {
        return profileService.getCurrentUserInfo();
    }
    @GetMapping("/me/property")
    public Iterable<House> getMyProperty() {
        return profileService.getCurrentUserProperty();
    }
}
