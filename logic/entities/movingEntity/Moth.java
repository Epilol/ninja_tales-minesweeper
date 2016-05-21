package saga.progetto.metodologie.core.logic.entities.movingEntity;

/**
 *
 * The Moth is an enemy entity. It will try to reach the {@linkplain saga.progetto.metodologie.core.logic.entities.movingEntity.Player Player} and kill him.
 * 
 */
public class Moth extends Enemy 
{
	private static final float MOTH_SPEED = 1.0f;
	
	public Moth(int x, int y)
	{
		super(x, y);
	}
	
	@Override
	public float getSpeed()
	{ 
		return MOTH_SPEED * getSpeedModifier(); 
	}
	
	@Override
	public boolean canFly() 
	{
		return true;
	}
}
