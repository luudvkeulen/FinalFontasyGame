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

import java.util.HashMap;
import java.util.Objects;
import support.Vector;

/**
 * Contains a player of a game, which can be either a player or a spectator.
 * This class contains all the properties of a Player.
 */
public class Player {

    /**
     * The name of the player. The player can use this to indentify himself.
     */
    private final String name;

    /**
     * The score the player. When a player kills a unit it the score should
     * increase.
     */
    private int score;

    /**
     * The maximum amount of hitpoints which the player has.
     */
    private final int maxHitPoints;

    /**
     * The amount of hitpoints which the player has. This should decrease when
     * the player takes damage by an melee attack or a projectile.
     */
    private int hitPoints;

    /**
     * A boolean indicating whether the player is dead.
     */
    private boolean isDead;

    /**
     * The position of the player within a room.
     */
    private Vector position;

    /**
     * The movement speed of a player.
     */
    private float moveSpeed;

    /**
     * The rotation of a player. If the rotation is 0 the player looks up. If
     * the rotation is 90, the player looks right. If the rotation is 180, the
     * player looks down. If the rotation is 270, the player looks left. The
     * rotation needs to be within a range of 0 - 359.
     */
    private float rotation;

    /**
     * The file path of the animation/sprite of a player.
     */
    private String animationPath;

    /**
     * The total amount of ammunition a player has which is not in his gun, but
     * in his backpack. Each AmmoType has an integer with the amount of bullets.
     */
    private HashMap<AmmoType, Integer> ammoReserves;

    /**
     * The weapon which the player is holding right now.
     */
    private Weapon currentWeapon;

    /**
     * The current room which the player is in now.
     */
    private Room currentRoom;

