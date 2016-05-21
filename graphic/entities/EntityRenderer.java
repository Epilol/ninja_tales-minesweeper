package saga.progetto.metodologie.core.graphic.entities;

import playn.core.ImmediateLayer;
import playn.core.ImmediateLayer.Renderer;
import playn.core.Surface;

/**
 * 
 * The class {@code EntityRenderer} is an implementation of the interface {@link Renderer}.
 *
 */
public class EntityRenderer implements ImmediateLayer.Renderer
{
	private Renderable renderable;
	
	public EntityRenderer(Renderable renderable)
	{
		this.renderable = renderable;
	}
	
	@Override
	public void render(Surface surface) 
	{
		renderable.drawFrame(surface);
	}
}
