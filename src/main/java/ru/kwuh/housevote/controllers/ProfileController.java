package ru.kwuh.housevote.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kwuh.housevote.entities.User;
import ru.kwuh.housevote.repository.ProfileRepository;

@RestController
@RequestMapping(path = "/profile", produces = "application/json")
public class ProfileController {
    @Autowired
    ProfileRepository profileRepository;

    @PostMapping(path = "/create", consumes = "application/json")
    public User createNewProfile(@RequestBody User profile) {
        return profileRepository.save(profile);
    }
}
