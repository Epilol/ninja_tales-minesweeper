package saga.progetto.metodologie.core.logic.entities.movingEntity;

/**
 * 
 * The class Enemy represents entities that will try to kill the player. 
 *
 */
public abstract class Enemy extends MovingEntity
{
	public Enemy(int x, int y)
	{
		setX(x);
		setY(y);
	}
}
