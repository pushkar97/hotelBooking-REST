package io.github.pushkar97.hotelBooking.controllers;

import io.github.pushkar97.hotelBooking.modelAssemblers.ReviewAssembler;
import io.github.pushkar97.hotelBooking.models.Review;
import io.github.pushkar97.hotelBooking.services.ReviewService;
import lombok.var;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final PagedResourcesAssembler<Review> pagedResourcesAssembler;
    private final ReviewAssembler reviewAssembler;

    ReviewController(ReviewService reviewService,
                     PagedResourcesAssembler<Review> pagedResourcesAssembler,
                     ReviewAssembler reviewAssembler) {
        this.reviewService = reviewService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.reviewAssembler = reviewAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Review>>> all(Pageable pageable){
        var users = reviewService.all(pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(users, reviewAssembler));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Review>> one(@PathVariable Long id){
        return ResponseEntity.ok(reviewAssembler.toModel(reviewService.one(id)));
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<Review>> update(@RequestBody Review review, @PathVariable Long id) {
        var reviewInDb = reviewService.update(review, id);
        return ResponseEntity
                .ok(reviewAssembler.toModel(reviewInDb));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
