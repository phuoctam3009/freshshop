package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.Entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
public class LoginController {
    // Login form

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response) throws IOException {

        Principal principal = request.getUserPrincipal();
        System.out.println(principal);
        if(principal != null){
            System.out.println(principal.getName());
            return "redirect:/";
        }
        String errorMessage = null;
        if(error != null) {
            errorMessage = "Username or Password is incorrect !!";
        }
        if(logout != null) {
            errorMessage = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.POST)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request,
                    response,
                    auth);
            System.out.println("aaaaaaaaaaaaaaaaaaaaa");
        }
        System.out.println("bbbbbbbbbbbbbbbbbbbbb");
        return "redirect:/login?logout=true";
    }

    @GetMapping("403")
    public String deniedPage(){
        return "403";
    }


}
