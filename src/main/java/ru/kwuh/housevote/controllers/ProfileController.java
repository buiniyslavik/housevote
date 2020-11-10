package ru.kwuh.housevote.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.kwuh.housevote.entities.Profile;
import ru.kwuh.housevote.repository.ProfileRepository;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping(path = "/profile", produces = "application/json")
public class ProfileController {
    @Autowired
    ProfileRepository profileRepository;

    @PostMapping(path = "/create", consumes = "application/json")
    public Profile createNewProfile(@RequestBody @Valid Profile profile) {
        return profileRepository.save(profile);
    }

    @GetMapping(value = {"/all", "/all/{page}"})
    public Iterable<Profile> showAllProfiles(@PathVariable(name = "page", required = false) Integer pageNumber) {
        PageRequest page = PageRequest.of(
                Objects.requireNonNullElse(
                        pageNumber, 0),
                25,
                Sort.by("postingDate").descending());
        return profileRepository.findAll(page).getContent();
    }
}
