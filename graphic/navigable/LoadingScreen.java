package saga.progetto.metodologie.core.graphic.navigable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImmediateLayer;
import playn.core.Surface;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.audio.AudioManager;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;

/**
 * 
 * The class {@code LoadingScreen} represents a menu that appears when the game is loading.
 *
 */
public class LoadingScreen implements Navigable
{
	private static final String PATH = "images/menu/loading.png";
	private static final IPoint P1 = new Point(320 - 75, 240 - 100);
	private static final IDimension SIZE = new Dimension(150, 200);
	private static final int frames = 6;
	private static final int frameDuration = 90;
	private static Image loadingImage;
	
	private NinjaTalesMinesweeper game;
	private ImmediateLayer loadingLayer;
	private GameLoop gameLoop;
	private AudioManager audio;
	private int elapsed;
	
	public LoadingScreen(NinjaTalesMinesweeper game, GameLoop gameLoop, AudioManager audio)
	{
		this.game = game;
		this.gameLoop = gameLoop;
		this.audio = audio;
		elapsed = 0;
		
		loadingLayer = graphics().createImmediateLayer(new ImmediateLayer.Renderer() 
		{
			
			@Override
			public void render(Surface surface) 
			{
				surface.drawImage(loadingImage, P1.x(), P1.y(), SIZE.width(), SIZE.height(), SIZE.width() * ((elapsed / frameDuration) % frames), 0, SIZE.width(), SIZE.height());
			}
		});
		
		loadingLayer.setVisible(false);
		graphics().rootLayer().add(loadingLayer);
	}
	
	/**
	 * Synchronously loads the assets of this class.
	 */
	public static void loadAssets()
	{
		loadingImage = assets().getImageSync(PATH);
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event) 
	{
		return this;
	}

	@Override
	public void setVisible(boolean visibility) 
	{
		loadingLayer.setVisible(visibility);
	}

	@Override
	public Navigable onKeyDown(Event event) 
	{
		return this;
	}
	
	@Override
	public void update(int delta)
	{
		elapsed += delta;
		if (game.texturesLoaded())
		{
			audio.playGameTheme(true);
			audio.playMenuTheme(false);
			game.setGameState(gameLoop);
			setVisible(false);
			gameLoop.setVisible(true);
		}
	}
}
