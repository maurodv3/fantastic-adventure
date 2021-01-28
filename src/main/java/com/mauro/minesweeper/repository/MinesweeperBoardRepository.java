package com.mauro.minesweeper.repository;

import com.mauro.minesweeper.model.MinesweeperBoard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinesweeperBoardRepository extends ReactiveMongoRepository<MinesweeperBoard, String> {

}
