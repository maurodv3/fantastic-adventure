package com.mauro.minesweeper.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;


public class MinesweeperBoardTest {

    private static final Set<Mine> TEST_MINES = Set.of(
            new Mine(0, 0), new Mine(1, 1), new Mine(2, 2),
            new Mine(3, 3), new Mine(4, 4), new Mine(5, 5),
            new Mine(6, 6), new Mine(7, 7), new Mine(8, 8)
    );

    @Test
    @DisplayName("Game can be won.")
    public void testGameWinner() {
        var board = MinesweeperBoard.create(9, 9, TEST_MINES);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i != j) {
                    board.click(new ClickAction(i, j));
                }
            }
        }
        Assertions.assertTrue(board.isWinner());
        Assertions.assertTrue(board.isCompleted());
    }

    @Test
    @DisplayName("Game will reveal adjacent empty squares.")
    public void testGameWinnerWithLessMoves() {
        var board = MinesweeperBoard.create(9, 9, TEST_MINES)
                .click(new ClickAction(0, 8))
                .click(new ClickAction(8, 0))
                .click(new ClickAction(0, 1))
                .click(new ClickAction(1, 0))
                .click(new ClickAction(8, 7))
                .click(new ClickAction(7, 8));
        Assertions.assertTrue(board.isWinner());
        Assertions.assertTrue(board.isCompleted());
    }

    @Test
    @DisplayName("Game can be lost.")
    public void testGameLose() {
        var board = MinesweeperBoard.create(9, 9, TEST_MINES)
                .click(new ClickAction(0, 0));
        Assertions.assertTrue(board.isCompleted());
        Assertions.assertFalse(board.isWinner());
    }

    @Test
    @DisplayName("Clicking invalid positions wont cause errors")
    public void testInvalidPositionsWontFail() {
        var board = MinesweeperBoard.create(9, 9, TEST_MINES)
                .click(new ClickAction(-1, -99))
                .click(new ClickAction(100, 100));
    }

    @Test
    @DisplayName("Clicking a flag marking is safe and won't make the game lost until marking is removed.")
    public void testGameFlagMarking() {
        var board = MinesweeperBoard.create(9, 9, TEST_MINES);
        var click1 = new ClickAction(0,0);
        click1.setFlag(true);
        board.click(click1); // Mark with flag.
        board.click(new ClickAction(0, 0)); // Click on flag.
        Assertions.assertFalse(board.isCompleted()); // Click is safe -> game is not finished.
        var click2 = new ClickAction(0,0);
        click2.setClearMarkings(true);
        board.click(click2); // Remove flag -> Mark is cleared no longer safe to click.
        board.click(new ClickAction(0, 0));
        Assertions.assertTrue(board.isCompleted());
        Assertions.assertFalse(board.isWinner());
    }

    @Test
    @DisplayName("Clicking a question marking is safe and won't make the game lost until marking is removed.")
    public void testGameQuestionMarking() {
        var board = MinesweeperBoard.create(9, 9, TEST_MINES);
        var click1 = new ClickAction(0,0);
        click1.setQuestionMark(true);
        board.click(click1); // Mark with flag.
        board.click(new ClickAction(0, 0)); // Click on question mark.
        Assertions.assertFalse(board.isCompleted()); // Click is safe -> game is not finished.
        var click2 = new ClickAction(0,0);
        click2.setClearMarkings(true);
        board.click(click2); // Remove question mark -> Mark is cleared no longer safe to click.
        board.click(new ClickAction(0, 0));
        Assertions.assertTrue(board.isCompleted());
        Assertions.assertFalse(board.isWinner());
    }

}
