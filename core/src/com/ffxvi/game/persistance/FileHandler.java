/*
 * (C) Copyright 2016 - S33A
 * Final Fontasy XVI, Version 1.0.
 * 
 * Contributors:
 *   Pim Janissen
 *   Luud van Keulen
 *   Robin de Kort
 *   Koen Schilders
 *   Guido Thomasse
 *   Joel Verbeek
 */
package com.ffxvi.game.persistance;

import com.ffxvi.game.models.AmmoType;
import com.ffxvi.game.models.RoomObjectType;
import com.ffxvi.game.models.RoomType;
import com.ffxvi.game.models.WeaponType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Handles all in- and output concerning files for the application. Makes calls
 * to other persistance classes to translate data types. For example the JSON
 * Handler.
 */
public class FileHandler
{

    /**
     * Instance of the FileHandler class.
     */
    private final static FileHandler FILEHANDLER = new FileHandler();

    /**
     * Initializes a new FileHandler. This constructor is private to make sure
     * no other instances of this class are created.
     */
    private FileHandler()
    {

    }

    /**
     * Gets the current instance of FileHandler.
     *
     * @return the current instance of JSONHandler.
     */
    public static FileHandler getInstance()
    {
        return FILEHANDLER;
    }

    /**
     * Gets a properties file from the given file path. If the path is an empty
     * String (excluding spaces) or the file in the path does not have a
     * '.properties' extension, throws a new IllegalArgumentException.
     *
     * @param filePath the file path of the properties file.
     * @return A HashMap containing the properties in pairs of a String
     * identifier and a String value.
     * @throws IOException Throws IOException when something unexpected went
     * wrong while reading the file.
     */
    public HashMap<String, String> getProperties(String filePath) throws IOException
    {
        if (filePath == null || filePath.trim().isEmpty())
        {
            throw new IllegalArgumentException("FilePath can neither be null nor an empty String (excluding spaces).");
        }

        if (!filePath.substring(filePath.indexOf(".")).equals("properties"))
        {
            throw new IllegalArgumentException("FilePath must have a '.properties' extension.");
        }

        Map properties = new Properties();

        File file = new File(filePath);

        InputStream inputStream = new FileInputStream(file);
        ((Properties) properties).load(inputStream);

        return new HashMap(properties);
    }

    /**
     * Gets the ammo types from the given file path. If the path is an empty
     * String (excluding spaces) or the file in the path does not have a '.json'
     * extension, throws a new IllegalArgumentException.
     *
     * @param filePath the file path of the properties file.
     * @return A HashMap containing the ammo types in pairs of a String
     * identifier and an AmmoType value.
     * @throws IOException Throws IOException when something unexpected went
     * wrong while reading the file.
     */
    public HashMap<String, AmmoType> getAmmoTypes(String filePath) throws IOException
    {
        if (filePath == null || filePath.trim().isEmpty())
        {
            throw new IllegalArgumentException("FilePath can neither be null nor an empty String (excluding spaces).");
        }

        if (!filePath.substring(filePath.indexOf(".")).equals("json"))
        {
            throw new IllegalArgumentException("FilePath must have a '.json' extension.");
        }

        File file = new File(filePath);

        InputStream inputStream = new FileInputStream(file);

        String ammoTypesAsJSON = new String();

        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null)
            {
                ammoTypesAsJSON += line;
            }
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
        }

        return JSONHandler.getInstance().getAmmoTypes(ammoTypesAsJSON);
    }

    /**
     * Gets the weapon types from the given file path. If the path is an empty
     * String (excluding spaces) or the file in the path does not have a '.json'
     * extension, throws a new IllegalArgumentException.
     *
     * @param filePath the file path of the json file.
     * @return A HashMap containing the weapon types in pairs of a String
     * identifier and a WeaponType value.
     * @throws IOException When something unexpected went wrong while reading
     * the file.
     */
    public HashMap<String, WeaponType> getWeaponTypes(String filePath) throws IOException
    {
        if (filePath == null || filePath.trim().isEmpty())
        {
            throw new IllegalArgumentException("FilePath can neither be null nor an empty String (excluding spaces).");
        }

        if (!filePath.substring(filePath.indexOf(".")).equals("json"))
        {
            throw new IllegalArgumentException("FilePath must have a '.json' extension.");
        }

        File file = new File(filePath);

        InputStream inputStream = new FileInputStream(filePath);

        String weaponTypesAsJSON = new String();

        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null)
            {
                weaponTypesAsJSON += line;
            }
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
        }

        return JSONHandler.getInstance().getWeaponTypes(weaponTypesAsJSON);
    }

    /**
     * Gets the room object types from the given file path. If the path is an
     * empty String (excluding spaces) or the file in the path does not have a
     * '.json' extension, throws a new IllegalArgumentException.
     *
     * @param filePath the file path of the json file.
     * @return A HashMap containing the room object types in pairs of a String
     * identifier and a RoomObjectType value.
     * @throws IOException When something unexpected went wrong while reading
     * the file.
     */
    public HashMap<String, RoomObjectType> getRoomObjectTypes(String filePath) throws IOException
    {
        if (filePath == null || filePath.trim().isEmpty())
        {
            throw new IllegalArgumentException("FilePath can neither be null nor an empty String (excluding spaces).");
        }

        if (!filePath.substring(filePath.indexOf(".")).equals("json"))
        {
            throw new IllegalArgumentException("FilePath must have a '.json' extension.");
        }

        File file = new File(filePath);

        InputStream inputStream = new FileInputStream(file);

        String roomObjectTypesAsJSON = new String();

        BufferedReader reader = null;
        
        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            
            String line;

            while ((line = reader.readLine()) != null)
            {
                roomObjectTypesAsJSON += line;
            }
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
        }

        return JSONHandler.getInstance().getRoomObjectTypes(roomObjectTypesAsJSON);
    }

    /**
     * Gets the room types from the given file path. If the path is an empty
     * String (excluding spaces) or the file in the path does not have a '.json'
     * extension, throws a new IllegalArgumentException.
     *
     * @param filePath the file path of the json file.
     * @return An ArrayList containing the room types.
     * @throws IOException When something unexpected went wrong while reading
     * the file.
     */
    public ArrayList<RoomType> getRoomTypes(String filePath) throws IOException
    {
        if (filePath == null || filePath.trim().isEmpty())
        {
            throw new IllegalArgumentException("FilePath can neither be null nor an empty String (excluding spaces).");
        }

        if (!filePath.substring(filePath.indexOf(".")).equals("json"))
        {
            throw new IllegalArgumentException("FilePath must have a '.json' extension.");
        }

        File file = new File(filePath);

        InputStream inputStream = new FileInputStream(file);

        String roomTypesAsJSON = new String();

        BufferedReader reader = null;
        
        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null)
            {
                roomTypesAsJSON += line;
            }
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
        }

        return JSONHandler.getInstance().getRoomTypes(roomTypesAsJSON);
    }
}
