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
package com.ffxvi.game.entities;

import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.support.Vector;
import org.junit.*;

/**
 * The test class for the SimplePlayer class.
 * @author Guido
 */
public class SimplePlayerTest {
    SimplePlayer simplePlayer;
    String playerName = "Test";
    float posX = 20.0f;
    float posY = 20.0f;
    int roomID = 1;
    
    @Before
    public void initialize() {
        simplePlayer = new SimplePlayer(playerName, posX, posY, roomID, 
                PlayerCharacter.SKELETON_DAGGER);
    }
    
    /**
     * Test the getX method of SimplePlayer.
     */
    @Test
    public void testGetX() {
       /**
        * Get the X position of the player.
        * @return the X position of the player.
        */
       
        float expected = posX;
        Assert.assertTrue(expected == simplePlayer.getX());
    }
    
    /**
     * Test the getY method of SimplePlayer.
     */
    @Test
    public void testGetY() {
       /**
        * Get the Y position of the player.
        * @return the Y position of the player.
        */
       
        float expected = posX;
        Assert.assertTrue(expected == simplePlayer.getY());
    }
    
    /**
     * Test the constructor with a player object correct.
     */
    @Test
    public void testConstructorPlayerCorrect() {
       /**
        * The constructor of the SimplePlayer class.
        * Initializes all the fields in this class.
        * This constructor is used when you want to make
        * a SimplePlayer on the client.
        * @param player The player which you want to create a SimplePlayer from,
        * this may not be null.
        * 
        * @throws IllegalArgumentException when the player is null.
        */
       
        String name = "Testerino";
        Vector pos = new Vector(20, 20);
        int roomid = 1;
        
        Player player = new Player(PlayerCharacter.SKELETON_DAGGER, name, pos, roomid);
        Assert.assertNotNull(player);
        
        SimplePlayer sPlayer = new SimplePlayer(player);
        Assert.assertNotNull(sPlayer);
    }
    
    /**
     * Test the constructor with a player object which is null.
     * Testcase expects IllegalArgumentException.
     */
    @Test (expected=IllegalArgumentException.class)
    public void testConstructorPlayerNull() {
       /**
        * The constructor of the SimplePlayer class.
        * Initializes all the fields in this class.
        * This constructor is used when you want to make
        * a SimplePlayer on the client.
        * @param player The player which you want to create a SimplePlayer from,
        * this may not be null.
        * 
        * @throws IllegalArgumentException when the player is null.
        */
        
       Player p = null;
       SimplePlayer sp = new SimplePlayer(p);
    }
    
    /**
     * Test the constructor which needs multiple parameters in a correct way.
     */
    @Test
    public void testConstructorMultipleParametersCorrect() {
        /**
         * The constructor of the SimplePlayer class.
         * Initializes all the fields in this class.
         * This constructor is used when u want to make a player
         * from the server.
         * @param playerName The name of the player, this may not be null or empty.
         * @param posX The X-position of the player.
         * @param posY The Y-position of the player.
         * @param roomId The room ID of where the player is in.
         * @param skin The skin of the player, may not be null.
         * 
         * @throws IllegalArgumentException when the playername is null or empty.
         * @throws IllegalArgumentException when the skin is null.
         */
        
        SimplePlayer sP;
        String name = "Test";
        float posx = 20.0f;
        float posy = 20.0f;
        int roomid = 1;
        
        sP = new SimplePlayer(name, posx, posy, roomid, 
                PlayerCharacter.SKELETON_HOODED_DAGGER);
    }
    
    /**
     * Test the constructor which needs multiple parameters where the name is null.
     */
    @Test (expected=IllegalArgumentException.class)
    public void testConstructorMultipleParametersPlayerNameNull() {
        /**
         * The constructor of the SimplePlayer class.
         * Initializes all the fields in this class.
         * This constructor is used when u want to make a player
         * from the server.
         * @param playerName The name of the player, this may not be null or empty.
         * @param posX The X-position of the player.
         * @param posY The Y-position of the player.
         * @param roomId The room ID of where the player is in.
         * @param skin The skin of the player, may not be null.
         * 
         * @throws IllegalArgumentException when the playername is null or empty.
         * @throws IllegalArgumentException when the skin is null.
         */
        
        SimplePlayer sP;
        String name = null;
        float posx = 20.0f;
        float posy = 20.0f;
        int roomid = 1;
        
        sP = new SimplePlayer(name, posx, posy, roomid, 
                PlayerCharacter.SKELETON_HOODED_DAGGER);
    }
    
    /**
     * Test the constructor which needs multiple parameters 
     * where the name only contains spaces.
     */
    @Test (expected=IllegalArgumentException.class)
    public void testConstructorMultipleParametersPlayerNameEmptySpaces() {
        /**
         * The constructor of the SimplePlayer class.
         * Initializes all the fields in this class.
         * This constructor is used when u want to make a player
         * from the server.
         * @param playerName The name of the player, this may not be null or empty.
         * @param posX The X-position of the player.
         * @param posY The Y-position of the player.
         * @param roomId The room ID of where the player is in.
         * @param skin The skin of the player, may not be null.
         * 
         * @throws IllegalArgumentException when the playername is null or empty.
         * @throws IllegalArgumentException when the skin is null.
         */
        
        SimplePlayer sP;
        String name = "    ";
        float posx = 20.0f;
        float posy = 20.0f;
        int roomid = 1;
        
        sP = new SimplePlayer(name, posx, posy, roomid, 
                PlayerCharacter.SKELETON_HOODED_DAGGER);
    }
    
        /**
     * Test the constructor which needs multiple parameters where the name is empty.
     */
    @Test (expected=IllegalArgumentException.class)
    public void testConstructorMultipleParametersPlayerNameEmpty() {
        /**
         * The constructor of the SimplePlayer class.
         * Initializes all the fields in this class.
         * This constructor is used when u want to make a player
         * from the server.
         * @param playerName The name of the player, this may not be null or empty.
         * @param posX The X-position of the player.
         * @param posY The Y-position of the player.
         * @param roomId The room ID of where the player is in.
         * @param skin The skin of the player, may not be null.
         * 
         * @throws IllegalArgumentException when the playername is null or empty.
         * @throws IllegalArgumentException when the skin is null.
         */
        
        SimplePlayer sP;
        String name = "";
        float posx = 20.0f;
        float posy = 20.0f;
        int roomid = 1;
        
        sP = new SimplePlayer(name, posx, posy, roomid, 
                PlayerCharacter.SKELETON_HOODED_DAGGER);
    }
    
    /**
     * Test the constructor which needs multiple parameters where the name is empty.
     */
    @Test (expected=IllegalArgumentException.class)
    public void testConstructorMultipleParametersPlayerSkinNull() {
        /**
         * The constructor of the SimplePlayer class.
         * Initializes all the fields in this class.
         * This constructor is used when u want to make a player
         * from the server.
         * @param playerName The name of the player, this may not be null or empty.
         * @param posX The X-position of the player.
         * @param posY The Y-position of the player.
         * @param roomId The room ID of where the player is in.
         * @param skin The skin of the player, may not be null.
         * 
         * @throws IllegalArgumentException when the playername is null or empty.
         * @throws IllegalArgumentException when the skin is null.
         */
        
        SimplePlayer sP;
        String name = "TestTesterino";
        float posx = 20.0f;
        float posy = 20.0f;
        int roomid = 1;
        
        sP = new SimplePlayer(name, posx, posy, roomid, null);
    }
    
    
}
