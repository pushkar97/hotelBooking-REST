package io.github.pushkar97.hotelBooking.controllers;

import io.github.pushkar97.hotelBooking.helpers.AuthorizationHelper;
import io.github.pushkar97.hotelBooking.modelAssemblers.UserAssembler;
import io.github.pushkar97.hotelBooking.models.User;
import io.github.pushkar97.hotelBooking.services.UserService;
import lombok.var;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;
    public AuthorizationHelper authorizationHelper;
    public UserController(UserService userService,
                          UserAssembler userAssembler,
                          PagedResourcesAssembler<User> pagedResourcesAssembler,
                          AuthorizationHelper authorizationHelper){
        this.userService = userService;
        this.userAssembler = userAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.authorizationHelper = authorizationHelper;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<User>>> all(Pageable pageable){
        var users = userService.all(pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(users, userAssembler));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<User>> one(@PathVariable Long id) {
        var user = userService.one(id);
        return ResponseEntity.ok(userAssembler.toModel(user));
    }

    @PostMapping
    public ResponseEntity<EntityModel<User>> save(@RequestBody User user) {
        var userInDb = userService.save(user);
        return ResponseEntity
                .created(URI.create("/users/" + userInDb.getId()))
                .body(userAssembler.toModel(userInDb));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/enable")
    @PreAuthorize("this.authorizationHelper.isAdmin(authentication)")
    public ResponseEntity<Void> enableUser(@PathVariable Long id){
        userService.enableUser(id);
        return ResponseEntity.noContent().build();
    }
}
