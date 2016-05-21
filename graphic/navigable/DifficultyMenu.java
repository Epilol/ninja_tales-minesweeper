package saga.progetto.metodologie.core.graphic.navigable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;
import saga.progetto.metodologie.core.graphic.navigable.button.DifficultyButton;
import saga.progetto.metodologie.core.graphic.navigable.button.ReturnButton;
import saga.progetto.metodologie.core.graphic.navigable.button.Button.ButtonType;

/**
 * 
 * The class {@code DifficultyMenu} represents the menu that allows the difficulty selection.
 *
 */
public class DifficultyMenu extends Menu
{
	private static final String TITLE_PATH = "images/menu/difficultySelectionTitle.png";
	private static final IPoint TITLE_POINT = new Point(160, 20);
	private static final IPoint TOOLTIP_POINT = new Point(358, 144);
	private static final IPoint P1 = new Point(59, 140);
	private static final IPoint P2 = new Point(59, 220);
	private static final IPoint P3 = new Point(59, 300);
	private static final IPoint P4 = new Point(27, 413);
	private static final String EASY_TOOLTIP_PATH = "images/menu/easyTooltip.png";
	private static final String MEDIUM_TOOLTIP_PATH = "images/menu/mediumTooltip.png";
	private static final String HARD_TOOLTIP_PATH = "images/menu/hardTooltip.png";
	
	
	private Button easyButton;
	private Button mediumButton;
	private Button hardButton;
	private Button returnButton;	
	private ImageLayer easyTooltipLayer;
	private ImageLayer mediumTooltipLayer;
	private ImageLayer hardTooltipLayer;
	
	public DifficultyMenu(NinjaTalesMinesweeper game)
	{
		super(game);
		setTitleLayer();
		easyTooltipLayer = graphics().createImageLayer(assets().getImageSync(EASY_TOOLTIP_PATH));
		easyTooltipLayer.setTranslation(TOOLTIP_POINT.x(), TOOLTIP_POINT.y());
		easyTooltipLayer.setVisible(false);
		mediumTooltipLayer = graphics().createImageLayer(assets().getImageSync(MEDIUM_TOOLTIP_PATH));
		mediumTooltipLayer.setTranslation(TOOLTIP_POINT.x(), TOOLTIP_POINT.y());
		mediumTooltipLayer.setVisible(false);
		hardTooltipLayer = graphics().createImageLayer(assets().getImageSync(HARD_TOOLTIP_PATH));
		hardTooltipLayer.setTranslation(TOOLTIP_POINT.x(), TOOLTIP_POINT.y());
		hardTooltipLayer.setVisible(false);
		graphics().rootLayer().add(easyTooltipLayer);
		graphics().rootLayer().add(mediumTooltipLayer);
		graphics().rootLayer().add(hardTooltipLayer);
	}
	
	/**
	 * Creates the buttons of the menu passing the destination game states as arguments.
	 * 
	 * @param	easyMenu the easy difficulty menu.
	 * @param	mediumMenu the medium difficulty menu.
	 * @param	hardMenu the hard difficulty menu.
	 * @param	returnMenu the previous menu (char selection for single player or main menu for multiplayer).
	 */
	public void setButtons(Navigable easyMenu, Navigable mediumMenu, Navigable hardMenu, Navigable returnMenu)
	{
		easyButton = new DifficultyButton(easyMenu, ButtonType.EASY);
		easyButton.setTranslation(P1);
		mediumButton = new DifficultyButton(mediumMenu, ButtonType.MEDIUM);
		mediumButton.setTranslation(P2);
		hardButton = new DifficultyButton(hardMenu, ButtonType.HARD);
		hardButton.setTranslation(P3);
		returnButton = new ReturnButton(returnMenu);
		returnButton.setTranslation(P4);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		easyTooltipLayer.setVisible(false);
		mediumTooltipLayer.setVisible(false);
		hardTooltipLayer.setVisible(false);
		easyButton.setVisible(visible);
		mediumButton.setVisible(visible);
		hardButton.setVisible(visible);
		returnButton.setVisible(visible);
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{
		if (event.button() ==  Mouse.BUTTON_LEFT)
		{
			Point p = new Point(event.localX(), event.localY());
			
			if (easyButton.intersects(p))
				return clickButton(easyButton);
			
			else if (mediumButton.intersects(p))
				return clickButton(mediumButton);
			
			else if (hardButton.intersects(p))
				return clickButton(hardButton);
			
			else if (returnButton.intersects(p))
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
		easyTooltipLayer.setVisible(easyButton.getOverlayLayer().setVisible(easyButton.intersects(getMouseLocation())).visible());
		mediumTooltipLayer.setVisible(mediumButton.getOverlayLayer().setVisible(mediumButton.intersects(getMouseLocation())).visible());
		hardTooltipLayer.setVisible(hardButton.getOverlayLayer().setVisible(hardButton.intersects(getMouseLocation())).visible());
		returnButton.getOverlayLayer().setVisible(returnButton.intersects(getMouseLocation()));
	}
}
