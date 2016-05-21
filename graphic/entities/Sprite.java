package saga.progetto.metodologie.core.graphic.entities;

import playn.core.Image;
import saga.progetto.metodologie.core.graphic.Animation;
import saga.progetto.metodologie.core.graphic.GfxLevel;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Direction;

/**
 * 
 * The {@code Sprite} class manages entity animations.
 *
 */
public class Sprite 
{
	private Image texture;
	private float frameTime;
	private float frameWidth;
	private float frameHeight;
	private int elapsed;
	private Direction lastDirection;
	
	public Sprite(Image texture, float frameTime, float frameWidth, float frameHeight) 
	{
		this.texture = texture;
	    this.frameTime = frameTime;
	    this.frameWidth = frameWidth;
	    this.frameHeight = frameHeight;
	    lastDirection = Direction.DEFAULT;
	}
	
	/**
	 * Returns the sprite's texture.
	 * 
	 * @return	the sprite's texture.
	 */
	public Image getTexture() 
	{
		return texture;
	}

	/**
	 * Returns the image to be displayed.
	 */
	public Image getCurrentFrame()
	{
		return texture.subImage(getCurrentFrame(1), 0, frameWidth, frameHeight);
	}
	
	/**
	 * Returns the image to be displayed according to the input {@code animation}.
	 * 
	 * @param	animation the entity's current animation.
	 */
	public Image getCurrentFrame(Animation animation)
	{
		return texture.subImage(getCurrentFrame(animation.getCoefficient()), (animation.getIndex() + lastDirection.getUtilityIndex()) * frameHeight, frameWidth, frameHeight);
	}
	
	/**
	 * Returns the horizontal coordinate of the frame to be selected.
	 * 
	 * @param	coefficient the {@link Animation} coefficient index.
	 * @return	the x index of the frame to be selected.
	 */
	public float getCurrentFrame(int coefficient)
	{
		return ((int) (elapsed % (frameTime * coefficient * getFramesPerLine()) / (frameTime * coefficient))) * frameWidth;
	}
	
	/**
	 * Returns the number of frames per line.
	 * 
	 * @return	the number of frames per line.
	 */
	private float getFramesPerLine() 
	{
		return texture.width() / frameWidth;
	}
	
	/**
	 * Returns whether the current animation is over.
	 * 
	 * @return	true if the animation is over, false otherwise.
	 */
	public boolean isOver()
	{
		return elapsed >= frameTime * getFramesPerLine();
	}
	
	/**
	 * Returns whether the input {@code animation} is over.
	 * 
	 * @return	true if the animation is over, false otherwise.
	 */
	public boolean isOver(Animation animation)
	{
		return elapsed >= frameTime * animation.getCoefficient() * getFramesPerLine();
	}
	
	/**
	 * Resets the time elapsed. Used to force the sprite to begin from its first frame.
	 */
	public void resetDelta()
	{
		elapsed = 0;
	}

	/**
	 * Updates the direction to be used.
	 * 
	 * @param	direction the new direction to be used.
	 */
	public void setLastDirection(Direction direction) 
	{
		this.lastDirection = direction;
	}
	
	/**
	 * Called by {@link GfxLevel} on every update call.
	 * 
	 * @param	delta the time in milliseconds since the last call.
	 */
	public void update(int delta)
	{
		elapsed += delta;
	}
}

