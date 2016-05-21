package saga.progetto.metodologie.core.graphic.navigable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.ImageLayer;
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
 * The class {@code EndGameMenu} represents the menus appearing when the game ends.
 *
 */
public abstract class EndGameMenu extends Menu
{
	private static final String BG_PATH = "images/menu/bgGameMenu.png";
	private static final IPoint P1 = new Point(38, 434);
	private static final IPoint P2 = new Point(503, 434);
	protected static final IPoint TIME_POINT = new Point(170, 172);
	protected static final IPoint FLAG1_POINT = new Point(170, 222);
	protected static final IPoint FLAG2_POINT = new Point(170, 272);
	
	private Button exitButton;
	private Button restartButton;
	private ImageLayer bgLayer;
	private GameLoop gameLoop;
	
	public EndGameMenu(NinjaTalesMinesweeper game, GameLoop gameloop)
	{
		super(game);
		this.gameLoop = gameloop;
		bgLayer = graphics().createImageLayer(assets().getImageSync(BG_PATH));
		getTitleLayer().setVisible(true);
	}
	
	/**
	 * Creates the buttons of the menu passing the destination game states as arguments.
	 * 
	 * @param	home the home menu.
	 */
	public void setButtons(Navigable home) 
	{
		exitButton = new GameMenuButton(home, ButtonType.EXIT);
		exitButton.setTranslation(P1);
		restartButton = new GameMenuButton(gameLoop, ButtonType.RESTART);
		restartButton.setTranslation(P2);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		if (visible)
		{
			graphics().rootLayer().add(bgLayer);
			setTitleLayer();
		}
		else
		{
			graphics().rootLayer().remove(bgLayer);
			graphics().rootLayer().remove(getTitleLayer());
		}
		exitButton.setVisible(visible);
		restartButton.setVisible(visible);
		getTitleLayer().setVisible(visible);
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{	
		Point p = new Point(event.localX(), event.localY());
		
		if (exitButton.intersects(p))
		{
			getAudio().playVictoryTheme(false);
			getAudio().playDefeatTheme(false);
			getAudio().playSelectButton();
			setVisible(false);
			gameLoop.setVisible(false);
			exitButton.getNextState().setVisible(true);
			return exitButton.getNextState();
		}
		
		else if (restartButton.intersects(p))
		{
			getAudio().playVictoryTheme(false);
			getAudio().playDefeatTheme(false);
			getAudio().playGameTheme(true);
			getAudio().playSelectButton();
			setVisible(false);
			gameLoop.restartLevel();
			return restartButton.getNextState();
		}
		return this;
	}

	/**
	 * Returns the {@link GameLoop} associated with this menu.
	 * 
	 * @return the {@link GameLoop} associated with this menu.
	 */
	protected GameLoop getGameLoop()
	{
		return gameLoop;
	}
	
	@Override
	public Navigable onKeyDown(Event event) 
	{
		return this;
	}
	
	@Override
	public void update(int delta)
	{
		exitButton.getOverlayLayer().setVisible(exitButton.intersects(getMouseLocation()));
		restartButton.getOverlayLayer().setVisible(restartButton.intersects(getMouseLocation()));
	}
	
}
