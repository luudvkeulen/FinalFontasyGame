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
 * Is thrown when there is an attempt to start a new game, while a game is still
 * in progress.
 */
public class GameInProgressException extends Exception {

    /**
     * Initializes a new GameInProgressException with no parameters.
     */
    public GameInProgressException() {
    }

    /**
     * Initializes a new GameInProgressException with a given message.
     *
     * @param message the message which is shown to the user to provide more
     * detailed information about the exception.
     */
    public GameInProgressException(String message) {
        super(message);
    }
}
