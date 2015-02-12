import java.util.Arrays;

import junit.framework.TestCase;


public class PercolationTest extends TestCase {
    
    private Percolation percolation;
    
    public void setUp() {
        percolation = new Percolation(5);
    }

    public void testCanCreateAPercolation() {
        boolean[][] expectedGrid = new boolean[][]{
               {false, false, false, false, false},
               {false, false, false, false, false},
               {false, false, false, false, false},
               {false, false, false, false, false},
               {false, false, false, false, false}
        };
        
        assertEquals(true, Arrays.deepEquals(expectedGrid, percolation.getGrid()));
    }
    
    public void testCanOpenACell() {
        percolation.open(3, 2);
        
        boolean[][] expectedGrid = new boolean[][]{
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false}
         };
        
        assertEquals(true, Arrays.deepEquals(expectedGrid, percolation.getGrid()));
    }
    
    public void testCanTellWhetherACellOpen() {
        percolation.open(3, 1);
        
        assertTrue(percolation.isOpen(3, 1));
        assertFalse(percolation.isOpen(0, 0));
    }
    
    

}
