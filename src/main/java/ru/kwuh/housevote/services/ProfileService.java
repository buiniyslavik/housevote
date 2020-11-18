package ru.kwuh.housevote.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kwuh.housevote.entities.House;
import ru.kwuh.housevote.entities.Profile;
import ru.kwuh.housevote.entities.SignupRequest;
import ru.kwuh.housevote.repository.HouseRepository;
import ru.kwuh.housevote.repository.ProfileRepository;

import javax.validation.Valid;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    HouseRepository houseRepository;

    public Profile createNewProfile(SignupRequest signupRequest) {
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
        return null; // todo ProfileAlreadyExistsException
    }

    public Iterable<Profile> showAllProfiles(Integer pageNumber) {
        PageRequest page;
        if (pageNumber != null)
            page = PageRequest.of(pageNumber, 25, Sort.by("postingDate").descending());
        else
            page = PageRequest.of(0, 25, Sort.by("postingDate").descending());
        return profileRepository.findAll(page).getContent();
    }

    public Profile getCurrentUserInfo() {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return profileRepository.findUserByEmailAddress(currentEmail);
    }

    public Iterable<House> getCurrentUserProperty() {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<String> propIds = profileRepository.findUserByEmailAddress(currentEmail).getOwnedProperty();
        return houseRepository.findAllById(propIds);
    }

}
