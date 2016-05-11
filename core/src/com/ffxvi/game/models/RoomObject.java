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
import java.util.Objects;

/**
 * This class contains all the properties for RoomObject. It contains
 * RoomObjectType, position and rotation.
 */
public class RoomObject {

    /**
     * The type of the room object.
     */
    private RoomObjectType roomObjectType;

    /**
     * The position of the object within the room.
     */
    private Vector position;

    /**
     * The rotation of the room object.
     */
    private float rotation;

    /**
     * Gets the type of this room object.
     *
     * @return The type of the room object.
     */
    public RoomObjectType getRoomObjectType() {
        return roomObjectType;
    }

    /**
     * Sets the type of this room object.
     *
     * @param roomObjectType The new type of the room object.
     */
    public void setRoomObjectType(RoomObjectType roomObjectType) {
        if (roomObjectType == null) {
            throw new IllegalArgumentException();
        }

        this.roomObjectType = roomObjectType;
    }

    /**
     * Gets the position of this room object.
     *
     * @return The position of this room object.
     */
    public Vector getPosition() {
        return position;
    }

    /**
     * Gets the rotation of this room object.
     *
     * @return The rotation of the room object.
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation of this room object.
     *
     * @param rotation The new rotation of this room object.
     */
    public void setRotation(float rotation) {
        if (rotation > 359 || rotation < 0) {
            throw new IllegalArgumentException("Rotation must be a value in the range 0-359");
        }

        this.rotation = rotation;
    }

    /**
     * Initializes a new Room Object object.
     *
     * @param roomObjectType the type of the Room Object.
     * @param position the position of the Room Object. When not a positive
     * value, throw new IllegalArgumentException.
     * @param rotation the rotation of the Room Object. When not a value in the
     * range 0-359, throw an IllegalArgumentException.
     */
    public RoomObject(RoomObjectType roomObjectType, Vector position, float rotation) {
        if (roomObjectType == null)
        {
            throw new IllegalArgumentException("RoomObjectType can not be null.");
        }
        
        if (position == null || position.getX() < 0 || position.getY() < 0)
        {
            throw new IllegalArgumentException("Position must have both a positive x and y coördinate.");
        }
        
        if (rotation < 0 || rotation > 359)
        {
            throw new IllegalArgumentException("Rotation must be a value in the range 0-359");
        }
        
        this.roomObjectType = roomObjectType;
        this.position = position;
        this.rotation = rotation;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.roomObjectType);
        hash = 11 * hash + Objects.hashCode(this.position);
        hash = 11 * hash + Float.floatToIntBits(this.rotation);
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
        final RoomObject other = (RoomObject) obj;
        if (Float.floatToIntBits(this.rotation) != Float.floatToIntBits(other.rotation)) {
            return false;
        }
        if (!this.roomObjectType.equals(other.roomObjectType)) {
            return false;
        }

        return Objects.equals(this.position, other.position);
    }

}
