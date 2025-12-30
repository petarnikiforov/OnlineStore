package org.online.store.web;

import org.online.store.dto.LoginRequest;
import org.online.store.dto.LoginResponse;
import org.online.store.dto.UserResponse;
import org.online.store.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request){
        System.out.println(request.getUsername().toString() + request.getPassword().toString());
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response.getUser());
    }
}
