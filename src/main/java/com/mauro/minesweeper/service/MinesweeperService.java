package com.mauro.minesweeper.service;

import com.mauro.minesweeper.model.MinesweeperBoard;
import com.mauro.minesweeper.model.ClickAction;
import com.mauro.minesweeper.model.Mine;
import com.mauro.minesweeper.repository.MinesweeperBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MinesweeperService {

    private MinesweeperBoardRepository repository;

    public Mono<MinesweeperBoard> startNewGame(Integer height, Integer width, Integer mines) {
        return repository.save(MinesweeperBoard.create(height, width, Mine.generateMines(mines, height, width)));
    }

    public Mono<MinesweeperBoard> click(String gameID, ClickAction clickAction) {
        return repository.findById(gameID)
                .map(board -> board.click(clickAction));
    }

    @Autowired
    public void setRepository(MinesweeperBoardRepository repository) {
        this.repository = repository;
    }
}
