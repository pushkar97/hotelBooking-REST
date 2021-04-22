package io.github.pushkar97.hotelBooking.controllers;

import io.github.pushkar97.hotelBooking.helpers.AuthorizationHelper;
import io.github.pushkar97.hotelBooking.modelAssemblers.BookingAssembler;
import io.github.pushkar97.hotelBooking.modelAssemblers.HotelAssembler;
import io.github.pushkar97.hotelBooking.models.Booking;
import io.github.pushkar97.hotelBooking.models.Facilities;
import io.github.pushkar97.hotelBooking.models.Hotel;
import io.github.pushkar97.hotelBooking.models.SortType;
import io.github.pushkar97.hotelBooking.services.BookingService;
import io.github.pushkar97.hotelBooking.services.HotelService;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/hotels")
public class HotelController {

    private final HotelAssembler hotelAssembler;
    private final HotelService hotelService;
    private final PagedResourcesAssembler<Hotel> pagedResourcesAssembler;
    private final BookingService bookingService;
    private final BookingAssembler bookingAssembler;
    public final AuthorizationHelper authorizationHelper;
    HotelController(HotelAssembler hotelAssembler,
                    HotelService hotelService,
                    PagedResourcesAssembler<Hotel> pagedResourcesAssembler,
                    BookingService bookingService,
                    BookingAssembler bookingAssembler,
                    AuthorizationHelper authorizationHelper) {
        this.hotelAssembler = hotelAssembler;
        this.hotelService = hotelService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.bookingService = bookingService;
        this.bookingAssembler = bookingAssembler;
        pagedResourcesAssembler.setForceFirstAndLastRels(true);
        this.authorizationHelper = authorizationHelper;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<PagedModel<EntityModel<Hotel>>> all(
            Pageable pageable
            , @RequestParam Optional<String> city
            , @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam Optional<LocalDate> checkInDate
            , @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam Optional<LocalDate> checkOutDate
            , @RequestParam Optional<Integer> rooms
            , @RequestParam Optional<Integer> adults
            , @RequestParam Optional<Integer> children
            , @RequestParam Optional<Byte> rating
            , @RequestParam Optional<List<Facilities>> facilities
            , @RequestParam Optional<SortType> sortBy
            ) {
        Page<Hotel> hotels = hotelService.all(pageable, city, checkInDate, checkOutDate, rooms, adults, children, rating, facilities, sortBy);
        PagedModel<EntityModel<Hotel>> hotelPage = pagedResourcesAssembler.toModel(hotels, hotelAssembler);
        return ResponseEntity.ok(hotelPage);
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<EntityModel<Hotel>> one(@PathVariable Long id) {
        return ResponseEntity.ok(hotelAssembler.toModel(hotelService.one(id)));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @PreAuthorize("this.authorizationHelper.isOwnerOrAdmin(authentication)")
    public ResponseEntity<EntityModel<Hotel>> save(@RequestBody Hotel hotel, Authentication authentication){
        Hotel hotelInDb = hotelService.save(hotel, authentication);
        return ResponseEntity
                .created(URI.create("/hotels/" + hotelInDb.getId()))
                .body(hotelAssembler.toModel(hotelInDb));
    }

    @PutMapping(path="{id}", consumes = "application/json", produces = "application/json")
    @PreAuthorize("this.authorizationHelper.isCreatorOrAdmin(authentication, #id)")
    public ResponseEntity<EntityModel<Hotel>> update(@RequestBody Hotel hotel, @PathVariable Long id){
        var hotelInDb = hotelService.update(hotel, id);
        return ResponseEntity
                .ok(hotelAssembler.toModel(hotelInDb));
    }

    @DeleteMapping(path="{id}")
    @PreAuthorize("this.authorizationHelper.isCreatorOrAdmin(authentication, #id)")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path="{id}/book")
    public ResponseEntity<EntityModel<Booking>> book(@RequestBody Booking booking, @PathVariable Long id, Authentication authentication){
        var bookingInDb = bookingService.save(booking, id, (String)authentication.getPrincipal());
        return ResponseEntity
                .created(URI.create("/bookings/" + bookingInDb.getId()))
                .body(bookingAssembler.toModel(bookingInDb));
    }

    @GetMapping(path="{id}/availableRooms")
    public ResponseEntity<Integer> availableRooms(
            @PathVariable Long id
            ,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam Optional<LocalDate> checkInDate
            ,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam Optional<LocalDate> checkOutDate
    ) {
        var hotel = hotelService.one(id);
        return ResponseEntity.ok(
                hotelService.availableRoomsForDate(hotel
                        , checkInDate.orElse(LocalDate.now())
                        , checkOutDate.orElse(LocalDate.now())));
    }
}

