package saga.progetto.metodologie.core.graphic.navigable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import playn.core.Font.Style;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.Text;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;
import saga.progetto.metodologie.core.graphic.navigable.button.ReturnButton;

/**
 * 
 * The class {@code EndGameMenu} represents a menu containing highscores.
 *
 */
public class HighscoreMenu extends Menu
{
	private static final String TITLE_PATH = "images/menu/highscoreTitle.png";
	private static final String HIGHSCORE_PATH = "images/menu/highscore.png";
	private static final IPoint TITLE_POINT = new Point(147, 20);
	private static final IPoint HIGHSCORE_POINT = new Point(21, 90);
	private static final IPoint P1 = new Point(27, 413);
	private static final int FONT_SIZE = 15;
	private static final int FONT_COLOR_ONE = 0xFF016df3;
	private static final int FONT_COLOR_TWO = 0xFF297d39;
	private static final int FONT_COLOR_THREE = 0xFFe61615;

	private Text firstText;
	private Text secondText;
	private Text thirdText;
	private Button returnButton;
	private ImageLayer highscoreLayer;
	
	public HighscoreMenu(NinjaTalesMinesweeper game)
	{
		super(game);
		setTitleLayer();
		firstText = new Text(FONT_SIZE, FONT_COLOR_ONE, Style.BOLD);
		secondText = new Text(FONT_SIZE, FONT_COLOR_TWO, Style.BOLD);
		thirdText = new Text(FONT_SIZE, FONT_COLOR_THREE, Style.BOLD);
		highscoreLayer = graphics().createImageLayer(assets().getImageSync(HIGHSCORE_PATH));
		highscoreLayer.setTranslation(HIGHSCORE_POINT.x(), HIGHSCORE_POINT.y());
		highscoreLayer.setVisible(false);
		graphics().rootLayer().add(highscoreLayer);
	}
	
	/**
	 * 
	 * The enumeration {@code HighscoresPositions} assigns two coordinates to every level in the {@link HighscoreMenu}.
	 *
	 */
	public enum HighscoresPositions
	{
		EASY_ONE(126, 137), EASY_TWO(126, 177), EASY_THREE(126, 217), EASY_FOUR(126, 257), EASY_FIVE(126, 297), EASY_SIX(126, 337),
		MEDIUM_ONE(326, 137), MEDIUM_TWO(326, 177), MEDIUM_THREE(326, 217), MEDIUM_FOUR(326, 257), MEDIUM_FIVE(326, 297), MEDIUM_SIX(326, 337),
		HARD_ONE(526, 137), HARD_TWO(526, 177), HARD_THREE(526, 217), HARD_FOUR(526, 257), HARD_FIVE(526, 297), HARD_SIX(526, 337);
		
		private float x;
		private float y;
		
		private HighscoresPositions(float x, float y) 
		{
			this.x = x;
			this.y = y;
		}
		
		/**
		 * Returns an {@link IPoint} containing the coordinates on which the score has to be displayed.
		 * 
		 * @return	an {@link IPoint} containing the coordinates on which the score has to be displayed.
		 */
		public IPoint getPoint()
		{
			return new Point(x, y);
		}
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
		
		if (visible)
		{
			firstText.clear();
			secondText.clear();
			thirdText.clear();
			firstText.addText(getGame().getData().getProperty("EASY_1_RECORD"), HighscoresPositions.EASY_ONE.getPoint());
			firstText.addText(getGame().getData().getProperty("EASY_2_RECORD"), HighscoresPositions.EASY_TWO.getPoint());
			firstText.addText(getGame().getData().getProperty("EASY_3_RECORD"), HighscoresPositions.EASY_THREE.getPoint());
			firstText.addText(getGame().getData().getProperty("EASY_4_RECORD"), HighscoresPositions.EASY_FOUR.getPoint());
			firstText.addText(getGame().getData().getProperty("EASY_5_RECORD"), HighscoresPositions.EASY_FIVE.getPoint());
			firstText.addText(getGame().getData().getProperty("EASY_6_RECORD"), HighscoresPositions.EASY_SIX.getPoint());
			secondText.addText(getGame().getData().getProperty("MEDIUM_1_RECORD"), HighscoresPositions.MEDIUM_ONE.getPoint());
			secondText.addText(getGame().getData().getProperty("MEDIUM_2_RECORD"), HighscoresPositions.MEDIUM_TWO.getPoint());
			secondText.addText(getGame().getData().getProperty("MEDIUM_3_RECORD"), HighscoresPositions.MEDIUM_THREE.getPoint());
			secondText.addText(getGame().getData().getProperty("MEDIUM_4_RECORD"), HighscoresPositions.MEDIUM_FOUR.getPoint());
			secondText.addText(getGame().getData().getProperty("MEDIUM_5_RECORD"), HighscoresPositions.MEDIUM_FIVE.getPoint());
			secondText.addText(getGame().getData().getProperty("MEDIUM_6_RECORD"), HighscoresPositions.MEDIUM_SIX.getPoint());
			thirdText.addText(getGame().getData().getProperty("HARD_1_RECORD"), HighscoresPositions.HARD_ONE.getPoint());
			thirdText.addText(getGame().getData().getProperty("HARD_2_RECORD"), HighscoresPositions.HARD_TWO.getPoint());
			thirdText.addText(getGame().getData().getProperty("HARD_3_RECORD"), HighscoresPositions.HARD_THREE.getPoint());
			thirdText.addText(getGame().getData().getProperty("HARD_4_RECORD"), HighscoresPositions.HARD_FOUR.getPoint());
			thirdText.addText(getGame().getData().getProperty("HARD_5_RECORD"), HighscoresPositions.HARD_FIVE.getPoint());
			thirdText.addText(getGame().getData().getProperty("HARD_6_RECORD"), HighscoresPositions.HARD_SIX.getPoint());
		}

		firstText.setVisible(visible);
		secondText.setVisible(visible);
		thirdText.setVisible(visible);
		returnButton.setVisible(visible);
		highscoreLayer.setVisible(visible);
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