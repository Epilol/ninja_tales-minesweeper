package saga.progetto.metodologie.core.graphic;

import static playn.core.PlayN.graphics;

import java.util.LinkedList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Key;
import playn.core.Layer;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.AffineTransform;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import pythagoras.f.Transforms;
import saga.progetto.metodologie.core.graphic.entities.EffectManager;
import saga.progetto.metodologie.core.graphic.entities.movingEntity.GfxEnemy;
import saga.progetto.metodologie.core.graphic.entities.movingEntity.GfxMovingEntity;
import saga.progetto.metodologie.core.graphic.entities.movingEntity.GfxPlayer;
import saga.progetto.metodologie.core.graphic.entities.movingEntity.GfxPlayer2;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.GfxBonus;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.GfxTarget;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.MineExplosion;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.background.GfxCell;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.background.GfxNormalCell;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.background.GfxWall;
import saga.progetto.metodologie.core.graphic.navigable.GameLoop;
import saga.progetto.metodologie.core.logic.Level;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Direction;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Enemy;
import saga.progetto.metodologie.core.logic.entities.movingEntity.Player;
import saga.progetto.metodologie.core.logic.entities.staticEntity.Bonus;
import saga.progetto.metodologie.core.logic.entities.staticEntity.Target;
import saga.progetto.metodologie.core.logic.entities.staticEntity.TemporaryEntity;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.Cell;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.NormalCell;

/**
 * 
 * The class {@code GfxLevel} is the graphic implementation of {@link Level}.
 *
 */
public class GfxLevel extends Level
{
	private static final int REVEAL_SIZE = 4;
	
	private Level level;
	private boolean multiPlayer;
	private AffineTransform transform;
	private GfxCell[][] gfxBackground;
	private GfxPlayer player;
	private GfxPlayer2 player2;
	private GfxTarget target;
	private GroupLayer bgGround;
	private GroupLayer bgNumbers;
	private GroupLayer bgCover;
	private GroupLayer bgLocalCover;
	private GroupLayer mouseOver;
	private GroupLayer bgFlags;
	private List<GfxEnemy> enemies;
	private List<GfxBonus> bonusItems;
	private EffectManager manager;
	private Point destination;
	private Point destination2;
	private GfxCell mouseoverCell;
	private int gfxOnlyFlags;
	
	public GfxLevel(Level level)
	{
		this.level = level;
		gfxBackground = new GfxCell[Level.HEIGHT][Level.WIDTH];
		isometryInit();
		
		bgGround = graphics().createGroupLayer();
		bgNumbers = graphics().createGroupLayer();
		bgCover = graphics().createGroupLayer();
		bgLocalCover = graphics().createGroupLayer();
		mouseOver = graphics().createGroupLayer();
		bgFlags = graphics().createGroupLayer();
		
		bgGround.setVisible(false);
		bgNumbers.setVisible(false);
		bgCover.setVisible(false);
		bgLocalCover.setVisible(false);
		mouseOver.setVisible(false);
		bgFlags.setVisible(false);
		
		graphics().rootLayer().add(bgGround);
		graphics().rootLayer().add(bgNumbers);
		graphics().rootLayer().add(bgCover);
		graphics().rootLayer().add(bgLocalCover);
		graphics().rootLayer().add(mouseOver);
		graphics().rootLayer().add(bgFlags);
		
		for (int i = 0; i < Level.HEIGHT; i++)
			for (int j = 0; j < Level.WIDTH; j++)
				gfxBackground[i][j] = level.getBackground()[i][j].isWall()? 
						wallInit(new GfxWall(level.getBackground()[i][j])) : 
						cellInit(new GfxNormalCell(level.getBackground()[i][j]));
				
		applyIsometry(bgGround);
		applyIsometry(bgNumbers);
		applyIsometry(bgCover);
		applyIsometry(bgLocalCover);
		applyIsometry(mouseOver);
		applyIsometry(bgFlags);
		
		setLayerOrigin(bgGround);
		setLayerOrigin(bgNumbers);
		setLayerOrigin(bgCover);
		setLayerOrigin(bgLocalCover);
		setLayerOrigin(mouseOver);
		setLayerOrigin(bgFlags);
		
		enemies = new LinkedList<>();
		bonusItems = new LinkedList<>();
		manager = new EffectManager(this);
	}
	
