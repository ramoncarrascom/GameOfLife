package base;

/**
 * Cell class for Conway's Game of Life
 * 
 * @author Ramón Carrasco Muñoz
 *
 */
public class Cell implements Comparable<Cell> {
	
	/**
	 * Alive cell marker
	 */
	private boolean alive;
	
	/**
	 * Cell's position
	 */
	private int coordX, coordY;	
	
	/**
	 * Constructor. When only passed coordinates, the cell borns dead
	 * @param coordX indicates coordX
	 * @param coordY indicates coordY
	 */
	public Cell(int coordX, int coordY) {
		this(false, coordX, coordY);
	}
	
	/**
	 * Constructor
	 * @param alive indicates cell status
	 * @param coordX indicates coordX
	 * @param coordY indicates coordY
	 */
	public Cell(boolean alive, int coordX, int coordY) {
		if (coordX <= 0 || coordY <= 0)
			throw new IllegalArgumentException("Cells position must be positive");
		
		this.alive = alive;
		this.coordX = coordX;
		this.coordY = coordY;
	}
	
	/**
	 * Gets X coordinate
	 * @return X coordinate
	 */
	public int getX() {
		return coordX;
	}
	
	/**
	 * Getx Y coordinate
	 * @return Y coordinate
	 */
	public int getY() {
		return coordY;
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
	
	/**
	 * Calculates if the cell passed as parameter is a direct neighbour of the current cell
	 * @param other the other cell to check
	 * @return true is other is current cell's neighbour
	 */
	public boolean isNeighbour(Cell other) {
		
		if (this.equals(other))
			return false;
		
		if ((other.coordX >= this.coordX - 1) &&
			(other.coordX <= this.coordX + 1) &&
			(other.coordY >= this.coordY - 1) &&
			(other.coordY <= this.coordY + 1))
			return true;
		
		return false;
	}
	
	/**
	 * Kills the current cell
	 */
	public void kill() {
		this.alive = false;
	}
	
	/**
	 * Revives the current cell
	 */
	public void revive() {
		this.alive = true;
	}
	
	/**
	 * Object's toString override. Returns # character if the cell
	 * is alive, or · if it's dead
	 */
	@Override
	public String toString() {
		if (alive)
			return "#";
		else
			return "·";
	}
	
	/**
	 * Object's equals override. Two cells are equal if they are in the same coord
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Cell)) {
			return false;
		}
		
		return (this.coordX == ((Cell)other).coordX && this.coordY == ((Cell)other).coordY);
	}
	
	/**
	 * Object's hashCode override. Hashcode is calculated from coordX and coordY
	 */
	@Override
	public int hashCode() {		
		int res = (((this.coordX + this.coordY) * (this.coordX + this.coordY + 1)) / 2) + this.coordY;
		return res;
	}
	
	/**
	 * Clones current cell to a new one with same state
	 */
	public Cell clone() {
		return new Cell(alive, coordX, coordY);
	}

	/**
	 * Compares two cells
	 */
	@Override
	public int compareTo(Cell other) {
		if (this.coordX < other.coordX)
		{
			return -1;
		}
		else if (this.coordX > other.coordX)
		{
			return +1;
		}
		else if (this.coordX == other.coordX)
		{
			if (this.coordY < other.coordY)
			{
				return -1;
			}
			else if (this.coordY > other.coordY)
			{
				return +1;
			}
			else
			{
				return 0;
			}
		}
		return 0;
	}

}
