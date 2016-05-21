package saga.progetto.metodologie.core.logic.entities.staticEntity;

/**
 * 
 * The class {@code SpeedBonus} represents a speed bonus collectible by the {@linkplain saga.progetto.metodologie.core.logic.entities.movingEntity.Player Player}.
 *
 */
public class SpeedBonus extends Bonus 
{
	private static final float DURATION = 3000;
	private static final float SPEED_BUFF = 2.0f;
	private static final float DEFAULT_MODIFIER = 1.0f;
	
	public SpeedBonus(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	@Override
	public float getDuration()
	{
		return DURATION;
	}
	
	@Override
	public void startEffect()
	{
		super.startEffect();
		if (getPlayer().getSpeedModifier() != SPEED_BUFF)
			getPlayer().setSpeedModifier(SPEED_BUFF);
		else // if the buff is already active, deletes other speed buffs.
		{
			for (Bonus bonus : getLevel().getBonusItems())
				if (bonus instanceof SpeedBonus && bonus.isActive() && bonus != this)
					bonus.endEffect();
			getPlayer().setSpeedModifier(SPEED_BUFF);
		}
	}
	
	@Override
	public void endEffect()
	{
		super.endEffect();
		getPlayer().setSpeedModifier(DEFAULT_MODIFIER);
	}
}
