package saga.progetto.metodologie.core.graphic.navigable;

import playn.core.Keyboard.Event;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;
import saga.progetto.metodologie.core.graphic.navigable.button.LevelButton;
import saga.progetto.metodologie.core.graphic.navigable.button.ReturnButton;
import saga.progetto.metodologie.core.graphic.navigable.button.Button.ButtonType;

/**
 * 
 * The class {@code LevelMenu} represents a that allows the level selection.
 *
 */
public class LevelMenu extends Menu
{
	private static final String TITLE_PATH = "images/menu/levelSelectionTitle.png";
	private static final IPoint TITLE_POINT = new Point(58, 20);
	private static final IPoint P1 = new Point(74, 84);
	private static final IPoint P2 = new Point(254, 84);
	private static final IPoint P3 = new Point(434, 84);
	private static final IPoint P4 = new Point(74, 264);
	private static final IPoint P5 = new Point(254, 264);
	private static final IPoint P6 = new Point(434, 264);
	private static final IPoint P7 = new Point(27, 413);
	
	private LevelButton levelOneButton;
	private LevelButton levelTwoButton;
	private LevelButton levelThreeButton;
	private LevelButton levelFourButton;
	private LevelButton levelFiveButton;
	private LevelButton levelSixButton;
	private Button returnButton;
	private ButtonType difficulty;
	private GameLoop gameLoop;
	
	public LevelMenu(NinjaTalesMinesweeper game, GameLoop gameLoop, ButtonType difficulty)
	{
		super(game);
		this.gameLoop = gameLoop;
		this.difficulty = difficulty;
		setTitleLayer();
	}
	
	/**
	 * Creates the buttons of the menu passing the destination game states as arguments.
	 * 
	 * @param	difficultyMenu the difficulty selection menu.
	 * @param	loadingScreen the loading screen.
	 */
	public void setButtons(Navigable difficultyMenu, Navigable loadingScreen)
	{
		levelOneButton = new LevelButton(loadingScreen, ButtonType.LEVEL_ONE, difficulty, 
				Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_1_ACCESS")), 
				Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_1_STARS")));
		levelOneButton.setTranslation(P1);
		
		levelTwoButton = new LevelButton(loadingScreen, ButtonType.LEVEL_TWO, difficulty, 
				Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_2_ACCESS")), 
				Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_2_STARS")));
		levelTwoButton.setTranslation(P2);
		
		levelThreeButton = new LevelButton(loadingScreen, ButtonType.LEVEL_THREE, difficulty, 
				Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_3_ACCESS")), 
				Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_3_STARS")));
		levelThreeButton.setTranslation(P3);
		
		levelFourButton = new LevelButton(loadingScreen, ButtonType.LEVEL_FOUR, difficulty, 
				Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_4_ACCESS")), 
				Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_4_STARS")));
		levelFourButton.setTranslation(P4);
		
		levelFiveButton = new LevelButton(loadingScreen, ButtonType.LEVEL_FIVE, difficulty, 
				Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_5_ACCESS")), 
				Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_5_STARS")));
		levelFiveButton.setTranslation(P5);
		
		levelSixButton = new LevelButton(loadingScreen, ButtonType.LEVEL_SIX, difficulty, 
				Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_6_ACCESS")), 
				Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_6_STARS")));
		levelSixButton.setTranslation(P6);
		
		returnButton = new ReturnButton(difficultyMenu);
		returnButton.setTranslation(P7);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		
		if (visible)
		{
			levelOneButton.setLocked(Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_1_ACCESS")));
			levelOneButton.setStars(Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_1_STARS")));
			levelTwoButton.setLocked(Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_2_ACCESS")));
			levelTwoButton.setStars(Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_2_STARS")));
			levelThreeButton.setLocked(Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_3_ACCESS")));
			levelThreeButton.setStars(Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_3_STARS")));
			levelFourButton.setLocked(Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_4_ACCESS")));
			levelFourButton.setStars(Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_4_STARS")));
			levelFiveButton.setLocked(Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_5_ACCESS")));
			levelFiveButton.setStars(Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_5_STARS")));
			levelSixButton.setLocked(Boolean.parseBoolean(getGame().getData().getProperty(difficulty.name() + "_6_ACCESS")));
			levelSixButton.setStars(Integer.parseInt(getGame().getData().getProperty(difficulty.name() + "_6_STARS")));
		}
		
		levelOneButton.setVisible(visible);
		levelTwoButton.setVisible(visible);
		levelThreeButton.setVisible(visible);
		levelFourButton.setVisible(visible);
		levelFiveButton.setVisible(visible);
		levelSixButton.setVisible(visible);
		returnButton.setVisible(visible);
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{
		if (event.button() ==  Mouse.BUTTON_LEFT)
		{
			Point p = new Point(event.localX(), event.localY());
			
			if (levelOneButton.isUnlocked() && levelOneButton.intersects(p))
				return clickButton(levelOneButton);
			
			if (levelTwoButton.isUnlocked() && levelTwoButton.intersects(p))
				return clickButton(levelTwoButton);
				
			if (levelThreeButton.isUnlocked() && levelThreeButton.intersects(p))
				return clickButton(levelThreeButton);
			
			if (levelFourButton.isUnlocked() && levelFourButton.intersects(p))
				return clickButton(levelFourButton);
			
			if (levelFiveButton.isUnlocked() && levelFiveButton.intersects(p))
				return clickButton(levelFiveButton);
			
			if (levelSixButton.isUnlocked() && levelSixButton.intersects(p))
				return clickButton(levelSixButton);
		
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

	/**
	 * Handles the click of a button. Different from {@link #clickButton(Button)} because it specifically needs a {@link LevelButton}.
	 * 
	 * @param	button the clicked button.
	 * @return	the game state linked to the button.
	 */
	public Navigable clickButton(LevelButton button)
	{
		gameLoop.setLevel(button.getLevel());
		return super.clickButton(button);
	}

	@Override
	public Navigable onKeyDown(Event event) 
	{
		return this;
	}

	@Override
	public void update(int delta)
	{
		levelOneButton.getOverlayLayer().setVisible(levelOneButton.intersects(getMouseLocation()) && levelOneButton.isUnlocked());
		levelTwoButton.getOverlayLayer().setVisible(levelTwoButton.intersects(getMouseLocation()) && levelTwoButton.isUnlocked());
		levelThreeButton.getOverlayLayer().setVisible(levelThreeButton.intersects(getMouseLocation()) && levelThreeButton.isUnlocked());
		levelFourButton.getOverlayLayer().setVisible(levelFourButton.intersects(getMouseLocation()) && levelFourButton.isUnlocked());
		levelFiveButton.getOverlayLayer().setVisible(levelFiveButton.intersects(getMouseLocation()) && levelFiveButton.isUnlocked());
		levelSixButton.getOverlayLayer().setVisible(levelSixButton.intersects(getMouseLocation()) && levelSixButton.isUnlocked());
		returnButton.getOverlayLayer().setVisible(returnButton.intersects(getMouseLocation()));
	}
	
}
