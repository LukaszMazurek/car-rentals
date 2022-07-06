package com.example.registration.services;

import com.example.registration.models.Book;
import com.example.registration.repository.BookRepository;
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
    private final BookRepository bookRepository;

    public UserService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void save(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public boolean rentBook(User user, Long carId){
        Book book = bookRepository.findById(carId).orElse(null);
        if(book != null && book.isAvailable()){
            book.setAvailable(false);
            book.setOwner(user);
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    public Set<Book> setPaymants(Set<Book> books){

        Instant now = Instant.now();

        for(Book book : books){
            Duration timeElapsed = Duration.between(book.getTimeStart(), now);
            long minutes = timeElapsed.toMinutes();
            long payment = minutes * book.getPrice();
            book.setPayment(payment);
            bookRepository.save(book);
        }

        return books;
    }

    public long getTotalPayment(Set<Book> books){
        return books.stream().mapToLong(Book::getPayment).sum();
    }

}