	/**
	 * Initializes the transformation matrix of the level.
	 */
	public void isometryInit()
	{
		transform = new AffineTransform();
		
		float angle = pythagoras.f.FloatMath.toRadians(30);
		
		float scaleY = 0.86f;
		AffineTransform isometricScale = new AffineTransform(1, 0, 0, scaleY, 0, 0);
		
		float shearX = pythagoras.f.FloatMath.tan(-angle);
		AffineTransform isometricShear = new AffineTransform(1, 0, shearX, 1, 0, 0);
		
		float sin = pythagoras.f.FloatMath.sin(angle);
		float cos = pythagoras.f.FloatMath.cos(angle);
		AffineTransform isometricRotation = new AffineTransform(cos, sin, -sin, cos, 0, 0);
		
		// Applies the shear, scale and rotation values needed to obtain an isometry effect.
		Transforms.multiply(isometricRotation, Transforms.multiply(isometricShear, isometricScale, transform), transform);
		
		transform.setTranslation(graphics().width() / 2, graphics().height() / 2);
	}
	
	/**
	 * Applies the transformation matrix {@code transform} to the input {@layer}.
	 * 
	 * @param	layer the layer to be transformed.
	 */
	private void applyIsometry(Layer layer)
	{
		layer.transform().setTransform(transform.m00, transform.m01, transform.m10, transform.m11, transform.tx, transform.ty);
	}
	
	/**
	 * Returns the logical counterpart of the level.
	 * 
	 * @return	the logical component.
	 */
	public Level getLevel()
	{
		return level;
	}
	
	@Override
	public Cell[][] getBackground()
	{
		return level.getBackground();
	}
	
	/**
	 * Returns the graphic background containing {@link GfxCell}s.
	 * 
	 * @return	the graphic background containing {@link GfxCell}s.
	 */
	public GfxCell[][] getGfxBackground()
	{
		return gfxBackground;
	}
	
	/**
	 * Applies the transformation matrix {@code transform} to the input {@link Point} {@code p}.
	 * 
	 * @param	p the {@link Point} to be transformed.
	 * @return	an {@link IPoint} containing {@code p} after the transformation has been applied.
	 */
	public IPoint applyIsometry(Point p)
	{
		return transform.transform(p.subtract(getGfxPlayerPosition(), p), p);
	}
	
	/**
	 * Initializes the graphics of the input {@code wall}.
	 * 
	 * @param	wall the wall to be initialized.
	 * @return	a reference to {@code wall} for call chaining.
	 */
	private GfxWall wallInit(GfxWall wall)
	{
		wall.gfxInit();
		bgGround.add(wall.getWallLayer());
		return wall;
	}
	
	/**
	 * Initializes the graphics of the input {@code cell}.
	 * 
	 * @param	cell the cell to be initialized.
	 * @return	a reference to {@code cell} for call chaining.
	 */
	private GfxNormalCell cellInit(GfxNormalCell cell)
	{
		cell.gfxInit();
		bgGround.add(cell.getGroundLayer());
		bgNumbers.add(cell.getNumberLayer());
		bgCover.add(cell.getCoverLayer());
		bgLocalCover.add(cell.getLocalCoverLayer());
		mouseOver.add(cell.getMouseoverLayer());
		bgFlags.add(cell.getFlagLayer());
		return cell;
	}
	
	/**
	 * Returns the {@link GfxPlayer} position checking whether it is null. If null, returns the player's starting position.
	 * 
	 * @return	the {@link GfxPlayer} position or the player's starting position if the player is null.
	 */
	private Point getGfxPlayerPosition()
	{
		return player != null? player.getGfxPosition() : new Point(Level.PLAYER_START_X * GfxCell.CELL_WIDTH + GfxCell.CELL_WIDTH / 2, 
				Level.PLAYER_START_Y * GfxCell.CELL_HEIGHT  + GfxCell.CELL_HEIGHT / 2);
	}
	
