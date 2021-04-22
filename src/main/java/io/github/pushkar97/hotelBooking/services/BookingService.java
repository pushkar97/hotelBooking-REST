package io.github.pushkar97.hotelBooking.services;

import io.github.pushkar97.hotelBooking.errors.EntityNotFoundException;
import io.github.pushkar97.hotelBooking.errors.InvalidInputException;
import io.github.pushkar97.hotelBooking.models.Booking;
import io.github.pushkar97.hotelBooking.models.Hotel;
import io.github.pushkar97.hotelBooking.repositories.BookingRepository;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {

    BookingRepository bookingRepository;
    HotelService hotelService;
    UserService userService;
    BookingService(BookingRepository bookingRepository,
                   HotelService hotelService,
                   UserService userService) {
        this.bookingRepository = bookingRepository;
        this.hotelService = hotelService;
        this.userService = userService;
    }

    public Page<Booking> all(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public Booking one(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Booking.class, "Id", id.toString()));
    }

    public Booking save(Booking booking, Long hotelId, String username) {
        var hotel = hotelService.one(hotelId);
        if (booking.getRooms() > hotelService.availableRoomsForDate(hotel, booking.getCheckInDate(), booking.getCheckOutDate()))
            throw new InvalidInputException("Not enough rooms");
        booking.setHotel(hotel);
        booking.setUser(userService.findByUsername(username));
        booking.setBookingDate(LocalDate.now());
        return bookingRepository.save(booking);
    }

    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    public Float getHotelRating(Hotel hotel){
        return bookingRepository.ratingForHotel(hotel);
    }
}
