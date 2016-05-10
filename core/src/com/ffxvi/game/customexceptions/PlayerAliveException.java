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
 * Is thrown when there is an attempt to respawn a player, while the player is
 * still alive.
 */
public class PlayerAliveException extends Exception {

    /**
     * Initializes a new PlayerAliveException with no parameters.
     */
    public PlayerAliveException() {
    }

    /**
     * Initializes a new PlayerAliveException with a given message.
     *
     * @param message the message which is shown to the user to provide more
     * detailed information about the exception.
     */
    public PlayerAliveException(String message) {
        super(message);
    }
}
