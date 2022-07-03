package com.example.registration.models;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Car{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String brand, model, color, registerNumber;
    private int year, price;
    private int rantingDays;
    private Instant timeStart;
    private boolean isAvailable;
    private long payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = true)
    private User owner;

    public Car(String brand, String model, String color, String registerNumber, int year, int price) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.registerNumber = registerNumber;
        this.year = year;
        this.price = price;
        this.isAvailable = true;
        this.owner = null;
        this.rantingDays = 0;
        this.payment = 0;
    }

    public Car() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getRantingDays() {
        return rantingDays;
    }

    public void setRantingDays(int rantingDays) {
        this.rantingDays = rantingDays;
    }

    public Instant getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Instant timeStart) {
        this.timeStart = timeStart;
    }

    public long getPayment() {
        return payment;
    }

    public void setPayment(long payment) {
        this.payment = payment;
    }
}

