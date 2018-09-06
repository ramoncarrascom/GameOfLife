package base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	 * Calculates and returns next generation grid
	 * @return Next generation grid
	 */
	public void nextGeneration()
	{
		ArrayList<Cell> result = new ArrayList<>();
		
		this.current
			.stream()
			.forEach(x -> {
				result.add(applySurvivingRules(x.clone()));
			});
		
		this.current = result;
	}	

	/**
	 * Kills cell in selected coordinates
	 * @param coordX Cell's X coordinate
	 * @param coordY Cell's Y coordinate
	 */
	public void killCell(int coordX, int coordY)
	{
		Optional<Cell> cell = this.current
									.stream()
									.filter(x -> x.getX() == coordX && x.getY() == coordY)
									.findFirst();
		
		cell.ifPresent(x -> x.kill());
	}
	
	/**
	 * Revives cell in selected coordinates
	 * @param coordX Cell's X coordinate
	 * @param coordY Cell's Y coordinate
	 */
	public void reviveCell(int coordX, int coordY)
	{
		Optional<Cell> cell = this.current
									.stream()
									.filter(x -> x.getX() == coordX && x.getY() == coordY)
									.findFirst();
		
		cell.ifPresent(x -> x.revive());
	}
	
	/**
	 * Overriden version of toString
	 */
	@Override
	public String toString()
	{
		StringBuilder resp = new StringBuilder();
		int filaActual = 1;
		
		List<Cell> currentGrid = this.current
									.stream()
									.sorted()
									.collect(Collectors.toList());
		
		Iterator<Cell> gridIterator = currentGrid.iterator();		
		
		while(gridIterator.hasNext())
		{
			Cell celda = gridIterator.next();
			if (celda.getX() != filaActual)
			{
				resp.append("\n");
				filaActual = celda.getX();
			}
			
			// resp.append("(" + celda.getX() + "," + celda.getY() + ")");
			resp.append(celda);				
		}
			
		return resp.toString();
	}
	
	/**
	 * Applies surviving rules to the cell passed as parameter
	 * @param cell The cell to kill or revive
	 * @return Cell with updated status
	 */
	private Cell applySurvivingRules(Cell cell)
	{
		int neighbors = (int)this.current
				.stream()
				.filter(x -> x.isAlive() && cell.isNeighbor(x))
				.count();
		
		if (cell.isAlive())
		{
			if (neighbors < 2 || neighbors > 3)
			{
				cell.kill();
			}
		}
		else if (cell.isDead() && neighbors == 3)
		{
			cell.revive();
		}
		
		return cell;
	}
	
}
