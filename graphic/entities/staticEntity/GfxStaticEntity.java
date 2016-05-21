package saga.progetto.metodologie.core.graphic.entities.staticEntity;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImmediateLayer;
import playn.core.Surface;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.Animation;
import saga.progetto.metodologie.core.graphic.GfxLevel;
import saga.progetto.metodologie.core.graphic.entities.EntityRenderer;
import saga.progetto.metodologie.core.graphic.entities.Renderable;
import saga.progetto.metodologie.core.graphic.entities.Sprite;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.background.GfxCell;
import saga.progetto.metodologie.core.logic.entities.staticEntity.StaticEntity;

/**
 * 
 * The class {@code GfxStaticEntity} is the graphic implementation of {@link StaticEntity}.
 *
 */
public abstract class GfxStaticEntity extends StaticEntity implements Renderable
{
	private static final float FRAME_DURATION = 60.0f;
	
	private GfxLevel level;
	private StaticEntity staticEntity;
	private ImmediateLayer layer;
	private Animation animation;
	private Sprite sprite;
	
	public GfxStaticEntity(StaticEntity staticEntity) 
	{
		this.staticEntity = staticEntity;
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
	public StaticEntity getStaticEntity()
	{
		return staticEntity;
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
	public void setVisible(boolean visible)
	{
		staticEntity.setVisible(visible);
		layer.setVisible(visible);
	}
	
	@Override
	public boolean isVisible()
	{
		return staticEntity.isVisible();
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
		return staticEntity.getX(); 
	}
	
	@Override
	public int getY()
	{
		return staticEntity.getY();
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
	}
	
	@Override
	public void drawFrame(Surface surface)
	{
		surface.drawImage(getGfx(), getFrameLocation().x(), getFrameLocation().y());
	}
	
}
