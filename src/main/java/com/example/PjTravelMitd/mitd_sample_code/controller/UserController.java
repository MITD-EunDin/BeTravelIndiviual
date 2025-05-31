package com.example.PjTravelMitd.mitd_sample_code.controller;

import com.example.PjTravelMitd.mitd_sample_code.dto.request.UserRequestDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping("/customers")
    public String addUser (@Valid @RequestBody UserRequestDTO useDTO){
        return "User added";
    }


}
