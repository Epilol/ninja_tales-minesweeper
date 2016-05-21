package saga.progetto.metodologie.core.graphic.entities.movingEntity;

import playn.core.Surface;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Rectangle;
import saga.progetto.metodologie.core.graphic.Animation;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Enemy;

/**
 * 
 * The class {@code GfxEnemy} is the graphic implementation of {@link Enemy}.
 *
 */
public abstract class GfxEnemy extends GfxMovingEntity
{
	private Rectangle idleHitBox;
	private Rectangle attackHitBox;

	public GfxEnemy(Enemy enemy)
	{
		super(enemy);
	}

	/**
	 * Returns whether the enemy has concluded the attack (but not necessarily the animation).
	 * 
	 * @return	whether the enemy has concluded the attack.
	 */
	public abstract boolean hasAttacked();
	
	/**
	 * Returns an {@link IPoint} containing the top left corner of the hitbox when not moving.
	 * 
	 * @return	an {@link IPoint} containing the top left corner of the hitbox when not moving.
	 */
	public abstract IPoint getIdleHitBoxLocation();
	
	/**
	 * Returns an {@link IPoint} containing the top left corner of the hitbox when moving up.
	 * 
	 * @return	an {@link IPoint} containing the top left corner of the hitbox when moving up.
	 */
	public abstract IPoint getUpHitBoxLocation();
	
	/**
	 * Returns an {@link IPoint} containing the top left corner of the hitbox when moving to the left.
	 * 
	 * @return	an {@link IPoint} containing the top left corner of the hitbox when moving to the left.
	 */
	public abstract IPoint getLeftHitBoxLocation();
	
	/**
	 * Returns an {@link IPoint} containing the top left corner of the hitbox when moving down.
	 * 
	 * @return	an {@link IPoint} containing the top left corner of the hitbox when moving down.
	 */
	public abstract IPoint getDownHitBoxLocation();
	
	/**
	 * Returns an {@link IPoint} containing the top left corner of the hitbox when moving to the right.
	 * 
	 * @return	an {@link IPoint} containing the top left corner of the hitbox when moving to the right.
	 */
	public abstract IPoint getRightHitBoxLocation();
	
	@Override
	public IRectangle getHitBox()
	{
		return idleHitBox;
	}
	
	/**
	 * Sets the idleHitBox.
	 * 
	 * @param	idleHitBox the new hitBox.
	 */
	protected void setIdleHitBox(Rectangle idleHitBox) 
	{
		this.idleHitBox = idleHitBox;
	}
	
	/**
	 * Sets the attackHitBox.
	 * 
	 * @param	attackHitBox the new hitBox.
	 */
	protected void setAttackHitBox(Rectangle attackHitBox) 
	{
		this.attackHitBox = attackHitBox;
	}
	
	/**
	 * Checks whether there is collision between the {@link GfxPlayer} and the enemy.
	 * 
	 * @return	true if there is collision, false otherwise.
	 */
	public boolean checkPlayerCollision() 
	{
		return getLevel().getGfxPlayer().isHidden()? false : getCurrentAnimation() == Animation.ATTACK? 
				attackHitBox.intersects(getLevel().getGfxPlayer().getHitBox()) : idleHitBox.intersects(getLevel().getGfxPlayer().getHitBox());
	}
	
	
	/**
	 * Checks whether there is collision between the {@link GfxPlayer2} and the enemy.
	 * 
	 * @return	true if there is collision, false otherwise.
	 */
	public boolean checkPlayer2Collision() 
	{
		return getLevel().getGfxPlayer().isHidden()? false : getCurrentAnimation() == Animation.ATTACK? 
				attackHitBox.intersects(getLevel().getGfxPlayer2().getHitBox()) : idleHitBox.intersects(getLevel().getGfxPlayer2().getHitBox());
	}
	
	@Override
	public void update(int delta)
	{
		super.update(delta);
		idleHitBox.setLocation(getIdleHitBoxLocation());
		switch(getDirection())
		{
			case UP: case TOP_LEFT: attackHitBox.setLocation(getUpHitBoxLocation()); break;
			case LEFT: case BOTTOM_LEFT: attackHitBox.setLocation(getLeftHitBoxLocation()); break;
			case DOWN: case BOTTOM_RIGHT: attackHitBox.setLocation(getDownHitBoxLocation()); break;
			case RIGHT: case TOP_RIGHT: attackHitBox.setLocation(getRightHitBoxLocation()); break;
			default: attackHitBox.setLocation(getUpHitBoxLocation()); break;
		}
	}
	
	
	@Override
	public void drawFrame(Surface surface)
	{
		super.drawFrame(surface);
	}
}
