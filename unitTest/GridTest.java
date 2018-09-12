package unitTest;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import base.Grid;

class GridTest {

	@Test
	void GridBuildsUpAccordingToSpecifiedDimensions() {
		Grid test = new Grid(5, 5);
		assertTrue("Grid dimensions must match constructed dimensions (X)", test.getMaxX() == 5);
		assertTrue("Grid dimensions must match constructed dimensions (Y)", test.getMaxY() == 5);
	}
	
	@Test
	void GridBuildsUpWithAllCellsDied() {
		Grid test = new Grid(5, 5);
		assertTrue("Grid builds up with all its cells died", test.getCurrent().stream().filter(x -> x.isAlive()).count() == 0);
	}
	
	@Test
	void GridBuildsUpOn0Gen() {
		Grid test = new Grid(5, 5);
		assertTrue("Grid builds up on 0 generation", test.getGeneration() == 0);
	}
	
	@Test
	void EveryGridsPositionHasACell() {
		Grid test = new Grid(5, 5);
		assertTrue("Every grid position has a cell", test.getCurrent().stream().filter(x -> x == null).count() == 0);
	}
	
	@Test
	void EveryGridsCellIsDeadOrAlive() {
		Grid test = new Grid(5, 5);
		assertTrue("Every cell is dead or alive", test.getCurrent().stream().filter(x -> !(x.isAlive() || x.isDead())).count() == 0);
	}
	
	@Test
	void LivingCellWithFewerThan2NeighboursNextGenDied() {
		// ··········       ··········    Test's graphical explanation
		// ··········       ··········
		// ··········       ··········		All these cells will die, as they have only 1 or 0 neighbours
		// ··········       ··········
		// ····##····  ->   ··········
		// ··········       ··········
		// ··········       ··········
		// ·······#··       ··········
		// ··········       ··········
		// ··········       ··········		
		Grid test = new Grid(10, 10);
		test.reviveCell(5, 5);		// Cells with 1 neighbour
		test.reviveCell(5, 6);
		test.reviveCell(8, 8);		// Cell with 0 neighbours
		assertTrue("Living cells with 1 neighbour next generation dead - Checking (5, 5) alive", test.getCell(5, 5).isAlive() == true);
		assertTrue("Living cells with 1 neighbour next generation dead - Checking (5, 6) alive", test.getCell(5, 6).isAlive() == true);
		assertTrue("Living cells with 0 neighbours next generation dead - Checking (8, 8) alive", test.getCell(8, 8).isAlive() == true);		
		test.nextGeneration();
		assertTrue("Living cells with 1 neighbour next generation dead - Checking (5, 5) dead", test.getCell(5, 5).isDead() == true);
		assertTrue("Living cells with 1 neighbour next generation dead - Checking (5, 6) dead", test.getCell(5, 6).isDead() == true);
		assertTrue("Living cells with 0 neighbours next generation dead - Checking (8, 8) dead", test.getCell(8, 8).isDead() == true);
	}

	@Test
	void LivingCellWith2NeighboursNextGenAlive() {
		// ··········       ··········    Test's graphical explanation
		// ··········       ··········
		// ··········       ··········		Cells in (5, 6) and (5, 4) will die, but this is tested in other procedure
		// ··········       ····#·····		Cell in (5, 5) will stay alive cause it has exactly 2 neighbours
		// ···###····  ->   ····#·····		Cells in (4, 5) and (6, 5) come alive, but this is tested in other procedure
		// ··········       ····#·····
		// ··········       ··········
		// ··········       ··········
		// ··········       ··········
		// ··········       ··········	
		Grid test = new Grid(10, 10);
		test.reviveCell(5, 5);		// Cell with 2 neighbours
		test.reviveCell(5, 6);		// Cells with 1 neighbour
		test.reviveCell(5, 4);		
		assertTrue("Living cells with 2 neighbours next generation alive - Checking (5, 5) alive", test.getCell(5, 5).isAlive() == true);
		assertTrue("Living cells with 2 neighbours next generation alive - Checking (5, 6) alive", test.getCell(5, 6).isAlive() == true);
		assertTrue("Living cells with 2 neighbours next generation alive - Checking (5, 4) alive", test.getCell(5, 4).isAlive() == true);		
		test.nextGeneration();
		assertTrue("Living cells with 2 neighbours next generation alive - Checking (5, 5) alive", test.getCell(5, 5).isAlive() == true);
	}
	