	/**
	 * Sets the origin of the input {@code layer}.
	 * 
	 * @param	layer the layer on which the origin has to be set.
	 */
	private void setLayerOrigin(Layer layer)
	{
		Point  p = getGfxPlayerPosition();
		layer.setOrigin(p.x, p.y);
	}
	
	/**
	 * Sets the level as being played by one or two players.
	 * 
	 * @param	multiPlayer true if two players, false otherwise.
	 */
	public void setMultiPlayer(boolean multiPlayer)
	{
		this.multiPlayer = multiPlayer;
	}
	
	@Override
	public void setPlayer(Player player)
	{
		level.setPlayer(player);
		this.player = new GfxPlayer(player);
		this.player.setLevel(this);
		destination = new Point(player.getX(), player.getY());
	}
	
	@Override
	public void setPlayer2(Player player2) 
	{
		level.setPlayer2(player2);
		this.player2 = new GfxPlayer2(player2);
		this.player2.setLevel(this);
		this.player2.update(0);
		destination2 = new Point(player2.getX(), player2.getY());
	}
	
	@Override
	public void setEnemies(List<NormalCell> enemyLocations)
	{
		level.setEnemies(enemyLocations);
		for (Enemy enemy : getEnemies())
		{
			GfxEnemy gfxEnemy = null;
			try
			{
				// Uses reflection to instantiate the correct instance of GfxEnemy.
				Class<?> enemyClass = Class.forName(enemy.getClass().getName());
				Class<?> gfxClass = Class.forName(GfxEnemy.class.getName().replace("Enemy", "") + enemyClass.getSimpleName());
				Class<? extends GfxEnemy> gfxEnemyClass = gfxClass.asSubclass(GfxEnemy.class);
				gfxEnemy = gfxEnemyClass.getConstructor(enemyClass).newInstance(enemy);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			gfxEnemy.setLevel(this);
			gfxEnemy.update(0);
			this.enemies.add(gfxEnemy);
		}
	}
	
	@Override
	public void setTarget(Target target)
	{
		level.setTarget(target);
		this.target = new GfxTarget(target);
		this.target.setLevel(this);
		this.target.update(0);
	}
	
	@Override
	public void setBonusItems(List<NormalCell> bonusLocations)
	{
		level.setBonusItems(bonusLocations);
		for (Bonus bonus : getBonusItems())
		{
			GfxBonus gfxBonus = null;
			try
			{
				// Uses reflection to instantiate the correct instance of GfxBonus.
				Class<?> bonusClass = Class.forName(bonus.getClass().getName());
				Class<?> gfxClass = Class.forName(GfxBonus.class.getName().replace("Bonus", "") + bonusClass.getSimpleName());
				Class<? extends GfxBonus> gfxBonusClass = gfxClass.asSubclass(GfxBonus.class);
				gfxBonus = gfxBonusClass.getConstructor(bonusClass, EffectManager.class).newInstance(bonus, manager);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			gfxBonus.setLevel(this);
			gfxBonus.update(0);
			this.bonusItems.add(gfxBonus);
		}
	}
	
	/**
	 * Returns a list containing all the {@link GfxBonus} instances of the level.
	 * 
	 * @return	a list containing all the {@link GfxBonus} instances of the level.
	 */
	public List<GfxBonus> getGfxBonusItems()
	{
		return bonusItems;
	}
	
	@Override
	public List<Bonus> getBonusItems()
	{
		return level.getBonusItems();
	}
	
	/**
	 * Returns a list containing all the {@link GfxEnemy} instances of the level.
	 * 
	 * @return	a list containing all the {@link GfxEnemy} instances of the level.
	 */
	public List<Enemy> getGfxEnemies()
	{
		return level.getEnemies();
	}
	
	@Override
	public List<Enemy> getEnemies()
	{
		return level.getEnemies();
	}
	
	@Override
	public boolean targetCheck()
	{
		return level.targetCheck();
	}
	
	@Override
	public boolean levelCompleted()
	{
		return level.levelCompleted();
	}
	
	/**
	 * Returns the {@link GfxPlayer} instance of the level.
	 * 
	 * @return	the {@link GfxPlayer} instance of the level.
	 */
	public GfxPlayer getGfxPlayer()
	{
		return player;
	}

	/**
	 * Returns the {@link GfxPlayer2} instance of the level.
	 * 
	 * @return	the {@link GfxPlayer2} instance of the level.
	 */
	public GfxPlayer2 getGfxPlayer2() 
	{
		return player2;
	}

	/**
	 * Inverts the transformation of the {@link Point} {@code p}.
	 * 
	 * @param	p the {@link Point} to be transformed.
	 * @return	an {@link IPoint} containing {@code p} after the transformation.
	 */
	private IPoint getLogicPosition(Point p)
	{
		transform.inverseTransform(p, p);
		return p.set(p.x / GfxCell.CELL_WIDTH + player.getX(), p.y / GfxCell.CELL_HEIGHT + player.getY());
	}
	
	
	@Override
	public boolean player1BonusCheck(Bonus bonus)
	{
		return level.player1BonusCheck(bonus);
	}
	
	@Override
	public boolean player2BonusCheck(Bonus bonus)
	{
		return level.player2BonusCheck(bonus);
	}
	
	/**
	 * Sets the {@link GfxPlayer}'s destination cell.
	 * 
	 * @param	event the event toggled by the mouse click.
	 */
	public void setPlayerDestination(ButtonEvent event) 
	{
		if (mouseoverCell != null)
			destination = new Point(mouseoverCell.getX(), mouseoverCell.getY());
	}
	
	/**
	 * Sets the {@link GfxPlayer2}'s destination cell.
	 * 
	 * @param	keyboard a list of the currently pressed {@link Key}s.
	 */
	public void setPlayer2Destination(List<Key> keyboard) 
	{
		Point p = player2.getLogicPosition();
		if (keyboard.contains(Key.W) && !keyboard.contains(Key.S))
			p.addLocal(0, -1);
		if (keyboard.contains(Key.A) && !keyboard.contains(Key.D))
			p.addLocal(-1, 0);
		if (keyboard.contains(Key.S) && !keyboard.contains(Key.W))
			p.addLocal(0, 1);
		if (keyboard.contains(Key.D) && !keyboard.contains(Key.A))
			p.addLocal(1, 0);
		if (!level.getBackground()[Math.round(p.y)][Math.round(p.x)].isWall())
			destination2 = p;
	}
	
	/**
	 * Sets the cell being pointed by the mouse.
	 * 
	 * @param	mouseLocation the location of the mouse.
	 */
	public void setMouseOverCell(IPoint mouseLocation)
	{
		if (mouseoverCell != null) mouseoverCell.setMouseover(false);
		Point p = getLogicPosition(new Point(mouseLocation)).add(player.getVisualDelta().x, player.getVisualDelta().y);
		if (!level.getBackground()[Math.round(p.y)][Math.round(p.x)].isWall())
			mouseoverCell = gfxBackground[Math.round(p.y)][Math.round(p.x)];
		if (mouseoverCell != null) mouseoverCell.setMouseover(true); 
	}
	
	@Override
	public int getCorrectFlags()
	{
		return level.getCorrectFlags();
	}
	
	@Override
	public int getWrongFlags()
	{
		return level.getWrongFlags();
	}
	
	/**
	 * Returns the total number of flags set.
	 * 
	 * @return	the total number of flags set.
	 */
	public int getTotalFlags()
	{
		return getCorrectFlags() + getWrongFlags() + gfxOnlyFlags;
	}
	
	/**
	 * Returns the percentage of correct flags.
	 * 
	 * @return	the percentage of correct flags.
	 */
	public float getFlagPercentage()
	{
		return getTotalFlags() == 0 ? 0 : ((float) getCorrectFlags() / getTotalFlags()) * 100;
	}
	
	@Override
	public boolean bombCheck(int x, int y)
	{
		return level.bombCheck(x, y);
	}
	
	@Override
	public boolean playerBombCheck(int x, int y)
	{
		for (int i = y - REVEAL_SIZE; i <= y + REVEAL_SIZE; i++) 
			for (int j = x - REVEAL_SIZE; j <= x + REVEAL_SIZE; j++)
				if (!level.getBackground()[i][j].isWall())
				{
					if (gfxBackground[i][j].hasGfxFlag() && !gfxBackground[i][j].hasFlag()) gfxOnlyFlags--;
					gfxBackground[i][j].localReveal();
				}
		
		return level.playerBombCheck(x, y);
	}
	
	/**
	 * Returns whether there is flag collision. Flag collision happens when trying to set a flag on the cell the player is stepping on.
	 * 
	 * @param	x the x coordinate of the flag being set.
	 * @param	y the y coordinate of the flag being set.
	 * @return	true if there is flag collision, false otherwise.
	 */
	private boolean flagCollision(int x, int y) 
	{
		if (multiPlayer && player2.isMoving() && player2.getX() + player2.getDirection().getX() == x 
				&& player2.getY() + player2.getDirection().getY() == y) return true;
		return player.isMoving() && player.getX() + player.getDirection().getX() == x && player.getY() + player.getDirection().getY() == y;
	}
	
	/**
	 * Sets a flag on the cell the mouse is pointing to.
	 * 
	 * @param	event the button event toggled by the mouse.
	 */
	public void setFlag(ButtonEvent event)
	{
		if (mouseoverCell != null)
			setFlag(mouseoverCell.getX(), mouseoverCell.getY());
	}
	
	@Override
	public void setFlag(int x, int y)
	{
		if (!flagCollision(x, y))
		{
			if (gfxBackground[y][x].hasGfxFlag() && !gfxBackground[y][x].hasFlag())
				gfxOnlyFlags--;
			if (gfxBackground[y][x].setFlag() && gfxBackground[y][x].hasGfxFlag() && !gfxBackground[y][x].hasFlag())
				gfxOnlyFlags++;
			level.setFlag(x, y);
		}
	}
	
	/**
	 * Updates the {@code layer} origin based on the player's speed and direction.
	 * 
	 * @param	layer the layer on which the origin has to be set.
	 */
	private void updateLayerOrigin(Layer layer)
	{
		layer.setOrigin(layer.originX() + player.getDirection().getX() * player.getSpeed(), 
				layer.originY() + player.getDirection().getY() * player.getSpeed());
	}
	
	/**
	 * Returns whether the input {@link Point}s are close according to the entity's view distance.
	 * 
	 * @param	p1 an {@link IPoint} representing two coordinates.
	 * @param	p2 an {@link IPoint} representing two coordinates.
	 * @return	true if the two points are close, false otherwise.
	 */
	private boolean isClose(IPoint p1, IPoint p2)
	{
		return Math.abs(p1.x() - p2.x()) <  GfxMovingEntity.VIEW_DISTANCE && Math.abs(p1.y() - p2.y()) <  GfxMovingEntity.VIEW_DISTANCE;
	}
	
	/**
	 * Returns a reference to the closest player. Only used in multiplayer.
	 * 
	 * @param	enemy the enemy checking for the closer player.
	 * @return	a reference to the closest player.
	 */
	private GfxPlayer getCloserPlayer(GfxEnemy enemy)
	{
		return Math.min(Math.abs(player.getX() - enemy.getX()), Math.abs(player.getY() - enemy.getY())) <= 
				Math.min(Math.abs(player2.getX() - enemy.getX()), Math.abs(player2.getY() - enemy.getY()))? player : player2;
	}
	
	/**
	 * Checks for close flags to the {@code player}.
	 * 
	 * @return true if there are close flags to the {@code player}.
	 */
	public boolean playerFlagCheck(GfxPlayer player)
	{
		for (int i = player.getY() - 1; i <= player.getY() + 1; i++)
			for (int j = player.getX() - 1; j <= player.getX() +1; j++)
				if (gfxBackground[i][j].hasGfxFlag()) return false;
		return true;
	}
	
	/**
	 * Called by {@link GameLoop} on every update call.
	 * 
	 * @param	delta the time in milliseconds since the last call.
	 */
	public void update(int delta)
	{
		if (player.getCurrentAnimation() != Animation.DEATH)
		{
			if (!player.isMoving()) player.setDirection(destination);
			// if the player is moving and there are no close flags, an adjustment may be needed.
			else if (playerFlagCheck(player)) player.setAdjustment(destination);
			if (level.getBackground()[Math.round(player.getY() + player.getDirection().getY())]
					[Math.round(player.getX() + player.getDirection().getX())].isAccessible())
			{
				player.update(delta);
				// updates the origin of all groupLayers.
				updateLayerOrigin(bgGround);
				updateLayerOrigin(bgNumbers);
				updateLayerOrigin(bgCover);
				updateLayerOrigin(bgLocalCover);
				updateLayerOrigin(mouseOver);
				updateLayerOrigin(bgFlags);
				
				if (playerBombCheck(player.getX(), player.getY()))
				{
					player.getSprite().resetDelta();
					player.setCurrentAnimation(Animation.DEATH);
					manager.addEffect(new MineExplosion(new TemporaryEntity(player.getX(), player.getY())));
				}
				
				for (GfxBonus bonus : bonusItems) 
				{
					bonus.update(delta);
					if (player1BonusCheck(bonus))
						bonus.staticCollision(player.getPlayer());
				}
				
				if (targetCheck()) target.open();
			}
			else
			{
				destination = player.getLogicPosition();
				player.setDirection(destination);
				player.update(delta);
			}
		}
		// even if the player is dead, an update is needed for the sprite animation.
		else player.update(delta);
		
		if (multiPlayer)
		{
			if (player2.getCurrentAnimation() != Animation.DEATH)
			{
				player2.setDirection(destination2);
				// player's two direction is only adjusted if it is different than the DEFAULT direction.
				if (player2.getDirection() != Direction.DEFAULT && player2.isMoving()) player2.setAdjustment(destination2);
				if (level.getBackground()[player2.getY() + player2.getDirection().getY()]
						[player2.getX() + player2.getDirection().getX()].isAccessible())
				{
					player2.update(delta);
					
					if (playerBombCheck(player2.getX(), player2.getY()))
					{
						player2.getSprite().resetDelta();
						player2.setCurrentAnimation(Animation.DEATH);
						manager.addEffect(new MineExplosion(new TemporaryEntity(player2.getX(), player2.getY())));
					}
					
					for (GfxBonus bonus : bonusItems) 
						if (player2BonusCheck(bonus))
							bonus.staticCollision(player2.getPlayer());
					
					if (targetCheck()) target.open();
				}
				else
				{
					destination2 = player2.getLogicPosition();
					player2.setDirection(destination2);
					player2.update(delta);
				}
			}
			else player2.update(delta);
		}
		// A new list is created to avoid concurrent modification exceptions.
		for (GfxEnemy enemy : new LinkedList<>(enemies))
		{
			if (enemy.getCurrentAnimation() != Animation.DEATH && enemy.getCurrentAnimation() != Animation.ATTACK)
			{
				if (!enemy.isMoving())
				{
					if (isClose(player.getLogicPosition(), enemy.getLogicPosition()) && !player.isHidden() || 
							(multiPlayer && isClose(player2.getLogicPosition(), enemy.getLogicPosition()) && !player2.isHidden()))
					{
						if (multiPlayer) enemy.setDirection(getCloserPlayer(enemy).getLogicPosition());
						else enemy.setDirection(player.getLogicPosition());
					}
					// enemies' direction is only adjusted if they are not chasing a player anymore.
					else enemy.setDirection(enemy.getLogicPosition().add(enemy.getVisualDelta().x, enemy.getVisualDelta().y));
				}
				// enemies may step on flags.
				if (!level.getBackground()[enemy.getY() + enemy.getDirection().getY()][enemy.getX() + enemy.getDirection().getX()].isWall())
				{
					enemy.update(delta);
					
					if (!enemy.canFly() && bombCheck(enemy.getX(), enemy.getY()))
					{
						gfxBackground[enemy.getY()][enemy.getX()].localReveal();
						enemy.getSprite().resetDelta();
						enemy.setCurrentAnimation(Animation.DEATH);
						manager.addEffect(new MineExplosion(new TemporaryEntity(enemy.getX(), enemy.getY())));
					}
				}
				else
				{
					enemy.setDirection(enemy.getLogicPosition());
					enemy.update(delta);
				}
				if (enemy.checkPlayerCollision() || (multiPlayer && enemy.checkPlayer2Collision()))
				{
					enemy.getSprite().resetDelta();
					enemy.setCurrentAnimation(Animation.ATTACK);
				}
			}
			else
			{
				if (enemy.getCurrentAnimation() == Animation.ATTACK)
				{
					if (enemy.hasAttacked())
					{
						if (enemy.checkPlayerCollision() && player.getCurrentAnimation() != Animation.DEATH)
						{
							player.getSprite().resetDelta();
							player.setCurrentAnimation(Animation.DEATH);
						}
						else if (multiPlayer && enemy.checkPlayer2Collision() && player2.getCurrentAnimation() != Animation.DEATH)
						{
							player2.getSprite().resetDelta();
							player2.setCurrentAnimation(Animation.DEATH);
						}
					}
					if (enemy.getSprite().isOver(enemy.getCurrentAnimation()))
					{
						// if the attack fails, the enemy either keeps following the closest player or stops if both players are too far.
						if (!enemy.checkPlayerCollision() && (!multiPlayer || !enemy.checkPlayer2Collision()))
						{
							if (!isClose(player.getLogicPosition(), enemy.getLogicPosition()) && (!multiPlayer || 
									!isClose(player2.getLogicPosition(), enemy.getLogicPosition())))
								enemy.setDirection(enemy.getLogicPosition());
							else enemy.setCurrentAnimation(Animation.RUN);
						}
					}
					
				}
				enemy.update(delta);
			}
			if (enemy.isDead())
			{
				enemies.remove(enemy);
				enemy.clear();
			}
		}
		
		if (player.isDead() || (multiPlayer && player2.isDead())) 
			setGameOver(true);
		
		manager.update(delta);
		target.update(delta);
		
		for (int i = 0; i < Level.HEIGHT; i++)
			for (int j = 0; j < Level.WIDTH; j++)
				if (!gfxBackground[i][j].isWall())
					gfxBackground[i][j].update(delta);
	}
	
	/**
	 * Clears all the graphics of the level.
	 */
	public void clear() 
	{
		bgGround.clear();
		bgNumbers.clear();
		bgCover.clear();
		bgLocalCover.clear();
		mouseOver.clear();
		bgFlags.clear();
		for (GfxEnemy enemy : enemies) enemy.clear();
		for (GfxBonus bonus : bonusItems) bonus.clear();
		player.clear();
		if (multiPlayer) player2.clear();
		target.clear();
		manager.clear();
	}
	
	/**
	 * Displays all the graphics of the level.
	 */
	public void display()
	{
		bgGround.setVisible(true);
		bgNumbers.setVisible(true);
		bgCover.setVisible(true);
		bgLocalCover.setVisible(true);
		mouseOver.setVisible(true);
		bgFlags.setVisible(true);
		for (GfxEnemy enemy : enemies) enemy.setVisible(true);
		for (GfxBonus bonus : bonusItems) bonus.setVisible(true);
		player.setVisible(true);
		if (multiPlayer) player2.setVisible(true);
		target.setVisible(true);
	}
}
