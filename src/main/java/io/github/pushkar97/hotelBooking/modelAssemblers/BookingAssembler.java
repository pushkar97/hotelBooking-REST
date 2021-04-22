package io.github.pushkar97.hotelBooking.modelAssemblers;

import io.github.pushkar97.hotelBooking.controllers.BookingController;
import io.github.pushkar97.hotelBooking.controllers.HotelController;
import io.github.pushkar97.hotelBooking.models.Booking;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookingAssembler implements RepresentationModelAssembler<Booking, EntityModel<Booking>> {


    @Override
    public EntityModel<Booking> toModel(Booking entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(BookingController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(BookingController.class).all(null)).withRel("all"),
                linkTo(methodOn(BookingController.class).rate(null, entity.getId())).withRel("rate"),
                linkTo(methodOn(HotelController.class).one(entity.getHotel().getId())).withRel("hotel")
        );
    }

}
