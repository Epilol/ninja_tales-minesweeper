package saga.progetto.metodologie.core.graphic.entities.movingEntity;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import playn.core.AssetWatcher;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;
import saga.progetto.metodologie.core.graphic.entities.Sprite;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Player;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Player.Gender;

/**
 * 
 * The class {@code GfxPlayer} is the graphic implementation of {@link Player}.
 *
 */
public class GfxPlayer extends GfxMovingEntity 
{
	protected static final IDimension FRAME_SIZE = new Dimension(90, 120);
	protected static final IPoint FRAME_ADJUSTMENT = new Point(43, 102);
	protected static final IDimension HITBOX_SIZE = new Dimension(50, 90);
	protected static final IPoint HITBOX_ADJUSTMENT = new Point(FRAME_ADJUSTMENT).subtract(18, 17);
	private static final String PLAYER_1_PATH = "images/player/Male_Sheet.png";
	private static final String PLAYER_2_PATH = "images/player/Female_Sheet.png";
	private static Image maleImage;
	private static Image femaleImage;
	
	private Rectangle hitBox;
	private Player player;
	
	public GfxPlayer(Player player)
	{
		super(player);
		this.player = player;
		setSprite(new Sprite(player.getGender() == Gender.MALE? maleImage : femaleImage, getFrameDuration(), FRAME_SIZE.width(), FRAME_SIZE.height()));
		hitBox = new Rectangle(graphics().width() / 2 - HITBOX_ADJUSTMENT.x(), graphics().height() / 2 - HITBOX_ADJUSTMENT.y(), HITBOX_SIZE.width(), HITBOX_SIZE.height());
	}
	
	/**
	 * Adds all the class assets to the watcher. 
	 * 
	 * @param	watcher the watcher used to load the assets.
	 */
	public static void loadAssets(AssetWatcher watcher) 
	{
		maleImage = assets().getImage(PLAYER_1_PATH);
		femaleImage = assets().getImage(PLAYER_2_PATH);
		watcher.add(maleImage);
		watcher.add(femaleImage);
	}
	
	@Override
	public IPoint getFrameLocation()
	{
		return new Point(graphics().width() / 2, graphics().height() / 2).subtract(FRAME_ADJUSTMENT.x(), FRAME_ADJUSTMENT.y());
	}
	
	/**
	 * Returns the logical counterpart of the player. Needed to avoid casting.
	 * 
	 * @return	the logical counterpart of the player.
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	@Override
	public IRectangle getHitBox()
	{
		return hitBox;
	}
	
	/**
	 * Moves the hitBox in the location defined by the {@link Point} {@code p}.
	 * 
	 * @param p the destination {@link Point} of the hitBox.
	 */
	public void moveHitBox(Point p)
	{
		hitBox.setLocation(p);
	}
	
	/**
	 * Calls {@link #isHidden()}
	 * 
	 * @return the result of the called method {@link #isHidden()}.
	 */
	public boolean isHidden()
	{
		return player.isHidden();
	}
	
	/**
	 * Calls {@link #setHidden(boolean)} with the argument {@code hidden}.
	 * 
	 * @param hidden true to hide the player, false otherwise.
	 */
	public void setHidden(boolean hidden)
	{
		player.setHidden(hidden);
	}
}
