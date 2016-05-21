package saga.progetto.metodologie.core.graphic.entities.movingEntity;

import static playn.core.PlayN.assets;
import playn.core.AssetWatcher;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;
import saga.progetto.metodologie.core.graphic.entities.Sprite;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Moth;

/**
 * 
 * The class {@code GfxMoth} is the graphic implementation of {@link Moth}.
 *
 */
public class GfxMoth extends GfxEnemy
{
	private static final IDimension FRAME_SIZE = new Dimension(240, 240);
	private static final IPoint FRAME_ADJUSTMENT = new Point(114, 215);
	private static final IDimension IDLE_HITBOX_SIZE = new Dimension(61, 78);
	private static final IDimension ATTACK_HITBOX_SIZE = new Dimension(76, 92);
	private static final IPoint IDLE_HITBOX_ADJUSTMENT = new Point(FRAME_ADJUSTMENT).subtract(90, 118);
	private static final IPoint UP_ATTACK_HITBOX_ADJUSTMENT = new Point(FRAME_ADJUSTMENT).subtract(83, 111);
	private static final IPoint LEFT_ATTACK_HITBOX_ADJUSTMENT = new Point(FRAME_ADJUSTMENT).subtract(83, 111);
	private static final IPoint DOWN_ATTACK_HITBOX_ADJUSTMENT = new Point(FRAME_ADJUSTMENT).subtract(83, 111);
	private static final IPoint RIGHT_ATTACK_HITBOX_ADJUSTMENT = new Point(FRAME_ADJUSTMENT).subtract(83, 111);
	private static final String PATH = "images/enemies/mothSprite.png";
	private static Image spriteImage;
	
	private Point frameLocation;
	
	public GfxMoth(Moth moth)
	{
		super(moth);
		setIdleHitBox(new Rectangle(new Dimension(IDLE_HITBOX_SIZE.width(), IDLE_HITBOX_SIZE.height())));
		setAttackHitBox(new Rectangle(new Dimension(ATTACK_HITBOX_SIZE.width(), ATTACK_HITBOX_SIZE.height())));
		setSprite(new Sprite(spriteImage, getFrameDuration(), FRAME_SIZE.width(), FRAME_SIZE.height()));
		frameLocation = new Point();
	}

	/**
	 * Adds all the class assets to the watcher. 
	 * 
	 * @param	watcher the watcher used to load the assets.
	 */
	public static void loadAssets(AssetWatcher watcher)
	{
		spriteImage = assets().getImage(PATH);
		watcher.add(spriteImage);
	}
	
	@Override
	public boolean hasAttacked() 
	{
		return getSprite().getCurrentFrame(getCurrentAnimation().getCoefficient()) / FRAME_SIZE.width() > 2;
	}
	
	@Override
	public IPoint getFrameLocation()
	{
		return frameLocation;
	}
	
	public IPoint getHitBoxLocation(IPoint hitBoxAdjustment)
	{
		return getLevel().applyIsometry(getGfxPosition()).subtract(hitBoxAdjustment.x(), hitBoxAdjustment.y());
	}
	
	@Override
	public IPoint getIdleHitBoxLocation() 
	{
		return getHitBoxLocation(IDLE_HITBOX_ADJUSTMENT);
	}

	@Override
	public IPoint getUpHitBoxLocation() 
	{
		return getHitBoxLocation(UP_ATTACK_HITBOX_ADJUSTMENT);
	}

	@Override
	public IPoint getLeftHitBoxLocation() 
	{
		return getHitBoxLocation(LEFT_ATTACK_HITBOX_ADJUSTMENT);
	}

	@Override
	public IPoint getDownHitBoxLocation()
	{
		return getHitBoxLocation(DOWN_ATTACK_HITBOX_ADJUSTMENT);
	}

	@Override
	public IPoint getRightHitBoxLocation() 
	{
		return getHitBoxLocation(RIGHT_ATTACK_HITBOX_ADJUSTMENT);
	}
	
	@Override
	public void update(int delta)
	{
		super.update(delta);
		frameLocation = getLevel().applyIsometry(getGfxPosition()).subtract(FRAME_ADJUSTMENT.x(), FRAME_ADJUSTMENT.y());
	}
}
