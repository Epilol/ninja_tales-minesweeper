package saga.progetto.metodologie.core.graphic.navigable;

import playn.core.Key;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;

/**
 * 
 * The interface {@code Navigable} represents generic game states.
 *
 */
public interface Navigable 
{
	/**
	 * Sets the game state as {@code visible}.
	 * 
	 * @param	visible true to display, false to hide.
	 */
	void setVisible(boolean visible);
	
	/**
	 * Called every time there's a new mouse click to handle.
	 * 
	 * @param	event the event representing the mouse click.
	 * @return	<b>this</b> if the game state hasn't changed, another instance of {@link Navigable} 
	 * 			containing the subsequent game state otherwise.
	 */
	Navigable onMouseDown(ButtonEvent event);
	
	/**
	 * Called every time there's a new {@link Key} is pressed.
	 * 
	 * @param	event the event representing the {@link Key} press.
	 * @return	<b>this</b> if the game state hasn't changed, another instance of {@link Navigable} 
	 * 			containing the subsequent game state otherwise.
	 */
	Navigable onKeyDown(Event event);
	
	/**
	 * Called by {@link NinjaTalesMinesweeper} on every update call.
	 * 
	 * @param	delta the time in milliseconds since the last call.
	 */
	void update(int delta);
}
