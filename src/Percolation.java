import java.util.ArrayList;
import java.util.List;


public class Percolation {

    private boolean[][] grid;

    public Percolation(int gridSize) {
        grid = new boolean[gridSize][gridSize];
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public void open(int row, int column) {
        grid[row][column] = true;
    }

    public boolean isOpen(int row, int column) {
        return grid[row][column];
    }
    
    public boolean isFull(int row, int column) { 
        return isFull(row, column, new ArrayList<String>()); 
    }

    private boolean isFull(int row, int column, List<String> positionsChecked) {
        if (!isOpen(row, column)) return false;
        if (row == 0) return true;
        
        return checkIfNeighborsFull(row, column, positionsChecked);
    }

    private boolean checkIfNeighborsFull(int row, int column, List<String> positionsChecked) {
        String currentPosition = String.format("(%s, %s)", row, column);
        
        if (positionsChecked.contains(currentPosition)) return false;
        positionsChecked.add(currentPosition);
        
        return isFull(row - 1, column, positionsChecked) ||
               isFull(row + 1, column, positionsChecked) ||
               column > 0 && isFull(row, column - 1, positionsChecked) ||
               column < grid.length && isFull(row, column + 1, positionsChecked);
    }

}
