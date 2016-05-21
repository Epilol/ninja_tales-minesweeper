package saga.progetto.metodologie.core.graphic.navigable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.Text;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;
import saga.progetto.metodologie.core.graphic.navigable.button.GameMenuButton;
import saga.progetto.metodologie.core.graphic.navigable.button.Button.ButtonType;

/**
 * 
 * The class {@code VictoryMenu} represents a menu appearing after winning.
 *
 */
public class VictoryMenu extends EndGameMenu 
{
	private static final String TITLE_PATH = "images/menu/completedTitle.png";
	private static final String STATS_PATH = "images/menu/completedStats.png";
	private static final String SHURIKEN_PATH = "images/menu/glowShuriken.png";
	private static final IPoint TITLE_POINT = new Point(45, 20);
	private static final IPoint STATS_POINT = new Point(47, 105);
	private static final IPoint SCORE_POINT = new Point(170, 322);
	private static final IPoint SHURIKEN1_POINT = new Point(238, 82);
	private static final IPoint SHURIKEN2_POINT = new Point(301, 82);
	private static final IPoint SHURIKEN3_POINT = new Point(364, 82);
	private static final IPoint P1 = new Point(282, 434);
	
	private Text text;
	private Button nextButton;
	private ImageLayer statsLayer;
	private ImageLayer shuriken1;
	private ImageLayer shuriken2;
	private ImageLayer shuriken3;
	
	public VictoryMenu(NinjaTalesMinesweeper game, GameLoop gameloop)
	{
		super(game, gameloop);
		text = new Text();
		statsLayer = graphics().createImageLayer(assets().getImageSync(STATS_PATH));
		statsLayer.setTranslation(STATS_POINT.x(), STATS_POINT.y());
		shuriken1 = graphics().createImageLayer(assets().getImageSync(SHURIKEN_PATH));
		shuriken1.setTranslation(SHURIKEN1_POINT.x(), SHURIKEN1_POINT.y());
		shuriken2 = graphics().createImageLayer(assets().getImageSync(SHURIKEN_PATH));
		shuriken2.setTranslation(SHURIKEN2_POINT.x(), SHURIKEN2_POINT.y());
		shuriken3 = graphics().createImageLayer(assets().getImageSync(SHURIKEN_PATH));
		shuriken3.setTranslation(SHURIKEN3_POINT.x(), SHURIKEN3_POINT.y());
	}
	
	/**
	 * Creates the buttons of the menu passing the destination game states as arguments.
	 * 
	 * @param	home the home menu.
	 */
	public void setButtons(Navigable home) 
	{
		super.setButtons(home);
		nextButton = new GameMenuButton(getGameLoop(), ButtonType.NEXT);
		nextButton.setTranslation(P1);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		nextButton.setVisible(visible);
		
		if (visible)
		{
			text.clear();
			text.addText(getGameLoop().getElapsedTime(), TIME_POINT);
			text.addText(getGameLoop().getTotalFlags(), FLAG1_POINT);
			text.addText(getGameLoop().getFlagPercentage(), FLAG2_POINT);
			text.addText(getGameLoop().getScore(), SCORE_POINT);
			graphics().rootLayer().add(statsLayer);
			graphics().rootLayer().add(shuriken1);
			if(getGameLoop().getStarsValue() > 1) graphics().rootLayer().add(shuriken2);
			if(getGameLoop().getStarsValue() > 2) graphics().rootLayer().add(shuriken3);
		}
		else
		{
			graphics().rootLayer().remove(statsLayer);
			graphics().rootLayer().remove(shuriken1);
			if(getGameLoop().getStarsValue() > 1) graphics().rootLayer().remove(shuriken2);
			if(getGameLoop().getStarsValue() > 2) graphics().rootLayer().remove(shuriken3);
		}
		
		text.setVisible(visible);
	}

	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{	
		if (event.button() ==  Mouse.BUTTON_LEFT)
		{
			
			if (nextButton.intersects(new Point(event.localX(), event.localY())))
			{
				getAudio().playGameTheme(true);
				getAudio().playVictoryTheme(false);
				getAudio().playSelectButton();
				setVisible(false);
				getGameLoop().loadNextLevel();
				return nextButton.getNextState();
			}
		}
		return super.onMouseDown(event);
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
		nextButton.getOverlayLayer().setVisible(nextButton.intersects(getMouseLocation()));
	}
}
