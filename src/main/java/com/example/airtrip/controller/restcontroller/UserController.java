package com.example.airtrip.controller.restcontroller;

import com.example.airtrip.exception.AirTripNotFoundException;
import com.example.airtrip.exception.UserWasNotFound;
import com.example.airtrip.repository.AirTripRepository;
import com.example.airtrip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final AirTripRepository airTripRepository;
    @PostMapping("/addTicket")
    public ResponseEntity<String> addTripToUser(@RequestParam Long userId,
                                                @RequestParam Long tripId){
        try{
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserWasNotFound("User with id " +userId + " was now found"));
            var trips =airTripRepository.findById(tripId)
                    .orElseThrow(() -> new AirTripNotFoundException("Air Trip with id " + tripId + " was not found"));
        }
        catch (Exception exception){

        }
        return ResponseEntity.ok("ok");
    }
}
