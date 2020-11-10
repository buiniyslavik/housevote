package ru.kwuh.housevote.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.kwuh.housevote.entities.Profile;
import ru.kwuh.housevote.repository.ProfileRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class MongoUserDetailsService implements UserDetailsService {
    @Autowired
    ProfileRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Profile profile = userRepository.findUserByEmailAddress(s);
        if(profile ==null) throw new UsernameNotFoundException("Profile with that email does not exist");
        List<SimpleGrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("user")
        );
        return new User(
                profile.getEmailAddress(),
                profile.getPasswordHash(),
                authorities);
    }
}
