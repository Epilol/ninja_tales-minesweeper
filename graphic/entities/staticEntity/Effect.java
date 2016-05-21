package saga.progetto.metodologie.core.graphic.entities.staticEntity;

import pythagoras.f.Dimension;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.audio.AudioManager;
import saga.progetto.metodologie.core.logic.entities.staticEntity.TemporaryEntity;

/**
 * 
 * The class {@code Effect} is the graphic implementation of {@link TemporaryEntity}.
 *
 */
public abstract class Effect extends GfxStaticEntity
{
	protected static final Dimension SIZE = new Dimension(110, 120);
	private static final IPoint FRAME_ADJUSTMENT = new Point(48, 100);
	private static AudioManager audio; 
	
	private IPoint frameLocation;
	
	public Effect(TemporaryEntity staticEntity) 
	{
		super(staticEntity);
		frameLocation = new Point();
	}
	
	/**
	 * Plays the audio of this effect.
	 */
	public abstract void playAudio();
	
	/**
	 * Sets the {@code AudioManager} of this class. 
	 * 
	 * @param	audio the  {@code AudioManager} to be set.
	 */
	public static void loadAudio(AudioManager audio)
	{
		Effect.audio = audio;
	}
	
	/**
	 * Returns the {@code AudioManager} associated with the class.
	 * 
	 * @return	the {@code AudioManager} associated with the class.
	 */
	public AudioManager getAudio()
	{
		return audio;
	}
	
	@Override
	public IPoint getFrameLocation() 
	{
		return frameLocation;
	}
	
	@Override
	public void update(int delta)
	{
		super.update(delta);
		frameLocation = getLevel().applyIsometry(getGfxPosition()).subtract(FRAME_ADJUSTMENT.x(), FRAME_ADJUSTMENT.y());
	}
}
