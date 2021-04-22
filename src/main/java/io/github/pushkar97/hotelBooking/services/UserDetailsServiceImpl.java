package io.github.pushkar97.hotelBooking.services;

import io.github.pushkar97.hotelBooking.errors.EntityNotFoundException;
import io.github.pushkar97.hotelBooking.models.User;
import io.github.pushkar97.hotelBooking.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s).orElseThrow(() -> new EntityNotFoundException(User.class, "username", s));
        LOGGER.info(user.toString());
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .accountExpired(user.getExpired())
                .accountLocked(user.getLocked())
                .credentialsExpired(false)
                .disabled(user.getDisabled())
                .build();
    }
}
