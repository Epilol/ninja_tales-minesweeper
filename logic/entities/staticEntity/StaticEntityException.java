package saga.progetto.metodologie.core.logic.entities.staticEntity;


/**
 * 
 * Thrown when trying to change the starting position of a {@link StaticEntity}.
 *
 */
public class StaticEntityException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public StaticEntityException()
	{
		super("Cannot modify the position of static entities.");
	}
}
