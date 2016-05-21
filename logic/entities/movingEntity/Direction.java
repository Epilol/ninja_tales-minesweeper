package saga.progetto.metodologie.core.logic.entities.movingEntity;

/**
 * 
 * The enumeration {@code Direction} represents the direction of a {@link MovingEntity}.
 *
 */
public enum Direction 
{
	UP(0, -1, 1, 0), 
	TOP_LEFT(-1, -1, 0.625f, 0),
	LEFT(-1, 0, 1, 1), 
	BOTTOM_LEFT(-1, 1, 0.625f, 1), 
	DOWN(0, 1, 1, 2), 
	BOTTOM_RIGHT(1, 1, 0.625f, 2), 
	RIGHT(1, 0, 1, 3), 
	TOP_RIGHT(1, -1, 0.625f, 3), 
	DEFAULT(0, 0, 1, 0);
	
	private int x;
	private int y;
	private float speedAdjustment;
	private int utilityIndex;
	
	Direction(int x, int y, float speedAdjustment, int utilityIndex)
	{
		this.x = x;
		this.y = y;
		this.speedAdjustment = speedAdjustment;
		this.utilityIndex = utilityIndex;
	}
	
	/**
	 * Returns the direction offset on the x axis.
	 * 
	 * @return	the direction on the x axis.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Returns the direction offset on the y axis.
	 * 
	 * @return	the direction on the y axis.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * The speed in the diagonals should be slowed by {@code 2^1/2}, but this creates problems with the movement.
	 * Needs further testing.
	 * 
	 * @return	the speed adjustment needed for the current direction.
	 */
	public float getSpeedAdjustment() 
	{
		return speedAdjustment;
	}
	
	/**
	 * Returns an utility index for graphic implementations of the game. May come in handy for sprite sheets.
	 * 
	 * @return	an utility index.
	 */
	public int getUtilityIndex()
	{
		return utilityIndex;
	}

}
