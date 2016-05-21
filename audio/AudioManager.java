package saga.progetto.metodologie.core.audio;

import static playn.core.PlayN.*;
import playn.core.Sound;
import saga.progetto.metodologie.core.graphic.NinjaTalesMinesweeper;

/**
 * 
 * The class {@code AudioManager} manages the audio of the game.
 *
 */
public class AudioManager 
{
	public static final String MENU_THEME_PATH = "sound/music/menuTheme";
	public static final String GAME_THEME_PATH = "sound/music/gameTheme";
	public static final String VICTORY_THEME_PATH = "sound/music/victoryTheme";
	public static final String DEFEAT_THEME_PATH = "sound/music/defeatTheme";
	public static final String SELECT_BUTTON_PATH = "sound/effects/buttonGameEffect";
	public static final String START_BUTTON_PATH = "sound/effects/startGameEffect";
	public static final String FOOTSTEPS_PATH = "sound/effects/footsteps";
	public static final String EXPLOSION_PATH = "sound/effects/explosion";
	public static final String SMOKE_PATH = "sound/effects/smoke";
	
	private Sound menuTheme;
	private Sound gameTheme;
	private Sound victoryTheme;
	private Sound defeatTheme;
	private Sound selectSound;
	private Sound startSound;
	private Sound footstepsSound;
	private Sound explosion;
	private Sound smoke;
	private NinjaTalesMinesweeper game;
	private float musicVolume;
	private float effectVolume;
	
	public AudioManager(NinjaTalesMinesweeper game)
	{
		this.game = game;
		menuTheme = assets().getMusic(MENU_THEME_PATH);
		gameTheme = assets().getMusic(GAME_THEME_PATH);
		victoryTheme = assets().getMusic(VICTORY_THEME_PATH);
		defeatTheme = assets().getMusic(DEFEAT_THEME_PATH);
		selectSound = assets().getSound(SELECT_BUTTON_PATH);
		startSound = assets().getSound(START_BUTTON_PATH);
		footstepsSound = assets().getSound(FOOTSTEPS_PATH);
		explosion = assets().getSound(EXPLOSION_PATH);
		smoke = assets().getSound(SMOKE_PATH);
		
		musicVolume = Float.parseFloat(game.getData().getProperty("MUSIC"));
		effectVolume = Float.parseFloat(game.getData().getProperty("EFFECTS"));
		
		menuTheme.setVolume(musicVolume);
		gameTheme.setVolume(musicVolume);
		victoryTheme.setVolume(musicVolume);
		defeatTheme.setVolume(musicVolume);
		selectSound.setVolume(effectVolume);
		startSound.setVolume(effectVolume);
		footstepsSound.setVolume(effectVolume);
		explosion.setVolume(effectVolume);
		smoke.setVolume(effectVolume);
		
		menuTheme.setLooping(true);
		gameTheme.setLooping(true);	
		
		menuTheme.prepare();
		gameTheme.prepare();
		defeatTheme.prepare();
		defeatTheme.prepare();
		selectSound.prepare();
		startSound.prepare();
		footstepsSound.prepare();
		explosion.prepare();
		smoke.prepare();
		
	}
	
	/**
	 * Returns the music volume.
	 * 
	 * @return	the music volume.
	 */
	public float getMusicVolume()
	{
		return musicVolume;
	}
	
	/**
	 * Returns the effect volume.
	 * 
	 * @return	the effect volume.
	 */
	public float getEffectVolume()
	{
		return effectVolume;
	}
	
	/**
	 * Plays or stops the menu theme.
	 * 
	 * @param	play true to play, false to stop.
	 */
	public void playMenuTheme(boolean play)
	{
		if (play && !menuTheme.isPlaying()) menuTheme.play();
		else if (!play) menuTheme.stop();
	}
	
	/**
	 * Plays or stops the game theme.
	 * 
	 * @param	play true to play, false to stop.
	 */
	public void playGameTheme(boolean play)
	{
		if (play && !gameTheme.isPlaying()) gameTheme.play();
		else if (!play) gameTheme.stop();
	}
	
	/**
	 * Plays or stops the victory theme.
	 * 
	 * @param	play true to play, false to stop.
	 */
	public void playVictoryTheme(boolean play)
	{
		if (play && !victoryTheme.isPlaying()) victoryTheme.play();
		else if (!play) victoryTheme.stop();
	}
	
	/**
	 * Plays or stops the defeat theme.
	 * 
	 * @param	play true to play, false to stop.
	 */
	public void playDefeatTheme(boolean play)
	{
		if (play && !defeatTheme.isPlaying()) defeatTheme.play();
		else if (!play) defeatTheme.stop();
	}
	
	/**
	 * Plays the select button sound.
	 */
	public void playSelectButton()
	{
		selectSound.stop();
		selectSound.play();
	}
	
	/**
	 * Plays the start button sound.
	 */
	public void playStartButton()
	{
		startSound.stop();
		startSound.play();
	}
	
	/**
	 * Plays the footsteps sound.
	 */
	public void playFoosteps()
	{
		footstepsSound.stop();
		footstepsSound.play();
	}
	
	/**
	 * Plays the explosion sound.
	 */
	public void playExplosion()
	{
		explosion.play();
	}
	
	/**
	 * Plays the smoke bomb sound.
	 */
	public void playSmoke()
	{
		smoke.play();
	}
	
	/**
	 * Increases the music volume and saves the new settings.
	 */
	public void increaseMusicVolume()
	{
		musicVolume += 0.1f;
		menuTheme.setVolume(musicVolume);
		gameTheme.setVolume(musicVolume);
		victoryTheme.setVolume(musicVolume);
		defeatTheme.setVolume(musicVolume);
		game.getData().setProperty("MUSIC", String.valueOf(musicVolume));
	}
	
	/**
	 * Decreases the music volume and saves the new settings.
	 */
	public void decreaseMusicVolume()
	{		
		musicVolume -= 0.1f;
		menuTheme.setVolume(musicVolume);
		gameTheme.setVolume(musicVolume);
		victoryTheme.setVolume(musicVolume);
		defeatTheme.setVolume(musicVolume);
		game.getData().setProperty("MUSIC", String.valueOf(musicVolume));
	}
	
	/**
	 * Increases the effect volume and saves the new settings.
	 */
	public void increaseEffectVolume()
	{
		effectVolume += 0.1f;
		selectSound.setVolume(effectVolume);
		startSound.setVolume(effectVolume);
		footstepsSound.setVolume(effectVolume);
		explosion.setVolume(effectVolume);
		smoke.setVolume(effectVolume);
		game.getData().setProperty("EFFECTS", String.valueOf(effectVolume));
	}
	
	/**
	 * Decreases the effect volume and saves the new settings.
	 */
	public void decreaseEffectVolume()
	{
		effectVolume -= 0.1f;
		selectSound.setVolume(effectVolume);
		startSound.setVolume(effectVolume);
		footstepsSound.setVolume(effectVolume);
		explosion.setVolume(effectVolume);
		smoke.setVolume(effectVolume);
		game.getData().setProperty("EFFECTS", String.valueOf(effectVolume));
	}
}
