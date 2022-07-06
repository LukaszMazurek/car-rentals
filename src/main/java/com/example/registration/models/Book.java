package com.example.registration.models;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Book {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String author, title, topic, edition;
    private int year, price;
    private int rantingMonths;
    private Instant timeStart;
    private boolean isAvailable;
    private long payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = true)
    private User owner;

    public Book(String author, String title, String topic, String edition, int year, int price) {
        this.author = author;
        this.title = title;
        this.topic = topic;
        this.edition = edition;
        this.year = year;
        this.price = price;
        this.isAvailable = true;
        this.owner = null;
        this.rantingMonths = 0;
        this.payment = 0;
    }

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String brand) {
        this.author = brand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String model) {
        this.title = model;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String color) {
        this.topic = color;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String registerNumber) {
        this.edition = registerNumber;
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

    public int getRantingMonths() {
        return rantingMonths;
    }

    public void setRantingMonths(int rantingDays) {
        this.rantingMonths = rantingDays;
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

