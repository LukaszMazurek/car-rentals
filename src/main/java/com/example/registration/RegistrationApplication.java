package com.example.registration;

import com.example.registration.models.Book;
import com.example.registration.repository.BookRepository;
import com.example.registration.models.User;
import com.example.registration.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

@SpringBootApplication
public class RegistrationApplication {

    private final UserService userService;
    private final BookRepository bookRepository;

    public RegistrationApplication(UserService userService, BookRepository bookRepository) {
        this.userService = userService;
        this.bookRepository = bookRepository;
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
            User admin = new User("admin@admin", "admin", "admin", "admin");
            admin.setRole("ADMIN");
            userService.save(user1);
            userService.save(user2);
            userService.save(admin);

            Book cc = new Book("Robert C Martin", "Czysty Kod", "Programming",
                    "First", 2010, 5);
            Book mc = new Book("Robert C Martin", "Mistrz Czystego Kodu", "Programming",
                    "Second", 2014, 8);
            Book e4it = new Book("Beata Błaszczyk", "Englisz 4 IT", "English",
                    "Third", 2018, 7);
            Book ref = new Book("Martin Fowler", "Refaktoryzacja", "Programming",
                    "Second", 2016, 200);

            cc.setTimeStart(Instant.now());
            mc.setTimeStart(Instant.now());
            e4it.setTimeStart(Instant.now());
            ref.setTimeStart(Instant.now());

            bookRepository.save(cc);
            bookRepository.save(mc);
            bookRepository.save(e4it);
            bookRepository.save(ref);

            userService.rentBook(user1, cc.getId());
            userService.rentBook(user1, mc.getId());
            userService.rentBook(user2, e4it.getId());
        };
    }

}
