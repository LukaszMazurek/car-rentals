package com.example.registration;

import com.example.registration.models.Car;
import com.example.registration.repository.CarRepository;
import com.example.registration.models.User;
import com.example.registration.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RegistrationApplication {

    private final UserService userService;
    private final CarRepository carRepository;

    public RegistrationApplication(UserService userService, CarRepository carRepository) {
        this.userService = userService;
        this.carRepository = carRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(RegistrationApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(){
        return args -> {
            // user
            User user1 = new User("user1@user.com", "user", "user", "user1");
            User user2 = new User("user2@user.com", "user", "user", "user2");
            userService.save(user1);
            userService.save(user2);

            Car ford = new Car("Ford", "Mustang", "Red",
                    "ADF-1121", 2017, 59000);
            Car nissan = new Car("Nissan", "Leaf", "White",
                    "SSJ-3002", 2014, 29000);
            Car toyota = new Car("Toyota", "Prius", "Silver",
                    "KKO-0212", 2018, 39000);

            carRepository.save(ford);
            carRepository.save(nissan);
            carRepository.save(toyota);

            userService.rentCar(user1, ford.getId());
            userService.rentCar(user1, nissan.getId());
            userService.rentCar(user2, toyota.getId());
        };
    }

}
