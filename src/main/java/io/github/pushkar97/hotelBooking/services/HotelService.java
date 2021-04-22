package io.github.pushkar97.hotelBooking.services;

import io.github.pushkar97.hotelBooking.errors.EntityNotFoundException;
import io.github.pushkar97.hotelBooking.helpers.HotelSearchSpecification;
import io.github.pushkar97.hotelBooking.models.Facilities;
import io.github.pushkar97.hotelBooking.models.Hotel;
import io.github.pushkar97.hotelBooking.models.SortType;
import io.github.pushkar97.hotelBooking.repositories.HotelRepository;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    private final AddressService addressService;

    private final UserService userService;

    private final HotelSearchSpecification hotelSearchSpecification;

    HotelService(HotelRepository hotelRepository,
                 AddressService addressService,
                 UserService userService,
                 HotelSearchSpecification hotelSearchSpecification){
        this.hotelRepository = hotelRepository;
        this.addressService = addressService;
        this.userService = userService;
        this.hotelSearchSpecification = hotelSearchSpecification;
    }

    public Page<Hotel> all(Pageable pageable
            , @RequestParam Optional<String> city
            , @RequestParam Optional<LocalDate> checkInDate
            , @RequestParam Optional<LocalDate> checkOutDate
            , @RequestParam Optional<Integer> rooms
            , @RequestParam Optional<Integer> adults
            , @RequestParam Optional<Integer> children
            , @RequestParam Optional<Byte> rating
            , @RequestParam Optional<List<Facilities>> facilities
            , @RequestParam Optional<SortType> sortBy) {
        var specification = hotelSearchSpecification.search(city, checkInDate, checkOutDate, rooms, adults, children, rating, facilities, sortBy);
        return hotelRepository.findAll(specification, pageable);
    }

    public Hotel one(Long id){
        return hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Hotel.class, "Id", id.toString()));
    }

    public Hotel save(Hotel hotel, Authentication authentication){
        var username =  (String) authentication.getPrincipal();
        hotel.setOwner(userService.findByUsername(username));
        hotel.setAddress(addressService.save(hotel.getAddress()));
        return hotelRepository.save(hotel);
    }

    public Hotel update(Hotel hotel, Long id){
        var hotelToUpdate = this.one(id);
        hotel.setId(id);
        hotel.setAddress(addressService.update(hotel.getAddress(), hotelToUpdate.getAddress().getId()));
        return hotelRepository.save(hotel);
    }

    public void delete(Long id){
        var hotel = one(id);
        hotelRepository.delete(hotel);
    }

    public Hotel setRating(Long hotelId, Float rating) {
        var hotel = one(hotelId);
        hotel.setRating(rating);
        return hotelRepository.save(hotel);
    }

    public Integer availableRoomsForDate(Hotel hotel, LocalDate checkInDate, LocalDate checkOutDate) {
        return hotel.getTotalRooms() - hotelRepository.bookedRoomsCountForHotelForDate(hotel, checkInDate, checkOutDate).orElse(0);
    }

}