package saga.progetto.metodologie.core.logic.entities.staticEntity.background;

/**
 * 
 * The class {@code Wall} represents an unaccessible cell for both players and enemies. 
 * Walls may not contain mines or flags. Walls are used by {@linkplain saga.progetto.metodologie.core.logic.Level Level} 
 * to compose the background.
 *
 */
public class Wall extends Cell 
{

	public Wall(int x, int y) 
	{
		setX(x);
		setY(y);
	}

	@Override
	public boolean isWall() 
	{
		return true;
	}
	
	@Override
	public boolean isAccessible()
	{
		return false;
	}

	@Override
	public boolean hasMine() 
	{
		return false;
	}

	@Override
	public boolean hasFlag() 
	{
		return false;
	}

	@Override
	public boolean isVisible() 
	{
		return false;
	}

	@Override
	public boolean setFlag()
	{
		return false;
	}

	@Override
	public void setVisible(boolean visible)
	{
		throw new WallVisibilityException();
	}

	@Override
	public int getNearbyMines() 
	{
		return 0;
	}

	@Override
	public void setMine(boolean hasMine) 
	{
		throw new MineOnWallException("Cannot set mines on walls.");
	}

	@Override
	public void increaseMineCount() 
	{
		throw new MineOnWallException("Cannot increase mine count of walls.");
	}

}
