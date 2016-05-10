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
package com.ffxvi.game.business;

import com.ffxvi.game.customexceptions.PlayerAliveException;
import com.ffxvi.game.models.Player;
import com.ffxvi.game.models.Room;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Contains a game.
 */
public class Game {

    /**
     * ArrayList containing all the players which are currently in the game.
     */
    private ArrayList<Player> players;

    /**
     * ArrayList containing all the rooms in the game
     */
    private ArrayList<Room> rooms;

    /**
     * The startdate and startime of the game.
     */
    private final Date startTime;

    /**
     * The name of the game.
     */
    private String name;

    /**
     * Gets the players which are currently in the game.
     *
     * @return An ArrayList containing the players.
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /**
     * Gets the rooms which are currently in the game.
     *
     * @return An ArrayList containing the rooms.
     */
    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    /**
     * Gets the start date and time of the game.
     *
     * @return The start date and time of the game.
     */
    public Date getStartTime() {
        return (Date) this.startTime.clone();
    }

    /**
     * Gets the name of the game.
     *
     * @return The name of the game.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the game.
     *
     * @param name the new name of the game.
     */
    public void setName(String name) {
        if (name == null || name.trim().equals("")) {
            throw new IllegalArgumentException("The name of the game must be a decent text, meaning it can neither be null nor an empty string (excluding spaces).");
        }

        this.name = name;
    }

    /**
     * Initializes a new game object.
     *
     * @param startTime the start date and time of the game.
     * @param name the name of the game. If the name of the game is an empty
     * string (excluding spaces), throws an IllegalArgumentException
     */
    public Game(Date startTime, String name) {
        if (startTime == null) {
            throw new IllegalArgumentException("The start time of the game can not be null.");
        }

        if (name == null || name.trim().equals("")) {
            throw new IllegalArgumentException("The name of the game must be a decent text, meaning it can neither be null nor an empty string (excluding spaces).");
        }

        this.startTime = (Date) startTime.clone();
        this.name = name;

        this.players = new ArrayList<Player>();
        this.rooms = new ArrayList<Room>();
    }

    /**
     * Adds a new player to the game.
     *
     * @param name the name of the player. Has to be unique within the game.
     * When the given playerName is an empty string (excluding spaces), throws a
     * new IllegalArgumentException.
     * @param animationPath the file path of the animation of the player. When
     * an empty string (excluding spaces) an IllegalArgumentException is thrown.
     * @return boolean indicating whether the player was succesfully added. If a
     * player with the given name already exists, returns false. Otherwise,
     * return true.
     */
    public boolean addPlayer(String name, String animationPath) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("The name can neither be null norr be an empty string (excluding spaces).");
        }

        if (animationPath == null || animationPath.trim().isEmpty()) {
            throw new IllegalArgumentException("The animation path can neither be null nor an empty string (excluding spaces).");
        }

        if (this.findPlayerByName(name) != null) {
            return false;
        }

        Room room = this.getSpawnRoomForPlayer();

        Player player = new Player(name, 100, animationPath, room);

        room.spawnPlayer(player);

        this.players.add(player);
        return true;
    }

    /**
     * Removes a player with the given name from this game.
     *
     * @param name the name of the player which is to be removed. If the name of
     * the game is an empty string (excluding spaces), throws an
     * IllegalArgumentException
     */
    public void removePlayer(String name) {
        if (name == null || name.trim().equals("")) {
            throw new IllegalArgumentException("The name can not be null neither be an empty string (excluding spaces)");
        }

        Player player = this.findPlayerByName(name);

        if (player != null) {
            this.players.remove(player);
        }
    }

    /**
     * Respawns the given player. Sets the health of this player back to the
     * maximum and spawns the player in a new room.
     *
     * @param player the player to add. When the player is not dead, throw a
     * PlayerAliveException.
     * @throws PlayerAliveException When the given player is not dead.
     */
    public void respawnPlayer(Player player) throws PlayerAliveException {
        if (player == null) {
            throw new IllegalArgumentException("Player can not be null.");
        }

        if (!player.isDead()) {
            throw new PlayerAliveException("Player is not dead");
        }

        Room spawnRoom = this.getSpawnRoomForPlayer();

        spawnRoom.spawnPlayer(player);
    }

    /**
     * Finds a player by a given name.
     *
     * @param name the name of the player.
     * @return The player which was found. If no player was found, return null.
     */
    private Player findPlayerByName(String name) {
        if (this.name == null) {
            throw new IllegalArgumentException("Name can not be null.");
        }

        for (Player player : this.players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }

        return null;
    }

    /**
     * Adds a new room to the game.
     *
     * @param room the room to add.
     * @return A boolean returning: if the given room's coordinates overlap with
     * the coordinates of a room which is already in the game.
     */
    public boolean addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room can not be null.");
        }

        if (this.doesRoomOverlap(room)) {
            return false;
        }

        this.rooms.add(room);
        return true;
    }

    /**
     * Checks if the given room overlaps with a room which already exists in
     * this
     *
     * @param room The room to check if it overlaps.
     * @return A boolean indicating whether the given room overlaps with this
     * room.
     */
    private boolean doesRoomOverlap(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("The given room can not be null.");
        }

        for (Room roomToCheck : this.rooms) {
            if (room.doesPositionOverlapWith(roomToCheck)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets a room from the list of rooms where a new player can spawn in.
     */
    private Room getSpawnRoomForPlayer() {

        ArrayList<Room> emptyRooms = new ArrayList<Room>();

        // Checks if there are empty rooms in the game
        for (Room room : this.rooms) {
            if (room.getPlayers().isEmpty()) {
                emptyRooms.add(room);
            }
        }

        Random random = new Random();
        int index = 0;
        if (emptyRooms.size() > 0) {

            index = random.nextInt(emptyRooms.size());

            return this.rooms.get(index);
        }

        index = random.nextInt(this.rooms.size());
        return this.getRooms().get(index);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.players);
        hash = 89 * hash + Objects.hashCode(this.rooms);
        hash = 89 * hash + Objects.hashCode(this.startTime);
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.players, other.players)) {
            return false;
        }
        if (!Objects.equals(this.rooms, other.rooms)) {
            return false;
        }

        return Objects.equals(this.startTime, other.startTime);
    }

}
