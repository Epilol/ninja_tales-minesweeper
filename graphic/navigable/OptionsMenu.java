package saga.progetto.metodologie.core.graphic.navigable;

import playn.core.Mouse;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;
import saga.progetto.metodologie.core.graphic.navigable.button.ReturnButton;

/**
 * 
 * The class {@code OptionsMenu} represents a menu that allows the modification of the game settings.
 *
 */
public class OptionsMenu extends SettingsMenu
{
	private static final String TITLE_PATH = "images/menu/optionsTitle.png";
	private static final IPoint TITLE_POINT = new Point(200, 20);
	private static final IPoint P1 = new Point(27, 413);
	private Button returnButton;
		
	public OptionsMenu(NinjaTalesMinesweeper game) 
	{
		super(game);
		setTitleLayer();
	}

	/**
	 * Creates the buttons of the menu passing the destination game states as arguments.
	 * 
	 * @param	home the home menu.
	 */
	public void setButtons(Navigable home)
	{
		super.setButtons();
		returnButton = new ReturnButton(home);
		returnButton.setTranslation(P1);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		returnButton.setVisible(visible);
		getBgLayer().setVisible(visible);
		super.setVisible(visible);
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{	
		if (event.button() ==  Mouse.BUTTON_LEFT)
		{
			super.onMouseDown(event);
			Point p = new Point(event.localX(), event.localY());
			
			if (returnButton.intersects(p))
				return clickButton(returnButton);
		}
		
		return this;
	}

	@Override
	public Navigable onKeyDown(Event event) 
	{
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
	public void update(int delta) 
	{
		super.update(delta);
		returnButton.getOverlayLayer().setVisible(returnButton.intersects(getMouseLocation()));
	}
	
	

}
