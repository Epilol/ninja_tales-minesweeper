package saga.progetto.metodologie.core.graphic.entities.movingEntity;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImmediateLayer;
import playn.core.Surface;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.Animation;
import saga.progetto.metodologie.core.graphic.GfxLevel;
import saga.progetto.metodologie.core.graphic.entities.EntityRenderer;
import saga.progetto.metodologie.core.graphic.entities.Renderable;
import saga.progetto.metodologie.core.graphic.entities.Sprite;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.background.GfxCell;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Direction;
import saga.progetto.metodologie.core.logic.entities.movingEntity.MovingEntity;

/**
 * 
 * The class {@code GfxMovingEntity} is the graphic implementation of {@link MovingEntity}.
 *
 */
public abstract class GfxMovingEntity extends MovingEntity implements Renderable
{
	private static final float FRAME_DURATION = 100.0f;
	/**
	 * The view distance of the entity.
	 */
	public static final int VIEW_DISTANCE = 6;
	
	private GfxLevel level;
	private MovingEntity movingEntity;
	private ImmediateLayer layer;
	private Animation animation;
	private Sprite sprite;
	private float deltaX = 25.0f;
	private float deltaY = 25.0f;

	
	public GfxMovingEntity(MovingEntity movingEntity)
	{
		this.movingEntity = movingEntity;
		animation = Animation.IDLE;
		layer = graphics().createImmediateLayer(new EntityRenderer(this));
		layer.setVisible(false);
		graphics().rootLayer().add(layer);
	}
	
	/**
	 * Returns the logical counterpart of the moving entity.
	 * 
	 * @return	the logical component.
	 */
	public MovingEntity getMovingEntity()
	{
		return movingEntity;
	}
	
	/**
	 * Returns an {@link IRectangle} representing the hitBox of the entity.
	 * 
	 * @return	the hitBox of the entity.
	 */
	public abstract IRectangle getHitBox();
	
	/**
	 * Returns a {@link Point} containing the position in the logic background of the entity.
	 * 
	 * @return	the logic position of the entity.
	 */
	public Point getLogicPosition()
	{
		return new Point(getX(), getY());
	}
	
	@Override
	public boolean isDead()
	{
		return movingEntity.isDead();
	}
	
	@Override
	public void setDead(boolean isDead)
	{
		movingEntity.setDead(isDead);
	}
	
