package unitTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import base.Cell;

/**
 * Unit test for Cell class
 * 
 * @author Ramón Carrasco Muñoz
 *
 */
class CellTest {

	@Test
	void ACellWithOnlyCoordinatesBornsDead() {
		Cell a = new Cell(1, 1);
		assertTrue("A cell with only coordinates borns dead", a.isDead());
	}
	
	@Test
	void IfACellIsAliveItIsntDead() {
		Cell a = new Cell(true, 1, 1);
		assertFalse("A cell can't be alive and dead", a.isAlive() == a.isDead());
	}
	
	@Test
	void NewCellWithAliveParameterTrueIsAlive() {
		Cell a = new Cell(true, 1, 1);
		assertTrue("A cell initialized with alive=true must be alive", a.isAlive() == true);
		assertFalse("A cell initialized with alive=true can't be dead", a.isDead() == true);
	}
	
	@Test
	void ALivingCellMustReturnHashOnToString() {
		Cell a = new Cell(true, 1, 1);
		assertTrue("A living cell must return # with toString()", a.isAlive() && a.toString().equals("#"));
	}
	
	@Test
	void ADeadCellMustReturnDotOnToString() {
		Cell a = new Cell(false, 1, 1);
		assertTrue("A dead cell must return · with toString()", a.isDead() && a.toString().equals("·"));
	}
	
	@Test
	void TwoCellsInSamePositionAreEqual() {
		Cell a = new Cell(true, 1, 1);
		Cell b = new Cell(false, 1, 1);
		assertTrue("Two cells in same position are equal", a.equals(b));
	}
	
	@Test
	void TwoCellsInDifferentPossitionAreNotEqual() {
		Cell a = new Cell(true, 1, 1);
		Cell b = new Cell(true, 1, 2);
		assertFalse("Two cells in different possition are not equal", a.equals(b));
	}
	
	@Test
	void TwoEqualCellsReturnSameHashCode() {
		Cell a = new Cell(true, 1, 1);
		Cell b = new Cell(false, 1, 1);
		assertTrue("Two equal cells must have the same hash code", a.equals(b) && a.hashCode() == b.hashCode());
	}

	@Test
	void TwoCellsAreNeighboursIfTheyDifferOneCoordinate() {
		Cell orig = new Cell(5, 5);
		assertTrue("Two cells are neighbours if they differ one position", orig.isNeighbour(new Cell(4, 4)));
		assertTrue("Two cells are neighbours if they differ one position", orig.isNeighbour(new Cell(4, 5)));
		assertTrue("Two cells are neighbours if they differ one position", orig.isNeighbour(new Cell(4, 6)));
		assertTrue("Two cells are neighbours if they differ one position", orig.isNeighbour(new Cell(5, 4)));
		assertTrue("Two cells are neighbours if they differ one position", orig.isNeighbour(new Cell(5, 6)));
		assertTrue("Two cells are neighbours if they differ one position", orig.isNeighbour(new Cell(6, 4)));
		assertTrue("Two cells are neighbours if they differ one position", orig.isNeighbour(new Cell(6, 5)));
		assertTrue("Two cells are neighbours if they differ one position", orig.isNeighbour(new Cell(6, 6)));
	}
	
	@Test
	void TwoCellsAreNotNeighboursIfTheyDifferMoreThanOneCoordinate() {
		Cell orig = new Cell(5, 5);
		assertFalse("Two cells aren't neighbors if they differ more than one position", orig.isNeighbour(new Cell(3, 3)));
		assertFalse("Two cells aren't neighbors if they differ more than one position", orig.isNeighbour(new Cell(3, 4)));
		assertFalse("Two cells aren't neighbors if they differ more than one position", orig.isNeighbour(new Cell(3, 5)));
		assertFalse("Two cells aren't neighbors if they differ more than one position", orig.isNeighbour(new Cell(7, 3)));
		assertFalse("Two cells aren't neighbors if they differ more than one position", orig.isNeighbour(new Cell(7, 4)));
		assertFalse("Two cells aren't neighbors if they differ more than one position", orig.isNeighbour(new Cell(7, 5)));
	}
	
	@Test
	void TwoCellsAreNotNeighborsIfTheyAreEqual() {
		Cell orig = new Cell(5, 5);
		assertFalse("Two cells aren't neighbors if they are equal", orig.isNeighbour(new Cell(5, 5)));
	}
	
	@Test
	void CallingReviveMakesTheCellBeAlive() {
		Cell a = new Cell(false, 5, 5);
		assertTrue("The cell borns dead", a.isDead());
		a.revive();
		assertTrue("Calling revive makes the cell be alive", a.isAlive());
	}
	
	@Test
	void CallingKillMakesTheCellBeDead() {
		Cell a = new Cell(true, 5, 5);
		assertTrue("The cell borns alive", a.isAlive());
		a.kill();
		assertTrue("Calling kill makes the cell be dead", a.isDead());
	}
	
}
