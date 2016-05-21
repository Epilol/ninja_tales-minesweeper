package saga.progetto.metodologie.core.graphic.navigable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.ImageLayer;
import playn.core.Key;
import playn.core.Mouse;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;
import saga.progetto.metodologie.core.graphic.navigable.button.GameMenuButton;
import saga.progetto.metodologie.core.graphic.navigable.button.Button.ButtonType;

/**
 * 
 * The class {@code CharSelectionMenu} represents a menu appearing when the game is in pause.
 *
 */
public class PauseScreen extends SettingsMenu
{
	private static final String TITLE_PATH = "images/menu/pauseTitle.png";
	private static final String BG_PATH = "images/menu/bgGameMenu.png";
	private static final IPoint TITLE_POINT = new Point(230, 20);
	private static final IPoint P1 = new Point(38, 434);
	private static final IPoint P2 = new Point(262, 434);
	private static final IPoint P3 = new Point(503, 434);
	private Button exitButton;
	private Button resumeButton;
	private Button restartButton;
	private ImageLayer bgLayer;
	private GameLoop gameLoop;
	
	public PauseScreen(NinjaTalesMinesweeper game)
	{
		super(game);
		bgLayer = graphics().createImageLayer(assets().getImageSync(BG_PATH));
		getTitleLayer().setVisible(true);
	}
	
	/**
	 * Creates the buttons of the menu passing the destination game states as arguments.
	 * 
	 * @param	home the home menu.
	 * @param	gameLoop the game loop.
	 */
	public void setButtons(Navigable home, GameLoop gameLoop)
	{
		super.setButtons();
		this.gameLoop = gameLoop;
		exitButton = new GameMenuButton(home, ButtonType.EXIT);
		exitButton.setTranslation(P1);
		resumeButton = new GameMenuButton(gameLoop, ButtonType.RESUME);
		resumeButton.setTranslation(P2);
		restartButton = new GameMenuButton(gameLoop, ButtonType.RESTART);
		restartButton.setTranslation(P3);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		if (visible)
			graphics().rootLayer().add(bgLayer);
		else
			graphics().rootLayer().remove(bgLayer);
		
		exitButton.setVisible(visible);
		resumeButton.setVisible(visible);
		restartButton.setVisible(visible);
		super.setVisible(visible);
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{	
		if (event.button() ==  Mouse.BUTTON_LEFT)
		{
			super.onMouseDown(event);
			Point p = new Point(event.localX(), event.localY());
			
			if (exitButton.intersects(p))
			{
				getAudio().playGameTheme(false);
				getAudio().playSelectButton();
				setVisible(false);
				gameLoop.setVisible(false);
				exitButton.getNextState().setVisible(true);
				return exitButton.getNextState();
			}
			
			if (resumeButton.intersects(p))
			{
				getAudio().playSelectButton();
				setVisible(false);
				return resumeButton.getNextState();
			}
			
			if (restartButton.intersects(p))
			{
				getAudio().playSelectButton();
				setVisible(false);
				gameLoop.restartLevel();
				return restartButton.getNextState();
			}
		}
		
		return this;
	}
	
	@Override
	public Navigable onKeyDown(Event event) 
	{
		if (event.key() == Key.ESCAPE)
		{
			setVisible(false);
			return gameLoop;
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
	public void update(int delta)
	{
		super.update(delta);
		exitButton.getOverlayLayer().setVisible(exitButton.intersects(getMouseLocation()));
		resumeButton.getOverlayLayer().setVisible(resumeButton.intersects(getMouseLocation()));
		restartButton.getOverlayLayer().setVisible(restartButton.intersects(getMouseLocation()));
	}
}
