package ru.kwuh.housevote.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kwuh.housevote.entities.Profile;
import ru.kwuh.housevote.entities.SignupRequest;
import ru.kwuh.housevote.repository.ProfileRepository;

import java.util.Collections;
import java.util.List;

@Component
public class MongoUserDetailsService implements UserDetailsService {
    @Autowired
    ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Profile profile = profileRepository.findUserByEmailAddress(s);
        if (profile == null) throw new UsernameNotFoundException("Profile with that email does not exist");
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("user")
        );
        return new User(
                profile.getEmailAddress(),
                profile.getPasswordHash(),
                authorities);
    }

  /*  public Profile createProfile(SignupRequest signupRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Profile profile = new Profile(
                signupRequest.getEmail(),
                bCryptPasswordEncoder.encode(signupRequest.getRawPassword()),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getPaternal()
        );
        return profile;
    }

   */

}
