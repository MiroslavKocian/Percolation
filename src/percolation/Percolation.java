package percolation;

import java.util.ArrayList;
import java.util.List;


public class Percolation {

    private boolean[][] grid;

    public Percolation(int gridSize) {
        if (gridSize < 1) throw new IllegalArgumentException(wrongGridSizeError(gridSize));
        grid = new boolean[gridSize][gridSize];
    }

    private String wrongGridSizeError(int gridSize) {
        return String.format("Cannot create a percolation of size %s", gridSize);
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public void open(int row, int column) {
        grid[row - 1][column - 1] = true;
    }

    public boolean isOpen(int row, int column) {
        return grid[row - 1][column - 1];
    }
    
    public boolean isFull(int row, int column) { 
        return isFull(row, column, new ArrayList<String>()); 
    }

    private boolean isFull(int row, int column, List<String> positionsChecked) {
        if (!isOpen(row, column)) return false;
        if (row == 1) return true;

        return checkIfNeighborsFull(row, column, positionsChecked);
    }

    private boolean checkIfNeighborsFull(int row, int column, List<String> positionsChecked) {
        String currentPosition = String.format("(%s, %s)", row, column);
        
        if (positionsChecked.contains(currentPosition)) return false;
        positionsChecked.add(currentPosition);
        
        return row > 1 && isFull(row - 1, column, positionsChecked) ||
               row < lastColumn() && isFull(row + 1, column, positionsChecked) ||
               column > 1 && isFull(row, column - 1, positionsChecked) ||
               column < lastColumn() && isFull(row, column + 1, positionsChecked);
    }

	private int lastColumn() {
		return lastRow();
	}

    public boolean percolates() {
		for (int column = 1; column <= gridSize(); column++) {
			if (isFull(lastColumn(), column)) return true;
		}
		return false;
		
	}

	private int lastRow() {
		return gridSize();
	}

    public int gridSize() {
        return getGrid().length;
    }

    public double getPercolationThreshold() {
        double numberOfOpenCells = 0.0;
        for (boolean[] gridRow : getGrid()) {
            for (boolean cell : gridRow) {
                if (cell) numberOfOpenCells++;
            }
        }
        return numberOfOpenCells / Math.pow(gridSize(), 2);
    }
}
