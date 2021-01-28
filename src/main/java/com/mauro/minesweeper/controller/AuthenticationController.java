package com.mauro.minesweeper.controller;

import com.mauro.minesweeper.dto.AuthResponse;
import com.mauro.minesweeper.dto.LoginRequest;
import com.mauro.minesweeper.dto.SignupRequest;
import com.mauro.minesweeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.just;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private UserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> authenticate(@RequestBody LoginRequest login) {
        return userService.authenticate(login.getUsername(), login.getPassword())
                .map(AuthResponse::new)
                .map(ResponseEntity::ok)
                .switchIfEmpty(just(ResponseEntity.badRequest().build()));
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<AuthResponse>> signup(@RequestBody SignupRequest signup) {
        return userService.createNewUser(signup.getUsername(), signup.getPassword())
                .flatMap(user -> userService.authenticate(signup.getUsername(), signup.getPassword()))
                .map(AuthResponse::new)
                .map(ResponseEntity::ok)
                .switchIfEmpty(just(ResponseEntity.badRequest().build()));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
