package saga.progetto.metodologie.core.graphic;

import saga.progetto.metodologie.core.graphic.entities.movingEntity.GfxMovingEntity;

/**
 * 
 * The enumeration Animation represents the possible animations of a {@link GfxMovingEntity}.
 *
 */
public enum Animation 
{
	IDLE(0, 1),
	ACTION(1, 1),
	RUN(4, 1), 
	DEATH(8, 2), 
	ATTACK(12, 1);
	
	private int index;
	private int coefficient;
	
	Animation(int index, int coefficient)
	{
		this.index = index;
		this.coefficient = coefficient;
	}
	
	/**
	 * Returns the animation index in the sprite sheet.
	 * 
	 * @return the animation index in the sprite sheet.
	 */
	public int getIndex()
	{
		return index;
	}
	
	/**
	 * Returns a coefficient representing the speed of the animation.
	 * 
	 * @return a coefficient representing the speed of the animation.
	 */
	public int getCoefficient()
	{
		return coefficient;
	}
}
