package saga.progetto.metodologie.core.graphic.entities.staticEntity;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.AssetWatcher;
import playn.core.Image;
import playn.core.ImageLayer;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.entities.EffectManager;
import saga.progetto.metodologie.core.graphic.entities.Sprite;
import saga.progetto.metodologie.core.logic.entities.staticEntity.SmokeBonus;
import saga.progetto.metodologie.core.logic.entities.staticEntity.TemporaryEntity;

/**
 * 
 * The class {@code GfxSmokeBonus} is the graphic implementation of {@link SmokeBonus}.
 *
 */
public class GfxSmokeBonus extends GfxBonus
{
	private static final String PATH = "images/bonus/bonusSmoke.png";
	private static final String SMOKE_PATH = "images/effects/smoke.png";
	private static final IPoint SMOKE_ADJUSTMENT = new Point(454, 250);
	private static Image spriteImage;
	private static Image smokeImage;
	
	private ImageLayer smokeLayer;
	private SmokeBonus bonus;
	
	public GfxSmokeBonus(SmokeBonus bonus, EffectManager manager)
	{
		super(bonus, manager);
		this.bonus = bonus;
		setSprite(new Sprite(spriteImage, getFrameDuration(), SIZE.width, SIZE.height));
		smokeLayer = graphics().createImageLayer(smokeImage);
		smokeLayer.setAlpha(0.8f);
		smokeLayer.setVisible(false);
	}
	
	/**
	 * Adds all the class assets to the watcher. 
	 * 
	 * @param	watcher the watcher used to load the assets.
	 */
	public static void loadAssets(AssetWatcher watcher) 
	{
		spriteImage = assets().getImage(PATH);
		smokeImage = assets().getImage(SMOKE_PATH);
		watcher.add(spriteImage);
		watcher.add(smokeImage);
	}
	
	@Override
	public void clear()
	{
		super.clear();
		smokeLayer.destroy();
	}
	
	@Override
	public void startEffect()
	{
		super.startEffect();
		graphics().rootLayer().add(smokeLayer);
		getManager().addEffect(new SmokeExplosion(new TemporaryEntity(getX(), getY())));
		smokeLayer.setVisible(true);
	}
	
	@Override
	public void endEffect()
	{
		super.endEffect();
		smokeLayer.destroy();
	}
	
	@Override
	public void update(int delta)
	{
		super.update(delta);
		if (isActive())
		{
			if (bonus.isInRange()) getPlayer().setHidden(true);
			else endEffect();
		}
		Point p = getLevel().applyIsometry(getGfxPosition()).subtract(SMOKE_ADJUSTMENT.x(), SMOKE_ADJUSTMENT.y());
		smokeLayer.setTranslation(p.x, p.y);
	}

}
