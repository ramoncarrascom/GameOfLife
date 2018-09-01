package unitTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

import base.Cell;

class CellTest {

	@Test
	void NewCellWithNoParametersIsAlive() {
		Cell a = new Cell();
		assertTrue("Cell must initialize alive when no parameter constructor", a.isAlive() == true);
	}
	
	@Test
	void IfACellIsAliveItIsntDead() {
		Cell a = new Cell();
		assertFalse("A cell can't be alive and dead", a.isAlive() == a.isDead());
	}
	
	@Test
	void NewCellWithAliveParameterTrueIsAlive() {
		Cell a = new Cell(true);
		assertTrue("A cell initialized with alive=true must be alive", a.isAlive() == true);
		assertFalse("A cell initialized with alive=false can't be dead", a.isDead() == true);
	}

}
