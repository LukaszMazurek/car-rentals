package com.example.registration.controllers;

import com.example.registration.models.Book;
import com.example.registration.repository.BookRepository;
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
    private final BookRepository bookRepository;

    public UsersController(UserService userService, UserRepository userRepository, BookRepository bookRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/list_user_books/leave/{id}")
    public String leaveBook(Principal user, @PathVariable (value = "id") long id){
        Book book = bookRepository.findById(id).orElseGet(null);
        if (book.getPayment() > 0){
            return "warning_page";
        }
        book.setOwner(null);
        book.setAvailable(true);
        bookRepository.save(book);
        return "redirect:/list_user_books";
    }

    @GetMapping("/list_user_books/pay/{id}")
    public String payBook(Principal user, @PathVariable (value = "id") long id){
        Book book = bookRepository.findById(id).orElseGet(null);
        book.setPayment(0);
        book.setTimeStart(Instant.now());
        bookRepository.save(book);
        return "redirect:/list_user_books";
    }

    @GetMapping("add_book")
    public String showAddBookForm(Model model){
        model.addAttribute("book", new Book());
        return "add_book";
    }

    @PostMapping("/process_adding_book")
    public String processAddingBook(Book book){
        book.setAvailable(true);
        bookRepository.save(book);
        return "redirect:/all_books";
    }

    @GetMapping("/all_books")
    public String allBooks(Model model){
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "all_books";
    }

    @GetMapping("/list_user_books")
    public String viewUserList(Principal user, Model model) {
        User userObj = userRepository.findByEmail(user.getName());

        if(userObj.getRole().equals("ADMIN")){
            return "redirect:/all_books";
        }

        Set<Book> userBooks = userObj.getBooks();
        userService.setPaymants(userBooks);
        userRepository.save(userObj);
        final long totalPayment = userService.getTotalPayment(userBooks);
        model.addAttribute("setUserBooks", userBooks);
        model.addAttribute("totalPayment", totalPayment);
        model.addAttribute("firstName", userObj.getFirstName());
        return "list_user_books";
    }

    @GetMapping("/available_books")
    public String viewAllAvailableBooks(Model model){
        List<Book> books = bookRepository.findAll();
        List<Book> availableBooks = books.stream().filter(Book::isAvailable).collect(Collectors.toList());
        model.addAttribute("availableBooks", availableBooks);
        return "available_books";
    }

    @GetMapping("/available_books/rent/{id}")
    public String rentBook(Principal user, @PathVariable (value = "id") long id){
        User userObj = userRepository.findByEmail(user.getName());
        Book book = bookRepository.findById(id).orElseGet(null);
        book.setAvailable(false);
        book.setOwner(userObj);
        book.setTimeStart(Instant.now());
        book.setPayment(0);
        bookRepository.save(book);
        return "redirect:/list_user_books";
    }

    @GetMapping("/all_books/delete/{id}")
    public String deleteBook(@PathVariable (value = "id") long id){
        bookRepository.delete(bookRepository.getById(id));
        bookRepository.flush();
        return "redirect:/all_books";
    }
}
