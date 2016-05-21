package saga.progetto.metodologie.core.graphic.navigable.button;

import static playn.core.PlayN.assets;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IPoint;
import pythagoras.f.Rectangle;
import saga.progetto.metodologie.core.graphic.navigable.CharSelectionMenu;
import saga.progetto.metodologie.core.graphic.navigable.Navigable;

/**
 * 
 * The class {@code CharSelectionButton} represents buttons of the {@link CharSelectionMenu}.
 *
 */
public class CharSelectionButton extends Button 
{
	private static final String BUTTON_PATH = "images/button/charSelectionButton.png";
	private static final String OVERLAY_PATH = "images/button/charSelectionOverlay.png";
	private static final Dimension SIZE = new Dimension(138, 138);
	private static Image buttonImage;
	private static Image overlayImage;
	
	private Rectangle hitBox;
	
	public CharSelectionButton(Navigable nextState, ButtonType buttonType) 
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
	
}
