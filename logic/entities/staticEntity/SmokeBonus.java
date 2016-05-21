package saga.progetto.metodologie.core.logic.entities.staticEntity;

import pythagoras.f.Dimension;
import pythagoras.f.IDimension;

/**
 * 
 * The class {@code SmokeBonus} represents a smoke bonus collectible by the {@linkplain saga.progetto.metodologie.core.logic.entities.movingEntity.Player Player}.
 *
 */
public class SmokeBonus extends Bonus
{
	private static final float DURATION = 5000;
	private static final IDimension EFFECT_RANGE = new Dimension(4, 4);
	
	public SmokeBonus(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	@Override
	public float getDuration()
	{
		return DURATION;
	}
	
	/**
	 * Returns whether the {@linkplain saga.progetto.metodologie.core.logic.entities.movingEntity.Player Player} is within the bonus {@code EFFECT_SIZE}.
	 * 
	 * @return true if the {@linkplain saga.progetto.metodologie.core.logic.entities.movingEntity.Player Player} is in range, false otherwise.
	 */
	public boolean isInRange()
	{
		return Math.abs(getPlayer().getX() - getX()) <  EFFECT_RANGE.width() && 
				Math.abs(getPlayer().getY() - getY()) <  EFFECT_RANGE.height();
	}
	
	@Override
	public void startEffect()
	{
		super.startEffect();
		getPlayer().setHidden(true);
	}
	
	@Override
	public void endEffect()
	{
		super.endEffect();
		getPlayer().setHidden(false);
	}
}
