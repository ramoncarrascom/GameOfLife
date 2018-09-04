package base;

import java.util.ArrayList;

public class Grid {

	private int maxX, maxY;
	private ArrayList<Cell> current;
	
	/**
	 * Grid constructor. Initializes the current grid.
	 * @param maxX max x position
	 * @param maxY max y position
	 */
	public Grid(int maxX, int maxY)
	{
		if (maxX <= 0 || maxY <= 0)
			throw new IllegalArgumentException("Grid's dimensions must be positive");
		
		this.maxX = maxX;
		this.maxY = maxY;
		
		initialize();
	}
	
	/**
	 * Initializes current grid with dead cells
	 */
	public void initialize()
	{
		this.current = new ArrayList<>();
		
		for(int x = 1; x <= maxX; x++)
			for(int y = 1; y <= maxY; y++)
				this.current.add(new Cell(x, y));
	}
	
	/**
	 * Returns the cell in supplied coordinates
	 * @param coordX X coordinate
	 * @param coordY Y coordinate
	 * @return Cell in coordinates
	 */
	public Cell getCell(int coordX, int coordY)
	{
		return this.current
					.stream()
					.filter(x -> x.getX() == coordX && x.getY() == coordY)
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Cell doesn't exist"));
	}
}
