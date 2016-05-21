package saga.progetto.metodologie.core.logic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import saga.progetto.metodologie.core.logic.entities.movingEntity.*;
import saga.progetto.metodologie.core.logic.entities.staticEntity.*;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.*;

/**
 * 
 * The class {@code Level} handles the logic events of the game.
 *
 */
public class Level
{
	public static final int HEIGHT = 81;
	public static final int WIDTH = 37;
	public static final int PLAYER_START_X = 18;
	public static final int PLAYER_START_Y = 71;
	public static final int PLAYER2_START_X = 19;
	public static final int PLAYER2_START_Y = 71;
	public static final int TARGET_X = 18;
	public static final int TARGET_Y = 9;
	
	private Cell[][] background = new Cell[HEIGHT][WIDTH];
	private Player player;
	private Player player2;
	private List<Enemy> enemies = new LinkedList<>();
	private List<Bonus> bonusItems = new LinkedList<>();
	public Target target;
	private boolean gameOver;
	private boolean levelCompleted;
	private int correctFlags;
	private int wrongFlags;
	
	/**
	 * 
	 * The enumeration {@code Zone} is used to divide the {@code background} in zones that will be 
	 * used for the randomization of bonus items and enemies.
	 *
	 */
	private enum Zone
	{
		ZONE1(12, 13), ZONE2(13, 18), ZONE3(14, 23), ZONE4(13, 28), ZONE5(12, 33),
		ZONE6(11, 38), ZONE7(10, 43), ZONE8(9, 48), ZONE9(8, 53), ZONE10(9, 58);
		
		private int x;
		private int y;
		
