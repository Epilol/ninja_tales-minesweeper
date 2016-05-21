package saga.progetto.metodologie.core.graphic;

import static playn.core.PlayN.*;

import java.util.LinkedList;
import java.util.List;

import playn.core.AssetWatcher;
import playn.core.Game;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Mouse;
import playn.core.Keyboard.Event;
import playn.core.Keyboard.TypedEvent;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.audio.AudioManager;
import saga.progetto.metodologie.core.graphic.entities.movingEntity.GfxGolem;
import saga.progetto.metodologie.core.graphic.entities.movingEntity.GfxMoth;
import saga.progetto.metodologie.core.graphic.entities.movingEntity.GfxPlayer;
import saga.progetto.metodologie.core.graphic.entities.movingEntity.GfxSlime;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.Effect;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.GfxRevealBonus;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.GfxSmokeBonus;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.GfxSpeedBonus;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.GfxTarget;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.MineExplosion;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.SmokeExplosion;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.background.GfxNormalCell;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.background.GfxWall;
import saga.progetto.metodologie.core.graphic.navigable.CharSelectionMenu;
import saga.progetto.metodologie.core.graphic.navigable.DefeatMenu;
import saga.progetto.metodologie.core.graphic.navigable.GuideMenu;
import saga.progetto.metodologie.core.graphic.navigable.DifficultyMenu;
import saga.progetto.metodologie.core.graphic.navigable.GameLoop;
import saga.progetto.metodologie.core.graphic.navigable.HighscoreMenu;
import saga.progetto.metodologie.core.graphic.navigable.MainMenu;
import saga.progetto.metodologie.core.graphic.navigable.LevelMenu;
import saga.progetto.metodologie.core.graphic.navigable.LoadingScreen;
import saga.progetto.metodologie.core.graphic.navigable.Menu;
import saga.progetto.metodologie.core.graphic.navigable.Navigable;
import saga.progetto.metodologie.core.graphic.navigable.OptionsMenu;
import saga.progetto.metodologie.core.graphic.navigable.PauseScreen;
import saga.progetto.metodologie.core.graphic.navigable.VictoryMenu;
import saga.progetto.metodologie.core.graphic.navigable.button.Button;
import saga.progetto.metodologie.core.graphic.navigable.button.Button.ButtonType;
import saga.progetto.metodologie.core.logic.GameData;

public class NinjaTalesMinesweeper extends Game.Default 
{
	public static final int UPDATE_RATE = 33;
	private Navigable gameState;
	private GameData data;
	private List<Key> keyboard;
	private IPoint mouseLocation;
	public boolean texturesLoaded;
	
	public NinjaTalesMinesweeper() 
	{
		super(UPDATE_RATE);
	}

