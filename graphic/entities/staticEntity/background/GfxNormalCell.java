package saga.progetto.metodologie.core.graphic.entities.staticEntity.background;

import static playn.core.PlayN.*;

import playn.core.AssetWatcher;
import playn.core.Image;
import playn.core.ImageLayer;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.Cell;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.NormalCell;

/**
 * 
 * The class {@code GfxNormalCell} is the graphic implementation of {@link NormalCell}.
 *
 */
public class GfxNormalCell extends GfxCell 
{
	private static final String CELL_PATH = "images/cell/newCell.png";
	private static final String COVERED_PATH = "images/cell/coveredTile.png";
	private static final String GRASS_PATH = "images/cell/uncoveredTile.png";
	private static final String FLAG_PATH = "images/cell/flag.png";
	private static final String MOUSEOVER_PATH = "images/cell/overlayCell.png";
	private static final float MOUSEOVER_DURATION = 500;
	private static final int MINE_INDEX = 9;
	private static Tile coverTile;
	private static Tile grassTile;
	private static Image numberImage;
	private static Image flagImage;
	public static Image mouseoverImage; //TODO private
	
	private ImageLayer groundLayer;
	private ImageLayer numberLayer;
	private ImageLayer coverLayer;
	private ImageLayer flagLayer;
	private ImageLayer localCoverLayer;
	private ImageLayer mouseoverLayer;
	private boolean mouseover;
	
	public GfxNormalCell(Cell cell)
	{
		super(cell);
	}
	
	/**
	 * Adds all the class assets to the watcher. 
	 * 
	 * @param	watcher the watcher used to load the assets.
	 */
	public static void loadAssets(AssetWatcher watcher) 
	{
		coverTile = new Tile(assets().getImage(COVERED_PATH), CELL_WIDTH, CELL_HEIGHT, OVERLAY);
		grassTile = new Tile(assets().getImage(GRASS_PATH), CELL_WIDTH, CELL_HEIGHT, OVERLAY);
		numberImage = assets().getImage(CELL_PATH);
		flagImage = assets().getImage(FLAG_PATH);
		mouseoverImage = assets().getImage(MOUSEOVER_PATH);
		watcher.add(numberImage);
		watcher.add(flagImage);
		watcher.add(mouseoverImage);
	}
	
	/**
	 * Initializes the graphics of the cell.
	 */
	public void gfxInit()
	{
		groundLayer = graphics().createImageLayer(getGrassTileImage());
		groundLayer.setTranslation(getX() * CELL_WIDTH - OVERLAY, getY() * GfxCell.CELL_HEIGHT - OVERLAY);
		numberLayer = graphics().createImageLayer(getGfx());
		numberLayer.setTranslation(getX() * CELL_WIDTH, getY() * GfxCell.CELL_HEIGHT);
		coverLayer = graphics().createImageLayer(getCoveredTileImage());
		coverLayer.setTranslation(getX() * CELL_WIDTH - OVERLAY, getY() * GfxCell.CELL_HEIGHT - OVERLAY);
		localCoverLayer = graphics().createImageLayer(getCoveredTileImage());
		localCoverLayer.setTranslation(getX() * CELL_WIDTH - OVERLAY, getY() * GfxCell.CELL_HEIGHT - OVERLAY);
		flagLayer = graphics().createImageLayer(flagImage);
		flagLayer.setTranslation(getX() * CELL_WIDTH, getY() * GfxCell.CELL_HEIGHT);
		flagLayer.setVisible(false);
		mouseoverLayer = graphics().createImageLayer(mouseoverImage);
		mouseoverLayer.setTranslation(getX() * CELL_WIDTH, getY() * GfxCell.CELL_HEIGHT);
		mouseoverLayer.setVisible(false);
	}
	
	/**
	 * Returns the {@code groundLayer} associated with the cell.
	 * 
	 * @return	the {@code groundLayer} associated with the cell.
	 */
	public ImageLayer getGroundLayer()
	{
		return groundLayer;
	}
	
	/**
	 * Returns the {@code numberLayer} associated with the cell.
	 * 
	 * @return	the {@code numberLayer} associated with the cell.
	 */
	public ImageLayer getNumberLayer()
	{
		return numberLayer;
	}
	
