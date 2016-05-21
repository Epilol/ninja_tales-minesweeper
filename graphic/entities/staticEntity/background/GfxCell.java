package saga.progetto.metodologie.core.graphic.entities.staticEntity.background;

import saga.progetto.metodologie.core.graphic.GfxLevel;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.Cell;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.MineOnWallException;

/**
 * 
 * The class {@code GfxCell} is the graphic implementation of {@link Cell}.
 *
 */
public abstract class GfxCell extends Cell 
{
	/**
	 * The cell's height in pixels.
	 */
	public static final int CELL_HEIGHT = 50;
	
	/**
	 * The cell's width in pixels.
	 */
	public static final int CELL_WIDTH = 50;
	
	/**
	 * The size of the texture's overlay.
	 */
	public static final float OVERLAY = 25;
	
	private Cell cell;
	
	public GfxCell(Cell cell) 
	{
		this.cell = cell;
	}
	
	/**
	 * Returns the logical counterpart.
	 * 
	 * @return the logical component.
	 * 
	 */
	public Cell getCell()
	{
		return cell;
	}
	
	@Override
	public int getX()
	{
		return getCell().getX();
	}
	
	@Override
	public int getY()
	{
		return getCell().getY();
	}
	
	@Override
	public void setX(int x)
	{
		getCell().setX(x);
	}

	@Override
	public void setY(int y)
	{
		getCell().setY(y);
	}

	@Override
	public boolean isWall() 
	{
		return getCell().isWall();
	}
	
	@Override
	public boolean isAccessible() 
	{
		return getCell().isAccessible();
	}

	@Override
	public boolean hasMine() 
	{
		return getCell().hasMine();
	}

	@Override
	public boolean hasFlag()
	{
		return getCell().hasFlag();
	}

	@Override
	public boolean isVisible() 
	{
		return getCell().isVisible();
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		getCell().setVisible(visible);
	}
	
	@Override
	public void setMine(boolean hasMine) 
	{
		getCell().setMine(hasMine);
	}

	@Override
	public int getNearbyMines() 
	{
		return getCell().getNearbyMines();
	}

	@Override
	public void increaseMineCount() 
	{
		getCell().increaseMineCount();
	}
	
	@Override
	public boolean setFlag()
	{
		return getCell().setFlag();
	}
	
	/**
	 * Returns whether the {@code flagLayer} of the cell is visible.
	 * 
	 * @return	true if the cell's {@code flagLayer} is visible.
	 */
	public abstract boolean hasGfxFlag();
	
	/**
	 * Removes the local cover of the cell.
	 * 
	 * @throws	MineOnWallException if the cell is a wall.
	 */
	public abstract void localReveal();
	
	/**
	 * Sets whether the mouse is pointing this cell.
	 * 
	 * @param	mouseover true if the cell is being pointed, false otherwise.
	 */
	public abstract void setMouseover(boolean mouseover);

	/**
	 * Called by {@link GfxLevel} on every update call.
	 * 
	 * @param	delta the time in milliseconds since the last call.
	 */
	public abstract void update(int delta);
}