	@Override
	public boolean canFly()
	{
		return movingEntity.canFly();
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
	public void setVisible(boolean visibility)
	{
		layer.setVisible(visibility);
	}
	
	@Override
	public void clear() 
	{
		layer.destroy();
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
	public float getFrameDuration() 
	{
		return FRAME_DURATION;
	}

	@Override
	public Point getGfxPosition()
	{
		return new Point(getX() * GfxCell.CELL_WIDTH + deltaX, getY() * GfxCell.CELL_HEIGHT + deltaY);
	}
	
	@Override
	public int getX()
	{
		return movingEntity.getX();
	}
	
	@Override
	public int getY()
	{
		return movingEntity.getY();
	}
	
	/**
	 * Prevents the desynchronization of logic and graphic positions by adjusting the direction suitably.
	 * 
	 * @param	destination the destination of the entity.
	 */
	public void setAdjustment(Point destination)
	{
		setDirection(destination);
		
		switch(getDirection())
		{
			case TOP_LEFT:
				setDirection(getDestinationDelta().y < getDestinationDelta().x? Direction.LEFT : getDestinationDelta().y > getDestinationDelta().x? Direction.UP : Direction.TOP_LEFT); 
				break;
			case TOP_RIGHT:
				setDirection(getDestinationDelta().y < getDestinationDelta().x? Direction.RIGHT : getDestinationDelta().y > getDestinationDelta().x? Direction.UP : Direction.TOP_RIGHT); 
				break;
			case BOTTOM_LEFT:
				setDirection(getDestinationDelta().y < getDestinationDelta().x? Direction.LEFT : getDestinationDelta().y > getDestinationDelta().x? Direction.DOWN : Direction.BOTTOM_LEFT); 
				break;
			case BOTTOM_RIGHT:
				setDirection(getDestinationDelta().y < getDestinationDelta().x? Direction.RIGHT : getDestinationDelta().y > getDestinationDelta().x? Direction.DOWN : Direction.BOTTOM_RIGHT); 
				break;
			case RIGHT:
				if (getDestinationDelta().x < getDestinationDelta().y) setDirection(deltaY > 25? Direction.UP  : deltaY < 25? Direction.DOWN : Direction.RIGHT);
				else setDirection(deltaY > 25? Direction.TOP_RIGHT  : deltaY < 25? Direction.BOTTOM_RIGHT : Direction.RIGHT);
				break;
			case LEFT:
				if (getDestinationDelta().x < getDestinationDelta().y) setDirection(deltaY > 25? Direction.UP  : deltaY < 25? Direction.DOWN : Direction.LEFT);
				else setDirection(deltaY > 25? Direction.TOP_LEFT : deltaY < 25? Direction.BOTTOM_LEFT : Direction.LEFT);
				break;
			case UP:
				if (getDestinationDelta().y < getDestinationDelta().x) setDirection(deltaX > 25? Direction.LEFT  : deltaX < 25? Direction.RIGHT : Direction.UP);
				else setDirection(deltaX > 25? Direction.TOP_LEFT : deltaX < 25? Direction.TOP_RIGHT : Direction.UP);
				break;
			case DOWN:
				if (getDestinationDelta().y < getDestinationDelta().x) setDirection(deltaX > 25? Direction.LEFT  : deltaX < 25? Direction.RIGHT : Direction.DOWN);
				else setDirection(deltaX > 25? Direction.BOTTOM_LEFT : deltaX < 25? Direction.BOTTOM_RIGHT : Direction.DOWN);
				break;
			default: setDirection(getLogicPosition().subtract(getVisualDelta().x, getVisualDelta().y));
		}
		sprite.setLastDirection(getDirection());
	}
	
	@Override
	public void setDirection(Point destination)
	{
		movingEntity.setDirection(destination);
		if (getDirection() != Direction.DEFAULT)
		{
			animation = Animation.RUN;
			sprite.setLastDirection(getDirection());
		}
		else animation = Animation.IDLE;
	}
	
	@Override
	public void setDirection(Direction direction)
	{
		movingEntity.setDirection(direction);
	}
	
	@Override
	public Direction getDirection()
	{
		return movingEntity.getDirection();
	}
	
	@Override
	public void setX(int directionX)
	{
		deltaX += directionX * getSpeed();
		if (deltaX <= GfxCell.CELL_WIDTH / 2 - GfxCell.CELL_WIDTH || deltaX >= GfxCell.CELL_WIDTH / 2 + GfxCell.CELL_WIDTH)
		{
			// the logic position is updated everytime the middle of a cell is reached.
			movingEntity.setX(getX() + directionX);
			deltaX = deltaX - GfxCell.CELL_WIDTH * directionX;
		}
	}
	
	@Override
	public void setY(int directionY)
	{
		deltaY += directionY * getSpeed();
		if (deltaY <= GfxCell.CELL_HEIGHT / 2 - GfxCell.CELL_HEIGHT|| deltaY >= GfxCell.CELL_HEIGHT / 2 + GfxCell.CELL_HEIGHT)
		{
			// the logic position is updated everytime the middle of a cell is reached.
			movingEntity.setY(getY() + directionY);
			deltaY = deltaY - GfxCell.CELL_HEIGHT * directionY;
		}
			
	}
	
	/**
	 * The logic position of a moving entity is not changed until it reaches the middle of a cell. This method allows the
	 * calculation of the cell being stepped on by the entity. The return value ranges in the interval [-1, 1].
	 * 
	 * @return	the entity's offset in cells from its logic position.
	 */
	public Point getVisualDelta()
	{
		return new Point((deltaX - GfxCell.CELL_WIDTH / 2) / GfxCell.CELL_WIDTH, (deltaY - GfxCell.CELL_HEIGHT / 2) / GfxCell.CELL_HEIGHT);
	}
	
	/**
	 * Returns the entity's distance from the next cell, given the current direction.
	 * 
	 * @return	the entity's distance from the next cell, given the current direction.
	 */
	public Point getDestinationDelta()
	{
		return new Point(Math.abs(getDirection().getX() * GfxCell.CELL_WIDTH +  GfxCell.CELL_WIDTH / 2 - deltaX), 
				Math.abs(getDirection().getY() * GfxCell.CELL_HEIGHT +  GfxCell.CELL_HEIGHT / 2 - deltaY));
	}
	
	/**
	 * Returns whether the entity is in the middle of a cell.
	 * 
	 * @return true if the entity is in the middle of a cell, false otherwise.
	 */
	public boolean isMoving()
	{
		return deltaY != GfxCell.CELL_HEIGHT / 2 || deltaX != GfxCell.CELL_WIDTH / 2;
	}
	
	@Override
	public float getSpeed()
	{
		return movingEntity.getSpeed();
	}
	
	@Override
	public void setSpeedModifier(float speedModifier)
	{
		movingEntity.setSpeedModifier(speedModifier);
	}
	
	@Override
	public float getSpeedModifier()
	{
		return movingEntity.getSpeedModifier();
	}
	
	@Override
	public void update(int delta) 
	{
		if (!isDead())
		{
			if (animation != Animation.ATTACK && animation != Animation.DEATH)
			{
				setX(getDirection().getX());
				setY(getDirection().getY());
			}
			
			sprite.update(delta);
			
			if (animation == Animation.DEATH && sprite.isOver(animation))
				setDead(true);
		}
	}
	
	@Override
	public void drawFrame(Surface surface)
	{
		surface.drawImage(getGfx(), getFrameLocation().x(), getFrameLocation().y());
	}
}
