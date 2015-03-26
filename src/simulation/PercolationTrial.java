package simulation;

import percolation.Percolation;

public class PercolationTrial {
    private Percolation percolation;
    private RandomPositionGenerator randomPositionGenerator;

    public PercolationTrial(int gridSize) {
        percolation = new Percolation(gridSize);
    }

    public Percolation getPercolation(){
        return percolation;
    }

    public void run() {
        while (!percolation.percolates()) openARandomPosition();
    }

    private void openARandomPosition() {
        Integer[] nextPositionToOpen = randomPositionGenerator.nextPosition();
        percolation.open(nextPositionToOpen[0], nextPositionToOpen[1]);
    }

    public void setRandomPositionGenerator(RandomPositionGenerator randomPositionGenerator) {
        this.randomPositionGenerator = randomPositionGenerator;
    }

    public double percolationThreshold() {
        return percolation.getPercolationThreshold();
    }

}
