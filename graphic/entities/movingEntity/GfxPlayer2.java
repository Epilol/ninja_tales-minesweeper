package saga.progetto.metodologie.core.graphic.entities.movingEntity;

import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Player;

/**
 * 
 * The class {@code GfxPlayer2} represents the second playable character.
 *
 */
public class GfxPlayer2 extends GfxPlayer
{
	private Point frameLocation;

	public GfxPlayer2(Player player) 
	{
		super(player);
		frameLocation = new Point();
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
		moveHitBox(getLevel().applyIsometry(getGfxPosition()).subtract(HITBOX_ADJUSTMENT.x(), HITBOX_ADJUSTMENT.y()));
		frameLocation = getLevel().applyIsometry(getGfxPosition()).subtract(FRAME_ADJUSTMENT.x(), FRAME_ADJUSTMENT.y());
	}
}
