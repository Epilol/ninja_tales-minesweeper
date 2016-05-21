package saga.progetto.metodologie.core.logic.entities.movingEntity;

/**
 * 
 * The class {@code Player} represents the {@link MovingEntity} controlled by the player.
 *
 */
public class Player extends MovingEntity 
{
	private static final float PLAYER_SPEED = 5.0f;
	private boolean hidden;
	private Gender gender;
	
	public Player(int x, int y, Gender gender)
	{
		this.gender = gender;
		setX(x);
		setY(y);
	}

	/**
	 * 
	 * The enumeration {@code Gender} represents the gender of the player.
	 *
	 */
	public enum Gender
	{
		MALE, FEMALE;
	}
	
	/**
	 * Returns the selected {@link Gender} of the player.
	 * 
	 * @return	the selected {@link Gender}.
	 */
	public Gender getGender()
	{
		return gender;
	}
	
	@Override
	public float getSpeed() 
	{
		return PLAYER_SPEED * getSpeedModifier();
	}
	
	/**
	 * Returns whether the player is hidden.
	 * 
	 * @return	true if the player is hidden, false otherwise.
	 */
	public boolean isHidden()
	{
		return hidden;
	}

	/**
	 * Sets whether the player is {@code hidden} or not.
	 * 
	 * @param	hidden true to hide the player, false otherwise.
	 */
	public void setHidden(boolean hidden)
	{
		this.hidden = hidden;
	}

	@Override
	public boolean canFly() 
	{
		return false;
	}
}
