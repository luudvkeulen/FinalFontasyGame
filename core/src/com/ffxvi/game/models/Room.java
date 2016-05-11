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
package com.ffxvi.game.models;

import com.ffxvi.game.support.Vector;
import java.util.*;


/**
 * This class contains all the room properties. It contains the position,
 * RoomType, RoomObjects, Players and Projectiles.
 */
public class Room {

    /**
     * The position of the room.
     */
    private Vector position;

    /**
     * The kind of RoomType which this room has.
     */
    private RoomType roomType;

    /**
     * A List of RoomObjects which are in this room.
     */
    private List<RoomObject> roomObjects;

    /**
     * A List of Players which are in this room.
     */
    private List<Player> players;

    /**
     * A List of Projectiles which are in this room.
     */
    private List<Projectile> projectiles;

    /**
     * The constructor of the class Room. This instantiates all the fields which
     * are in this class.
     *
     * @param position The position of this room in the Game.
     * @param roomType The pre-constructed room layout that this room uses.
     */
    public Room(Vector position, RoomType roomType) {
        if (position == null) {
            throw new IllegalArgumentException();
        }

        if (roomType == null) {
            throw new IllegalArgumentException();
        }

        this.position = position;
        this.roomType = roomType;
        this.roomObjects = new ArrayList();
        Collections.copy(roomType.getRoomObjects(), this.roomObjects);
        this.players = new ArrayList<Player>();
        this.projectiles = new ArrayList<Projectile>();
    }

    /**
     * Get the position of this room.
     *
     * @return The position of this room in the Game.
     */
    public Vector getPosition() {
        return position;
    }

    /**
     * Get the RoomType of this room.
     *
     * @return The pre-constructed room layout that this room uses.
     */
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * Gets all the RoomObjects which are in the room.
     *
     * @return The objects in this room.
     */
    public List<RoomObject> getRoomObjects() {
        List ret = Collections.unmodifiableList(roomObjects);
        return ret;
    }

    /**
     * Gets all the players which are in this room.
     *
     * @return The players in this room.
     */
    public List<Player> getPlayers() {
        List ret = Collections.unmodifiableList(players);
        return ret;
    }

    /**
     * Gets all the projectiles which are in this room.
     *
     * @return The projectiles in this room.
     */
    public List<Projectile> getProjectiles() {
        List ret = Collections.unmodifiableList(projectiles);
        return ret;
    }

    /**
     * Spawns a projectile in the room.
     *
     * @param projectile The projectile that is fired from within this room.
     * @return A boolean containing: If the projectile's position was invalid,
     * false. Else, true.
     */
    public boolean spawnProjectile(Projectile projectile) {
        if (validPlacement(projectile.getPosition())) {
            this.projectiles.add(projectile);
            return true;
        }

        return false;
    }

    /**
     * Spawns a player in the room. The player's current room is set to this
     * room and the player's spawn method is called.
     *
     * @param player The player that enters/spawns in this room.
     * @return A boolean containing: If the player was already in the room or
     * the location was not valid, false. Else, true.
     */
    public boolean spawnPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player can not be null.");
        }

        if (!this.players.contains(player)) {

            Vector playerPosition = player.getPosition();

            if (playerPosition == null) {
                playerPosition = this.getSpawnPositionForPlayer();
            }

            if (validPlacement(playerPosition) && !this.isPositionBlocked(playerPosition)) {
                for (Player p : this.players) {
                    if (p.getName().equals(player.getName())) {
                        return false;
                    }
                }

                this.players.add(player);
                player.spawn();
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a room object to this room.
     *
     * @param obj The roomObject that's being placed in this room.
     * @return A boolean containing: if the room object's location is equal to
     */
    public boolean addRoomObject(RoomObject obj) {
        if (validPlacement(obj.getPosition()) && !this.isPositionBlocked(obj.getPosition())) {
            this.roomObjects.add(obj);
            return true;
        }

        return false;
    }

    /**
     * Checks if the given position is inside the room's borders and if the
     * given position is not blocked.
     *
     * @param position The position that's being checked for validity.
     * @return True if the attempted placement is valid, otherwise false.
     */
    private boolean validPlacement(Vector position) {
        if (position == null) {
            throw new IllegalArgumentException("Position can not be null.");
        }

        return !(position.getX() < 0
                || position.getX() > roomType.getRoomSizeX() - 1
                || position.getY() < 0
                || position.getY() > roomType.getRoomSizeY() - 1);
    }

    /**
     * Checks if the given position is already blocked by either a room object,
     * a player or a projectile.
     *
     * @param position the position to check.
     * @return A boolean indicating if the location was blocked.
     */
    private boolean isPositionBlocked(Vector position) {
        if (position == null) {
            throw new IllegalArgumentException("Position can not be null");
        }

        for (RoomObject roomObject : this.roomObjects) {
            if (roomObject.getPosition().equals(position)) {
                return true;
            }
        }

        for (Player player : this.getPlayers()) {
            if (player.getPosition().equals(position)) {
                return true;
            }
        }

        for (Projectile projectile : this.getProjectiles()) {
            if (projectile.getPosition().equals(position)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets a spawn position for a player inside a room.
     *
     * @return A Vector2f containing the found spawn position for the player.
     */
    private Vector getSpawnPositionForPlayer() {

        Random random = new Random();

        Vector spawnPosition = null;

        while (spawnPosition == null || this.isPositionBlocked(spawnPosition)) {
            float x = (float) random.nextInt(this.roomType.getRoomSizeX());
            float y = (float) random.nextInt(this.roomType.getRoomSizeY());
            spawnPosition = new Vector(x, y);
        }

        return spawnPosition;
    }

    /**
     * Check if this room overlaps with another room.
     *
     * @param room The room which to check this overlapping with.
     * @return Returns true if the Room param overlaps with this room.
     */
    public boolean doesPositionOverlapWith(Room room) {

        if (room == null) {
            throw new IllegalArgumentException();
        }

        return !(room.getPosition().getX() > this.position.getX() + this.roomType.getRoomSizeX() - 1
                || room.getPosition().getX() + room.getRoomType().getRoomSizeX() - 1 < this.position.getX()
                || room.getPosition().getY() > this.position.getY() + this.roomType.getRoomSizeY() - 1
                || room.getPosition().getY() + room.getRoomType().getRoomSizeY() - 1 < this.position.getY());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.position);
        hash = 79 * hash + Objects.hashCode(this.roomType);
        hash = 79 * hash + Objects.hashCode(this.roomObjects);
        hash = 79 * hash + Objects.hashCode(this.players);
        hash = 79 * hash + Objects.hashCode(this.projectiles);
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
        final Room other = (Room) obj;
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        if (!Objects.equals(this.roomType, other.roomType)) {
            return false;
        }
        if (!Objects.equals(this.roomObjects, other.roomObjects)) {
            return false;
        }
        if (!Objects.equals(this.players, other.players)) {
            return false;
        }
        return Objects.equals(this.projectiles, other.projectiles);
    }

}
