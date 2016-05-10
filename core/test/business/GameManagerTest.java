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
package business;

import customexceptions.GameInProgressException;
import customexceptions.NoGameInProgressException;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Pim Janissen
 */
public class GameManagerTest {

    /**
     * A game manager object used for testing.
     */
    private GameManager gameManager;

    /**
     * Initializes the game manager object before every test.
     */
    @Before
    public void initialize() {
        GameManager.resetInstance();
        this.gameManager = GameManager.getInstance();
    }

    /**
     * Tests if the getCurrentGame method returns a game object when a game has
     * been started by consecutively starting a game and calling the
     * getCurrentGame method to check if the given Game is not null.
     */
    @Test
    public void testGetCurrentGameGameStarted() {
        /**
         * Gets the current game.
         *
         * @return The current game. If no game is currently active, returns
         * null.
         */

        try {
            this.gameManager.startGame();
        } catch (GameInProgressException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assert.assertNotNull(this.gameManager.getCurrentGame());
    }

    /**
     * Tests if the getCurrentGame method returns a game object when a game has
     * not been started by calling the getCurrentGame method to check if the
     * given Game is null.
     */
    @Test
    public void testGetCurrentGameNoGameStarted() {
        /**
         * Gets the current game.
         *
         * @return The current game. If no game is currently active, returns
         * null.
         */

        Assert.assertNull(this.gameManager.getCurrentGame());
    }

    /**
     * Tests if the getCurrentGame method returns a game object when a game has
     * been started and then stopped by consecutively starting, stopping and
     * calling the getCurrentGame method to check if the given Game is null.
     */
    @Test
    public void testGetCurrentGameGameStopped() {
        /**
         * Gets the current game.
         *
         * @return The current game. If no game is currently active, returns
         * null.
         */

        try {

            this.gameManager.startGame();
            this.gameManager.endGame();

        } catch (GameInProgressException | NoGameInProgressException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assert.assertNull(this.gameManager.getCurrentGame());
    }

    /**
     * Tests if the startGame method properly starts a game by checking if the
     * getCurrentGame method doesn't return null.
     */
    @Test
    public void testStartGame() {
        /**
         * Starts a new game. A new game object is created and replaces the
         * current value of the currentGame field.
         *
         * @throws GameInProgressException When there currently is a game in
         * progress.
         */

        try {
            this.gameManager.startGame();
        } catch (GameInProgressException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assert.assertNotNull(this.gameManager.getCurrentGame());
    }

    /**
     * Tests if the startGame method properly starts a game by checking if the
     * getCurrentGame method doesn't return null.
     * @throws GameInProgressException
     */
    @Test(expected = GameInProgressException.class)
    public void testStartGameGameInProgress() throws GameInProgressException {
        /**
         * Starts a new game. A new game object is created and replaces the
         * current value of the currentGame field.
         *
         * @throws GameInProgressException When there currently is a game in
         * progress.
         */

        this.gameManager.startGame();
        this.gameManager.startGame();
    }

    /**
     * Tests if the startGame method properly starts a game by checking if the
     * getCurrentGame method doesn't return null.
     */
    @Test
    public void testStartGameGameStopped() {
        /**
         * Starts a new game. A new game object is created and replaces the
         * current value of the currentGame field.
         *
         * @throws GameInProgressException When there currently is a game in
         * progress.
         */

        try {
            this.gameManager.startGame();
            this.gameManager.endGame();
            this.gameManager.startGame();
        } catch (GameInProgressException | NoGameInProgressException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assert.assertNotNull(this.gameManager.getCurrentGame());
    }

    /**
     * Tests if the endGame method properly ends a game by checking if the
     * getCurrentGame method returns null.
     */
    @Test
    public void testEndGame() {
        /**
         * Ends the current game. A new game object is created and replaces the
         * current value of the currentGame field.
         *
         * @throws NoGameInProgressException When there is no game currently in
         * progress.
         */

        try {
            this.gameManager.startGame();
            this.gameManager.endGame();
        } catch (GameInProgressException | NoGameInProgressException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assert.assertNull(this.gameManager.getCurrentGame());
    }

    /**
     * Tests if the endGame method properly ends a game by checking if the
     * getCurrentGame method returns null.
     * @throws NoGameInProgressException
     */
    @Test(expected = NoGameInProgressException.class)
    public void testEndGameNoGameInProgress() throws NoGameInProgressException {
        /**
         * Ends the current game. A new game object is created and replaces the
         * current value of the currentGame field.
         *
         * @throws NoGameInProgressException When there is no game currently in
         * progress.
         */

        this.gameManager.endGame();
    }

    /**
     * Tests if the endGame method properly ends a game when a previous game was
     * closed and a new game was started by checking if the getCurrentGame
     * method returns null.
     */
    @Test
    public void testEndGameNewGameStarted() {
        /**
         * Ends the current game. A new game object is created and replaces the
         * current value of the currentGame field.
         *
         * @throws NoGameInProgressException When there is no game currently in
         * progress.
         */

        try {
            this.gameManager.startGame();
            this.gameManager.endGame();
            this.gameManager.startGame();
            this.gameManager.endGame();
        } catch (GameInProgressException | NoGameInProgressException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assert.assertNull(this.gameManager.getCurrentGame());
    }

    /**
     * Tests if the getInstance method returns an instance by checking if the
     * returned value is not null.
     */
    @Test
    public void testGetInstance() {
        /**
         * Gets the current instance of GameManager.
         *
         * @return the current instance of GameManager.
         */

        Assert.assertNotNull(GameManager.getInstance());
    }

    /**
     * Tests if the resetInstance method properly resets the instance by
     * starting a game in an instance, resetting it and checking if currentGame
     * returns null.
     */
    @Test
    public void testResetInstance() {
        /**
         * Resets this instance of the game manager to a new instance.
         */

        try {
            this.gameManager.startGame();
        } catch (GameInProgressException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        GameManager.resetInstance();
        Assert.assertNull(GameManager.getInstance().getCurrentGame());
    }
}
