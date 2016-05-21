package saga.progetto.metodologie.core.logic.entities.staticEntity;

import java.util.LinkedList;
import java.util.List;

import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import saga.progetto.metodologie.core.logic.entities.staticEntity.background.Cell;

/**
 * 
 * The class {@code RevealBonus} represents a reveal bonus collectible by the {@linkplain saga.progetto.metodologie.core.logic.entities.movingEntity.Player Player}.
 *
 */
public class RevealBonus extends Bonus
{
	private static final float DURATION = 0;
	private static final IDimension EFFECT_SIZE = new Dimension(6, 6);
	
	public RevealBonus(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	@Override
	public float getDuration()
	{
		return DURATION;
	}
	
	public static IDimension getEffectSize()
	{
		return EFFECT_SIZE;
	}
	
	public List<Cell> revealCells()
	{
		List<Cell> revealedCells = new LinkedList<>();
		
		for (int i = - Math.round(EFFECT_SIZE.height() / 2) + getY(); i <= Math.round(EFFECT_SIZE.height() / 2) + getY(); i++)
			for (int j = - Math.round(EFFECT_SIZE.width() / 2) + getX(); j <= Math.round(EFFECT_SIZE.width() / 2) + getX(); j++)
				if (!getLevel().getBackground()[i][j].hasFlag() && getLevel().bombCheck(j, i))
					revealedCells.add(getLevel().getBackground()[i][j]);
		
		return revealedCells;
	}
}
