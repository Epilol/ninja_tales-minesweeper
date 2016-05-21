package saga.progetto.metodologie.core.graphic.entities.staticEntity;

import static playn.core.PlayN.assets;
import playn.core.AssetWatcher;
import playn.core.Image;
import saga.progetto.metodologie.core.graphic.entities.Sprite;
import saga.progetto.metodologie.core.logic.entities.staticEntity.TemporaryEntity;

/**
 * 
 * The class {@code MineExplosion} represents the explosion of a mine.
 *
 */
public class MineExplosion extends Effect
{
	private static final String PATH = "images/effects/explosion.png";
	private static Image spriteImage;

	public MineExplosion(TemporaryEntity temporaryEntity) 
	{
		super(temporaryEntity);
		setSprite(new Sprite(spriteImage, getFrameDuration(), SIZE.width, SIZE.height));
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
	public void playAudio() 
	{
		getAudio().playExplosion();
	}
}
