package com.mauro.minesweeper.repository;

import com.mauro.minesweeper.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository  extends ReactiveMongoRepository<User, String> {

    Mono<User> findUserByUsername(String username);

}
