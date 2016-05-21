package saga.progetto.metodologie.core.graphic.navigable;

import static playn.core.PlayN.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import playn.core.Keyboard.Event;
import playn.core.Key;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.util.Callback;
import saga.progetto.metodologie.core.audio.AudioManager;
import saga.progetto.metodologie.core.graphic.GfxLevel;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.entities.movingEntity.GfxPlayer;
import saga.progetto.metodologie.core.logic.GameData;
import saga.progetto.metodologie.core.logic.Level;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Player;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Player.Gender;
import saga.progetto.metodologie.core.logic.entities.staticEntity.Target;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.NormalCell;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.Wall;

/**
 * 
 * The class {@code GameLoop} manages the events of the game loop.
 *
 */
public class GameLoop implements Navigable
{
	private static final String FILE_EXT = ".txt";
	private static final String TOKEN = "TOKEN";
	private static final int COMPUTABLE_LEVELS = 10;
	
	private NinjaTalesMinesweeper game;
	private PauseScreen pauseScreen;
	private VictoryMenu victoryMenu;
	private DefeatMenu defeatMenu;
	private GfxLevel level;
	private int selectedLevel;
	private LevelDifficulty difficulty;
	private Gender gender;
	private boolean multiPlayer;
	private boolean levelLoaded;
	private AudioManager audio;
	private int elapsed;

	public GameLoop(NinjaTalesMinesweeper game, AudioManager audio)
	{
		this.game = game;
		this.audio = audio;
	}
	
	/**
	 * 
	 * The enumeration {@code DataIndex} assigns an index to the types of data gathered from the level assets.
	 *
	 */
	public enum DataIndex { PATH, WALLS, AVAILABLES; }
	
	/**
	 * 
	 * The enumeration {@code LevelDifficulty} defines the properties of the possible level difficulties.
	 *
	 */
	public enum LevelDifficulty 
	{ 
		EASY("levels/easy", 1, 50, 9, 2), MEDIUM("levels/medium", 7, 60, 6, 3), HARD("levels/hard", 13, 70, 3, 4); 
		
		private String path;
		private int index;
		private int mines;
		private int bonusItems;
		private int enemies;
		
		LevelDifficulty(String path, int index, int mines, int bonusItems, int enemies)
		{
			this.path = path;
			this.index = index;
			this.mines = mines;
			this.bonusItems = bonusItems;
			this.enemies = enemies;
		}
		
		/**
		 * Returns the path of the levels associated with this difficulty.
		 * 
		 * @return	the path of the levels associated with this difficulty.
		 */
		public String getPath()
		{
			return path;
		}
		
		/**
		 * Returns the index of this difficulty. Used to gather the difficulty of a level from its number.
		 * 
		 * @return	the index of the difficulty. 
		 */
		public int getIndex()
		{
			return index;
		}
		
		/**
		 * Returns the number of mines associated with this difficulty.
		 * 
		 * @return	the number of mines of this difficulty.
		 */
		public int getMines()
		{
			return mines;
		}
		
		/**
		 * Returns the number of bonus items associated with this difficulty.
		 * 
		 * @return	the number of bonus items associated with this difficulty.
		 */
		public int getBonusItems()
		{
			return bonusItems;
		}
		
		/**
		 * Returns the number of enemies associated with this difficulty.
		 * 
		 * @return	the number of enemies associated with this difficulty.
		 */
		public int getEnemies()
		{
			return enemies;
		}
	}
	
	/**
	 * Sets the menus to which this game state as a link.
	 * 
	 * @param	pauseScreen the pause screen.
	 * @param	victoryMenu the victory menu.
	 * @param	defeatMenu the defeat menu.
	 */
	public void setLinkedMenu(PauseScreen pauseScreen, VictoryMenu victoryMenu, DefeatMenu defeatMenu)
	{
		this.pauseScreen = pauseScreen;
		this.victoryMenu = victoryMenu;
		this.defeatMenu = defeatMenu;
	}
	
	/**
	 * Loads the informations of the level based on the {@code selectedLevel}, which can be set throw {@link #setLevel(int)}.
	 * When done, calls {@link #startLevel(String[])}.
	 */
	public void startLevel()
	{
		difficulty = getSelectedDifficulty();
		assets().getText(difficulty.getPath() + new Random().nextInt(COMPUTABLE_LEVELS) + FILE_EXT, new Callback<String>()
		{
			@Override
			public void onSuccess(String result) 
			{
				startLevel(result.split("\r\n" + TOKEN + "\r\n"));
			}

			@Override
			public void onFailure(Throwable cause) 
			{
				log().error(cause.getMessage());
			}
		});
	}
	
