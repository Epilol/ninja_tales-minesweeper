package saga.progetto.metodologie.core.graphic.navigable.button;

import playn.core.Image;
import playn.core.ImageLayer;
import pythagoras.f.IPoint;
import saga.progetto.metodologie.core.audio.AudioManager;
import saga.progetto.metodologie.core.graphic.navigable.Menu;
import saga.progetto.metodologie.core.graphic.navigable.Navigable;
import static playn.core.PlayN.graphics;

/**
 * 
 * The class {@code Button} represents generic menu buttons.
 *
 */
public abstract class Button 
{
	private static AudioManager audio;
	private Navigable nextState;
	private ImageLayer buttonLayer;
	private ImageLayer overlayLayer;
	
	public Button(Navigable nextState)
	{
		this.nextState = nextState;
	}
	
	/**
	 * 
	 * The enumeration {@code ButtonType}  is used to associate every button type with its index.
	 *
	 */
	public enum ButtonType
	{
		SINGLEPLAYER(0), MULTIPLAYER(1), OPTIONS(2), HIGHSCORE(3), GUIDE(4),
		RIKIMARU(0), AYAME(1),
		EASY(0), MEDIUM(1), HARD(2), LOCKED(3),
		LEVEL_ONE(0), LEVEL_TWO(1), LEVEL_THREE(2), LEVEL_FOUR(3), LEVEL_FIVE(4), LEVEL_SIX(5),
		EXIT(0), RESTART(1), NEXT(2), RESUME(3),
		PLUS(0), MINUS(1);
		
		private int index;
		
		ButtonType(int index)
		{
			this.index = index;
		}
		
		public int getIndex()
		{
			return index;
		}
	}
	
	/**
	 * Returns the {@link Menu} this buttons links to.
	 * 
	 * @return	the {@link Menu} this buttons links to.
	 */
	public Navigable getNextState()
	{
		return nextState;
	}
	
	/**
	 * Returns the {@link ImageLayer} containing the image of the button.
	 * 
	 * @return	the {@link ImageLayer} containing the image of the button.
	 */
	public ImageLayer getButtonLayer()
	{
		return buttonLayer;
	}
	
	
	/**
	 * Returns the {@link ImageLayer} containing the overlay image of the button.
	 * 
	 * @return	the {@link ImageLayer} containing the overlay image of the button.
	 */
	public ImageLayer getOverlayLayer()
	{
		return overlayLayer;
	}
	
	/**
	 * Sets the image of this button.
	 * 
	 * @param	buttonImage the image to set.
	 */
	public void setButtonLayer(Image buttonImage)
	{
		buttonLayer = graphics().createImageLayer(buttonImage);
		buttonLayer.setVisible(false);
		graphics().rootLayer().add(buttonLayer);
	}
	
	/**
	 * Sets the overlay image of this button.
	 * 
	 * @param	overlayImage the image to set.
	 */
	public void setOverlayLayer(Image overlayImage)
	{
		overlayLayer = graphics().createImageLayer(overlayImage);
		overlayLayer.setVisible(false);
		graphics().rootLayer().add(overlayLayer);
	}
	
	/**
	 * Sets the visibility of the button.
	 * 
	 * @param	visible true to display the button, false to hide it.
	 */
	public void setVisible(boolean visible)
	{
		buttonLayer.setVisible(visible);
		overlayLayer.setVisible(false);
	}
	
	/**
	 * Sets the visibility of the overlay.
	 * 
	 * @param	visible true to display the overlay, false to hide it.
	 */
	public void mouseOver(boolean visible)
	{
		overlayLayer.setVisible(visible);
	}
	
	/**
	 * Translates the button to the specified location {@code p}.
	 * 
	 * @param	p an {@link IPoint} containing the location.
	 */
	public void setTranslation(IPoint p)
	{
		buttonLayer.setTranslation(p.x(), p.y());
		overlayLayer.setTranslation(p.x(), p.y());
		setHitBox(p);
	}
	
	/**
	 * Plays the audio associated with buttons.
	 */
	public void playButtonAudio()
	{
		audio.playSelectButton();
	}
	
	/**
	 * Sets the {@link AudioManager} associated with the buttons.
	 * 
	 * @param	audio the {@link AudioManager} to associate.
	 */
	public static void loadAudio(AudioManager audio)
	{
		Button.audio = audio;
	}
	
	/**
	 * Returns the {@link AudioManager} associated with the button.
	 * 
	 * @return	the {@link AudioManager} associated with the button.
	 */
	protected AudioManager getAudio()
	{
		return audio;
	}
	
	/**
	 * Loads all the button assets.
	 */
	public static void loadAssets()
	{
		CharSelectionButton.loadAssets();
		DifficultyButton.loadAssets();
		GameMenuButton.loadAssets();
		LevelButton.loadAssets();
		MainMenuButton.loadAssets();
		ReturnButton.loadAssets();
		VolumeButton.loadAssets();
	}
	
	/**
	 * Returns whether the {@link IPoint} {@code point} intersects the button.
	 * 
	 * @param	point the {@link IPoint} that needs to be checked.
	 * @return	true if there is intersection, false otherwise.
	 */
	public abstract boolean intersects(IPoint point);
	
	
	/**
	 * Moves the upper left corner of the hitBox of this button to the specified location.
	 * 
	 * @param	point the destination of the hitBox.
	 */
	public abstract void setHitBox(IPoint point);
}
