/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertiesfileforip;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gebruiker-pc
 */
public class PropertiesFileForIP
{

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		Properties properties = new Properties();

		properties.put(PropertyKeys.QUERYSERVERADDRESS, args[0]);

		String path = args[1] + "/IPSettings.properties";
		OutputStream out = null;

		try
		{
			out = new FileOutputStream(path);
		}
		catch (FileNotFoundException ex)
		{
			Logger.getLogger(PropertiesFileForIP.class.getName()).log(Level.SEVERE, null, ex);
		}

		try
		{
			properties.store(out, "");
		}
		catch (IOException ex)
		{
			Logger.getLogger(PropertiesFileForIP.class.getName()).log(Level.SEVERE, null, ex);
		}

		Properties loadedProperties = new Properties();
		
		InputStream inStream = null;
		try
		{
			inStream = new FileInputStream(path);
		}
		catch (FileNotFoundException ex)
		{
			Logger.getLogger(PropertiesFileForIP.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		try
		{
			loadedProperties.load(inStream);
		}
		catch (IOException ex)
		{
			Logger.getLogger(PropertiesFileForIP.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		loadedProperties.entrySet().stream().forEach((property) ->
		{
			System.out.printf("%s = %s\n", property.getKey().toString(), property.getValue().toString());
		});
	}
}
