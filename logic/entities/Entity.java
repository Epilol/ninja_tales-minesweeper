package saga.progetto.metodologie.core.logic.entities;

/**
 * 
 * The class {@code Entity} represents generic entities with coordinates. 
 *
 */
public abstract class Entity
{
	private int x;
	private int y;
	private boolean isVisible;
	
	/**
	 * Returns the horizontal axis' coordinate.
	 * 
	 * @return	the x coordinate.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Returns the vertical axis' coordinate.
	 * 
	 * @return	the y coordinate.
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Sets a new x coordinate.
	 * 
	 * @param	x the x coordinate to be set.
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * Sets a new y coordinate.
	 * 
	 * @param	y the y coordinate to be set.
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * Sets the visibility of the entity.
	 * 
	 * @param 	visible true if willing to display the entity, false otherwise.
	 * @throws	WallVisibilityException if the entity is a {@linkplain saga.progetto.metodologie.core.logic.entities.staticEntity.background.Wall Wall}.
	 */
	public void setVisible(boolean visible)
	{
		isVisible = visible;
	}
	
	/**
	 * Returns whether the entity is visible.
	 * 
	 * @return	true if the entity is visible, false otherwise.
	 */
	public boolean isVisible()
	{
		return isVisible;
	}
}
