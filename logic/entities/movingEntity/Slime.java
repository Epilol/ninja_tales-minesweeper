package saga.progetto.metodologie.core.logic.entities.movingEntity;

/**
 *
 * The {@code Slime} is an enemy entity. It will try to reach the {@linkplain saga.progetto.metodologie.core.logic.entities.movingEntity.Player Player} and kill him.
 * 
 */
public class Slime extends Enemy 
{
	private static final float SLIME_SPEED = 1.0f;
	
	public Slime(int x, int y)
	{
		super(x, y);
	}
	
	@Override
	public float getSpeed()
	{ 
		return SLIME_SPEED * getSpeedModifier(); 
	}
	
	@Override
	public boolean canFly() 
	{
		return false;
	}
}
