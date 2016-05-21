package saga.progetto.metodologie.core.graphic.entities;

import playn.core.Image;
import playn.core.ImmediateLayer;
import playn.core.Surface;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.Animation;
import saga.progetto.metodologie.core.graphic.GfxLevel;

/**
 * 
 * The interface {@code Renderable} is used to represent the entities containing animations.
 *
 */
public interface Renderable 
{
	/**
	 * Called by the render method of the entity's ImmediateLayer.
	 * 
	 * @param	surface the layer's surface.
	 */
	void drawFrame(Surface surface);
	
	/**
	 * Returns an {@link IPoint} containing the location of the upper left corner of the image to be drawn.
	 * 
	 * @return	the frame's location.
	 */
	IPoint getFrameLocation();
	
	/**
	 * Returns the associated {@link Sprite}.
	 * 
	 * @return	the associated {@link Sprite}.
	 */
	Sprite getSprite();
	
	/**
	 * Sets the associated {@link Sprite}.
	 * 
	 * @param	sprite the associated {@link Sprite}.
	 */
	void setSprite(Sprite sprite);

	/**
	 * Returns the subimage of the sprite sheet to be rendered.
	 *  
	 * @return	the subimage sprite sheet to be rendered.
	 */
	Image getGfx();
	
	/**
	 * Clears the graphics of the entity.
	 */
	void clear();
	
	/**
	 * Sets the origin {@link GfxLevel} of the entity.
	 * 
	 * @param	level the level to be set.
	 */
	void setLevel(GfxLevel level);
	
	/**
	 * Returns the origin {@link GfxLevel} of the entity.
	 * 
	 * @return	the origin {@link GfxLevel} of the entity.
	 */
	GfxLevel getLevel(); 
	
	/**
	 * Returns the {@link ImmediateLayer} associated with the entity.
	 * 
	 * @return	the {@link ImmediateLayer} associated with the entity.
	 */
	ImmediateLayer getLayer();

	/**
	 * Returns the graphic position of the entity represented by a {@link Point}.
	 * 
	 * @return	the graphic position of the entity.
	 */
	Point getGfxPosition();
	
	/**
	 * Sets a new {@link Animation} for the entity.
	 * 
	 * @param	currentAnimation the {@link Animation} to be set.
	 */
	void setCurrentAnimation(Animation currentAnimation);
	
	/**
	 * Returns the current {@link Animation} of the entity.
	 * 
	 * @return	the current {@link Animation} of the entity.
	 */
	Animation getCurrentAnimation();
	
	/**
	 * Returns the duration of the entity's frames.
	 * 
	 * @return	the duration of the entity's frames.
	 */
	float getFrameDuration();
	
	/**
	 * Called by {@link GfxLevel} on every update call.
	 * 
	 * @param	delta the time in milliseconds since the last call.
	 */
	void update(int delta);
}
