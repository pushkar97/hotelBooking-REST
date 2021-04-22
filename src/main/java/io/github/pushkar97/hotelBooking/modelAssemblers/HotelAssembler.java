package io.github.pushkar97.hotelBooking.modelAssemblers;

import io.github.pushkar97.hotelBooking.controllers.HotelController;
import io.github.pushkar97.hotelBooking.models.Hotel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HotelAssembler implements RepresentationModelAssembler<Hotel, EntityModel<Hotel>> {


    @Override
    public EntityModel<Hotel> toModel(Hotel entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(HotelController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(HotelController.class).all(null, null, null, null, null, null, null, null, null, null)).withRel("all"),
                linkTo(methodOn(HotelController.class).book(null, entity.getId(), null)).withRel("book"),
                linkTo(methodOn(HotelController.class).availableRooms(entity.getId(), null, null)).withRel("availableRooms")
        );
    }
}
