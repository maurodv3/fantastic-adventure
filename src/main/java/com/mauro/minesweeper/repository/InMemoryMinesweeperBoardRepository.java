package com.mauro.minesweeper.repository;

import com.mauro.minesweeper.model.MinesweeperBoard;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// TODO - Data layer
@Repository
public class InMemoryMinesweeperBoardRepository {

    private Map<String, MinesweeperBoard> boards = new HashMap<>();

    public Mono<MinesweeperBoard> save(MinesweeperBoard board) {
        String newID = UUID.randomUUID().toString();
        board.setId(newID);
        boards.put(newID, board);
        return Mono.just(board);
    }

    public Mono<MinesweeperBoard> findById(String id) {
        return Mono.justOrEmpty(boards.get(id));
    }

}
