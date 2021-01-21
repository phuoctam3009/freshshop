package com.example.SpringBootProject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "tam1234";
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println(encodedPassword);
    }

    public static String encodePassword(String rawPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(rawPassword);
        return encode;
    }
}
