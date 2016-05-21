package saga.progetto.metodologie.core.logic.entities.movingEntity;

/**
 *
 * The {@code Golem} is an enemy entity. It will try to reach the {@linkplain saga.progetto.metodologie.core.logic.entities.movingEntity.Player Player} and kill him.
 * 
 */
public class Golem extends Enemy 
{
	private static final float GOLEM_SPEED = 1.0f;
	
	public Golem(int x, int y)
	{
		super(x, y);
	}
	
	@Override
	public float getSpeed()
	{ 
		return GOLEM_SPEED * getSpeedModifier(); 
	}

	@Override
	public boolean canFly() 
	{
		return false;
	}
}
