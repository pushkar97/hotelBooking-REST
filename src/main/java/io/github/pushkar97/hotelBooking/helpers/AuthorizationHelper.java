package io.github.pushkar97.hotelBooking.helpers;

import io.github.pushkar97.hotelBooking.services.HotelService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AuthorizationHelper {

    HotelService hotelService;

    AuthorizationHelper(HotelService hotelService){
        this.hotelService = hotelService;
    }

    public boolean isOwnerOrAdmin(Authentication authentication) {
        return authentication
                .getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .anyMatch(a -> Arrays.asList("ADMIN", "OWNER").contains(a));
    }

    public boolean isCreatorOrAdmin(Authentication authentication, Long id) {
        return authentication.getPrincipal().equals(hotelService.one(id).getOwner().getUsername())
                || authentication.getAuthorities().stream().map(a -> a.getAuthority()).anyMatch(a -> a.equals("ADMIN"));
    }

    public boolean isAdmin(Authentication authentication){
        return authentication.getAuthorities().stream().map(a -> a.getAuthority()).anyMatch(a -> a.equals("ADMIN"));
    }
}
