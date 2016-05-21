package saga.progetto.metodologie.core.graphic.navigable.button;

import static playn.core.PlayN.assets;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IPoint;
import pythagoras.f.Rectangle;
import saga.progetto.metodologie.core.graphic.navigable.Navigable;
import saga.progetto.metodologie.core.graphic.navigable.OptionsMenu;
import saga.progetto.metodologie.core.graphic.navigable.SettingsMenu;

/**
 * 
 * The class {@code VolumeButton} represents a button of the {@link SettingsMenu} or the {@link OptionsMenu}.
 *
 */
public class VolumeButton extends FadingButton
{
	private static final String BUTTON_PATH = "images/button/volumeButton.png";
	private static final Dimension SIZE = new Dimension(17, 17);
	private static Image buttonImage;
	
	private Rectangle hitBox;
	
	public VolumeButton(Navigable nextState, ButtonType buttonType) 
	{
		super(nextState);
		setButtonLayer(buttonImage.subImage(SIZE.width, SIZE.height * buttonType.getIndex(), SIZE.width, SIZE.height));
		setOverlayLayer(buttonImage.subImage(0, SIZE.height * buttonType.getIndex(), SIZE.width, SIZE.height));
	}
	
	/**
	 * Synchronously loads the assets of this class.
	 */
	public static void loadAssets() 
	{
		buttonImage = assets().getImageSync(BUTTON_PATH);
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