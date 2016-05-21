package saga.progetto.metodologie.core.graphic.entities.staticEntity;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImmediateLayer;
import playn.core.Surface;
import pythagoras.f.Dimension;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.Animation;
import saga.progetto.metodologie.core.graphic.GfxLevel;
import saga.progetto.metodologie.core.graphic.entities.EffectManager;
import saga.progetto.metodologie.core.graphic.entities.EntityRenderer;
import saga.progetto.metodologie.core.graphic.entities.Renderable;
import saga.progetto.metodologie.core.graphic.entities.Sprite;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.background.GfxCell;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Player;
import saga.progetto.metodologie.core.logic.entities.staticEntity.Bonus;

/**
 * 
 * The class {@code GfxBonus} is the graphic implementation of {@link Bonus}.
 *
 */
public abstract class GfxBonus extends Bonus implements Renderable
{
	private static final float FRAME_DURATION = 60.0f;
	private static final IPoint FRAME_ADJUSTMENT = new Point(12, 0);
	protected static final Dimension SIZE = new Dimension(25, 34);
	
	private Bonus bonus;
	private Point frameLocation;
	private int elapsed;
	private GfxLevel level;
	private ImmediateLayer layer;
	private Animation animation;
	private Sprite sprite;
	private EffectManager manager;
	
	public GfxBonus(Bonus bonus, EffectManager manager)
	{
		this.bonus = bonus;
		this.manager = manager;
		frameLocation = new Point();
		animation = Animation.IDLE;
		layer = graphics().createImmediateLayer(new EntityRenderer(this));
		layer.setVisible(false);
		graphics().rootLayer().add(layer);
	}

	/**
	 * Returns the logical counterpart.
	 * 
	 * @return the logical component.
	 */
	public Bonus getBonus()
	{
		return bonus;
	}
	
	/**
	 * Returns the {@code EffectManager} associated with the class.
	 * 
	 * @return the {@code EffectManager} associated with the class.
	 */
	public EffectManager getManager()
	{
		return manager;
	}
	
	@Override
	public float getDuration()
	{
		return bonus.getDuration();
	}
	
	@Override
	public boolean isActive() 
	{ 
		return bonus.isActive(); 
	}
	
	@Override
	public void setActive(boolean active)
	{
		bonus.setActive(active);
	}
	
	@Override
	public Player getPlayer()
	{
		return bonus.getPlayer();
	}
	
	@Override
	public boolean staticCollision(Player player)
	{
		if (bonus.staticCollision(player))
		{
			startEffect();
			setVisible(false);
			elapsed = 0;
			return true;
		}
		return false;
	}
	
	@Override
	public void startEffect()
	{
		bonus.startEffect();
	}
	
	@Override
	public void endEffect()
	{
		bonus.endEffect();
	}
	
	@Override
	public IPoint getFrameLocation() 
	{
		return frameLocation;
	}
	
	@Override
	public Sprite getSprite() 
	{
		return sprite;
	}

	@Override
	public void setSprite(Sprite sprite) 
	{
		this.sprite = sprite;
	}

	@Override
	public Image getGfx() 
	{
		return sprite.getCurrentFrame(animation);
	}
	
	@Override
	public void setCurrentAnimation(Animation currentAnimation)
	{
		this.animation = currentAnimation;
	}
	
	@Override
	public Animation getCurrentAnimation()
	{
		return animation;
	}
	
	@Override
	public void setVisible(boolean visibility)
	{
		bonus.setVisible(visibility);
		layer.setVisible(visibility);
	}
	
	@Override
	public boolean isVisible()
	{
		return bonus.isVisible();
	}
	
	@Override
	public void clear() 
	{
		layer.destroy();
	}
	
	@Override
	public void setLevel(GfxLevel level)
	{
		this.level = level;
		bonus.setLevel(level.getLevel());
	}
	
	@Override
	public GfxLevel getLevel()
	{
		return level;
	}
	
	@Override
	public ImmediateLayer getLayer()
	{
		return layer;
	}
	
	@Override
	public float getFrameDuration() 
	{
		return FRAME_DURATION;
	}
	
	@Override
	public int getX() 
	{
		return bonus.getX(); 
	}
	
	@Override
	public int getY()
	{
		return bonus.getY();
	}
	
	@Override
	public Point getGfxPosition()
	{
		return new Point(getX() * GfxCell.CELL_WIDTH, getY() * GfxCell.CELL_HEIGHT);
	}
	
	@Override
	public void update(int delta)
	{
		sprite.update(delta);
		frameLocation = getLevel().applyIsometry(getGfxPosition()).subtract(FRAME_ADJUSTMENT.x(), FRAME_ADJUSTMENT.y());
		if (isActive())
		{
			elapsed += delta;
			if (elapsed >= getDuration())
				endEffect();
		}
	}
	
	@Override
	public void drawFrame(Surface surface)
	{
		surface.drawImage(getGfx(), getFrameLocation().x(), getFrameLocation().y());
	}
}
