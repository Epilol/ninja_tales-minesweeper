package saga.progetto.metodologie.core.graphic.navigable;

import playn.core.Mouse;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;
import saga.progetto.metodologie.core.graphic.navigable.button.MainMenuButton;
import saga.progetto.metodologie.core.graphic.navigable.button.Button.ButtonType;

/**
 * 
 * The class {@code MainMenu} represents a main menu.
 *
 */
public class MainMenu extends Menu
{
	private static final String TITLE_PATH = "images/menu/title.png";
	private static final IPoint TITLE_POINT = new Point(98, 20);
	private static final IPoint P1 = new Point(180, 140);
	private static final IPoint P2 = new Point(180, 200);
	private static final IPoint P3 = new Point(180, 260);
	private static final IPoint P4 = new Point(180, 320);
	private static final IPoint P5 = new Point(180, 380);
	private Button singleplayerButton;
	private Button multiplayerButton;
	private Button optionsButton;
	private Button highscoreButton;
	private Button guideButton;
	private GameLoop gameLoop;
	
	public MainMenu(NinjaTalesMinesweeper game, GameLoop gameLoop)
	{
		super(game);
		this.gameLoop = gameLoop;
		setTitleLayer();
	}
	
	/**
	 * Creates the buttons of the menu passing the destination game states as arguments.
	 * 
	 * @param	charSelectionMenu the character selection menu.
	 * @param	optionsMenu the options menu.
	 * @param	highscoreMenu the highscore menu.
	 * @param	guideMenu the guide menu.
	 * @param	difficultyMenu the difficulty menu.
	 */
	public void setButtons(Navigable charSelectionMenu, Navigable optionsMenu, Navigable highscoreMenu, Navigable guideMenu, Navigable difficultyMenu)
	{
		singleplayerButton = new MainMenuButton(charSelectionMenu, ButtonType.SINGLEPLAYER);
		singleplayerButton.setTranslation(P1);
		multiplayerButton = new MainMenuButton(difficultyMenu, ButtonType.MULTIPLAYER);
		multiplayerButton.setTranslation(P2);
		optionsButton = new MainMenuButton(optionsMenu, ButtonType.OPTIONS);
		optionsButton.setTranslation(P3);
		highscoreButton = new MainMenuButton(highscoreMenu, ButtonType.HIGHSCORE);
		highscoreButton.setTranslation(P4);
		guideButton = new MainMenuButton(guideMenu, ButtonType.GUIDE);
		guideButton.setTranslation(P5);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		if (visible) getAudio().playMenuTheme(true);
		super.setVisible(visible);
		singleplayerButton.setVisible(visible);
		multiplayerButton.setVisible(visible);
		optionsButton.setVisible(visible);
		highscoreButton.setVisible(visible);
		guideButton.setVisible(visible);
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{	
		if (event.button() ==  Mouse.BUTTON_LEFT)
		{
			Point p = new Point(event.localX(), event.localY());
			
			if (singleplayerButton.intersects(p))
			{
				gameLoop.setMultiPlayer(false);
				return clickButton(singleplayerButton);
			}
			
			if (multiplayerButton.intersects(p))
			{
				gameLoop.setMultiPlayer(true);
				return clickButton(multiplayerButton);
			}
			
			if (optionsButton.intersects(p))
				return clickButton(optionsButton);
			
			if (highscoreButton.intersects(p))
				return clickButton(highscoreButton);
			
			if (guideButton.intersects(p))
				return clickButton(guideButton);
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
		singleplayerButton.getOverlayLayer().setVisible(singleplayerButton.intersects(getMouseLocation()));
		multiplayerButton.getOverlayLayer().setVisible(multiplayerButton.intersects(getMouseLocation()));
		optionsButton.getOverlayLayer().setVisible(optionsButton.intersects(getMouseLocation()));
		highscoreButton.getOverlayLayer().setVisible(highscoreButton.intersects(getMouseLocation()));
		guideButton.getOverlayLayer().setVisible(guideButton.intersects(getMouseLocation()));
	}
}
