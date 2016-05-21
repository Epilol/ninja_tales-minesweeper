package saga.progetto.metodologie.core.logic.entities.staticEntity;

/**
 * 
 * The class {@code Target} represents the end of the {@linkplain saga.progetto.metodologie.core.logic.Level Level} 
 * and the goal of the {@linkplain saga.progetto.metodologie.core.logic.entities.movingEntity.Player Player}.
 *
 */
public class Target extends StaticEntity
{
	public Target(int x, int y) 
	{
		setX(x);
		setY(y);
	}
}
