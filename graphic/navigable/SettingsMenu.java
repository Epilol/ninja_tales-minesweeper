package saga.progetto.metodologie.core.graphic.navigable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.Dimension;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;
import saga.progetto.metodologie.core.graphic.navigable.button.VolumeButton;
import saga.progetto.metodologie.core.graphic.navigable.button.Button.ButtonType;

/**
 * 
 * The class {@code SettingsMenu} represents a menu that allows the modification of the game settings.
 *
 */
public abstract class SettingsMenu extends Menu 
{
	private static final String BAR_PATH = "images/menu/volumeBar.png";
	private static final String OPTIONS_PATH = "images/menu/volumeOptions.png";
	private static final Dimension BAR_SIZE = new Dimension(293, 40);
	private static final IPoint MUSIC_POINT = new Point(175, 215);
	private static final IPoint EFFECT_POINT = new Point(175, 265);
	private static final IPoint OPTIONS_POINT = new Point(107, 227);
	private static final IPoint P1 = new Point(445, 225);
	private static final IPoint P2 = new Point(179, 225);
	private static final IPoint P3 = new Point(445, 275);
	private static final IPoint P4 = new Point(179, 275);
	private Button musicPlusButton;
	private Button musicMinusButton;
	private Button effectPlusButton;
	private Button effectMinusButton;
	private ImageLayer musicLayer;
	private ImageLayer effectLayer;
	private ImageLayer optionsLayer;
	private Image barImage;
	
	public SettingsMenu(NinjaTalesMinesweeper game)
	{
		super(game);
		barImage = assets().getImageSync(BAR_PATH);
		optionsLayer = graphics().createImageLayer(assets().getImageSync(OPTIONS_PATH));
		optionsLayer.setTranslation(OPTIONS_POINT.x(), OPTIONS_POINT.y());
		musicLayer = graphics().createImageLayer(getMusicImage());
		musicLayer.setTranslation(MUSIC_POINT.x(), MUSIC_POINT.y());
		effectLayer = graphics().createImageLayer(getEffectImage());
		effectLayer.setTranslation(EFFECT_POINT.x(), EFFECT_POINT.y());
	}
	
	/**
	 * Returns an image representing the current volume of the music.
	 * 
	 * @return	an image representing the current volume of the music.
	 */
	private Image getMusicImage()
	{
		return barImage.subImage(0, BAR_SIZE.height() * getAudio().getMusicVolume() * 10, BAR_SIZE.width(), BAR_SIZE.height());
	}
	
	/**
	 * Returns an image representing the current volume of the effects.
	 * 
	 * @return	an image representing the current volume of the effects.
	 */
	private Image getEffectImage()
	{
		return barImage.subImage(0, BAR_SIZE.height() * getAudio().getEffectVolume() * 10, BAR_SIZE.width(), BAR_SIZE.height());
	}
	
	/**
	 * Creates the buttons of the menu passing the destination game states as arguments.
	 */
	public void setButtons() 
	{
		musicPlusButton = new VolumeButton(this, ButtonType.PLUS);
		musicPlusButton.setTranslation(P1);
		musicMinusButton = new VolumeButton(this, ButtonType.MINUS);
		musicMinusButton.setTranslation(P2);
		effectPlusButton = new VolumeButton(this, ButtonType.PLUS);
		effectPlusButton.setTranslation(P3);
		effectMinusButton = new VolumeButton(this, ButtonType.MINUS);
		effectMinusButton.setTranslation(P4);	
	}
	
	@Override
	public void setVisible(boolean visible)
	{		
		if (visible)
		{
			musicLayer.setImage(getMusicImage());
			effectLayer.setImage(getEffectImage());
			graphics().rootLayer().add(optionsLayer);
			graphics().rootLayer().add(musicLayer);
			graphics().rootLayer().add(effectLayer);
			setTitleLayer();
		}
		else
		{
			graphics().rootLayer().remove(effectLayer);
			graphics().rootLayer().remove(musicLayer);
			graphics().rootLayer().remove(optionsLayer);
			graphics().rootLayer().remove(getTitleLayer());
		}
		
		getTitleLayer().setVisible(visible);
		musicPlusButton.setVisible(visible);
		musicMinusButton.setVisible(visible);
		effectPlusButton.setVisible(visible);
		effectMinusButton.setVisible(visible);
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{
		Point p = new Point(event.localX(), event.localY());
		
		if (musicPlusButton.intersects(p) && getAudio().getMusicVolume() < 1.0f)
		{
			getAudio().increaseMusicVolume();
			musicPlusButton.playButtonAudio();
			musicLayer.setImage(getMusicImage());
		}
		
		if (musicMinusButton.intersects(p) && getAudio().getMusicVolume() >= 0.1f)
		{
			getAudio().decreaseMusicVolume();
			musicMinusButton.playButtonAudio();
			musicLayer.setImage(getMusicImage());
		}
		
		if (effectPlusButton.intersects(p) && getAudio().getEffectVolume() < 1.0f)
		{
			getAudio().increaseEffectVolume();
			effectPlusButton.playButtonAudio();
			effectLayer.setImage(getEffectImage());
		}
		
		if (effectMinusButton.intersects(p) && getAudio().getEffectVolume() >= 0.1f)
		{
			getAudio().decreaseEffectVolume();
			effectMinusButton.playButtonAudio();
			effectLayer.setImage(getEffectImage());
		}
		return this;
	}

	@Override
	public void update(int delta)
	{
		musicPlusButton.getOverlayLayer().setVisible(musicPlusButton.intersects(getMouseLocation()) && getAudio().getMusicVolume() < 1.0f);
		musicMinusButton.getOverlayLayer().setVisible(musicMinusButton.intersects(getMouseLocation()) && getAudio().getMusicVolume() >= 0.1f);
		effectPlusButton.getOverlayLayer().setVisible(effectPlusButton.intersects(getMouseLocation()) && getAudio().getEffectVolume() < 1.0f);
		effectMinusButton.getOverlayLayer().setVisible(effectMinusButton.intersects(getMouseLocation()) && getAudio().getEffectVolume() >= 0.1f);
	}
}
