package saga.progetto.metodologie.core.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * The class {@code GameData} is used to save the settings and data of the game.
 *
 */
public class GameData 
{
	public static final String RECORD = "RECORD";
	public static final String STARS = "STARS";	
	public static final String ACCESS = "ACCESS";
	private static final String PATH = "./gameData.properties";
	private static final String CIPHER_ALGO = "DESede";
	private static final String KEY = "348592399877281075103740459838730116538407103616981791949";
	private Properties properties;
	
	private Cipher cipher;
	private SecretKey secKey;
	
	public GameData()
	{
		try
		{
			cipher = Cipher.getInstance(CIPHER_ALGO);
			secKey = SecretKeyFactory.getInstance(CIPHER_ALGO).generateSecret(new DESedeKeySpec(new BigInteger(KEY, 16).toByteArray()));
		}
		catch(GeneralSecurityException e)
		{
			e.printStackTrace();
		}
		
		properties = new Properties();
		FileInputStream in = null;
		try
		{
			in = new FileInputStream(PATH);
			properties.load(in);
		}
		catch(IOException e)
		{
	        setDefaults();
	        save();
		}
		finally
		{
			try 
			{	
				if (in != null)
					in.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Initializes the default values.
	 */
	private void setDefaults() 
	{
	    properties.put(encrypt("MUSIC"), encrypt("0.5"));
	    properties.put(encrypt("EFFECTS"), encrypt("0.5"));
	    
	    properties.put(encrypt("EASY_1_ACCESS"), encrypt("true"));
	    properties.put(encrypt("EASY_1_RECORD"), encrypt("0"));
	    properties.put(encrypt("EASY_1_STARS"), encrypt("0"));
	    properties.put(encrypt("EASY_2_ACCESS"), encrypt("false"));
	    properties.put(encrypt("EASY_2_RECORD"), encrypt("0"));
	    properties.put(encrypt("EASY_2_STARS"), encrypt("0"));
	    properties.put(encrypt("EASY_3_ACCESS"), encrypt("false"));
	    properties.put(encrypt("EASY_3_RECORD"), encrypt("0"));
	    properties.put(encrypt("EASY_3_STARS"), encrypt("0"));
	    properties.put(encrypt("EASY_4_ACCESS"), encrypt("false"));
	    properties.put(encrypt("EASY_4_RECORD"), encrypt("0"));
	    properties.put(encrypt("EASY_4_STARS"), encrypt("0"));
	    properties.put(encrypt("EASY_5_ACCESS"), encrypt("false"));
	    properties.put(encrypt("EASY_5_RECORD"), encrypt("0"));
	    properties.put(encrypt("EASY_5_STARS"), encrypt("0"));
	    properties.put(encrypt("EASY_6_ACCESS"), encrypt("false"));
	    properties.put(encrypt("EASY_6_RECORD"), encrypt("0"));
	    properties.put(encrypt("EASY_6_STARS"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_1_ACCESS"), encrypt("true"));
	    properties.put(encrypt("MEDIUM_1_RECORD"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_1_STARS"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_2_ACCESS"), encrypt("false"));
	    properties.put(encrypt("MEDIUM_2_RECORD"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_2_STARS"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_3_ACCESS"), encrypt("false"));
	    properties.put(encrypt("MEDIUM_3_RECORD"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_3_STARS"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_4_ACCESS"), encrypt("false"));
	    properties.put(encrypt("MEDIUM_4_RECORD"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_4_STARS"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_5_ACCESS"), encrypt("false"));
	    properties.put(encrypt("MEDIUM_5_RECORD"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_5_STARS"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_6_ACCESS"), encrypt("false"));
	    properties.put(encrypt("MEDIUM_6_RECORD"), encrypt("0"));
	    properties.put(encrypt("MEDIUM_6_STARS"), encrypt("0"));
	    properties.put(encrypt("HARD_1_ACCESS"), encrypt("true"));
	    properties.put(encrypt("HARD_1_RECORD"), encrypt("0"));
	    properties.put(encrypt("HARD_1_STARS"), encrypt("0"));
	    properties.put(encrypt("HARD_2_ACCESS"), encrypt("false"));
	    properties.put(encrypt("HARD_2_RECORD"), encrypt("0"));
	    properties.put(encrypt("HARD_2_STARS"), encrypt("0"));
	    properties.put(encrypt("HARD_3_ACCESS"), encrypt("false"));
	    properties.put(encrypt("HARD_3_RECORD"), encrypt("0"));
	    properties.put(encrypt("HARD_3_STARS"), encrypt("0"));
	    properties.put(encrypt("HARD_4_ACCESS"), encrypt("false"));
	    properties.put(encrypt("HARD_4_RECORD"), encrypt("0"));
	    properties.put(encrypt("HARD_4_STARS"), encrypt("0"));
	    properties.put(encrypt("HARD_5_ACCESS"), encrypt("false"));
	    properties.put(encrypt("HARD_5_RECORD"), encrypt("0"));
	    properties.put(encrypt("HARD_5_STARS"), encrypt("0"));
	    properties.put(encrypt("HARD_6_ACCESS"), encrypt("false"));
	    properties.put(encrypt("HARD_6_RECORD"), encrypt("0"));
	    properties.put(encrypt("HARD_6_STARS"), encrypt("0"));
	}
	
	/**
	 * Stores the data in a property file located in {@code PATH}.
	 */
	public void save()
	{
		FileOutputStream out = null;
		try 
		{
			out = new FileOutputStream(new File(PATH));
			properties.store(out, "game properties file");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if (out != null)
					out.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns the String value associated with the given {@code key}.
	 * 
	 * @param	key the key value.
	 * @return	the String value associated with the key.
	 */
	public String getProperty(String key) 
	{
	    return decrypt(properties.getProperty(encrypt(key)));
	}
	
	/**
	 * Sets a new association between {@code key} and {@code value}.
	 * 
	 * @param	key the key value.
	 * @param	value the value to be associated.
	 */
	public void setProperty(String key, String value) 
	{
	    properties.setProperty(encrypt(key), encrypt(value));
	    save();
	}

	/**
	 * Encrypts a message using Triple-DES
	 * 
	 * @param	text the text in clear.
	 * @return	the encrypted message.
	 */
	private String encrypt(String text)
	{
		try
		{
			cipher.init(Cipher.ENCRYPT_MODE, secKey);
			Base64 encoder = new Base64();
			return new String(encoder.encode(cipher.doFinal(text.getBytes(Charset.forName("UTF-8")))));
		}
		catch(GeneralSecurityException e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Decrypts a message encrypted with Triple-DES
	 * 
	 * @param	text the text encrypted.
	 * @return	the decrypted message.
	 */
	private String decrypt(String text)
	{
		try 
		{
			cipher.init(Cipher.DECRYPT_MODE, secKey);
			Base64 decoder = new Base64();
			return new String (cipher.doFinal(decoder.decode(text.getBytes(Charset.forName("UTF-8")))));
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "";
	}
}