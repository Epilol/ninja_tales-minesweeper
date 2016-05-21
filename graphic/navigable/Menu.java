package saga.progetto.metodologie.core.graphic.navigable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import playn.core.ImageLayer;
import pythagoras.f.IPoint;
import saga.progetto.metodologie.core.audio.AudioManager;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;

/**
 * 
 * The class {@code Menu} represents generic menus.
 *
 */
public abstract class Menu implements Navigable
{
	private static final String BG_PATH = "images/menu/menuBackground.png";
	private static AudioManager audio;
	private ImageLayer bgLayer;
	private ImageLayer titleLayer;
	private NinjaTalesMinesweeper game;
	
	
	public Menu(NinjaTalesMinesweeper game)
	{
		this.game = game;
		bgLayer = graphics().createImageLayer(assets().getImageSync(BG_PATH));
		bgLayer.setVisible(false);
		titleLayer = graphics().createImageLayer(assets().getImageSync(getTitlePath()));
		titleLayer.setTranslation(getTitlePoint().x(), getTitlePoint().y());
		titleLayer.setVisible(false);
		graphics().rootLayer().add(bgLayer);
	}

	/**
	 * Handles the click of the button.
	 * 
	 * @param	button the clicked button.
	 * @return	the game state associated with the button.
	 */
	public Navigable clickButton(Button button)
	{	
		button.playButtonAudio();
		setVisible(false);
		button.getNextState().setVisible(true);
		return button.getNextState();
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		bgLayer.setVisible(visible);
		titleLayer.setVisible(visible);
	}
	
	/**
	 * Sets the {@link AudioManager} for this class.
	 * 
	 * @param	audio the {@link AudioManager} instance to be set.
	 */
	public static void loadAudio(AudioManager audio)
	{
		Menu.audio = audio;
	}
	
	/**
	 * Returns the {@link AudioManager} instance associated with this menu.
	 * 
	 * @return	the {@link AudioManager} instance associated with this menu.
	 */
	protected AudioManager getAudio()
	{
		return audio;
	}
	
	/**
	 * Returns the {@linkplain NinjaTalesMinesweeper game} associated with this menu.
	 * 
	 * @return	the {@linkplain NinjaTalesMinesweeper game} associated with this menu.
	 */
	protected NinjaTalesMinesweeper getGame()
	{
		return game;
	}
	
	/**
	 * Adds the {@code titleLayer} to the rootLayer.
	 */
	public void setTitleLayer()
	{
		graphics().rootLayer().add(titleLayer);
	}
	
	/**
	 * Returns the {@link ImageLayer} containing the title of this menu.
	 * 
	 * @return	the {@link ImageLayer} containing the title of this menu.
	 */
	public ImageLayer getTitleLayer()
	{
		return titleLayer;
	}
	
	/**
	 * Returns the {@link ImageLayer} containing the background of this menu.
	 * 
	 * @return	the {@link ImageLayer} containing the background of this menu.
	 */
	public ImageLayer getBgLayer()
	{
		return bgLayer;
	}
	
	/**
	 * Returns an {@link IPoint} containing the current coordinates of the mouse pointer.
	 * 
	 * @return 	the location the mouse is currently pointing to.
	 */
	public IPoint getMouseLocation()
	{
		return game.getMouseLocation();
	}
	
	/**
	 * Returns a {@link String} containing the path to the title asset.
	 * 
	 * @return	a {@link String} containing the path to the title asset.
	 */
	public abstract String getTitlePath();
	
	/**
	 * Returns an {@link IPoint} containing the location on which the title has to be set.
	 * 
	 * @return	an {@link IPoint} containing the location on which the title has to be set.
	 */
	public abstract IPoint getTitlePoint();
	
}