    /**
     * Initializes a new player object with an unknown position within the room.
     *
     * @param name is the name of the player. When an empty string (excluding
     * spaces), throw IllegalArgumentException.
     * @param hitPoints the amount of hitpoints of the player. When not a
     * positive value, throw IllegalArgumentException.
     * @param animationPath the file path of the animation of the player.
     * @param startingRoom the starting room of the player
     */
    public Player(String name, int hitPoints, String animationPath, Room startingRoom) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name can neither be null nor an empty string (excluding spaces).");
        }

        if (hitPoints <= 0) {
            throw new IllegalArgumentException("HitPoints must be a positive value.");
        }

        if (startingRoom == null) {
            throw new IllegalArgumentException("StartingRoom can not be null.");
        }

        if (animationPath == null || animationPath.trim().isEmpty()) {
            throw new IllegalArgumentException("AnimationPath can neither be null nor an empty string (excluding spaces).");
        }

        this.name = name;
        this.score = 0;
        this.rotation = 0.0f;
        this.animationPath = animationPath;
        this.ammoReserves = new HashMap<AmmoType, Integer>();
        this.currentWeapon = null;
        this.currentRoom = startingRoom;
        this.maxHitPoints = hitPoints;
        this.moveSpeed = 3.0f;
        this.isDead = true;
    }

    /**
     * Creation with name and position
     *
     * @param name is the name of the player. When an empty string (excluding
     * spaces), throw IllegalArgumentException.
     * @param hitPoints the amount of hitpoints of the player. When not a
     * positive value, throw IllegalArgumentException.
     * @param position the starting position of the player. When the x or y is
     * not a positive value, throw new IllegalArgumentException.
     * @param animationPath the file path of the animation of the player.
     * @param startingRoom the starting room of the player
     */
    public Player(String name, int hitPoints, Vector position, String animationPath, Room startingRoom) {
        this(name, hitPoints, animationPath, startingRoom);

        if (position == null || position.getX() < 0 || position.getY() < 0) {
            throw new IllegalArgumentException("Position must have a positive x and y value.");
        }

        this.position = position;
    }

    /**
     * Gets the players name.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player score.
     *
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the players score.
     *
     * @param score The score to set.
     */
    public void setScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException();
        }

        this.score = score;

    }

    /**
     * Gets the player's maximum amount of hitpoints.
     *
     * @return Returns the maximum amount of hitpoints of the player.
     */
    public int getMaximumHitPoints() {
        return this.hitPoints;
    }

    /**
     * Gets the player's hitpoints.
     *
     * @return Returns the amount of hitpoints of the player.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Gets the players position.
     *
     * @return The position of the player.
     */
    public Vector getPosition() {
        return this.position;
    }

    /**
     * Sets the players position.
     *
     * @param position The player's position.
     */
    public void setPosition(Vector position) {
        this.position = position;
    }

    /**
     * Gets the players movement speed.
     *
     * @return The movementspeed of the player.
     */
    public float getMoveSpeed() {
        return this.moveSpeed;
    }

    /**
     * Sets the players movement speed.
     *
     * @param moveSpeed The movementspeed of the player.
     */
    public void setMoveSpeed(float moveSpeed) {
        if (moveSpeed < 0) {
            throw new IllegalArgumentException();
        }

        this.moveSpeed = moveSpeed;
    }

    /**
     * Gets the players rotation.
     *
     * @return The rotation of the player.
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Returns the room where the player is currently in.
     *
     * @return The room where the player is currently in.
     */
    public Room getRoom() {
        return currentRoom;
    }

    /**
     * Sets the room where the player is currently in.
     *
     * @param room where to player has to go
     */
    public void setRoom(Room room) {
        currentRoom = room;
    }

    /**
     * Sets the players rotation.
     *
     * @param rotation the rotation to set
     */
    public void setRotation(float rotation) {
        if (rotation < 0 || rotation > 359) {
            throw new IllegalArgumentException();
        }

        this.rotation = rotation;
    }

    /**
     * Gets the player's animation path.
     *
     * @return The animation/image of the player.
     */
    public String getAnimationPath() {
        return this.animationPath;
    }

    /**
     * Sets the player's animation path.
     *
     * @param animationPath The animation/image to set. When the string is empty
     * (excluding spaces), throws a new IllegalArgumentException.
     */
    public void setAnimationPath(String animationPath) {
        if (animationPath == null || animationPath.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.animationPath = animationPath;
    }

    /**
     * Sets the player's weapon.
     *
     * @param weapon The weapon which needs to be the currentweapon.
     */
    public void setWeapon(Weapon weapon) {
        this.currentWeapon = weapon;
        this.currentWeapon.setOwner(this);
    }

    /**
     * Get the player's weapon.
     *
     * @return Return the player's currentweapon.
     */
    public Weapon getWeapon() {
        return currentWeapon;
    }

    /**
     * Gets a boolean indicating whether the current player is dead.
     *
     * @return A boolean indicating whether the current player is dead.
     */
    public boolean isDead() {
        return this.isDead;
    }

    /**
     * Spawns a player in the given room. The player's hitpoints, position and
     * rotation are also set.
     */
    protected void spawn() {
        this.hitPoints = this.maxHitPoints;
        this.rotation = 0;

        this.isDead = false;
    }

    /**
     * Kills a player. This method should be called when the hitpoints of a
     * player are 0.
     */
    private void die() {
        this.isDead = true;

        this.currentRoom = null;
        this.position = null;
        this.rotation = 0;
    }

    /**
     * Move the player in the given direction.
     *
     * @param angle The direction the player needs to move in. Valid angle are
     * from 0 to 359;
     */
    public void move(float angle) {
        if (angle > 359 || angle < 0) {
            throw new IllegalArgumentException();
        }

        Vector newPosition = new Vector(0, 0);
        newPosition.setX((float) (position.getX() + Math.cos(angle) * moveSpeed));
        newPosition.setY((float) (position.getY() + Math.sin(angle) * moveSpeed));
        position = newPosition;

    }

    /**
     * Lets the player take damage from various sources.
     *
     * @param damage Amount of damage the player is hit with
     */
    public void takeDamage(int damage) {
        this.hitPoints -= damage;

        this.hitPoints = Math.max(this.hitPoints - damage, 0);

        if (this.hitPoints < 0) {
            this.die();
        }

    }

    /**
     * Calls the fire method of the current weapon which in turn creates a
     * projectile at the players position.
     *
     * @return The boolean which the currentWeapon.fire() method returns.
     */
    public boolean fire() {
        if (currentWeapon != null) {
            return currentWeapon.fire();
        } else {
            return false;
        }
    }

    /**
     * Reloads the current weapon.
     */
    public void reload() {
        AmmoType ammoType = this.currentWeapon.getWeaponType().getAmmoType();
        int ammoSupply = this.ammoReserves.get(ammoType);

        int ammoReloaded = this.currentWeapon.reload(ammoSupply);

        this.ammoReserves.replace(ammoType, ammoSupply - ammoReloaded);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + this.score;
        hash = 53 * hash + this.hitPoints;
        hash = 53 * hash + Objects.hashCode(this.position);
        hash = 53 * hash + Float.floatToIntBits(this.moveSpeed);
        hash = 53 * hash + Float.floatToIntBits(this.rotation);
        hash = 53 * hash + Objects.hashCode(this.ammoReserves);
        hash = 53 * hash + Objects.hashCode(this.currentWeapon);
        hash = 53 * hash + Objects.hashCode(this.currentRoom);
        hash = 53 * hash + this.animationPath.hashCode();
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
        final Player other = (Player) obj;
        if (this.score != other.score) {
            return false;
        }
        if (this.hitPoints != other.hitPoints) {
            return false;
        }
        if (Float.floatToIntBits(this.moveSpeed) != Float.floatToIntBits(other.moveSpeed)) {
            return false;
        }
        if (Float.floatToIntBits(this.rotation) != Float.floatToIntBits(other.rotation)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }

        if (!Objects.equals(this.currentRoom, other.getRoom())) {
            return false;
        }

        if (!this.animationPath.equals(other.animationPath)) {
            return false;
        }

        return Objects.equals(this.ammoReserves, other.ammoReserves);
    }

}
