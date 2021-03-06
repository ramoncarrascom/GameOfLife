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
 * @author Ram�n Carrasco Mu�oz
 *
 */
public class GameOfLife {

	public static void main(String[] args) throws InterruptedException {
		
		boolean playing;
		Grid game = new Grid(20, 20);
		
		game.randomInitialize(70);
		playing = true;
		
		while (playing) {
			
			Grid current = game.clone();
			game.nextGeneration();
			
			if (game.equals(current))
				playing = false;
			
			if (playing) {
				System.out.println(game);
				Thread.sleep(1000);
			}			
		}
	}

}
