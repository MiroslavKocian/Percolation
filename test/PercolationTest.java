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
    
    public void testOpenSitesOnTheTopRowAreFull() {
        assertFalse(percolation.isFull(0, 2));
        assertFalse(percolation.isFull(2, 3));
        
        percolation.open(0, 2);
        percolation.open(2, 3);
        
        assertTrue(percolation.isFull(0, 2));
        assertFalse(percolation.isFull(2, 3));
    }
    
    public void testSitesThatAreSouthOfAFullSiteAreFull() {
        percolation.open(0, 1);
        
        for(int row = 1; row < percolation.getGrid().length; row++) {
            assertFalse(percolation.isFull(row, 1));
            
            percolation.open(row, 1);
            
            assertTrue(percolation.isFull(row, 1));
        }
    }
    
    public void testOpenSitesToTheEastOfTheFullSitesAreFull() {
        percolation.open(0,0);
        for (int column = 0; column < percolation.getGrid().length; column++) {
            assertFalse(percolation.isFull(1, column));
            percolation.open(1, column);
            assertTrue(String.format("Site (%s, %s) should be full", 1, column), percolation.isFull(1, column));
        }
    }
    
    public void testOpenSitesDiagonalToFullSitesAreNotFull() {
        percolation.open(0, 0);
        percolation.open(1, 1);
        
        assertFalse(percolation.isFull(1, 1));
    }
    
    public void testChecksSouthForFullness() {
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(2, 3);
        percolation.open(1, 3);
        percolation.open(0, 3);
        
        assertTrue(percolation.isFull(1,1));
    }
    
    public void testOpenSitesToTheWestOfTheFullSitesAreFull() {
        assertOpenSitesToTheWestOfFullSitesAreFull(percolation);
    }
    
    public void testOpenSitesToTheWestOfFullSitesAreFullRegardlessOfGridSize() {
        Percolation percolation = new Percolation(6);
        
        assertOpenSitesToTheWestOfFullSitesAreFull(percolation);
    }

    private void assertOpenSitesToTheWestOfFullSitesAreFull(Percolation percolation) {
        int lastColumn = percolation.getGrid().length - 1;
        percolation.open(0, lastColumn);
        
        for (int column = lastColumn; column >= 0; column--) {
            assertFalse(percolation.isFull(1, column));
            percolation.open(1, column);
            assertTrue(percolation.isFull(1, column));
        }
    }
    
    
    
    
}
