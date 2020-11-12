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

    @PostMapping(path = "/create", consumes = "application/json")
    public Profile createNewProfile(@RequestBody @Valid SignupRequest signupRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!profileRepository.existsUserByEmailAddress(signupRequest.getEmail())) {
            Profile profile = new Profile(
                    signupRequest.getEmail(),
                    bCryptPasswordEncoder.encode(signupRequest.getRawPassword()),
                    signupRequest.getFirstName(),
                    signupRequest.getLastName(),
                    signupRequest.getPaternal()
            );
            //profile.setJsonId();
            return profileRepository.save(profile);
        }
        return null;
    }

    @GetMapping(value = {"/all", "/all/{page}"})
    public Iterable<Profile> showAllProfiles(@PathVariable(name = "page", required = false) Integer pageNumber) {
        PageRequest page;
        if (pageNumber != null)
            page = PageRequest.of(pageNumber, 25, Sort.by("postingDate").descending());
        else
            page = PageRequest.of(0, 25, Sort.by("postingDate").descending());
        return profileRepository.findAll(page).getContent();
    }

    @GetMapping("/me")
    public Profile getMyInfo() {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return profileRepository.findUserByEmailAddress(currentEmail);
    }
    @GetMapping("/me/property")
    public Iterable<House> getMyProperty() {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<String> propIds = profileRepository.findUserByEmailAddress(currentEmail).getOwnedProperty();
        return houseRepository.findAllById(propIds);
    }
}
