package com.example.registration.models;
import com.example.registration.models.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository <Car, Long> {
    List<Car> findByBrand(String brand);
    List<Car> findByColor(String color);
    List<Car> findByYear(int year);
    List<Car> findByBrandAndModel(String brand, String model);
    List<Car> findByBrandOrColor(String brand, String Color);
    List<Car> findByBrandOrderByYearAsc(String brand);
}


