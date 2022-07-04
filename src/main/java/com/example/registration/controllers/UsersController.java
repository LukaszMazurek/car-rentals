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
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UsersController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public UsersController(UserService userService, UserRepository userRepository, CarRepository carRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @GetMapping("/list_user_cars/leave/{id}")
    public String leaveCar(Principal user, @PathVariable (value = "id") long id){
        Car car = carRepository.findById(id).orElseGet(null);
        if (car.getPayment() > 0){
            return "warning_page";
        }
        car.setOwner(null);
        car.setAvailable(true);
        carRepository.save(car);
        return "redirect:/list_user_cars";
    }

    @GetMapping("/list_user_cars/pay/{id}")
    public String payCar(Principal user, @PathVariable (value = "id") long id){
        Car car = carRepository.findById(id).orElseGet(null);
        car.setPayment(0);
        car.setTimeStart(Instant.now());
        carRepository.save(car);
        return "redirect:/list_user_cars";
    }

    @GetMapping("add_car")
    public String showAddCarForm(Model model){
        model.addAttribute("car", new Car());
        return "add_car";
    }

    @PostMapping("/process_adding_car")
    public String processAddinCar(Car car){
        car.setAvailable(true);
        carRepository.save(car);
        return "redirect:/all_cars";
    }

    @GetMapping("/all_cars")
    public String allCars(Model model){
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "all_cars";
    }

    @GetMapping("/list_user_cars")
    public String viewUserList(Principal user, Model model) {
        User userObj = userRepository.findByEmail(user.getName());

        if(userObj.getRole().equals("ADMIN")){
            return "redirect:/all_cars";
        }

        Set<Car> userCars = userObj.getCars();
        userService.setPaymants(userCars);
        userRepository.save(userObj);
        final long totalPayment = userService.getTotalPayment(userCars);
        model.addAttribute("setUserCars", userCars);
        model.addAttribute("totalPayment", totalPayment);
        model.addAttribute("firstName", userObj.getFirstName());
        return "list_user_cars";
    }

    @GetMapping("/available_cars")
    public String viewAllAvailableCars(Model model){
        List<Car> cars = carRepository.findAll();
        List<Car> availableCars = cars.stream().filter(Car::isAvailable).collect(Collectors.toList());
        model.addAttribute("availableCars", availableCars);
        return "available_cars";
    }

    @GetMapping("/available_cars/rent/{id}")
    public String rentCar(Principal user, @PathVariable (value = "id") long id){
        User userObj = userRepository.findByEmail(user.getName());
        Car car = carRepository.findById(id).orElseGet(null);
        car.setAvailable(false);
        car.setOwner(userObj);
        car.setTimeStart(Instant.now());
        car.setPayment(0);
        carRepository.save(car);
        return "redirect:/list_user_cars";
    }
}
