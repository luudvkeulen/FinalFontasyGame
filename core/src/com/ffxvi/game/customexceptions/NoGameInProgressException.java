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
 * Is thrown when there is an attempt to end the current game, while there is no
 * game currently in progress.
 */
public class NoGameInProgressException extends Exception {

    /**
     * Initializes a new NoGameInProgressException with no parameters.
     */
    public NoGameInProgressException() {
    }

    /**
     * Initializes a new NoGameInProgressException with a given message.
     *
     * @param message the message which is shown to the user to provide more
     * detailed information about the exception.
     */
    public NoGameInProgressException(String message) {
        super(message);
    }
}