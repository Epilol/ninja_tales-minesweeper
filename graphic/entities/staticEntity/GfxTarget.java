package saga.progetto.metodologie.core.graphic.entities.staticEntity;

import static playn.core.PlayN.assets;
import playn.core.AssetWatcher;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.Animation;
import saga.progetto.metodologie.core.graphic.entities.Sprite;
import saga.progetto.metodologie.core.logic.entities.staticEntity.Target;

/**
 * 
 * The class {@code GfxTarget} is the graphic implementation of {@link Target}.
 *
 */
public class GfxTarget extends GfxStaticEntity
{
	private static final String PATH = "images/target/target.png";
	private static final Dimension SIZE = new Dimension(100, 100);
	private static final IPoint FRAME_ADJUSTMENT = new Point(58, 42);
	private static Image spriteImage;
	
	private IPoint frameLocation;
	
	public GfxTarget(Target target) 
	{
		super(target);
		setSprite(new Sprite(spriteImage, getFrameDuration(), SIZE.width, SIZE.height));
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
	
	/**
	 * Changes the animation of the target to {@code ACTION}.
	 */
	public void open()
	{
		setCurrentAnimation(Animation.ACTION);
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
