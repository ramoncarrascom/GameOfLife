package base;

public class Cell {
	
	/**
	 * Alive cell marker
	 */
	private boolean alive;
	
	/**
	 * No parameter constructor. The cell is alive.
	 */
	public Cell() {
		this.alive = true;
	}
	
	/**
	 * Constructor
	 * @param alive indicates cell status
	 */
	public Cell(boolean alive) {
		this.alive = alive;
	}
	
	/**
	 * Indicates if a cell is alive
	 * @return true if cell is alive
	 */
	public boolean isAlive() {
		if(alive)
			return true;
		else
			return false;
	}
	
	/**
	 * Indicates if a cell is dead
	 * @return true if cell is dead
	 */
	public boolean isDead() {
		if(alive)
			return false;
		else
			return true;
	}

}
