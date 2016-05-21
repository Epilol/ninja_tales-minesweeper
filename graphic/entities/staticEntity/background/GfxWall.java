package saga.progetto.metodologie.core.graphic.entities.staticEntity.background;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.AssetWatcher;
import playn.core.Image;
import playn.core.ImageLayer;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.Cell;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.Wall;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.WallVisibilityException;

/**
 * 
 * The class {@code GfxWall} is the graphic implementation of {@link Wall}.
 *
 */
public class GfxWall extends GfxCell
{
	private static final String WALL_PATH = "images/cell/wallTile.png";
	private static Image wallImage;
	private static Tile wallTile;
	
	private ImageLayer wallLayer;

	public GfxWall(Cell wall) 
	{
		super(wall);
	}
	
	/**
	 * Adds the cell assets to the watcher. 
	 * 
	 * @param	watcher the watcher used to load the assets.
	 */
	public static void loadAssets(AssetWatcher watcher) 
	{
		wallImage = assets().getImage(WALL_PATH);
		wallTile = new Tile(assets().getImage(WALL_PATH), CELL_WIDTH, CELL_HEIGHT, OVERLAY);
		watcher.add(wallImage);
	}
	
	/**
	 * Initializes the graphics of the wall.
	 */
	public void gfxInit()
	{
		wallLayer = graphics().createImageLayer(getWallTile());
		wallLayer.setTranslation(getX() * CELL_WIDTH - OVERLAY, getY() * CELL_HEIGHT - OVERLAY);
	}
	
	/**
	 * Returns the {@code wallLayer} associated with the wall.
	 * 
	 * @return	the {@code wallLayer} associated with the wall.
	 */
	public ImageLayer getWallLayer()
	{
		return wallLayer;
	}
	
	/**
	 * Returns the correct wall subtile to be associated with the wall.
	 * 
	 * @return	the correct wall subtile to be associated with the wall.
	 */
	public Image getWallTile() 
	{
		return wallTile.getNextCell(getCell());
	}

	@Override
	public void setVisible(boolean visible) 
	{
		getCell().setVisible(visible);
	}
	
	@Override
	public void localReveal() 
	{
		throw new WallVisibilityException();
	}
	
	@Override
	public boolean setFlag()
	{
		return getCell().setFlag();
	}
	
	@Override
	public boolean hasGfxFlag()
	{
		return false;
	}
	
	@Override
	public void setMouseover(boolean mouseover) 
	{
		
	}
	
	@Override
	public void update(int delta)
	{
		
	}
}
