package saga.progetto.metodologie.core.logic.entities.staticEntity.background;

/**
 * 
 * Thrown when a mine is associated with a {@link Wall}.
 *
 */
public class MineOnWallException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	
	public MineOnWallException(String error)
	{
		super(error);
	}
}
