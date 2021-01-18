package com.mauro.minesweeper.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.Set;

/**
 * Minesweeper game encapsulating class, holds the current status of the game visible and the internal game
 * representation along with the board specifications.
 */
@Schema(description = "Minesweeper game representation, board specifications and current status of the game.")
public class MinesweeperBoard {

    private static final int MINE = -1;
    private static final int UNKNOWN = -2;

    @Schema(description = "Game Unique Identifier.")
    private String id;
    @Schema(description = "Game height size.")
    private final int height;
    @Schema(description = "Game width size.")
    private final int width;
    private final Set<Mine> mines;

    @Schema(description = "Game status.")
    private boolean completed;

    private int[][] board;
    private int[][] visibleCells;
    private int visibleCount;

    private MinesweeperBoard(int height, int width, Set<Mine> mines) {
        this.height = height;
        this.width = width;
        this.mines = mines;
    }

    /**
     * Creates a new minesweeper game with the desired specs.
     * @param height number of board rows.
     * @param width number of board columns.
     * @param mines to be included in the generated board.
     * @return a fresh minesweeper board with desired specs.
     */
    public static MinesweeperBoard create(int height, int width, Set<Mine> mines) {
        MinesweeperBoard newBoard = new MinesweeperBoard(height, width, mines);
        newBoard.initialize();
        return newBoard;
    }

    /**
     * Executes a click action in the current board. Will:
     * - Ignore the action if the cell was already visible.
     * - Terminate the game if mine is selected.
     * - Reveal new cells, and expand if cell is safe cell.
     * @param action to execute in the current board.
     * @return the updated new board.
     */
    public MinesweeperBoard click(ClickAction action) {
        int x = action.getxPosition();
        int y = action.getyPosition();
        if (x < 0 || x > width || y < 0 || y > height) {
            return this;
        }
        // Already visible ignore action.
        if (visibleCells[x][y] != UNKNOWN) {
            return this;
        }
        // Mine is clicked -> game lost
        if (board[x][y] == MINE) {
            completed = true;
            visibleCells[x][y] = -1;
            visibleCount++;
            return this;
        }
        // Zero(0) is clicked expand visible area recursively
        if (board[x][y] == 0) {
            visibleCells[x][y] = board[x][y];
            visibleCount++;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int cPosX = x + i;
                    int cPosY = y + j;
                    if (cPosX >= 0 && cPosY >= 0 && cPosX < height && cPosY < width) {
                        click(new ClickAction(cPosX, cPosY));
                    }
                }
            }
            return this;
        }
        //Other is clicked set to visible.
        visibleCells[x][y] = board[x][y];
        visibleCount++;
        return this;
    }

    @Schema(description = "Game is already won.")
    public boolean isWinner() {
        return ((height * width) - mines.size()) - visibleCount == 0;
    }

    /**
     * Initializes a new game board.
     * - Starts a blank board.
     * - Sets mines in the new board.
     * - Calculates all near cell proximity to mines.
     */
    private void initialize() {

        completed = false;
        board = new int[height][width];
        visibleCells = new int[height][width];
        //Set visible board to UNKNOWN.
        for (int[] row : visibleCells) {
            Arrays.fill(row, UNKNOWN);
        }
        visibleCount = 0;

        //Set mines in the board.
        mines.forEach(mine -> board[mine.getX()][mine.getY()] = -1);

        //Calculate cell proximity to mines.
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] != MINE) {
                    board[i][j] = countNeighbourhood(board, i, j);
                }
            }
        }

    }

    /**
     * Calculates the cell proximity to mines.
     * @param board with all mines to calculate the proximity.
     * @param posX calculating position X.
     * @param posY calculating position Y.
     * @return Sum of all near mines to the cell.
     */
    private int countNeighbourhood(int[][] board, int posX, int posY) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int cPosX = posX + i;
                int cPosY = posY + j;
                if (cPosX >= 0 && cPosY >= 0 && cPosX < height && cPosY < width) {
                    if (board[cPosX][cPosY] == -1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Prints the current visible game status.
     */
    public void print() {
        this._print(this.visibleCells);
    }

    private void _print(int[][] matrix) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (matrix[i][j]) {
                    case -2 -> System.out.print("[?]");
                    case -1 -> System.out.print("\u001B[31m[*]\u001B[0m");
                    case 1 -> System.out.print("\u001B[32m[" + board[i][j] + "]\u001B[0m");
                    case 2 -> System.out.print("\u001B[33m[" + board[i][j] + "]\u001B[0m");
                    case 3 -> System.out.print("\u001B[35m[" + board[i][j] + "]\u001B[0m");
                    default -> System.out.print("\u001B[37m[" + board[i][j] + "]\u001B[0m");
                }
            }
            System.out.println();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isCompleted() {
        return isWinner() || completed;
    }

    @Schema(description = "Visible user status of the board.")
    public int[][] getVisibleCells() {
        return Arrays.stream(visibleCells)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
