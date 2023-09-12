package com.example.airtrip.controller.restcontroller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {
    @GetMapping("/hello-world")
    private ResponseEntity<String> getHello(){
        return ResponseEntity.ok()
                .body("Hello world");
    }
    @GetMapping("/success")
    private ResponseEntity<String> success(){
        return ResponseEntity.ok()
                .body("Success");
    }
}
