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

import static com.ffxvi.game.entities.Direction.*;
import static com.ffxvi.game.entities.PlayerAnimation.*;
import java.io.Serializable;

/**
 * The SimplePlayer class, this is send to the server. It only contains all the
 * necessary informations for the other players.
 */
public class SimplePlayer implements Serializable {

    /**
     * The name of the player. This may not be null or empty.
     */
    protected String playerName;

    /**
     * The hitpoints of the player. This may not be null. If it is lower or 0,
     * the player should die.
     */
    protected int hitPoints;

    /**
     * The score of the player. This may not be null.
     */
    protected int score;

    /**
     * The X-position of the player. This may not be null.
     */
    protected float x;

    /**
     * The Y-position of the player. This may not be null.
     */
    protected float y;

    /**
     * The speed of the player. May not be null.
     */
    protected float speed;

    /**
     * The direction where the player is heading. May not be null.
     */
    protected Direction direction;

    /**
     * The skin of the player. May not be null.
     */
    protected PlayerCharacter skin;

    /**
     * The animation of the player. May not be null.
     */
    protected PlayerAnimation animation;

    /**
     * The room ID where the player is in. May not be null.
     */
    protected int roomId;

    /**
     * The time within the animation.
     */
    protected float stateTime;

    /**
     * The constructor of the SimplePlayer class. Initializes all the fields in
     * this class. This constructor is used when u want to make a player from
     * the server.
     *
     * @param playerName The name of the player, this may not be null or empty.
     * @param posX The X-position of the player. If negative, throw an
     * IllegalArgumentException.
     * @param posY The Y-position of the player. If negative, throw an
     * IllegalArgumentException.
     * @param roomID The room ID of the room that the player is in. If not
     * greater than 0, throw an IllegalArgumentException.
     * @param skin The skin of the player.
     *
     * @throws IllegalArgumentException when the playername is null or empty.
     * @throws IllegalArgumentException when the skin is null.
     */
    public SimplePlayer(String playerName, float posX, float posY, int roomID, PlayerCharacter skin) {

        if (skin == null) {
            throw new IllegalArgumentException("Skin may not be null");
        }

        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Playername is null or empty!");
        }

        if (posX < 0) {
            throw new IllegalArgumentException("PosX can not be negative.");
        }

        if (posY < 0) {
            throw new IllegalArgumentException("PosY can not be negative.");
        }

        if (roomID < 1) {
            throw new IllegalArgumentException("RoomID must be at least 1.");
        }
        
        this.playerName = playerName;
        this.hitPoints = 100;
        this.score = 0;

        this.roomId = roomID;
        this.x = posX;
        this.y = posY;
        this.speed = 0.0f;

        this.direction = DOWN;
        this.skin = skin;
        this.animation = IDLE;
        this.stateTime = 0f;
    }

    /**
     * The constructor of the SimplePlayer class. Initializes all the fields in
     * this class. This constructor is used when you want to make a SimplePlayer
     * on the client.
     *
     * @param player The player which you want to create a SimplePlayer from,
     * this may not be null.
     *
     * @throws IllegalArgumentException when the player is null.
     */
    public SimplePlayer(Player player) {

        if (player == null) {
            throw new IllegalArgumentException("Player may not be null");
        }

        this.playerName = player.playerName;
        this.hitPoints = player.hitPoints;
        this.score = player.score;
        
        this.roomId = player.roomId;
        this.x = player.x;
        this.y = player.y;
        this.speed = player.speed;
        
        this.direction = player.direction;
        this.skin = player.skin;
        this.animation = player.animation;
        this.stateTime = player.stateTime;
    }

    /**
     * Get the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return this.playerName;
    }

    /**
     * Get the hitpoints of the player.
     *
     * @return The hitpoints of the player.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Get the score of the player.
     *
     * @return The score of the player.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Get the ID of the room where the player is in.
     *
     * @return The ID of the room where the player is in.
     */
    public int getRoomId() {
        return this.roomId;
    }

    /**
     * Get the speed of the player.
     *
     * @return The speed of the player.
     */
    public float getSpeed() {
        return this.speed;
    }

    /**
     * Get the Direction of the player.
     *
     * @return The Direction of the player.
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Get the PlayerCharacter (skin) of the player.
     *
     * @return The skin of the player.
     */
    public PlayerCharacter getSkin() {
        return this.skin;
    }

    /**
     * Get the PlayerAnimation of the player.
     *
     * @return The PlayerAnimation of the player.
     */
    public PlayerAnimation getAnimation() {
        return this.animation;
    }

    /**
     * Get the statetime of the player's current animation.
     *
     * @return The statetime of the player's current animation.
     */
    public float getStateTime() {
        return this.stateTime;
    }

    /**
     * Get the X position of the player.
     *
     * @return the X position of the player.
     */
    public float getX() {
        return this.x;
    }

    /**
     * Get the Y position of the player.
     *
     * @return the Y position of the player.
     */
    public float getY() {
        return this.y;
    }
}