	/**
	 * Creates the level reading the informations in {@code levelData}.
	 * 
	 * @param levelData an array of {@link String}s containing all the informations needed for the creation of a {@link Level}.
	 */
	public void startLevel(String[] levelData)
	{
		elapsed = 0;
		int mines = difficulty.mines + selectedLevel * 2;
		
		List<NormalCell> path = new LinkedList<>();
		List<Wall> walls = new LinkedList<>();
		List<NormalCell> availables = new LinkedList<>();
		List<NormalCell> enemyLocations = new LinkedList<>();
		List<NormalCell> bonusLocations = new LinkedList<>();

		for (String s : levelData[DataIndex.PATH.ordinal()].split("\r\n"))
		{
			String[] cellData = s.split(" ");
			path.add(new NormalCell(Integer.parseInt(cellData[0]), Integer.parseInt(cellData[1])));
		}
		for (String s : levelData[DataIndex.WALLS.ordinal()].split("\r\n"))
		{
			String[] wallData = s.split(" ");
			walls.add(new Wall(Integer.parseInt(wallData[0]), Integer.parseInt(wallData[1])));
		}
		for (String s : levelData[DataIndex.AVAILABLES.ordinal()].split("\r\n"))
		{
			String[] cellData = s.split(" ");
			availables.add(new NormalCell(Integer.parseInt(cellData[0]), Integer.parseInt(cellData[1])));
		}
		for (NormalCell cell : Level.getRandomLocations(difficulty.getBonusItems()))
		{
			availables.remove(cell);
			path.add(cell);
			bonusLocations.add(cell);
		}
		for (NormalCell cell : Level.getRandomLocations(difficulty.getEnemies()))
		{
			availables.remove(cell);
			path.add(cell);
			enemyLocations.add(cell);
		}
		
		level = new GfxLevel(new Level().setBackground(mines, path, walls, availables));
		level.setMultiPlayer(multiPlayer);
		level.setTarget(new Target(Level.TARGET_X, Level.TARGET_Y));
		level.setBonusItems(bonusLocations);
		level.setPlayer(new Player(Level.PLAYER_START_X, Level.PLAYER_START_Y, multiPlayer? Gender.MALE : gender));
		if (multiPlayer) level.setPlayer2(new Player(Level.PLAYER2_START_X, Level.PLAYER2_START_Y, Gender.FEMALE));
		level.setEnemies(enemyLocations);
		level.display();
		levelLoaded = true;
	}
	
	@Override
	public void setVisible(boolean visibility) 
	{
		if (visibility) 
			startLevel();
		else 
		{
			level.clear();
			levelLoaded = false;
		}
	}

	@Override
	public Navigable onMouseDown(ButtonEvent event) 
	{
		if (event.button() ==  Mouse.BUTTON_LEFT)
		{
			level.setPlayerDestination(event);
			audio.playFoosteps();
		}
		else if (event.button() ==  Mouse.BUTTON_RIGHT)
			level.setFlag(event);
		return this;
	}

	/**
	 * Sets the level that needs to be loaded next.
	 * 
	 * @param	selectedLevel the next level to be loaded.
	 */
	public void setLevel(int selectedLevel) 
	{
		this.selectedLevel = selectedLevel;
	}
	
	/**
	 * Sets the gender of the {@link GfxPlayer}. Multiplayer doesn't allow gender choices.
	 * 
	 * @param	gender the gender to be set.
	 */
	public void setGender(Gender gender) 
	{
		this.gender = gender;
	}
	
	@Override
	public Navigable onKeyDown(Event event) 
	{
		if (event.key() == Key.ESCAPE)
		{
			audio.playSelectButton();
			pauseScreen.setVisible(true);
			return pauseScreen;
		}
		return this;
	}

	/**
	 * Sets whether one or two players are playing.
	 * 
	 * @param	multiPlayer true if two players are playing, false if only one player is playing.
	 */
	public void setMultiPlayer(boolean multiPlayer) 
	{
		this.multiPlayer = multiPlayer;
	}
	
	/**
	 * Restarts the current level.
	 */
	public void restartLevel()
	{
		setVisible(false);
		startLevel();
	}
	
	/**
	 * Loads the next level.
	 */
	public void loadNextLevel()
	{
		setVisible(false);
		selectedLevel++;
		startLevel();
	}
	
