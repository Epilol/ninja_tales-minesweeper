package saga.progetto.metodologie.core.logic.entities.movingEntity;

import pythagoras.f.Point;
import saga.progetto.metodologie.core.logic.entities.Entity;

/**
 * 
 * The class {@code MovingEntity} represents generic entities that are able to move. 
 *
 */
public abstract class MovingEntity extends Entity 
{
	private float speedModifier = 1.0f;
	private Direction direction = Direction.DEFAULT;
	private boolean isDead;
	
	/**
	 * Returns the moving speed of the entity.
	 * 
	 * @return	the moving speed of the entity.
	 */
	public abstract float getSpeed();
	
	/**
	 * Returns whether the entity can fly.
	 * 
	 * @return	true if the entity can fly.
	 */
	public abstract boolean canFly();
	
	/**
	 * The {@code speedModifier} is a modifier used to increase or decrease the speed of a moving entity.
	 * 
	 * @return	the current {@code speedModifier} value.
	 */
	public float getSpeedModifier()
	{
		return speedModifier;
	}
	
	/**
	 * Modifies the current {@code speedModifier} value.
	 * 
	 * @param	speedModifier the new modifier.
	 */
	public void setSpeedModifier(float speedModifier)
	{
		this.speedModifier = speedModifier;
	}
	
	/**
	 * Returns whether this entity is dead.
	 * 
	 * @return	true if the entity is dead, false otherwise.
	 */
	public boolean isDead()
	{
		return isDead;
	}
	
	/**
	 * Sets whether the entity is dead.
	 * 
	 * @param	isDead true to kill the entity, false otherwise.
	 */
	public void setDead(boolean isDead)
	{
		this.isDead = isDead;
	}
	
	/**
	 * Returns the current {@link Direction} of the moving entity.
	 * 
	 * @return	the current {@link Direction}.
	 */
	public Direction getDirection()
	{
		return direction;
	}
	
	/**
	 * Updates the direction of the entity based on the {@code destination}.
	 * 
	 * @param	destination the destination's coordinates.
	 */
	public void setDirection(Point destination)
	{
		if (destination.x < getX() && destination.y < getY())
			direction = Direction.TOP_LEFT;
		else if (destination.x > getX() && destination.y < getY())
			direction = Direction.TOP_RIGHT;
		else if (destination.x < getX() && destination.y > getY())
			direction = Direction.BOTTOM_LEFT;
		else if (destination.x > getX() && destination.y > getY())
			direction = Direction.BOTTOM_RIGHT;
		else if (destination.y > getY())
			direction = Direction.DOWN;
		else if (destination.y < getY())
			direction = Direction.UP;
		else if (destination.x > getX())
			direction = Direction.RIGHT;
		else if (destination.x < getX())
			direction = Direction.LEFT;
		else
			direction = Direction.DEFAULT;
	}
	
	/**
	 * Sets a new {@link Direction} for the entity.
	 * 
	 * @param	direction the new {@link Direction}.
	 */
	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}
}
