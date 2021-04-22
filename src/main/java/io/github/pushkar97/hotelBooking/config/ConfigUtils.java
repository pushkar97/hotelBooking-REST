package io.github.pushkar97.hotelBooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ConfigUtils
{

    public static String SECRET = "password1@23";

    public static long EXPIRATION_TIME = 12 * 60 * 60 * 1000;//12 hours

    public static String HEADER_STRING = "Authorization";

    public static String TOKEN_PREFIX = "Bearer ";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}