import base.Grid;

/**
 * Main class of Conway's Game Of Life
 * 
 * Your task is to write a program to calculate the next generation of Conway's game of life, 
 * given any starting position. 
 * 
 * You start with a two dimensional grid of cells, where each cell is either alive or dead. 
 * The grid is finite, and no life can exist off the edges. When calculating the next 
 * generation of the grid, follow these four rules:
 * 
 * 1. Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
 * 2. Any live cell with more than three live neighbours dies, as if by overcrowding.
 * 3. Any live cell with two or three live neighbours lives on to the next generation.
 * 4. Any dead cell with exactly three live neighbours becomes a live cell.
 * 
 * @author Ramón Carrasco Muñoz
 *
 */
public class GameOfLife {

	public static void main(String[] args) throws InterruptedException {
		
		Grid juego = new Grid(10, 10);
		
		juego.randomInitialize(90);
		while (true) {
			juego.nextGeneration();
			System.out.println(juego);
			Thread.sleep(1000);
		}
	}

}
