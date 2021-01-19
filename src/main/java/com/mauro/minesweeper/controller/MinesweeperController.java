package com.mauro.minesweeper.controller;

import com.mauro.minesweeper.model.MinesweeperBoard;
import com.mauro.minesweeper.model.ClickAction;
import com.mauro.minesweeper.service.MinesweeperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class MinesweeperController {

    private MinesweeperService service;

    @PostMapping(path = "/start")
    @Operation(summary = "Starts a new minesweeper game.")
    public Mono<ResponseEntity<MinesweeperBoard>> start(
            @Parameter(description = "Desired height of the new board.") @RequestParam(value = "height", defaultValue = "9") int height,
            @Parameter(description = "Desired width of the new board.") @RequestParam(value = "width", defaultValue = "9") int width,
            @Parameter(description = "Desired number of mines in the new board.") @RequestParam(value = "mines", defaultValue = "10") int mines
    ) {
        return service.startNewGame(height, width, mines)
                .map(ResponseEntity::ok);
    }

    @PatchMapping(path = "/{gameID}/click")
    @Operation(summary = "Executes an click operation on an existing minesweeper game board.")
    public Mono<ResponseEntity<MinesweeperBoard>> click(
            @Parameter(description = "Existing board ID.") @PathVariable String gameID,
            @Parameter(description = "Click operation to execute in board.") @RequestBody ClickAction clickAction
    ) {
        return service.click(gameID, clickAction)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }

    @Autowired
    public void setService(MinesweeperService service) {
        this.service = service;
    }
}
