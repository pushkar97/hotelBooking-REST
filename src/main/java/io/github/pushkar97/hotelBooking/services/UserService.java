package io.github.pushkar97.hotelBooking.services;

import io.github.pushkar97.hotelBooking.errors.EntityNotFoundException;
import io.github.pushkar97.hotelBooking.models.User;
import io.github.pushkar97.hotelBooking.repositories.UserRepository;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<User> all(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public User one(Long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()));
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(User.class, "username", username));
    }
    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDisabled(true);
        user.setExpired(false);
        user.setLocked(false);
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public void enableUser(Long id) {
        var user = one(id);
        user.setDisabled(false);
        userRepository.save(user);
    }
}
