package saga.progetto.metodologie.core.graphic.entities.staticEntity;

import static playn.core.PlayN.assets;

import playn.core.AssetWatcher;
import playn.core.Image;
import saga.progetto.metodologie.core.graphic.entities.EffectManager;
import saga.progetto.metodologie.core.graphic.entities.Sprite;
import saga.progetto.metodologie.core.logic.entities.staticEntity.RevealBonus;
import saga.progetto.metodologie.core.logic.entities.staticEntity.TemporaryEntity;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.Cell;

/**
 * 
 * The class {@code GfxRevealBonus} is the graphic implementation of {@link RevealBonus}.
 *
 */
public class GfxRevealBonus extends GfxBonus
{
	private static final String PATH = "images/bonus/bonusExplosion.png";
	private static Image spriteImage;
	
	private RevealBonus bonus;
	
	public GfxRevealBonus(RevealBonus bonus, EffectManager manager)
	{
		super(bonus, manager);
		this.bonus = bonus;
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
	public void startEffect()
	{
		super.startEffect();
		for (Cell cell : bonus.revealCells())
		{
			getLevel().getGfxBackground()[cell.getY()][cell.getX()].localReveal();
			getManager().addEffect(new MineExplosion(new TemporaryEntity(cell.getX(), cell.getY())));
		}
	}
	
	@Override
	public void endEffect()
	{
		super.endEffect();
	}
	
}
