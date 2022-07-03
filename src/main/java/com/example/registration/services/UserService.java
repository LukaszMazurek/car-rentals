package com.example.registration.services;

import com.example.registration.models.Car;
import com.example.registration.repository.CarRepository;
import com.example.registration.models.User;
import com.example.registration.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;

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

    public Set<Car> setPaymants(Set<Car> cars){

        Instant now = Instant.now();

        for(Car car : cars){
            Duration timeElapsed = Duration.between(car.getTimeStart(), now);
            long minutes = timeElapsed.toMinutes();
            long payment = minutes * car.getPrice();
            car.setPayment(payment);
            carRepository.save(car);
        }

        return cars;
    }

}
