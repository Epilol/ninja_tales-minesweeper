package saga.progetto.metodologie.core.logic.entities.staticEntity;

import saga.progetto.metodologie.core.logic.entities.Entity;

/**
 * 
 * The class {@code StaticEntity} represents an entity that can not be moved. 
 *
 */
public abstract class StaticEntity extends Entity
{
	private boolean xSet;
	private boolean ySet;
	
	@Override
	public void setX(int x)
	{
		if (xSet) throw new StaticEntityException();
		xSet = true;
		super.setX(x);
	}
	
	@Override
	public void setY(int y)
	{
		if (ySet) throw new StaticEntityException();
		ySet = true;
		super.setY(y);
	}
}
