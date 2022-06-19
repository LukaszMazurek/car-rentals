package com.example.registration.services;

import com.example.registration.models.Car;
import com.example.registration.models.CarRepository;
import com.example.registration.models.User;
import com.example.registration.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public UserService(UserRepository userRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public void save(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public boolean rentCar(User user, Long carId){
        Car car = carRepository.findById(carId).orElse(null);
        if(car != null && car.isAvailable()){
            car.setAvailable(false);
            car.setOwner(user);
            carRepository.save(car);
            return true;
        }
        return false;
    }
}