	/**
	 * Returns the time elapsed since the beginning of level creation.
	 * 
	 * @return	the time elapsed since the beginning of level creation.
	 */
	public String getElapsedTime()
	{		
		int minutes = elapsed / 60000;
		int seconds = elapsed / 1000 - minutes * 60;
		return String.valueOf((minutes < 10 ? "0" : "") + minutes + (seconds < 10 ? ":0" : ":") + seconds);
	}
	
	/**
	 * Returns a {@link String} containing the total number of flags set during the last completed or failed level.
	 * 
	 * @return	the total number of flags set.
	 */
	public String getTotalFlags()
	{
		return String.valueOf(level.getTotalFlags());
	}
	
	/**
	 * Returns a {@link String} containing the percentage of correct flags set during the last completed or failed level.
	 * 
	 * @return	the correct percentage of flags.
	 */
	public String getFlagPercentage()
	{
		return level.getFlagPercentage() == 100? "100" + "%" : level.getFlagPercentage() < 10? 
				String.format("%.2f", level.getFlagPercentage()) + "%" : String.format("%.1f", level.getFlagPercentage()) + "%";
	}
	
	/**
	 * Returns the score value of the last completed level.
	 * 
	 * @return	the score value of the last completed level.
	 */
	public int getScoreValue()
	{
		return Math.round(0.3f * 100 * level.getCorrectFlags() * level.getFlagPercentage() / 100.0f * (60000.0f / elapsed) + 0.7f * 500 * (60000.0f / elapsed));
	}
	
	/**
	 * Returns a {@link String} representing the score value of the last completed level.
	 * 
	 * @return	the score value of the last completed level.
	 */
	public String getScore()
	{
		int currentHighscore = Integer.parseInt(game.getData().getProperty(getSelectedLevel() + GameData.RECORD));
		if( currentHighscore < getScoreValue() )
			game.getData().setProperty(getSelectedLevel() + GameData.RECORD, String.valueOf(getScoreValue()));
		return String.valueOf(getScoreValue());
	}
	
	/**
	 * Returns the number of stars assigned on the last completed level.
	 * 
	 * @return the number of stars assigned on the last completed level.
	 */
	public int getStarsValue()
	{
		return getScoreValue() >= 2000? 3 : getScoreValue() >= 1000? 2 : 1;
	}
	
	/**
	 * Returns a {@link String} representing the number of stars assigned on the last completed level.
	 * 
	 * @return	the number of stars assigned on the last completed level.
	 */
	public String getStars()
	{
		return getScoreValue() >= 2000? "3" : getScoreValue() >= 1000? "2" : "1";
	}
	
	/**
	 * Calculates the selected difficulty.
	 * 
	 * @return the selected difficulty.
	 */
	private LevelDifficulty getSelectedDifficulty()
	{
		return selectedLevel >= LevelDifficulty.HARD.getIndex()? LevelDifficulty.HARD : 
			selectedLevel >= LevelDifficulty.MEDIUM.getIndex()? LevelDifficulty.MEDIUM : LevelDifficulty.EASY;
	}
	
	/**
	 * Returns a {@link String} representing the selected level.
	 * 
	 * @return the selected level.
	 */
	public String getSelectedLevel()
	{
		return getSelectedDifficulty().name() + "_" + ((selectedLevel - 1) % 6 + 1) + "_";
	}
	
	/**
	 * Returns a {@link String} representing the next level.
	 * 
	 * @return the next level.
	 */
	public String getNextLevel()
	{
		return getSelectedDifficulty().name() + "_" + ((selectedLevel) % 6 + 1) + "_";
	}
	
	@Override
	public void update(int delta)
	{
		elapsed += delta;
		if (levelLoaded)
		{
			if (level.levelCompleted())
			{
				audio.playGameTheme(false);
				audio.playVictoryTheme(true);
				game.getData().setProperty(getNextLevel() + GameData.ACCESS, "true");
				game.getData().setProperty(getSelectedLevel() + GameData.STARS, getStars());
				victoryMenu.setVisible(true);
				game.setGameState(victoryMenu);
			}
			else if (level.gameOver())
			{
				audio.playGameTheme(false);
				audio.playDefeatTheme(true);
				defeatMenu.setVisible(true);
				game.setGameState(defeatMenu);
			}
			else 
			{
				level.setMouseOverCell(game.getMouseLocation());
				if (multiPlayer) level.setPlayer2Destination(game.getKeyboard());
				level.update(delta);
			}
		}
	}
}
