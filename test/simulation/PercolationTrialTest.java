package simulation;

import junit.framework.TestCase;
import percolation.Percolation;

public class PercolationTrialTest extends TestCase {

    private PercolationTrial trial;

    public void setUp() throws Exception {
        super.setUp();
        trial = new PercolationTrial(3);
        FakeRandomPositionGenerator randomPositionGenerator = new FakeRandomPositionGenerator(
                new Integer[][]{{1, 1}, {1, 2}, {2, 2}, {3, 2}}
        );
        trial.setRandomPositionGenerator(randomPositionGenerator);
    }

    public void testCanConstructATrialForAGridSize() throws Exception {
        PercolationTrial secondTrial = new PercolationTrial(4);

        assertEquals(3, trial.getPercolation().getGrid().length);
        assertEquals(4, secondTrial.getPercolation().getGrid().length);
    }

    public void testRunningTheTrialOpensRandomSitesInThePercolationUntilItPercolates() throws Exception {
        trial.run();

        Percolation percolation = trial.getPercolation();

        assertTrue(percolation.percolates());

        assertTrue(percolation.isOpen(1, 1));
        assertTrue(percolation.isOpen(1, 2));
        assertFalse(percolation.isOpen(1, 3));

        assertFalse(percolation.isOpen(2, 1));
        assertTrue(percolation.isOpen(2, 2));
        assertFalse(percolation.isOpen(2, 3));

        assertFalse(percolation.isOpen(3, 1));
        assertTrue(percolation.isOpen(3, 2));
        assertFalse(percolation.isOpen(3, 3));
    }

    public void testCanCalculateThePercolationThresholdForTheTrial() throws Exception {
        assertEquals(0.0, trial.percolationThreshold());

        trial.run();

        assertEquals(4. / 9, trial.percolationThreshold());

        PercolationTrial secondTrial = new PercolationTrial(2);

        secondTrial.setRandomPositionGenerator(new FakeRandomPositionGenerator(new Integer[][]{{1, 1}, {2, 1}}));

        secondTrial.run();

        assertEquals(0.5, secondTrial.percolationThreshold());
    }

    private class FakeRandomPositionGenerator extends RandomPositionGenerator {
        private Integer[][] randomPositions;
        public int positionIndex;

        public FakeRandomPositionGenerator(Integer[][] randomPositions) {
            super(PercolationTrialTest.this.trial.getPercolation().getGrid());
            this.randomPositions = randomPositions;
        }

        @Override
        public Integer[] nextPosition() {
            Integer[] randomPosition = randomPositions[positionIndex];
            positionIndex = positionIndex + 1;
            return randomPosition;
        }
    }
}