package saga.progetto.metodologie.core.logic.entities.staticEntity;

import saga.progetto.metodologie.core.logic.Level;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Player;

/**
 * 
 * The class {@code Bonus} represents a generic bonus item collectible by the {@linkplain saga.progetto.metodologie.core.logic.entities.movingEntity.Player Player}.
 *
 */
public abstract class Bonus extends StaticEntity 
{
	private boolean active;
	private Player player;
	private Level level;
	
	/**
	 * Returns the duration of the bonus' effect in milliseconds.
	 * 
	 * Returns the duration of the bonus.
	 */
	public abstract float getDuration();
	
	/**
	 * Returns whether the bonus is active.
	 * 
	 * @return	true if the bonus is active, false otherwise.
	 */
	public boolean isActive() 
	{ 
		return active; 
	}
	
	/**
	 * Activates or deactivates the bonus.
	 * 
	 * @param	active true to activate, false to deactivate.
	 */
	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	/**
	 * Returns the player who collected the bonus.
	 * 
	 * @return	the player who collected the bonus.
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	/**
	 * Returns origin level of the bonus. Used to check the status of other bonuses.
	 * 
	 * @return	the bonus' level.
	 */
	public Level getLevel()
	{
		return level;
	}
	
	/**
	 * Sets the origin level of the bonus.
	 * 
	 * @param	level the level containing the bonus.
	 */
	public void setLevel(Level level)
	{
		this.level = level;
	}
	
	/**
	 * Handles the collision between the bonus and a player.
	 * 
	 * @param	player the {@linkplain saga.progetto.metodologie.core.logic.entities.movingEntity.Player Player} who collected the bonus.
	 * @return	true if the bonus can be collected, false otherwise.
	 */
	public boolean staticCollision(Player player)
	{
		if (isVisible()) this.player = player;
		return isVisible();
	}
	
	/**
	 * Starts the effect of the bonus.
	 */
	public void startEffect()
	{
		setActive(true);
	}
	
	/**
	 * Ends the effect of the bonus.
	 */
	public void endEffect()
	{
		setActive(false);
	}
}
