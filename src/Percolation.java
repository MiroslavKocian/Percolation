
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

}
