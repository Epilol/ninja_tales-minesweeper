package saga.progetto.metodologie.core.graphic.navigable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.ImageLayer;
import playn.core.Keyboard.Event;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;
import saga.progetto.metodologie.core.graphic.navigable.button.CharSelectionButton;
import saga.progetto.metodologie.core.graphic.navigable.button.ReturnButton;
import saga.progetto.metodologie.core.graphic.navigable.button.Button.ButtonType;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Player.Gender;

/**
 * 
 * The class {@code CharSelectionMenu} represents a menu that allows the character selection.
 *
 */
public class CharSelectionMenu extends Menu 
{
	private static final String TITLE_PATH = "images/menu/charSelectionTitle.png";
	private static final String RIKIMARU_LORE_PATH = "images/menu/rikimaruLore.png";
	private static final String AYAME_LORE_PATH = "images/menu/ayameLore.png";
	private static final String BG_LORE_PATH = "images/menu/bgLore.png";
	private static final IPoint TITLE_POINT = new Point(75, 20);
	private static final IPoint RIKIMARU_LORE_POINT = new Point(10, 244);
	private static final IPoint AYAME_LORE_POINT = new Point(325, 244);
	private static final IPoint BG1_LORE_POINT = new Point(10, 244);
	private static final IPoint BG2_LORE_POINT = new Point(325, 244);
	private static final IPoint P1 = new Point(102, 102);
	private static final IPoint P2 = new Point(402, 102);
	private static final IPoint P3 = new Point(27, 413);
	
	private Button rikimaruButton;
	private Button ayameButton;
	private Button returnButton;
	private ImageLayer rikimaruLoreLayer;
	private ImageLayer ayameLoreLayer;
	private ImageLayer rikimaruBgLayer;
	private ImageLayer ayameBgLayer;
	private GameLoop gameLoop;
	
	public CharSelectionMenu(NinjaTalesMinesweeper game, GameLoop gameLoop)
	{
		super(game);
		this.gameLoop = gameLoop;
		setTitleLayer();
		rikimaruLoreLayer = graphics().createImageLayer(assets().getImageSync(RIKIMARU_LORE_PATH));
		rikimaruLoreLayer.setVisible(false);
		rikimaruLoreLayer.setTranslation(RIKIMARU_LORE_POINT.x(), RIKIMARU_LORE_POINT.y());
		ayameLoreLayer = graphics().createImageLayer(assets().getImageSync(AYAME_LORE_PATH));
		ayameLoreLayer.setVisible(false);
		ayameLoreLayer.setTranslation(AYAME_LORE_POINT.x(), AYAME_LORE_POINT.y());
		rikimaruBgLayer = graphics().createImageLayer(assets().getImageSync(BG_LORE_PATH));
		rikimaruBgLayer.setVisible(false);
		rikimaruBgLayer.setTranslation(BG1_LORE_POINT.x(), BG1_LORE_POINT.y());
		ayameBgLayer = graphics().createImageLayer(assets().getImageSync(BG_LORE_PATH));
		ayameBgLayer.setVisible(false);
		ayameBgLayer.setTranslation(BG2_LORE_POINT.x(), BG2_LORE_POINT.y());
		graphics().rootLayer().add(rikimaruBgLayer);
		graphics().rootLayer().add(ayameBgLayer);
		graphics().rootLayer().add(rikimaruLoreLayer);
		graphics().rootLayer().add(ayameLoreLayer);
	}
	
	/**
	 * Creates the buttons of the menu passing the destination game states as arguments.
	 * 
	 * @param	difficultyMenu the difficulty selection menu.
	 * @param	home the home menu.
	 */
	public void setButtons(Navigable difficultyMenu, Navigable home)
	{
		rikimaruButton = new CharSelectionButton(difficultyMenu, ButtonType.RIKIMARU);
		rikimaruButton.setTranslation(P1);
		ayameButton = new CharSelectionButton(difficultyMenu, ButtonType.AYAME);
		ayameButton.setTranslation(P2);
		returnButton = new ReturnButton(home);
		returnButton.setTranslation(P3);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		rikimaruButton.setVisible(visible);
		ayameButton.setVisible(visible);
		returnButton.setVisible(visible);
		rikimaruLoreLayer.setVisible(false);
		ayameLoreLayer.setVisible(false);
		rikimaruBgLayer.setVisible(visible);
		ayameBgLayer.setVisible(visible);
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{
		if (event.button() ==  Mouse.BUTTON_LEFT)
		{
			Point p = new Point(event.localX(), event.localY());
			
			if (rikimaruButton.intersects(p))
			{
				gameLoop.setGender(Gender.MALE);
				return clickButton(rikimaruButton);
			}
			
			else if (ayameButton.intersects(p))
			{
				gameLoop.setGender(Gender.FEMALE);
				return clickButton(ayameButton);
			}
			
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
		rikimaruLoreLayer.setVisible(rikimaruButton.getOverlayLayer().setVisible(rikimaruButton.intersects(getMouseLocation())).visible());
		ayameLoreLayer.setVisible(ayameButton.getOverlayLayer().setVisible(ayameButton.intersects(getMouseLocation())).visible());
		returnButton.getOverlayLayer().setVisible(returnButton.intersects(getMouseLocation()));
	}
}