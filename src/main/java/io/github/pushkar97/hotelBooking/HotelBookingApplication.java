package io.github.pushkar97.hotelBooking;

import io.github.pushkar97.hotelBooking.models.User;
import io.github.pushkar97.hotelBooking.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HotelBookingApplication implements CommandLineRunner {

	UserRepository userRepository;
	PasswordEncoder passwordEncoder;

	HotelBookingApplication(UserRepository userRepository
							,PasswordEncoder passwordEncoder
	) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingApplication.class, args);
	}

	@Override
	public void run(String... args) {
		//preloading admin, user and hotel owner
		userRepository.save(new User(null, "admin@test.com", passwordEncoder.encode("password"),User.Role.ADMIN ,false, false, false,null));
		userRepository.save(new User(null, "user@test.com", passwordEncoder.encode("password"), User.Role.USER, false, false, false, null));
		userRepository.save(new User(null, "owner@test.com", passwordEncoder.encode("password"), User.Role.OWNER, false, false, false, null));
	}
}
