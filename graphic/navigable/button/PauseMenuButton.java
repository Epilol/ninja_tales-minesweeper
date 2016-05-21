package saga.progetto.metodologie.core.graphic.navigable.button;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IPoint;
import pythagoras.f.Rectangle;
import saga.progetto.metodologie.core.graphic.navigable.Navigable;

/**
 * 
 * The class {@code PauseMenuButton} represents buttons of the {@link PauseMenuButton}.
 *
 */
public class PauseMenuButton extends Button 
{
	private static final String BUTTON_PATH = "images/button/pauseButton.png";
	private static final String OVERLAY_PATH = "images/button/pauseOverlay.png";
	private static final Dimension SIZE = new Dimension(87,32);
	private static Image buttonImage;
	private static Image overlayImage;
	
	private Rectangle hitBox;
	
	public PauseMenuButton(Navigable nextState, ButtonType buttonType) 
	{
		super(nextState);
		setButtonLayer(buttonImage.subImage(0, SIZE.height * buttonType.getIndex(), SIZE.width, SIZE.height));
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
		hitBox = new Rectangle(point, SIZE);
	}
	
	@Override
	public void setButtonLayer(Image buttonImage)
	{
		super.setButtonLayer(buttonImage);
		graphics().rootLayer().remove(getButtonLayer());
	}
	
	@Override
	public void setOverlayLayer(Image overlayImage)
	{
		super.setOverlayLayer(overlayImage);
		graphics().rootLayer().remove(getOverlayLayer());
	}
	
}
