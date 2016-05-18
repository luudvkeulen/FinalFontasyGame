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
 *   Joel Bleeker
 */
package com.ffxvi.game.business;

import com.ffxvi.game.customexceptions.GameInProgressException;
import com.ffxvi.game.customexceptions.NoGameInProgressException;
import com.ffxvi.game.models.AmmoType;
import com.ffxvi.game.models.RoomObjectType;
import com.ffxvi.game.models.RoomType;
import com.ffxvi.game.models.WeaponType;
import com.ffxvi.game.persistance.FileHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the current game instance and functions as controller class. Due to
 * using the Singleton design pattern, it's only constructor is private, to
 * prevent the initialization of new instances of this class.
 * <p>
 */
public class GameManager
{

    /**
     * A constant String containing the hard coded relative file path to a
     * properties file containing the settings for the game manager.
     */
    private static final String PROPERTIESFILEPATH = "config.properties";

    /**
     * Instance of the GameManager class.
     */
    private static GameManager GAMEMANAGER = new GameManager(PROPERTIESFILEPATH);

    /**
     * Dictionary containing a key (String) and an AmmoType of all the ammo
     * types which are used in the application.
     */
    private HashMap<String, AmmoType> ammoTypes;

    /**
     * Dictionary containing a key (String) and a WeaponType of all the weapon
     * types which are used in the application.
     */
    private HashMap<String, WeaponType> weaponTypes;

    /**
     * Dictionary containing a key (String) and a RoomObjectType of all the room
     * object types which are used in the application.
     */
    private HashMap<String, RoomObjectType> roomObjectTypes;

    /**
     * ArrayList containing all the room types which are used in the
     * application.
     */
    private ArrayList<RoomType> roomTypes;

    /**
     * Contains the Game object of the currently running game.
     */
    private Game currentGame;

    /**
     * Gets the current game.
     *
     * @return The current game.
     */
    public Game getCurrentGame()
    {
        return currentGame;
    }

    /**
     * The constructor of the GameManager class. Initializes the non-static
     * fields of this class. This constructor is private to make sure no other
     * instances of this class are created.
     *
     * @param filePath a String containing the file path for the properties file
     * of this application.
     */
    private GameManager(String filePath)
    {
        if (filePath == null || filePath.trim().isEmpty())
        {
            throw new IllegalArgumentException("Invalid file path.");
        }

        this.ammoTypes = new HashMap<String, AmmoType>();
        this.weaponTypes = new HashMap<String, WeaponType>();
        this.roomObjectTypes = new HashMap<String, RoomObjectType>();

        this.roomTypes = new ArrayList<RoomType>();

        this.currentGame = null;

//        try
//        {
//            this.loadProperties(filePath);
//        }
//        catch (IOException ex)
//        {
//            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * Loads the properties of this GameManager.
     *
     * @param filePath The file path of the properties file.
     */
    private void loadProperties(String filePath) throws IOException
    {
        FileHandler fileHandler = FileHandler.getInstance();

        HashMap<String, String> properties = fileHandler.getProperties(filePath);

        for (Entry<String, String> property : properties.entrySet())
        {
            String propertyKey = property.getKey();

            if (propertyKey.equals("ammoTypesFilePath"))
            {
                this.ammoTypes = fileHandler.getAmmoTypes(property.getValue());
            }
            else if (propertyKey.equals("weaponTypesFilePath"))
            {
                this.weaponTypes = fileHandler.getWeaponTypes(property.getValue());
            }
            else if (propertyKey.equals("roomObjectTypesFilePath"))
            {
                this.roomObjectTypes = fileHandler.getRoomObjectTypes(property.getValue());
            }
            else if (propertyKey.equals("roomTypesFilePath"))
            {
                this.roomTypes = fileHandler.getRoomTypes(property.getValue());
            }
        }
    }

    /**
     * Starts a new game. A new game object is created and replaces the current
     * value of the currentGame field.
     *
     * @throws GameInProgressException When there currently is a game in
     * progress.
     */
    public void startGame() throws GameInProgressException
    {
        if (this.currentGame != null)
        {
            throw new GameInProgressException("A game is already in progress.");
        }

        this.currentGame = new Game(new Date(), "Official Game Server") {};
    }

    /**
     * Ends the current game. A new game object is created and replaces the
     * current value of the currentGame field.
     *
     * @throws NoGameInProgressException When there is no game currently in
     * progress.
     */
    public void endGame() throws NoGameInProgressException
    {
        if (this.currentGame == null)
        {
            throw new NoGameInProgressException("No game is currently in progress.");
        }

        this.currentGame = null;
    }

    /**
     * Gets the current instance of GameManager.
     *
     * @return the current instance of GameManager.
     */
    public static GameManager getInstance()
    {
        return GAMEMANAGER;
    }

    /**
     * Resets this instance of the game manager to a new instance.
     */
    public static void resetInstance()
    {
        GAMEMANAGER = new GameManager(PROPERTIESFILEPATH);
    }
}
