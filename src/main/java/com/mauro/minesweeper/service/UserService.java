package com.mauro.minesweeper.service;

import com.mauro.minesweeper.model.User;
import com.mauro.minesweeper.repository.UserRepository;
import com.mauro.minesweeper.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.*;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private TokenProvider tokenProvider;

    public Mono<String> authenticate(String username, String clearPassword) {
        return userRepository.findUserByUsername(username)
                .filter(user -> passwordEncoder.matches(clearPassword, user.getPassword()))
                .map(tokenProvider::generateToken);
    }

    public Mono<User> createNewUser(String username, String clearPassword) {
        return userRepository.findUserByUsername(username)
                .flatMap(exits -> error(new Exception("User already exists.")))
                .switchIfEmpty(defer(() -> this.createUser(username, clearPassword)))
                .cast(User.class);
    }

    private Mono<User> createUser(String username, String clearPassword) {
        var newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(clearPassword));
        return userRepository.save(newUser);
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
}
