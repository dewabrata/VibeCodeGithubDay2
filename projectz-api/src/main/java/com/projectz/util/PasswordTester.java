package com.projectz.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTester {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String password = "password123";
        String hashedFromDB = "$2a$10$N9qo8uLOickgx2ZMRZoMye6oQPOkZcb8Q4QH7VLtjNzqNqEH8ybOq";
        
        System.out.println("Testing password: " + password);
        System.out.println("Hash from DB: " + hashedFromDB);
        System.out.println("Password matches: " + encoder.matches(password, hashedFromDB));
        
        // Generate new hash for comparison
        String newHash = encoder.encode(password);
        System.out.println("New hash: " + newHash);
        System.out.println("New hash matches: " + encoder.matches(password, newHash));
    }
}
