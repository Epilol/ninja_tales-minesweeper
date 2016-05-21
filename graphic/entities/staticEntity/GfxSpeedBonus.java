package saga.progetto.metodologie.core.graphic.entities.staticEntity;

import static playn.core.PlayN.assets;
import playn.core.AssetWatcher;
import playn.core.Image;
import saga.progetto.metodologie.core.graphic.entities.EffectManager;
import saga.progetto.metodologie.core.graphic.entities.Sprite;
import saga.progetto.metodologie.core.logic.entities.staticEntity.SpeedBonus;

/**
 * 
 * The class {@code GfxSpeedBonus} is the graphic implementation of {@link SpeedBonus}.
 *
 */
public class GfxSpeedBonus extends GfxBonus
{
	private static final String PATH = "images/bonus/bonusHaste.png";
	private static Image spriteImage;
	
	public GfxSpeedBonus(SpeedBonus bonus, EffectManager manager)
	{
		super(bonus, manager);
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
}
