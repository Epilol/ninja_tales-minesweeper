package saga.progetto.metodologie.core.graphic.navigable.button;

import static playn.core.PlayN.assets;
import playn.core.Image;
import pythagoras.f.Circle;
import pythagoras.f.Dimension;
import pythagoras.f.IPoint;
import saga.progetto.metodologie.core.graphic.navigable.Menu;
import saga.progetto.metodologie.core.graphic.navigable.Navigable;

/**
 * 
 * The class {@code ReturnButton} represents a button linking to the previous {@link Menu}.
 *
 */
public class ReturnButton extends Button 
{
	private static final String BUTTON_PATH = "images/button/returnButton.png";
	private static final String OVERLAY_PATH = "images/button/returnOverlay.png";
	private static final Dimension SIZE = new Dimension(48, 48);
	private static Image buttonImage;
	private static Image overlayImage;
	
	private Circle hitBox;
	
	public ReturnButton(Navigable nextState) 
	{
		super(nextState);
		setButtonLayer(buttonImage);
		setOverlayLayer(overlayImage);
	}
	
	/**
	 * Synchronously loads the assets of this class.
	 */
	public static void loadAssets() 
	{
		buttonImage = assets().getImageSync(BUTTON_PATH);
		overlayImage = assets().getImageSync(OVERLAY_PATH);
	}
	
	@Override
	public boolean intersects(IPoint point) 
	{
		return hitBox.contains(point);
	}
	
	@Override
	public void setHitBox(IPoint point) 
	{
		hitBox = new Circle(point.add(SIZE.width / 2, SIZE.height / 2), SIZE.width / 2);
	}
}
