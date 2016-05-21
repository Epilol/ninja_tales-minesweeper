package saga.progetto.metodologie.core.graphic.navigable;

import pythagoras.f.IPoint;
import pythagoras.f.Point;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;
import saga.progetto.metodologie.core.graphic.navigable.button.ReturnButton;

/**
 * 
 * The class {@code GuideMenu} represents a menu containing game info.
 *
 */
public class GuideMenu extends Menu
{
	private static final String TITLE_PATH = "images/menu/guideTitle.png";
	private static final String GUIDE_PATH = "images/menu/guide.png";
	private static final IPoint TITLE_POINT = new Point(239, 20);
	private static final IPoint GUIDE_POINT = new Point(20, 91);
	private static final IPoint P1 = new Point(27, 413);
	
	private Button returnButton;
	private ImageLayer guideLayer;
	
	public GuideMenu(NinjaTalesMinesweeper game)
	{
		super(game);
		setTitleLayer();
		guideLayer = graphics().createImageLayer(assets().getImageSync(GUIDE_PATH));
		guideLayer.setTranslation(GUIDE_POINT.x(), GUIDE_POINT.y());
		guideLayer.setVisible(false);
		graphics().rootLayer().add(guideLayer);
	}
	
	/**
	 * Creates the buttons of the menu passing the destination game states as arguments.
	 * 
	 * @param home the home menu.
	 */
	public void setButtons(Navigable home)
	{
		returnButton = new ReturnButton(home);
		returnButton.setTranslation(P1);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		returnButton.setVisible(visible);
		guideLayer.setVisible(visible);
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{
		if (event.button() ==  Mouse.BUTTON_LEFT)
		{
			Point p = new Point(event.localX(), event.localY());
			
			if (returnButton.intersects(p))
				return clickButton(returnButton);
		}
		
		return this;
	}
	
	@Override
	public String getTitlePath()
	{
		return TITLE_PATH;
	}
	
	@Override
	public IPoint getTitlePoint()
	{
		return TITLE_POINT;
	}
	
	@Override
	public Navigable onKeyDown(Event event) 
	{
		return this;
	}
	
	
	@Override
	public void update(int delta)
	{
		returnButton.getOverlayLayer().setVisible(returnButton.intersects(getMouseLocation()));
	}
	
}