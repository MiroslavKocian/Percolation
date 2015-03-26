package simulation;

import java.util.ArrayList;
import java.util.Random;

public class RandomPositionGenerator {

    private boolean[][] grid;
    private Random random;

    public RandomPositionGenerator(boolean[][] grid, Random random) {
        this.grid = grid;
        this.random = random;
    }

    public RandomPositionGenerator(boolean[][] grid) {
        this(grid, new Random());
    }

    public Integer[] nextPosition() {
        ArrayList<Integer[]> closedPositions = closedPositions();

        int randomPosition = random.nextInt(closedPositions.size());
        return closedPositions.get(randomPosition);
    }

    private ArrayList<Integer[]> closedPositions() {
        ArrayList<Integer[]> availablePositions = new ArrayList<Integer[]>();

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid.length; column++) {
                if (isClosed(row, column)) availablePositions.add(position(row, column));
            }
        }
        return availablePositions;
    }

    private boolean isClosed(int row, int column) {
        return !grid[row][column];
    }

    private Integer[] position(int row, int column) {
        return new Integer[]{ row, column };
    }
}