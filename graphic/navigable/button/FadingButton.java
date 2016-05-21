package saga.progetto.metodologie.core.graphic.navigable.button;

import static playn.core.PlayN.graphics;
import saga.progetto.metodologie.core.graphic.navigable.Navigable;

/**
 * 
 * The class {@code FadingButton} represents buttons needing to be added and removed from the rootLayer.
 *
 */
public abstract class FadingButton extends Button
{

	public FadingButton(Navigable nextState) 
	{
		super(nextState);
	}
	
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		if (visible)
		{
			graphics().rootLayer().remove(getButtonLayer());
			graphics().rootLayer().remove(getOverlayLayer());
			graphics().rootLayer().add(getButtonLayer());
			graphics().rootLayer().add(getOverlayLayer());
		}
	}



}
