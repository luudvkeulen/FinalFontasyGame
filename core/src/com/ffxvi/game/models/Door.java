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

import com.ffxvi.game.customexceptions.DoorInSameRoomException;
import com.ffxvi.game.support.Vector;
import java.util.Objects;


/**
 * This class contains all the properties for a door. This door is connected
 * with another door.
 */
public class Door extends RoomObject {

    /**
     * The door which is connected to this door.
     */
    private Door connectedDoor;

    /**
     * The room which this door is located in.
     */
    private Room room;

    /**
     * Gets the door which is connected to this door.
     *
     * @return The door which is connected to the door.
     */
    public Door getConnectedDoor() {
        return connectedDoor;
    }

    /**
     * Gets the room which this door is located in.
     *
     * @return The room which this door is located in.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the connected door.
     *
     * @param door the door to connect this door to
     * @throws DoorInSameRoomException The connected door may not be in the same
     * room as the original door.
     */
    public void setConnectedDoor(Door door) throws DoorInSameRoomException {
        if (door == null) {
            throw new IllegalArgumentException();
        }

        if (door.room.equals(this.room)) {
            throw new DoorInSameRoomException();
        }

        this.connectedDoor = door;
    }

    /**
     * Initializes a new Door instance.
     *
     * @param position the position of the door.
     * @param rotation the rotation of the door. If the rotation is not in the
     * range 0-359, throws an IllegalArgumentException.
     * @param room the room where the door is in.
     */
    public Door(Vector position, float rotation, Room room) {
        // TODO: roomObjectType should be GameManager.getInstance().getRoomObjectType for a door,
        // TODO: which currently does not yet exist
        super(new RoomObjectType(0.2f, "Placeholder"), position, rotation);

        if (room == null) {
            throw new IllegalArgumentException();
        }

        this.room = room;
    }

    /**
     * Opens this door and returns the door on the other side.
     *
     * @param player The player which opens the door.
     * @return The door which the player comes out of.
     */
    public boolean openDoor(Player player) {
        if (player == null) {
            throw new IllegalArgumentException();
        }

        if (this.connectedDoor == null) {
            return false;
        }

        player.setRoom(this.connectedDoor.room);
        player.setPosition(this.connectedDoor.getPosition());

        return true;
    }

    /**
     * Gets the hashcode of this object.
     *
     * @return An integer which contains the hashcode of this object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.connectedDoor);
        hash = 89 * hash + Objects.hashCode(this.room);
        return hash;
    }

    /**
     * Checks if the parameter and this object and the same object.
     *
     * @param obj The object to check.
     * @return Returns true if they are the same, false if not.
     */
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
        final Door other = (Door) obj;
        if (!Objects.equals(this.connectedDoor, other.connectedDoor)) {
            return false;
        }
        return Objects.equals(this.room, other.room);
    }

}
