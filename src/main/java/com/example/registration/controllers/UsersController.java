package com.example.registration.controllers;

import com.example.registration.models.Car;
import com.example.registration.repository.CarRepository;
import com.example.registration.models.User;
import com.example.registration.repository.UserRepository;
import com.example.registration.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Set;

@Controller
public class UsersController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public UsersController(UserService userService, UserRepository userRepository, CarRepository carRepository1) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.carRepository = carRepository1;
    }

    @GetMapping("/list_user_cars/leave/{id}")
    public String leaveCar(Principal user, @PathVariable (value = "id") long id){
        Car car = carRepository.findById(id).orElseGet(null);
        car.setOwner(null);
        carRepository.save(car);
        return "redirect:/list_user_cars";
    }


    @GetMapping("/list_user_cars")
    public String viewUserList(Principal user, Model model) {
        User userObj = userRepository.findByEmail(user.getName());
        Set<Car> userCars = userObj.getCars();
        model.addAttribute("setUserCars", userCars);
        return "list_user_cars";
    }
}
