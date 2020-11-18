package ru.kwuh.housevote.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kwuh.housevote.entities.House;
import ru.kwuh.housevote.entities.Profile;
import ru.kwuh.housevote.repository.HouseRepository;
import ru.kwuh.housevote.repository.ProfileRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class HouseService {
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    ProfileRepository profileRepository;

    public House addHouse(House house) {
        return houseRepository.save(house);
    }

    public Iterable<House> showAllHouses(Integer pageNumber) {
        PageRequest page;
        if(pageNumber != null)
            page = PageRequest.of(pageNumber, 25, Sort.by("postingDate").descending());
        else
            page = PageRequest.of(0,25, Sort.by("postingDate").descending());
        return houseRepository.findAll(page).getContent();
    }

    public House addProfileToHouse(
            String houseId,
            String userId
    ) throws House.DuplicateProfileException {
        if (houseRepository.findById(houseId).isPresent() && profileRepository.findById(userId).isPresent()) {
            House currentHouse = houseRepository.findById(houseId).get();
            Profile currentProfile = profileRepository.findById(userId).get();

            currentHouse.addRegisteredProfile(currentProfile);
            currentProfile.addProperty(currentHouse);
            profileRepository.save(currentProfile);
            return houseRepository.save(currentHouse);
        }
        return null; // todo exceptions
    }
}