	@Override
	public void init() 
	{
		mouseLocation = new Point();
		data = new GameData();
		Button.loadAssets();
		LoadingScreen.loadAssets();
		loadAssets();
		
		AudioManager audio = new AudioManager(this);
		Menu.loadAudio(audio);
		Button.loadAudio(audio);
		Effect.loadAudio(audio);
		
		// menu initialization
		PauseScreen pauseScreen = new PauseScreen(this);
		GameLoop gameLoop = new GameLoop(this, audio);
		MainMenu mainMenu = new MainMenu(this, gameLoop);
		CharSelectionMenu charSelectionMenu = new CharSelectionMenu(this, gameLoop);
		DifficultyMenu difficultyMenu = new DifficultyMenu(this);
		DifficultyMenu difficultyMenuMp = new DifficultyMenu(this);
		LevelMenu easyMenu = new LevelMenu(this, gameLoop, ButtonType.EASY);
		LevelMenu mediumMenu = new LevelMenu(this, gameLoop, ButtonType.MEDIUM);
		LevelMenu hardMenu = new LevelMenu(this, gameLoop, ButtonType.HARD);
		LevelMenu easyMenuMp = new LevelMenu(this, gameLoop, ButtonType.EASY);
		LevelMenu mediumMenuMp = new LevelMenu(this, gameLoop, ButtonType.MEDIUM);
		LevelMenu hardMenuMp = new LevelMenu(this, gameLoop, ButtonType.HARD);
		OptionsMenu optionsMenu = new OptionsMenu(this);
		HighscoreMenu highscoreMenu = new HighscoreMenu(this);
		GuideMenu guideMenu = new GuideMenu(this);
		LoadingScreen loadingScreen = new LoadingScreen(this, gameLoop, audio);
		VictoryMenu victoryMenu = new VictoryMenu(this, gameLoop);
		DefeatMenu defeatMenu = new DefeatMenu(this, gameLoop);
		
		gameLoop.setLinkedMenu(pauseScreen, victoryMenu, defeatMenu);
		
		// button initialization
		mainMenu.setButtons(charSelectionMenu, optionsMenu, highscoreMenu, guideMenu, difficultyMenuMp);
		charSelectionMenu.setButtons(difficultyMenu, mainMenu);
		difficultyMenu.setButtons(easyMenu, mediumMenu, hardMenu, charSelectionMenu);
		difficultyMenuMp.setButtons(easyMenuMp, mediumMenuMp, hardMenuMp, mainMenu);
		easyMenu.setButtons(difficultyMenu, loadingScreen);
		mediumMenu.setButtons(difficultyMenu, loadingScreen);
		hardMenu.setButtons(difficultyMenuMp, loadingScreen);
		easyMenuMp.setButtons(difficultyMenuMp, loadingScreen);
		mediumMenuMp.setButtons(difficultyMenuMp, loadingScreen);
		hardMenuMp.setButtons(difficultyMenu, loadingScreen);
		optionsMenu.setButtons(mainMenu);
		highscoreMenu.setButtons(mainMenu);
		guideMenu.setButtons(mainMenu);
		pauseScreen.setButtons(mainMenu, gameLoop);
		victoryMenu.setButtons(mainMenu);
		defeatMenu.setButtons(mainMenu);
		
		setGameState(mainMenu);
		keyboard = new LinkedList<>();
		mainMenu.setVisible(true);
		
		mouse().setListener(new Mouse.Listener()
		{
			@Override
			public void onMouseDown(ButtonEvent event) 
			{
				setGameState(gameState.onMouseDown(event));
			}
			
			@Override
			public void onMouseMove(MotionEvent event) 
			{
				mouseLocation = new Point(event.localX(), event.localY());
			}
			
			@Override
			public void onMouseUp(ButtonEvent event)
			{
				
			}
			
			@Override
			public void onMouseWheelScroll(WheelEvent event)
			{
				
			}
		});
		
		keyboard().setListener(new Keyboard.Listener() 
		{
			@Override
			public void onKeyDown(Event event) 
			{
				keyboard.add(event.key());
				setGameState(gameState.onKeyDown(event));
			}
			
			@Override
			public void onKeyUp(Event event) 
			{
				keyboard.remove(event.key());
			}
			
			@Override
			public void onKeyTyped(TypedEvent event) 
			{
				
			}
		});
		
	}
	
	/**
	 * Loads the game's textures asynchronously.
	 */
	public void loadAssets()
	{
		AssetWatcher watcher = new AssetWatcher(new AssetWatcher.Listener() {
			
			@Override
			public void error(Throwable e) 
			{
				log().error(e.getMessage());
			}
			
			@Override
			public void done() 
			{
				texturesLoaded = true;
			}
		});
		
		GfxNormalCell.loadAssets(watcher);
		GfxWall.loadAssets(watcher);
		GfxPlayer.loadAssets(watcher);
		GfxTarget.loadAssets(watcher);
		GfxGolem.loadAssets(watcher);
		GfxSlime.loadAssets(watcher);
		GfxMoth.loadAssets(watcher);
		GfxSpeedBonus.loadAssets(watcher);
		GfxSmokeBonus.loadAssets(watcher);
		GfxRevealBonus.loadAssets(watcher);
		MineExplosion.loadAssets(watcher);
		SmokeExplosion.loadAssets(watcher);
		watcher.start();
	}
	
	/**
	 * Returns a list containing all the currently pressed {@link Key}s.
	 * 
	 * @return	a list containing all the currently pressed {@link Key}s.
	 */
	public List<Key> getKeyboard()
	{
		return keyboard;
	}
	
	/**
	 * Sets a new {@code gameState}.
	 * 
	 * @param	gameState the new game state.
	 */
	public void setGameState(Navigable gameState)
	{
		this.gameState = gameState;
	}
	
	/**
	 * Returns whether the game textures are loaded.
	 * 
	 * @return	true if the textures are loaded, false otherwise.
	 */
	public boolean texturesLoaded()
	{
		return texturesLoaded;
	}
	
	/**
	 * Returns the current mouse location.
	 * 
	 * @return	 the current mouse location.
	 */
	public IPoint getMouseLocation()
	{
		return mouseLocation;
	}
	
	/**
	 * Returns the {@link GameData}s of the game.
	 * 
	 * @return the {@link GameData}s of the game.
	 */
	public GameData getData()
	{
		return data;
	}

	@Override
	public void update(int delta) 
	{
		gameState.update(delta);
	}
	
	@Override
	public void paint(float alpha) 
	{
		
	}
}
