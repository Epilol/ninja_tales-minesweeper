package saga.progetto.metodologie.core.graphic.navigable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.ImageLayer;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;
import saga.progetto.metodologie.core.graphic.Text;

/**
 * 
 * The class {@code DefeatMenu} represents a menu appearing after losing.
 *
 */
public class DefeatMenu extends EndGameMenu 
{
	private static final String TITLE_PATH = "images/menu/defeatTitle.png";
	private static final String STATS_PATH = "images/menu/defeatStats.png";
	private static final IPoint TITLE_POINT = new Point(113, 20);
	private static final IPoint STATS_POINT = new Point(48, 175);
	
	private Text text;
	private ImageLayer statsLayer;
	
	public DefeatMenu(NinjaTalesMinesweeper game, GameLoop gameloop)
	{
		super(game, gameloop);
		text = new Text();
		statsLayer = graphics().createImageLayer(assets().getImageSync(STATS_PATH));
		statsLayer.setTranslation(STATS_POINT.x(), STATS_POINT.y());
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		
		if (visible)
		{
			text.clear();
			text.addText(getGameLoop().getElapsedTime(), TIME_POINT);
			text.addText(getGameLoop().getTotalFlags(), FLAG1_POINT);
			text.addText(getGameLoop().getFlagPercentage(), FLAG2_POINT);
			graphics().rootLayer().add(statsLayer);
		}
		else
			graphics().rootLayer().remove(statsLayer);
		
		text.setVisible(visible);
	}
	
	@Override
	public String getTitlePath() 
	{
		return TITLE_PATH;
	}

	@Override
	public IPoint getTitlePoint() 
	{
		return TITLE_POINT;
	}
}
