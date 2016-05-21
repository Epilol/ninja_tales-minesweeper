package saga.progetto.metodologie.core.graphic.entities.staticEntity.background;

import playn.core.Image;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.Cell;

/**
 * 
 * The class {@code Tile} manages background texture partition.
 *
 */
public class Tile 
{
	private Image texture;
	private float cellWidth;
	private float cellHeight;
	private float overlay;
	
	public Tile(Image texture, float cellWidth, float cellHeight, float overlay) 
	{
		this.texture = texture;
	    this.cellWidth = cellWidth;
	    this.cellHeight = cellHeight;
	    this.overlay = overlay;
	}
	
	/**
	 * Returns the tile's texture.
	 * 
	 * @return	the tile's texture.
	 */
	public Image getTexture()
	{
		return texture;
	}
	
	/**
	 * Returns correct subimage of the texture based on the input {@code cell}'s coordinates and size.
	 * 
	 * @param	cell a cell.
	 * @return	the correct subimage of the texture based on the input {@code cell}'s coordinates and size.
	 */
	public Image getNextCell(Cell cell)
	{
		return texture.subImage(getIndex(cell).x(), getIndex(cell).y(), cellWidth + overlay * 2, cellHeight + overlay * 2); 
		// only works because Lines == Columns (in the texture)
	}
	
	/**
	 * Returns correct index of the texture based on the input {@code cell}'s coordinates and size.
	 * 
	 * @param	cell a cell.
	 * @return	the correct index of the texture based on the input {@code cell}'s coordinates and size.
	 */
	public IPoint getIndex(Cell cell)
	{
		return new Point((cell.getX() % getFramesPerLine()) * (cellWidth + 25 * 2), (cell.getY() % getFramesPerLine()) * (cellWidth + 25 * 2)); 
	}
	
	/**
	 * Returns he number of subtiles per line.
	 * 
	 * @return	the number of subtiles per line.
	 */
	private float getFramesPerLine() 
	{
		return texture.width() / (cellWidth + 25 * 2);
	}
}