	/**
	 * Returns the {@code coverLayer} associated with the cell.
	 * 
	 * @return	the {@code coverLayer} associated with the cell.
	 */
	public ImageLayer getCoverLayer()
	{
		return coverLayer;
	}
	
	/**
	 * Returns the {@code localCoverLayer} associated with the cell.
	 * 
	 * @return	the {@code localCoverLayer} associated with the cell.
	 */
	public ImageLayer getLocalCoverLayer()
	{
		return localCoverLayer;
	}
	
	/**
	 * Returns the {@code flagLayer} associated with the cell.
	 * 
	 * @return	the {@code flagLayer} associated with the cell.
	 */
	public ImageLayer getFlagLayer()
	{
		return flagLayer;
	}
	
	/**
	 * Returns the {@code mouseoverLayer} associated with the cell.
	 * 
	 * @return	the {@code mouseoverLayer} associated with the cell.
	 */
	public ImageLayer getMouseoverLayer()
	{
		return mouseoverLayer;
	}
	
	/**
	 * Returns the correct cover subtile to be associated with the cell.
	 * 
	 * @return	the correct cover subtile to be associated with the cell.
	 */
	public Image getCoveredTileImage()
	{
		return coverTile.getNextCell(getCell());
	}
	
	/**
	 * Returns the correct grass subtile to be associated with the cell.
	 * 
	 * @return	the correct grass subtile to be associated with the cell.
	 */
	public Image getGrassTileImage()
	{
		return grassTile.getNextCell(getCell());
	}
	
	/** 
	 * Returns the image of the cell with the appropriate number of nearby mines if the cell is visible, an image of a covered cell otherwise.
	 *
	 * @return the current graphic representation of the cell.
	 */
	private Image getGfx()
	{
		return hasMine() ? numberImage.subImage(CELL_WIDTH * MINE_INDEX, 0, CELL_WIDTH, CELL_HEIGHT) : 
			numberImage.subImage(CELL_WIDTH * getNearbyMines(), 0, CELL_WIDTH, CELL_HEIGHT);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		if (isVisible())
		{
			coverLayer.destroy();
			if (localCoverLayer.destroyed())
			{
				// if (flagLayer.visible()) gfxOnlyFlags--; potential bug. Needs further testing but proved wrong so far.
				flagLayer.setVisible(false); // needed for the gfxOnlyFlag check
				flagLayer.destroy();
			}
		}
	}
	
	@Override
	public void localReveal()
	{
		localCoverLayer.destroy();
		if (isVisible())
		{
			flagLayer.setVisible(false); // needed for the gfxOnlyFlag check
			flagLayer.destroy();
		}
	}

	@Override
	public boolean setFlag()
	{
		super.setFlag();
		if (!localCoverLayer.destroyed())
		{
			flagLayer.setVisible(!flagLayer.visible());
			return true;
		}
		else flagLayer.setVisible(hasFlag());
		return hasFlag();
	}

	@Override
	public boolean hasGfxFlag() 
	{
		return flagLayer.visible();
	}
	
	@Override
	public void setMouseover(boolean mouseover)
	{
		if (mouseover && !mouseoverLayer.visible())
		{
			mouseoverLayer.setVisible(true);
			this.mouseover = true;
		}
		else this.mouseover = mouseover;
	}
	
	/**
	 * Manages the opacity of the {@code mouseoverLayer}.
	 * 
	 * @param	delta the time elapsed since last call.
	 */
	public void setOpacity(int delta)
	{
		if (mouseoverLayer.visible())
			if (!mouseover)
			{
				mouseoverLayer.setAlpha(mouseoverLayer.alpha() - delta / MOUSEOVER_DURATION);
				if (mouseoverLayer.alpha() <= 0)
				{
					mouseoverLayer.setAlpha(1.0f);
					mouseoverLayer.setVisible(false);
				}
			}
			else mouseoverLayer.setAlpha(1.0f);
	}
	
	@Override
	public void update(int delta)
	{
		setVisible(true);
		setOpacity(delta);
	}

}
