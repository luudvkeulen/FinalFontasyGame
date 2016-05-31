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
package com.ffxvi.game.logics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.entities.Direction;
import com.ffxvi.game.entities.Player;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.screens.MenuScreen;
import com.ffxvi.game.support.Vector;
import java.util.Observable;

/**
 * Class which is responsible for the registration of keyboard, mouse and
 * controller input.
 *
 */
public class InputManager extends Observable {

    /**
     * This float determines how much a analog stick needs to move before the
     * input is used
     */
    private static final float DEADZONE = 0.3f;
    /**
     * The int that the library needs to identify which axis it is
     */
    private static final int LEFT_AXIS_X = 1;
    /**
     * The int that the library needs to identify which axis it is
     */
    private static final int LEFT_AXIS_Y = 0;
    /**
     * The int that the library needs to identify which axis it is
     */
    private static final int RIGHT_AXIS_X = 3;
    /**
     * The int that the library needs to identify which axis it is
     */
    private static final int RIGHT_AXIS_Y = 2;
    /**
     * The amount of triggers on the controller
     */
    private static final int TRIGGERS = 4;

    /**
     * The gameController
     */
    private final MainClass game;
    /**
     * The player thats is controlled by this instance of the game
     */
    private final Player mainPlayer;

    /**
     * The Constructor which is used to create an inputManager
     *
     * @param mainPlayer The player which needs to be moved by this inputManager.
     */
    public InputManager(Player mainPlayer) {
        
        if (mainPlayer == null)
        {
            throw new IllegalArgumentException("MainPlayer can not be null.");
        }
        
        this.game = MainClass.getInstance();
        this.mainPlayer = mainPlayer;
    }

    /**
     * This method checks if the controller sends input by calling
     * checkControllerInput. If that is not the case the checkKeyboardInput()
     * method is called. This makes sure there cant be controller input when
     * there is keyboard input and vice versa
     */
    public void checkInput() {
        if (!this.checkControllerInput()) {
            this.checkKeyboardInput();
        }

        this.checkHUDInput();
    }

    /**
     * Checks if the enter key is pressed. which in turn notifies the observers
     */
    public void checkHUDInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            this.setChanged();
            this.notifyObservers();
        }
    }

    /**
     * This method checks if certain keys are pressed and calls the necessary
     * methods in the mainPlayer or game keys that are checked: ESCAPE
     * SHIFT_LEFT LEFT RIGHT UP DOWN A S D W (mouse) buttons that are checked
     * LEFT RIGHT
     *
     * @return boolean If there has been keyboard input this tick, this will
     * return true
     */
    public boolean checkKeyboardInput() {
        boolean returnValue = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getScreen().dispose();
            game.setScreen(new MenuScreen());
            return returnValue;
        }
        

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
                || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            mainPlayer.setSprint(true);
            returnValue = true;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
                && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            mainPlayer.setSprint(false);
            returnValue = true;
        }

        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT)
                || Gdx.input.isKeyPressed(Input.Keys.A);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                || Gdx.input.isKeyPressed(Input.Keys.D);

        if (leftPressed) {
            if (rightPressed) {
                // to prevent setting the animation to right when right is pressed while left is held
                mainPlayer.setIdle();
            } else {
                mainPlayer.setDirection(Direction.LEFT);
                returnValue = true;
            }
        } else if (rightPressed) {
            mainPlayer.setDirection(Direction.RIGHT);
            returnValue = true;
        }

        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP)
                || Gdx.input.isKeyPressed(Input.Keys.W);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN)
                || Gdx.input.isKeyPressed(Input.Keys.S);

        if (upPressed) {
            if (downPressed) {
                // To prevent setting the animation to idle when both up and down are pressed, but not both left and right
                if (!(leftPressed || rightPressed)) {
                    mainPlayer.setIdle();
                }
            } else {
                mainPlayer.setDirection(Direction.UP);
                returnValue = true;
            }
        } else if (downPressed) {
            mainPlayer.setDirection(Direction.DOWN);
            returnValue = true;
        }

        if (!leftPressed && !rightPressed && !upPressed && !downPressed) {
            mainPlayer.setIdle();
            returnValue = true;
        }

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY() + 50;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            this.mainPlayer.setAimDirection(new Vector((float) mouseX, (float) mouseY));
            mainPlayer.fire();
            returnValue = true;
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            mainPlayer.slash();
            returnValue = true;
        }
        
        

        return returnValue;
    }

    /**
     * This method checks if certain controller buttons are pressed and calls
     * the necessary methods in the mainPlayer or game buttons that are checked
     * --none Axis that are checked LEFT_AXIS_X LEFT_AXIS_Y RIGHT_AXIS_X
     * RIGHT_AXIS_Y TRIGGERS
     *
     * @return boolean If there has been controller input this tick, this will
     * return true
     */
    private boolean checkControllerInput() {
        Boolean returnValue = false;

        if (Controllers.getControllers().size > 0) {
            Controller controller = Controllers.getControllers().first();

            float x = controller.getAxis(LEFT_AXIS_X);
            float y = controller.getAxis(LEFT_AXIS_Y);

            if (x < -DEADZONE) {
                mainPlayer.setDirection(Direction.LEFT);
                returnValue = true;
            } else if (x > DEADZONE) {
                mainPlayer.setDirection(Direction.RIGHT);
                returnValue = true;
            }

            if (y < -DEADZONE) {
                mainPlayer.setDirection(Direction.UP);
                returnValue = true;
            } else if (y > DEADZONE) {
                mainPlayer.setDirection(Direction.DOWN);
                returnValue = true;
            } else if (!(x > DEADZONE || y < -DEADZONE || y > DEADZONE || x < -DEADZONE)) {
                mainPlayer.setIdle();

            }

            x = controller.getAxis(RIGHT_AXIS_X);
            y = controller.getAxis(RIGHT_AXIS_Y);

            if (y > DEADZONE || y < -DEADZONE || x > DEADZONE || x < -DEADZONE) {
                this.mainPlayer.setAimDirection(x, y);
                this.mainPlayer.fire();
                returnValue = true;
            }

            float shouldShoot = controller.getAxis(TRIGGERS);

            this.mainPlayer.setSprint(false);

            if (shouldShoot < -DEADZONE) {
                this.mainPlayer.setSprint(true);
                returnValue = true;
            } else if (shouldShoot > DEADZONE) {
                this.mainPlayer.slash();
                returnValue = true;
            }
        }

        return returnValue;
    }
}
