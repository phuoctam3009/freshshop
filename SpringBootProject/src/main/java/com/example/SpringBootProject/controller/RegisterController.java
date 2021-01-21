package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.DAO.RoleRepository;
import com.example.SpringBootProject.DAO.UserRepository;
import com.example.SpringBootProject.DTO.UserDTO;
import com.example.SpringBootProject.Entity.Role;
import com.example.SpringBootProject.Entity.User;
import com.example.SpringBootProject.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RegisterController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/register")
    public String register(@RequestParam(value = "error", required = false) String error,
                           Model model){
        String errorMessage = null;
        if (error != null){
            errorMessage = "Username or Password is incorrect !!";
        }
        model.addAttribute("userRegister", new UserDTO());
        model.addAttribute("errorMessage", errorMessage);
        return "register";
    }

    @PostMapping("/formregister")
    public String formRegister(@ModelAttribute("userRegister")UserDTO userDTO, Model model){
        System.out.println("Get data from FORM REGISTER");
        System.out.println(userDTO.getUsername());
        System.out.println(userDTO.getPassword());
        User byUsername = userRepository.findByUsername(userDTO.getUsername());
        if(byUsername == null){
            Optional<Role> byId = roleRepository.findById(2L);
            User user = new User();
            user.setActived(true);
            user.setRole(byId.get());
            userRepository.save(user);
            user.setUsername(userDTO.getUsername());
            user.setPassword(PasswordGenerator.encodePassword(userDTO.getPassword()));
            user.setActived(true);
            User user1 = userRepository.saveAndFlush(user);
        }else{
            return "redirect:/register?error=true";
        }
        return "login";
    }
}
