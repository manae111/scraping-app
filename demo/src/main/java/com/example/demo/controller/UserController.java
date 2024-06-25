package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.User;
import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    //新規登録 user
    @GetMapping("/toRegister")
    public String toRegister(UserForm userForm) {
        return "register";
    }

    @PostMapping("/register")
    public String register(UserForm userform,
                        RedirectAttributes redirectAttributes) {
        User user = new User();
        user.setUsername(userform.getUsername());
        user.setPassword(userform.getPassword());
        userService.insertUser(user);
        return "redirect:/toLogin";
    }
}
