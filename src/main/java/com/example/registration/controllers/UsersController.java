package com.example.registration.controllers;

import com.example.registration.models.Car;
import com.example.registration.models.User;
import com.example.registration.repository.UserRepository;
import com.example.registration.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class UsersController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UsersController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/list_user_cars")
    public String viewUserList(Principal user, Model model) {
        User userObj = userRepository.findByEmail(user.getName());
        Set<Car> userCars = userObj.getCars();
        model.addAttribute("setUserCars", userCars);
        return "list_user_cars";
    }
}
