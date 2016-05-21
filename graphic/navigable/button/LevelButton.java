package saga.progetto.metodologie.core.graphic.navigable.button;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;
import pythagoras.f.Dimension;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;
import saga.progetto.metodologie.core.graphic.navigable.LevelMenu;
import saga.progetto.metodologie.core.graphic.navigable.Navigable;

/**
 * 
 * The class {@code LevelButton} represents buttons of the {@link LevelMenu}.
 *
 */
public class LevelButton extends Button 
{
	private static final String BUTTON_PATH = "images/button/levelButton.png";
	private static final String OVERLAY_PATH = "images/button/levelOverlay.png";
	private static final String SHURIKEN_PATH = "images/button/shuriken.png";
	private static final Dimension SIZE = new Dimension(135, 135);
	private static final IPoint P1 = new Point(8, 94);
	private static final IPoint P2 = new Point(51, 94);
	private static final IPoint P3 = new Point(93, 94);
	private static Image buttonImage;
	private static Image overlayImage;
	private static Image shurikenImage;
	
	private ImageLayer lockedLayer;
	private ImageLayer firstShuriken;
	private ImageLayer secondShuriken;
	private ImageLayer thirdShuriken;
	private boolean isUnlocked;
	private Rectangle hitBox;
	private int level;
	
	public LevelButton(Navigable nextState, ButtonType levelType, ButtonType difficultyType, boolean isUnlocked, int stars) 
	{
		super(nextState);
		level = difficultyType.getIndex() * (ButtonType.LEVEL_SIX.getIndex() + 1) + levelType.getIndex() + 1;
		this.isUnlocked = isUnlocked;
		setButtonLayer(buttonImage.subImage(SIZE.width * difficultyType.getIndex(), SIZE.height * levelType.getIndex(), SIZE.width, SIZE.height));
		setOverlayLayer(overlayImage);
		lockedLayer = graphics().createImageLayer(buttonImage.subImage(SIZE.width * ButtonType.LOCKED.getIndex(), SIZE.height * levelType.getIndex(), SIZE.width, SIZE.height));
		lockedLayer.setVisible(false);
		
		firstShuriken = graphics().createImageLayer(shurikenImage);
		firstShuriken.setVisible(false);
		secondShuriken = graphics().createImageLayer(shurikenImage);
		secondShuriken.setVisible(false);
		thirdShuriken = graphics().createImageLayer(shurikenImage);
		thirdShuriken.setVisible(false);
	
		if (stars < 3) thirdShuriken.setAlpha(0.4f);
		if (stars < 2) secondShuriken.setAlpha(0.4f);
		if (stars < 1) firstShuriken.setAlpha(0.4f);
		
		graphics().rootLayer().add(firstShuriken);
		graphics().rootLayer().add(secondShuriken);
		graphics().rootLayer().add(thirdShuriken);
		graphics().rootLayer().add(lockedLayer);
	}
	
	/**
	 * Synchronously loads the assets of this class.
	 */
	public static void loadAssets() 
	{
		buttonImage = assets().getImageSync(BUTTON_PATH);
		overlayImage = assets().getImageSync(OVERLAY_PATH);
		shurikenImage = assets().getImageSync(SHURIKEN_PATH);
	}
	
	@Override
	public boolean intersects(IPoint point) 
	{
		return hitBox.contains(point);
	}
	
	@Override
	public void setHitBox(IPoint point) 
	{
		hitBox = new Rectangle(point, SIZE);
	}

	/**
	 * Returns whether the level linked to this button is locked.
	 * 
	 * @return true if the level is locked, false otherwise.
	 */
	public boolean isUnlocked() 
	{
		return isUnlocked;
	}
	
	/**
	 * Returns the level associated with this button.
	 * 
	 * @return the level associated with this button.
	 */
	public int getLevel()
	{
		return level;
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		if (visible)
		{
			lockedLayer.setVisible(!isUnlocked);
			getButtonLayer().setVisible(isUnlocked);
		}
		else 
			lockedLayer.setVisible(false);
		firstShuriken.setVisible(visible);
		secondShuriken.setVisible(visible);
		thirdShuriken.setVisible(visible);
	}
	
	@Override
	public void setTranslation(IPoint p)
	{
		super.setTranslation(p);
		lockedLayer.setTranslation(p.x(), p.y());
		firstShuriken.setTranslation(p.x() + P1.x(), p.y() + P1.y());
		secondShuriken.setTranslation(p.x() + P2.x(), p.y() + P2.y());
		thirdShuriken.setTranslation(p.x() + P3.x(), p.y() + P3.y());
	}
	
	/**
	 * Sets the level associated with this button as locked or unlocked.
	 * 
	 * @param isUnlocked true to lock the level, false to unlock it.
	 */
	public void setLocked(boolean isUnlocked)
	{
		this.isUnlocked = isUnlocked;
	}
	
	/**
	 * Sets the stars associated with this button's linked level. This should be the highest number of stars ever scored by the player.
	 * 
	 * @param stars the stars associated with this button's linked level.
	 */
	public void setStars(int stars)
	{
		if (stars > 0) firstShuriken.setAlpha(1.0f);
		if (stars > 1) secondShuriken.setAlpha(1.0f);
		if (stars > 2) thirdShuriken.setAlpha(1.0f);
	}
	
	@Override
	public void playButtonAudio()
	{
		getAudio().playStartButton();
	}
}