		Zone(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	/**
	 * Returns the background of the level.
	 * 
	 * @return	the background.
	 */
	public Cell[][] getBackground() 
	{
		return background;
	}
	
	/**
	 * Creates random locations using the {@link Zone} enumeration to better distribute the locations.
	 * 
	 * @param	n the number of locations needed.
	 * @return	a list of locations defined by instances of {@link Cell}.
	 */
	public static List<NormalCell> getRandomLocations(int n)
	{
		Random r = new Random();
		List<NormalCell> cells = new LinkedList<>();
		List<Zone> zones = new LinkedList<>(Arrays.asList(Zone.values()));
		
		for (int i = 0; i < n; i++)
		{
			Zone zone = zones.remove(r.nextInt(zones.size()));
			cells.add(new NormalCell(r.nextInt(15) + zone.x, r.nextInt(5) + zone.y));
		}
		return cells;
	}
	
	/**
	 * Returns a list containing the bonus items of the level.
	 * 
	 * @return	the bonus items of the level.
	 */
	public List<Bonus> getBonusItems()
	{
		return bonusItems;
	}
	
	/**
	 * Returns a list containing the enemies of the level.
	 * 
	 * @return	the enemies of the level.
	 */
	public List<Enemy> getEnemies()
	{
		return enemies;
	}
	
	/**
	 * Sets the level's first {@link Player}.
	 * 
	 * @param player the {@link Player}.
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	/**
	 * Sets the level's second {@link Player}.
	 * 
	 * @param player the {@link Player}.
	 */
	public void setPlayer2(Player player2)
	{
		this.player2 = player2;
	}
	
	/**
	 * Sets the enemies of the level.
	 * 
	 * @param	enemies a list of locations defined by instances of {@link Cell}.
	 */
	public void setEnemies(List<NormalCell> enemyLocations)
	{
		Random r = new Random();
		for (NormalCell cell : enemyLocations)
		{
			Enemy enemy = null;
			switch(r.nextInt(3))
			{
				case 0: enemy = new Golem(cell.getX(), cell.getY()); break;
				case 1: enemy = new Moth(cell.getX(), cell.getY()); break;
				case 2: enemy = new Slime(cell.getX(), cell.getY()); 
			}
			enemies.add(enemy);
		}
	}
	
	/**
	 * Sets the bonus items of the level.
	 * 
	 * @param	bonus a list of locations defined by instances of {@link Cell}.
	 */
	public void setBonusItems(List<NormalCell> bonusLocations)
	{
		Random r = new Random();
		for (NormalCell cell : bonusLocations)
		{
			Bonus bonus = null;
			switch(r.nextInt(3))
			{
				case 0: bonus = new RevealBonus(cell.getX(), cell.getY()); break;
				case 1: bonus = new SmokeBonus(cell.getX(), cell.getY()); break;
				case 2: bonus = new SpeedBonus(cell.getX(), cell.getY()); 
			}
			bonusItems.add(bonus);
		}
	}
	
	/**
	 * Sets the {@link Target} of the level.
	 * 
	 * @param target the player's goal.
	 */
	public void setTarget(Target target)
	{
		this.target = target;
	}
	
	/**
	 * Checks whether the player has set a flag on a correct position.
	 * 
	 * @param	x the x coordinate.
	 * @param	y the y coordinate.
	 */
	public void setFlag(int x, int y)
	{
		if (!background[y][x].isVisible())
		{
			if (background[y][x].hasFlag())
			{
				if (background[y][x].hasMine()) correctFlags++;
				else wrongFlags++;
			}
			else
			{
				if (background[y][x].hasMine()) correctFlags--;
				else wrongFlags--;
			}
		}
	}
	
	/**
	 * Returns whether the level has been completed.
	 * 
	 * @return	true if the level has been completed, false otherwise.
	 */
	public boolean levelCompleted()
	{
		return levelCompleted;
	}

	/**
	 * Returns whether the level has been failed.
	 * 
	 * @return	true if the level has been failed, false otherwise.
	 */
	public boolean gameOver() 
	{
		return gameOver;
	}

	/**
	 * Sets whether the level has been failed.
	 * 
	 * @param gameOver true if the level has been failed, false otherwise.
	 */
	public void setGameOver(boolean gameOver) 
	{
		this.gameOver = gameOver;
	}
	
	/**
	 * Initializes the background. 
	 * 
	 * @param	mines the number of mines to be set.
	 * @param	path a list of cells that can not contain mines.
	 * @param	walls a list of walls.
	 * @param	availables a list of cells that may contain mines.
	 * @return	a reference to this level, for call chaining.
	 */
	public Level setBackground(int mines, List<NormalCell> path, List<Wall> walls, List<NormalCell> availables)
	{
		for (NormalCell cell : path)
			background[cell.getY()][cell.getX()] = cell;
		for (Wall wall : walls)
			background[wall.getY()][wall.getX()] = wall;
		for (NormalCell cell : availables)
			background[cell.getY()][cell.getX()] = cell;
		
		setMines(mines, new LinkedList<>(availables));
		return this;
	}
	
	/**
	 * Initializes the mines of the background.
	 * 
	 * @param	mines the number of mines to be set.
	 * @param	availables a list of cells that may contain mines.
	 */
	private void setMines(int mines, List<NormalCell> availables)
	{
		if (mines > availables.size()) mines = availables.size();
		
		Random r = new Random();
		while (mines-- > 0)
		{
			Cell cell = availables.get(r.nextInt(availables.size()));
			background[cell.getY()][cell.getX()].setMine(true);
			setMines(cell.getX(), cell.getY());
			availables.remove(cell);
		}
	}
	
	/**
	 * Updates the number of mines in nearby cells.
	 * 
	 * @param	x the x coordinate of the cell.
	 * @param	y the y coordinate of the cell.
	 */
	private void setMines(int x, int y)
	{
		for (int i = y - 1; i <= y + 1; i++) 
			for (int j = x - 1; j <= x + 1; j++) 
				if (background[i][j].isAccessible() && (i != y || j != x))
					background[i][j].increaseMineCount();
	}
	
	/**
	 * Calls the recursive method {@link #reveal(Cell)}. If the cell in the x, y position is a bomb, the method 
	 * {@link #setGameOver(boolean)} is called instead. 
	 * 
	 * @return true if the cell in the x, y position is a bomb, false otherwise.
	 */
	public boolean playerBombCheck(int x, int y)
	{
		if (!background[y][x].isVisible())
			reveal(background[y][x]);
		return background[y][x].hasMine();
	}
	
	/**
	 * Unveils the cell in the x, y position. If the cell has no nearby mines, the method is called recursively on adjacent cells.
	 * 
	 * @param	x the x coordinate of the cell.
	 * @param	y the y coordinate of the cell.
	 */
	private void reveal(Cell cell)
	{
		if (cell.isWall() || cell.isVisible()) return;
		cell.setVisible(true);
		if (cell.getNearbyMines() > 0 || cell.hasMine()) return;
		for (int i = cell.getY() - 1; i <= cell.getY() + 1; i++) 
			for (int j = cell.getX() - 1; j <= cell.getX() + 1; j++) 
				if (i != cell.getY() || j != cell.getX())
					reveal(background[i][j]);
	}
	
	
	/**
	 * Checks the cell in the given coordinates for bombs.
	 * 
	 * @param	x the x coordinate.
	 * @param	y the y coordinate.
	 * @return	true if the cell contains a bomb, false otherwise.
	 */
	public boolean bombCheck(int x, int y)
	{
		if (background[y][x].hasMine())
		{
			background[y][x].setVisible(true);
			background[y][x].setMine(false);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if the {@link Player} has reached the {@link Target}.
	 * 
	 * @return	true if the {@link Player} has reached the {@link Target}.
	 */
	public boolean targetCheck()
	{
		levelCompleted = target.getX() == player.getX() && target.getY() == player.getY() - 1;
		return levelCompleted;
	}
	
	/**
	 * Checks if the {@link Player} 1 is in the same cell as {@code bonus}.
	 * 
	 * @param	bonus the bonus to check.
	 * @return	true if the {@link Player} 1 and {@code bonus} are in the same cell.
	 */
	public boolean player1BonusCheck(Bonus bonus)
	{
		return bonus.getY() == player.getY() && bonus.getX() == player.getX();
	}

	/**
	 * Checks if the {@link Player} 2 is in the same cell as {@code bonus}.
	 * 
	 * @param	bonus the bonus to check.
	 * @return	true if the {@link Player} 2 and {@code bonus} are in the same cell.
	 */
	public boolean player2BonusCheck(Bonus bonus)
	{
		return bonus.getY() == player2.getY() && bonus.getX() == player2.getX();
	}
	
	/**
	 * Returns the number of correct flags set by the player.
	 * 
	 * @return	the number of correct flags set by the player.
	 */
	public int getCorrectFlags()
	{
		return correctFlags;
	}
	
	/**
	 * Returns the number of wrong flags set by the player.
	 * 
	 * @return	the number of wrong flags set by the player.
	 */
	public int getWrongFlags()
	{
		return wrongFlags;
	}
}
