package saga.progetto.metodologie.core.graphic.entities;


import java.util.LinkedList;
import java.util.List;

import saga.progetto.metodologie.core.graphic.GfxLevel;
import saga.progetto.metodologie.core.graphic.entities.staticEntity.Effect;

/**
 * 
 * The class {@code EffectManager} manages the {@link Effect} objects.
 *
 */
public class EffectManager
{
	private List<Effect> effects;
	private GfxLevel level;
	
	public EffectManager(GfxLevel level)
	{
		this.level = level;
		effects = new LinkedList<>();
	}
	
	/**
	 * Adds a new {@link Effect} to the manager.
	 * 
	 * @param	effect the {@link Effect} to be added.
	 */
	public void addEffect(Effect effect)
	{
		effect.setLevel(level);
		effect.setVisible(true);
		effect.update(0);
		effect.playAudio();
		effects.add(effect);
	}

	/**
	 * Clears all the {@link Effect}s in the manager.
	 */
	public void clear() 
	{
		for (Effect e : effects) 
			e.clear();
	}
	
	/**
	 * Removes a single {@link Effect} from the manager.
	 * 
	 * @param	e the {@link Effect} to remove.
	 */
	public void clear(Effect e) 
	{
		e.clear();
		effects.remove(e);
	}

	/**
	 * Called by {@link GfxLevel} on every update call.
	 * 
	 * @param	delta the time in milliseconds since the last call.
	 */
	public void update(int delta) 
	{
		for (Effect e : new LinkedList<>(effects))
		{
			e.update(delta);
			if (e.getSprite().isOver()) clear(e);
		}
	}
	
}
