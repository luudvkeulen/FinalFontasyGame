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
package com.ffxvi.game.customexceptions;

/**
 * Is thrown when the connected door of a door object is in the same room.
 */
public class DoorInSameRoomException extends Exception {

    /**
     * Initializes a new DoorInSameRoomException with no parameters.
     */
    public DoorInSameRoomException() {
    }

    /**
     * Initializes a new DoorInSameRoomException with a given message.
     *
     * @param message the message which is shown to the user to provide more
     * detailed information about the exception.
     */
    public DoorInSameRoomException(String message) {
        super(message);
    }
}
