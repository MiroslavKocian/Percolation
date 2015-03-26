package simulation;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomPositionGeneratorTest extends TestCase {

    private RandomPositionGenerator randomPositionGenerator;

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testKeepsGeneratingRandomGridPositions() throws Exception {
        Random fakeRandom = new FakeRandom(2, 0, 1);

        boolean[][] grid = new boolean[2][2];
        randomPositionGenerator = new RandomPositionGenerator(grid, fakeRandom);

        assertNextPosition(1, 0);
        assertNextPosition(0, 0);
        assertNextPosition(0, 1);
    }

    public void testKeepsOnlyGeneratingCellsThatAreClosed() throws Exception {
        boolean [][] grid = new boolean[2][2];
        grid[0][0] = true;
        randomPositionGenerator = new RandomPositionGenerator(grid, new FakeRandom(0));
        assertNextPosition(0, 1);


    }

    private void assertNextPosition(int row, int column) {
        Integer[] expectedPosition = {row, column};
        Integer[] actualPosition = randomPositionGenerator.nextPosition();
        String failureMessage = String.format("Expected %s but was %s", Arrays.toString(expectedPosition), Arrays.toString(actualPosition));
        assertTrue(failureMessage, Arrays.deepEquals(expectedPosition, actualPosition));
    }

    private static class FakeRandom extends Random {
        private List<Integer> values;
        private int currentPosition;

        private FakeRandom(Integer ... valuesInOrder) {
            values = Arrays.asList(valuesInOrder);
        }

        @Override
        public int nextInt(int bound) {
            Integer result = values.get(currentPosition);
            currentPosition++;
            return result;
        }
    }
}