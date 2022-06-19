package com.example.registration.controllers;

import com.example.registration.models.User;
import com.example.registration.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String
    viewHomePage(){
        return "index";
    }

    @GetMapping("register")
    public String showSignUpForm(Model model){
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_registration")
    public String processRegistration(User user){
        userService.save(user);
        return "register_success";
    }
}
