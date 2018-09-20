package base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Grid {

	/**
	 * Grid's max positions
	 */
	private int maxX, maxY;
	
	/**
	 * Current grid contents
	 */
	private ArrayList<Cell> current;
	
	/**
	 * Current grid generation
	 */
	private int generation;
	
	//-------------------------------- CONSTRUCTORS
	
	/**
	 * Grid constructor. Initializes the current grid.
	 * @param maxX max x position
	 * @param maxY max y position
	 */
	public Grid(int maxX, int maxY)
	{
		if (maxX <= 0 || maxY <= 0)
			throw new IllegalArgumentException("Please think positive!");
		
		this.maxX = maxX;
		this.maxY = maxY;
		
		initialize();
	}
	

	
	//-------------------------------- GETTERS & SETTERS
	
	/**
	 * Gets current grid generation
	 * @return Current generation
	 */
	public int getGeneration() {
		return generation;
	}
	
	/**
	 * Gets a copy of the current grid
	 * @return Current grid contents
	 */
	public ArrayList<Cell> getCurrent() {
		ArrayList<Cell> resp = new ArrayList<Cell>();
		current.stream().forEach(x -> {
			resp.add(x.clone());
		});
		return resp;
	}

	/**
	 * Get maxX value
	 * @return maxX value
	 */
	public int getMaxX() {
		return maxX;
	}

	/**
	 * Get maxY value
	 * @return maxY value
	 */
	public int getMaxY() {
		return maxY;
	}
	
	/**
	 * Gets the cell in specified position
	 * @param coordX X coordinate
	 * @param coordY Y coordinate
	 * @return Returns the specified cell, or null if it doesn't exist
	 */
	public Cell getCell(int coordX, int coordY) {
		Optional<Cell> resp = current.stream()
									.filter(x -> x.getX() == coordX && x.getY() == coordY)
									.findFirst();
		return (resp.orElse(null)).clone();
	}
	
	
	//-------------------------------- GRID-RELATED
	
	/**
	 * Initializes current grid with dead cells
	 */
	public void initialize()
	{
		this.current = new ArrayList<>();
		
		for(int x = 1; x <= maxX; x++)
			for(int y = 1; y <= maxY; y++)
				this.current.add(new Cell(x, y));
		
		this.generation = 0;
	}
	
	/**
	 * Initializes a random grid
	 * @param percentage Percentage of living cells betwenn 10 and 90
	 */
	public void randomInitialize(int percentage) {
		this.initialize();
		
		if (percentage < 10 || percentage > 90)
			throw new IllegalArgumentException("Percentage must be an int between 10 and 90");
		
		for (int i = 0; i < (maxX * percentage) / 100; i++)
			for (int j = 0; j < (maxY * percentage) / 100; j++)
				this.switchCell(new Random().nextInt(maxX), new Random().nextInt(maxY));
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
		generation++;
	}	

	//-------------------------------- CELL-RELATED
	
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
	 * Switches cell status in selected coordinates
	 * @param coordX Cell's X coordinate
	 * @param coordY Cell's Y coordinate
	 */
	public void switchCell(int coordX, int coordY)
	{
		Optional<Cell> cell = this.current
									.stream()
									.filter(x -> x.getX() == coordX && x.getY() == coordY)
									.findFirst();
		
		cell.ifPresent(x -> { if(x.isAlive()) x.kill(); else x.revive(); });
	}
	

	
	/**
	 * Applies surviving rules to the cell passed as parameter
	 * @param cell The cell to kill or revive
	 * @return Cell with updated status
	 */
	private Cell applySurvivingRules(Cell cell)
	{
		int neighbours = (int)this.current
				.stream()
				.filter(x -> x.isAlive() && cell.isNeighbour(x))
				.count();
		
		if (cell.isAlive())
		{
			if (neighbours < 2 || neighbours > 3)
			{
				cell.kill();
			}
		}
		else if (cell.isDead() && neighbours == 3)
		{
			cell.revive();
		}
		
		return cell;
	}
	
	// ----------------------------------- OBJECT OVERRIDES
	
	/**
	 * Overriden version of toString
	 */
	@Override
	public String toString()
	{
		StringBuilder resp = new StringBuilder();
		resp.append(generation);
		int filaActual = -1;
		
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
	 * Overriden version of equals
	 */
	@Override
	public boolean equals(Object other) {
		
		boolean distintas = false;
		
		if (!(other instanceof Grid)) {
			return false;
		}
		
		if (this.maxX != ((Grid)other).maxX || this.maxY != ((Grid)other).maxY) {
			return false;
		}
		
		for (int i = 1; i <= maxX; i++)
			for (int j = 1; j <= maxY; j++)
				if (!this.getCell(i, j).equals(((Grid)other).getCell(i, j)))
					distintas = true;
		
		if (distintas)
			return false;
		else
			return true;
	}
	
	/**
	 * Clones current grid to a new one
	 */
	public Grid clone() {		
		Grid resp = new Grid(this.maxX, this.maxY);
		
		this.current.stream().forEach(x -> {
				if (x.isAlive())
					resp.reviveCell(x.getX(), x.getY());
				else
					resp.killCell(x.getX(), x.getY());
			});
		
		return resp;
	}
	
}
