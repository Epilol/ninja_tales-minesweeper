package saga.progetto.metodologie.core.graphic;

import static playn.core.PlayN.graphics;

import java.awt.Color;

import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.Font.Style;
import playn.core.ImageLayer;
import playn.core.TextFormat;
import pythagoras.f.IPoint;

/**
 * 
 * The class {@code Text} is used to display text assets.
 *
 */
public class Text 
{
	private static final String DEFAULT_FONT = "Myriad Pro";
	private static final int DEFAULT_SIZE = 20;
	private static final int DEFAULT_COLOR = Color.WHITE.getRGB();
	private static final Style DEFAULT_STYLE = Font.Style.PLAIN;
	
	private Font font;
	private CanvasImage textImage;
	private ImageLayer textLayer;
	
	public Text()
	{
		this(DEFAULT_SIZE, DEFAULT_COLOR, DEFAULT_STYLE);
	}
	
	public Text(int size, int color, Style style)
	{
		font = graphics().createFont(DEFAULT_FONT, style, size);
		textImage = graphics().createImage(640, 350);
		textImage.canvas().setFillColor(color); 
		textLayer = graphics().createImageLayer(textImage);
	}
	
	/**
	 * Clears the text.
	 */
	public void clear()
	{
		textImage.canvas().clear();
	}
	
	/**
	 * Displays a new text in the {@link IPoint} {@code p} location.
	 * @param	text the {@link String} to be displayed.
	 * @param	p the destination {@link IPoint}.
	 */
	public void addText(String text, IPoint p)
	{
		textImage.canvas().fillText(graphics().layoutText(text, new TextFormat().withFont(font)), p.x(), p.y());
	}
	
	/**
	 * Sets whether the text is visible.
	 * 
	 * @param	visible true to display the text, false to hide it.
	 */
	public void setVisible(boolean visible)
	{
		if (visible)
			graphics().rootLayer().add(textLayer);
		else
			graphics().rootLayer().remove(textLayer);
	}
	
}
