package saga.progetto.metodologie.core.logic.entities.staticEntity.background;

import saga.progetto.metodologie.core.logic.entities.staticEntity.StaticEntity;

/**
 * The class {@code Cell} represents a generic cell. Cells are used by {@linkplain saga.progetto.metodologie.core.logic.Level Level} 
 * to compose the background.
 */
public abstract class Cell extends StaticEntity
{	
	
	/**
	 * Utility method used to avoid the use of <i>instanceof</i>.
	 * 
	 * @return	true if the cell is a wall, false otherwise.
	 */
	public abstract boolean isWall();
	
	/**
	 * A cell is accessible if is a {@link NormalCell} and doesn't have a flag on it.
	 * 
	 * @return	true if the cell is accessible, false otherwise.
	 */
	public abstract boolean isAccessible();
	
	/**
	 * Checks the cell for mines.
	 * 
	 * @return	true if the cell contains a mine, false otherwise. 
	 */
	public abstract boolean hasMine(); 
	
	/**
	 * Checks the cell for flags.
	 * 
	 * @return	true if the cell has a flag, false otherwise. 
	 */
	public abstract boolean hasFlag();
	
	/**
	 * Sets or removes a mine from this cell.
	 * 
	 * @param	hasMine true if a mine needs to be added, false otherwise.
	 * @throws	MineOnWallException if the cell is a {@link Wall}.
	 */
	public abstract void setMine(boolean hasMine);
	
	/**
	 * Inverts, if possible, the current flag status of the cell.
	 * 
	 * @return	true if a flag is set, false otherwise.
	 */
	public abstract boolean setFlag(); 
	
	/**
	 * Returns the number of nearby cells containing a mine. Returns 0 if the cell is a {@link Wall}.
	 * 
	 * @return	the number of nearby mines.
	 */
	public abstract int getNearbyMines();
	
	/**
	 * Increases the mine count on this cell by one.
	 * 
	 * @throws	MineOnWallException if the cell is a {@link Wall}.
	 */
	public abstract void increaseMineCount();
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null && !(o instanceof Cell)) return false;
		return ((Cell) o).getX() == getX() && ((Cell) o).getY() == getY();
	}
}
