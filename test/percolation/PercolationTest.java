package percolation;

import junit.framework.TestCase;

import java.util.Arrays;


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
        percolation.open(4, 3);
        
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
        percolation.open(4, 2);
        
        assertTrue(percolation.isOpen(4, 2));
        assertFalse(percolation.isOpen(1, 1));
    }
    
    public void testOpenSitesOnTheTopRowAreFull() {
        assertFalse(percolation.isFull(1, 3));
        assertFalse(percolation.isFull(3, 4));
        
        percolation.open(1, 3);
        percolation.open(3, 4);
        
        assertTrue(percolation.isFull(1, 3));
        assertFalse(percolation.isFull(3, 4));
    }
    
    public void testSitesThatAreSouthOfAFullSiteAreFull() {
        percolation.open(1, 2);

        for(int row = 2; row <= percolation.gridSize(); row++) {
            assertFalse(percolation.isFull(row, 2));
            
            percolation.open(row, 2);
            
            assertTrue(percolation.isFull(row, 2));
        }
    }

    public void testOpenSitesToTheEastOfTheFullSitesAreFull() {
        percolation.open(1,1);
        for (int column = 1; column <= percolation.gridSize(); column++) {
            assertFalse(percolation.isFull(2, column));
            percolation.open(2, column);
            assertTrue(String.format("Site (%s, %s) should be full", 2, column), percolation.isFull(2, column));
        }
    }
    
    public void testOpenSitesDiagonalToFullSitesAreNotFull() {
        percolation.open(1, 1);
        percolation.open(2, 2);
        
        assertFalse(percolation.isFull(2, 2));
    }
    
    public void testChecksSouthForFullness() {
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(3, 3);
        percolation.open(3, 4);
        percolation.open(2, 4);
        percolation.open(1, 4);
        
        assertTrue(percolation.isFull(2,2));
    }
    
    public void testOpenSitesToTheWestOfTheFullSitesAreFull() {
        assertOpenSitesToTheWestOfFullSitesAreFull(percolation);
    }
    
    public void testOpenSitesToTheWestOfFullSitesAreFullRegardlessOfGridSize() {
        Percolation percolation = new Percolation(6);
        
        assertOpenSitesToTheWestOfFullSitesAreFull(percolation);
    }
    
    public void testPercolatesIfOneColumnIsOpen() {
    	int gridSize = 5;
    	
		for(int i = 1; i <= gridSize; i++) {
    		assertPercolatesWhenColumnOpen(new Percolation(gridSize), i);
    	}
    }

	private void assertPercolatesWhenColumnOpen(Percolation percolation, int column) {
		assertFalse(percolation.percolates());
    	
		percolation.open(1, column);
    	assertFalse(percolation.percolates());
    	
    	percolation.open(2, column);
    	assertFalse(percolation.percolates());
    	
    	percolation.open(3, column);
    	assertFalse(percolation.percolates());
    	
    	percolation.open(4, column);
    	assertFalse(percolation.percolates());
    	
    	percolation.open(5, column);
    	
    	String message = String.format("Column %s should percolate.", column);
		assertTrue(message, percolation.percolates());
	}
	
	public void testIfExampleGridPercolates() {
		int [][] openSites = new int [][] {
			{1, 3}, {1, 4}, {1, 5},
			{2, 1}, {2, 4}, {2, 5}, {2, 6}, {2, 7}, {2, 8},
			{3, 1}, {3, 2}, {3, 3}, {3, 6}, {3, 7},
			{4, 3}, {4, 4}, {4, 6}, {4, 7}, {4, 8},
			{5, 2}, {5, 3}, {5, 4}, {5, 6}, {5, 7},
			{6, 2}, {6, 7}, {6, 8},
			{7, 1}, {7, 3}, {7, 5}, {7, 6}, {7, 7}, {7, 8},
			{8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 6}
		};

        Percolation percolation = eightByEightGridWithOpenSites(openSites);

		assertTrue(percolation.percolates());	
	}
	
	public void testNonPercolatingGridFromAssignment() {
        int[][] openSites = {
                {1, 3}, {1, 4}, {1, 6},
                {2, 1}, {2, 2}, {2, 3}, {2, 4}, {2, 5}, {2, 6},
                {3, 1}, {3, 2}, {3, 6}, {3, 7},
                {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7},
                {5, 1}, {5, 7}, {5, 8},
                {6, 2}, {6, 4}, {6, 5}, {6, 6},
                {7, 2}, {7, 3}, {7, 5}, {7, 6}, {7, 8},
                {8, 1}, {8, 3}, {8, 7}
        };
        Percolation percolation = eightByEightGridWithOpenSites(openSites);

        assertFalse(percolation.percolates());
	}

    public void testOpenThrowsAnOutOfBoundsExceptionIfAskedAboutCellsBeyondTheDimensionsOfTheGrid() throws Exception {
        try {
            percolation.open(6, 6);
            fail("Should have thrown an exception when asking to open cell (6, 6).");
        } catch (IndexOutOfBoundsException e) {
            // test passes
        }
    }

    public void testIsOpenThrowsAnOutOfBoundsExceptionIfAskedAboutCellsBeyondTheDimensionsOfTheGrid() throws Exception {
        try {
            percolation.isOpen(6,6);
            fail("Should have thrown an exception when asking if cell (6, 6) is open");
        } catch (IndexOutOfBoundsException e) {
            // test passed
        }
    }

    public void testIsFullThrowsAnOutOfBoundsExceptionIfAskedAboutCellsBeyondTheDimensionsOfTheGrid() throws Exception {
        try {
            percolation.isFull(6,6);
            fail("Should have thrown an exception when asking if cell (6, 6) is full");
        } catch (IndexOutOfBoundsException e) {
            // test passed
        }
    }

    public void testThrowsExceptionWhenCreatingPercolationWithNonPositiveDimensions() throws Exception {
        try {
            new Percolation(0);
            fail("Should have thrown an exception when constructing percolation.Percolation with nonpositive dimensions");
        } catch (IllegalArgumentException e) {
            // test passed
        }

    }

    private Percolation eightByEightGridWithOpenSites(int[][] openSites) {
        Percolation percolation = new Percolation(8);

        for (int[] siteWeWantToOpen : openSites) {
            percolation.open(siteWeWantToOpen[0], siteWeWantToOpen[1]);
        }
        return percolation;
    }

    private void assertOpenSitesToTheWestOfFullSitesAreFull(Percolation percolation) {
        int lastColumn = percolation.getGrid().length;
        percolation.open(1, lastColumn);
        
        for (int column = lastColumn; column >= 1; column--) {
            assertFalse(percolation.isFull(2, column));
            percolation.open(2, column);
            assertTrue(percolation.isFull(2, column));
        }
    }


}
