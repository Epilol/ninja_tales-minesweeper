package saga.progetto.metodologie.core.logic.entities.staticEntity.background;

/**
 * 
 * Thrown when trying to change the visibility of a {@link Wall}.
 *
 */
public class WallVisibilityException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;

	public WallVisibilityException() 
	{
		super("Cannot change walls' visibility.");
	}

}