	@Test
	void LivingCellWith3NeighboursNextGenAlive() {
		// ··········       ··········    Test's graphical explanation
		// ··········       ··········
		// ··········       ··········		Cells in (4, 5) and (5, 5) will stay alive cause they have exactly 3 neighbours
		// ····#·····       ···###····		Cells in (5, 4) and (5, 6) will stay alive, but this is tested in other procedure
		// ···###····  ->   ···###····		Cells in (4, 4), (4, 6) and (6, 5) will come alive, but this is tested in other procedure
		// ··········       ····#·····
		// ··········       ··········
		// ··········       ··········
		// ··········       ··········
		// ··········       ··········	
		Grid test = new Grid(10, 10);
		test.reviveCell(5, 5);		// Cells with 3 neighbours
		test.reviveCell(4, 5);
		test.reviveCell(5, 6);		// Cells with 2 neighbours
		test.reviveCell(5, 4);	
		assertTrue("Living cells with 3 neighbours next generation alive - Checking (4, 5) alive", test.getCell(4, 5).isAlive() == true);
		assertTrue("Living cells with 3 neighbours next generation alive - Checking (5, 5) alive", test.getCell(5, 5).isAlive() == true);
		assertTrue("Living cells with 3 neighbours next generation alive - Checking (5, 6) alive", test.getCell(5, 6).isAlive() == true);
		assertTrue("Living cells with 3 neighbours next generation alive - Checking (5, 4) alive", test.getCell(5, 4).isAlive() == true);		
		test.nextGeneration();
		assertTrue("Living cells with 3 neighbours next generation alive - Checking (5, 5) alive", test.getCell(5, 5).isAlive() == true);
		assertTrue("Living cells with 3 neighbours next generation alive - Checking (4, 5) alive", test.getCell(4, 5).isAlive() == true);
	}
	
	@Test
	void LivingCellWith4OrMoreNeighboursNextGenDead() {
		// ··········       ··········    Test's graphical explanation
		// ··········       ··········
		// ··········       ··········		Cell in (5, 5) will die cause it has 4 or more neighbours
		// ····#·····       ···###····		Cells in (5, 4), (5, 6), (4, 5) and (6, 5) will stay alive, but this is tested in other procedure
		// ···###····  ->   ···#·#····		Cells in (4, 4), (4, 6), (6, 4) and (6, 6) will come alive, but this is tested in other procedure
		// ····#·····       ···###····
		// ··········       ··········
		// ··········       ··········
		// ··········       ··········
		// ··········       ··········	
		Grid test = new Grid(10, 10);
		test.reviveCell(5, 5);		// Cell with 4 or more neighbours
		test.reviveCell(4, 5);		// Cells with 3 neighbours
		test.reviveCell(5, 6);		
		test.reviveCell(5, 4);
		test.reviveCell(6, 5);
		assertTrue("Living cells with 4 or more neighbours next generation dead - Checking (4, 5) alive", test.getCell(4, 5).isAlive() == true);
		assertTrue("Living cells with 4 or more neighbours next generation dead - Checking (5, 5) alive", test.getCell(5, 5).isAlive() == true);
		assertTrue("Living cells with 4 or more neighbours next generation dead - Checking (5, 6) alive", test.getCell(5, 6).isAlive() == true);
		assertTrue("Living cells with 4 or more neighbours next generation dead - Checking (5, 4) alive", test.getCell(5, 4).isAlive() == true);
		assertTrue("Living cells with 4 or more neighbours next generation dead - Checking (6, 5) alive", test.getCell(6, 5).isAlive() == true);
		test.nextGeneration();
		assertTrue("Living cells with 4 or more neighbours next generation dead - Checking (5, 5) dead", test.getCell(5, 5).isDead() == true);
	}
	
	@Test
	void DeadCellWith3NeighboursNextGenAlive() {
		// ··········       ··········    Test's graphical explanation
		// ··········       ··········
		// ··········       ··········		Cells in (5, 6) and (5, 4) will die, but this is tested in other procedure
		// ··········       ····#·····		Cell in (5, 5) will stay alive, but this is tested in other procedure
		// ···###····  ->   ····#·····		Cells in (4, 5) and (6, 5) come alive because they have 3 neighbours
		// ··········       ····#·····
		// ··········       ··········
		// ··········       ··········
		// ··········       ··········
		// ··········       ··········	
		Grid test = new Grid(10, 10);
		test.reviveCell(5, 5);		// Cell with 2 neighbours
		test.reviveCell(5, 6);		// Cells with 1 neighbour
		test.reviveCell(5, 4);		
		assertTrue("Dead cells with 3 neighbours next generation alive - Checking (5, 5) alive", test.getCell(5, 5).isAlive() == true);
		assertTrue("Dead cells with 3 neighbours next generation alive - Checking (5, 6) alive", test.getCell(5, 6).isAlive() == true);
		assertTrue("Dead cells with 3 neighbours next generation alive - Checking (5, 4) alive", test.getCell(5, 4).isAlive() == true);
		assertTrue("Dead cells with 3 neighbours next generation alive - Checking (4, 5) dead", test.getCell(4, 5).isDead() == true);
		assertTrue("Dead cells with 3 neighbours next generation alive - Checking (6, 5) dead", test.getCell(6, 5).isDead() == true);
		test.nextGeneration();
		assertTrue("Dead cells with 3 neighbours next generation alive - Checking (4, 5) alive", test.getCell(4, 5).isAlive() == true);
		assertTrue("Dead cells with 3 neighbours next generation alive - Checking (6, 5) alive", test.getCell(6, 5).isAlive() == true);
	}
}
