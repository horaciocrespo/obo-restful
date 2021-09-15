package com.obo.oborestfulapp.controllers;

import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.domain.User;
import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.model.UserDTO;
import com.obo.oborestfulapp.model.UserListDTO;
import com.obo.oborestfulapp.services.UserService;
import com.obo.oborestfulapp.services.UserServiceImpl;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/api/v1/users";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserListDTO getAllCarriers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createNewOrder(@RequestBody UserDTO userDTO) {
        return userService.createNewUser(userDTO);
    }
}
