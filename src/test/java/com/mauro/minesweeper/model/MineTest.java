package com.mauro.minesweeper.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MineTest {

    @Test
    @DisplayName("Mines are generated correctly, and in the correct amount.")
    public void testMineGeneration() {
        Assertions.assertEquals(Mine.generateMines(10, 10, 10).size(), 10);
    }

    @Test
    @DisplayName("Mines cannot be calculated if the bounds combinations are less than the mine number requirement.")
    public void testShouldFailIfMinesOutOfRange() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Mine.generateMines(10, 1, 1));
    }

}
