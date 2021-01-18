package com.mauro.minesweeper.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * Convenient class to generate and represent mines, provides an interface to create random mines for future games.
 */
public class Mine {

    private final int x;
    private final int y;

    public Mine(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Generates a new set of unique random mines. Number of mines is limited by height and width as the number of
     * possible combinations depends of the board size.
     * @param mineNumber the number of mines to generate.
     * @param xCap the max number allowed by the board height.
     * @param yCap the max number allowed by the board width.
     * @return unique set of new random mines.
     */
    public static Set<Mine> generateMines(int mineNumber, int xCap, int yCap) {
        if (mineNumber > yCap * xCap) {
            throw new IllegalArgumentException("Cannot generate [" + mineNumber + "] mines with current range (" + xCap + "," + yCap + "). Number of combinations is too low.");
        }
        Set<Mine> minePlaces = new HashSet<>();
        while (minePlaces.size() < mineNumber) {
            minePlaces.add(nextMine(xCap, yCap));
        }
        return minePlaces;
    }

    private static Mine nextMine(int h, int w) {
        Random random = new Random();
        return new Mine(random.nextInt(h), random.nextInt(w));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mine mine = (Mine) o;
        return x == mine.x &&
                y == mine.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}