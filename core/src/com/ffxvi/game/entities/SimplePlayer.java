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
     * The time within the animation.
     */
    private float stateTime;

    /**
     * Get the name of the player.
     * @return The name of the player.
     */
    public String getName() {
        return playerName;
    }

    /**
     * Get the hitpoints of the player.
     * @return The hitpoints of the player.
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Get the score of the player.
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the ID of the room where the player is in.
     * @return The ID of the room where the player is in.
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Get the speed of the player.
     * @return The speed of the player.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Get the Direction of the player.
     * @return The Direction of the player.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Get the PlayerCharacter (skin) of the player.
     * @return The skin of the player.
     */
    public PlayerCharacter getSkin() {
        return skin;
    }

    /**
     * Get the PlayerAnimation of the player.
     * @return The PlayerAnimation of the player.
     */
    public PlayerAnimation getAnimation() {
        return animation;
    }

    /**
     * Get the statetime of the player's current animation.
     * @return The statetime of the player's current animation.
     */
    public float getStateTime() {
        return stateTime;
    }

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
     * @param skin The skin of the player, may not be null.
     * 
     * @throws IllegalArgumentException when the playername is null or empty.
     * @throws IllegalArgumentException when the skin is null.
     */
    public SimplePlayer(String playerName, float posX, float posY, int roomId, PlayerCharacter skin) {
        
        if (skin == null) {
            throw new IllegalArgumentException("Skin may not be null");
        }
        
        if (playerName == null || playerName.trim().equals("")) {
            throw new IllegalArgumentException("Playername is null or empty!");
        }

        this.playerName = playerName;
        hitPoints = 100;
        score = 0;

        this.roomId = roomId;
        x = posX;
        y = posY;
        speed = 0.0f;

        direction = DOWN;
        this.skin = skin;
        animation = IDLE;
        this.stateTime = 0;
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
