package io.github.pushkar97.hotelBooking.controllers;

import io.github.pushkar97.hotelBooking.modelAssemblers.BookingAssembler;
import io.github.pushkar97.hotelBooking.modelAssemblers.ReviewAssembler;
import io.github.pushkar97.hotelBooking.models.Booking;
import io.github.pushkar97.hotelBooking.models.Review;
import io.github.pushkar97.hotelBooking.services.BookingService;
import io.github.pushkar97.hotelBooking.services.ReviewService;
import lombok.var;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final PagedResourcesAssembler<Booking> pagedResourcesAssembler;
    private final BookingAssembler bookingAssembler;
    private final ReviewService reviewService;
    private final ReviewAssembler reviewAssembler;

    BookingController(BookingService bookingService,
                      PagedResourcesAssembler<Booking> pagedResourcesAssembler,
                      BookingAssembler bookingAssembler,
                      ReviewService reviewService,
                      ReviewAssembler reviewAssembler) {
        this.bookingService = bookingService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.bookingAssembler = bookingAssembler;
        this.reviewService = reviewService;
        this.reviewAssembler = reviewAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Booking>>> all(Pageable pageable){
        var users = bookingService.all(pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(users, bookingAssembler));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Booking>> one(@PathVariable Long id){
        return ResponseEntity.ok(bookingAssembler.toModel(bookingService.one(id)));
    }

    @PostMapping("{id}/rate")
    public ResponseEntity<EntityModel<Review>> rate(@RequestBody Review review, @PathVariable Long id) {
        var reviewInDb = reviewService.save(review, id);
        return ResponseEntity
                .created(URI.create("/reviews/" + reviewInDb.getId()))
                .body(reviewAssembler.toModel(reviewInDb));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
