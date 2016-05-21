package saga.progetto.metodologie.core.logic.entities.staticEntity.background;

/**
 * 
 * The class {@code NormalCell} represents an accessible cell for both players and enemies. 
 * Normal cells may contain mines. NormalCells are used by {@linkplain saga.progetto.metodologie.core.logic.Level Level} 
 * to compose the background.
 *
 */
public class NormalCell extends Cell 
{
	private boolean hasMine;
	private boolean hasFlag;
	private int nearbyMines;
	
	public NormalCell(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	@Override
	public boolean isWall() 
	{
		return false;
	}
	
	@Override
	public boolean isAccessible()
	{
		return !hasFlag;
	}
	
	@Override
	public boolean hasMine() 
	{ 
		return hasMine;
	}
	
	@Override
	public boolean hasFlag() 
	{
		return hasFlag;
	}
	
	@Override
	public void setMine(boolean hasMine)
	{ 
		this.hasMine = hasMine;
	}
	
	@Override
	public boolean setFlag()
	{
		if (isVisible() && !hasFlag) return false;
		hasFlag = !hasFlag;
		return hasFlag;
	}
	
	@Override
	public void setVisible(boolean visible)
	{ 
		super.setVisible(visible);
		if(visible)
			hasFlag = false;
	}

	@Override
	public int getNearbyMines() 
	{
		return nearbyMines;
	}
	
	@Override
	public void increaseMineCount()
	{
		nearbyMines++;
	}
}
