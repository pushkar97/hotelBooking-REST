package io.github.pushkar97.hotelBooking.modelAssemblers;

import io.github.pushkar97.hotelBooking.controllers.BookingController;
import io.github.pushkar97.hotelBooking.controllers.ReviewController;
import io.github.pushkar97.hotelBooking.models.Review;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReviewAssembler implements RepresentationModelAssembler<Review, EntityModel<Review>> {


    @Override
    public EntityModel<Review> toModel(Review entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ReviewController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(ReviewController.class).all(null)).withRel("all"),
                linkTo(methodOn(BookingController.class).one(entity.getBooking().getId())).withRel("booking")
        );
    }

}
