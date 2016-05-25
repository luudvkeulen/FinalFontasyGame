/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

import static com.ffxvi.game.entities.Direction.*;
import static com.ffxvi.game.entities.PlayerAnimation.*;
import java.io.Serializable;

/**
 * The SimplePlayer class, this is send to the server.
 * It only contains all the necessary informations for the other players.
 * @author Robin
 */
public class SimplePlayer implements Serializable {
    
    /**
     * The name of the player.
     * This may not be null or empty.
     */
    protected final String playerName;
    
    /**
     * The hitpoints of the player.
     * This may not be null.
     * If it is lower or 0, the player
     * should die.
     */
    protected int hitPoints;
    
    /**
     * The score of the player.
     * This may not be null.
     */
    protected int score;

    /**
     * The X-position of the player.
     * This may not be null.
     */
    protected float x;
    
    /**
     * The Y-position of hte player.
     * This may not be null.
     */
    protected float y;
    
    /**
     * The speed of the player.
     * May not be null.
     */
    protected float speed; 

    /**
     * The direction where the player is heading.
     * May not be null.
     */
    protected Direction direction;
    
    /**
     * The skin of the player.
     * May not be null.
     */
    protected PlayerCharacter skin;
    
    /**
     * The animation of the player.
     * May not be null.
     */
    protected PlayerAnimation animation;

    /**
     * The room ID where the player is in.
     * May not be null.
     */
    protected int roomId;

    /**
     * Get the X position of the player.
     * @return the X position of the player.
     */
    public float getX() {
            return x;
    }

    /**
     * Get the Y position of the player.
     * @return the Y position of the player.
     */
    public float getY() {
            return y;
    }

    /**
     * The constructor of the SimplePlayer class.
     * Initializes all the fields in this class.
     * This constructor is used when u want to make a player
     * from the server.
     * @param playerName The name of the player, this may not be null or empty.
     * @param posX The X-position of the player.
     * @param posY The Y-position of the player.
     * @param roomId The room ID of where the player is in.
     * 
     * @throws IllegalArgumentException when the playername is null or empty.
     */
    public SimplePlayer(String playerName, float posX, float posY, int roomId) {
        
        if (playerName == null || playerName.trim().equals("")) {
            throw new IllegalArgumentException("Playername may not be null or empty.");
        }

        this.playerName = playerName;
        hitPoints = 100;
        score = 0;
        x = posX;
        y = posY;
        speed = 0.0f;
        direction = DOWN;
        animation = IDLE;
        this.roomId = roomId;
    }

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
    public SimplePlayer(Player player){
        
        if (player == null) {
            throw new IllegalArgumentException("Player may not be null");
        }
        
        playerName = player.playerName;
        hitPoints = player.hitPoints;
        score = player.score;
        x = player.x;
        y = player.y;
        speed = player.speed;
        direction = player.direction;
        skin = player.skin;
        animation = player.animation;
    }
}
