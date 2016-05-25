package com.ffxvi.game.logics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.entities.Direction;
import com.ffxvi.game.entities.Player;
import com.ffxvi.game.screens.MenuScreen;
import com.ffxvi.game.support.Vector;
import java.util.Observable;

public class InputManager extends Observable
{

    private static final float DEADZONE = 0.3f;
    private static final int LEFT_AXIS_X = 1;
    private static final int LEFT_AXIS_Y = 0;
    private static final int RIGHT_AXIS_X = 3;
    private static final int RIGHT_AXIS_Y = 2;
    private static final int TRIGGERS = 4;

    private final MainClass game;
    private final Player mainPlayer;

    public InputManager(MainClass game, Player mainPlayer)
    {
        this.game = game;
        this.mainPlayer = mainPlayer;
    }

    public void checkInput()
    {
        if (!this.checkControllerInput())
        {
            this.checkKeyboardInput();
        }
        
        this.checkGUIInput();
    }
    
    public void checkGUIInput()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            this.setChanged();
            this.notifyObservers();
        }
    }

    public boolean checkKeyboardInput()
    {
        boolean returnValue = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            game.setScreen(new MenuScreen());
            return returnValue;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
                || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))
        {
            mainPlayer.setSprint(true);
            returnValue = true;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
                && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))
        {
            mainPlayer.setSprint(false);
            returnValue = true;
        }

        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT)
                || Gdx.input.isKeyPressed(Input.Keys.A);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                || Gdx.input.isKeyPressed(Input.Keys.D);

        if (leftPressed)
        {
            if (rightPressed)
            {
                mainPlayer.setIdle();
            }
            else
            {
                mainPlayer.setDirection(Direction.LEFT);
                returnValue = true;
            }
        }
        else if (rightPressed)
        {
            mainPlayer.setDirection(Direction.RIGHT);
            returnValue = true;
        }

        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP)
                || Gdx.input.isKeyPressed(Input.Keys.W);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN)
                || Gdx.input.isKeyPressed(Input.Keys.S);

        if (upPressed)
        {
            if (downPressed)
            {
                // To prevent setting the animation to idle when both up and down are pressed, but not both left and right
                if (!(leftPressed || rightPressed))
                {
                    mainPlayer.setIdle();
                }
            }
            else
            {
                mainPlayer.setDirection(Direction.UP);
                returnValue = true;
            }
        }
        else if (downPressed)
        {
            mainPlayer.setDirection(Direction.DOWN);
            returnValue = true;
        }

        if (!leftPressed && !rightPressed && !upPressed && !downPressed)
        {
            mainPlayer.setIdle();
            returnValue = true;
        }

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY() + 50;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            this.mainPlayer.setAimDirection(new Vector((float) mouseX, (float) mouseY));
            mainPlayer.fire();
            returnValue = true;
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
        {
            mainPlayer.slash();
            returnValue = true;
        }

        return returnValue;
    }

    private boolean checkControllerInput()
    {
        Boolean returnValue = false;

        if (Controllers.getControllers().size > 0)
        {
            Controller controller = Controllers.getControllers().first();

            float x = controller.getAxis(LEFT_AXIS_X);
            float y = controller.getAxis(LEFT_AXIS_Y);

            if (x < -DEADZONE)
            {
                mainPlayer.setDirection(Direction.LEFT);
                returnValue = true;
            }
            else if (x > DEADZONE)
            {
                mainPlayer.setDirection(Direction.RIGHT);
                returnValue = true;
            }

            if (y < -DEADZONE)
            {
                mainPlayer.setDirection(Direction.UP);
                returnValue = true;
            }
            else if (y > DEADZONE)
            {
                mainPlayer.setDirection(Direction.DOWN);
                returnValue = true;
            }
            else if (!(x > DEADZONE || y < -DEADZONE || y > DEADZONE || x < -DEADZONE))
            {
                mainPlayer.setIdle();

            }

            x = controller.getAxis(RIGHT_AXIS_X);
            y = controller.getAxis(RIGHT_AXIS_Y);

            if (y > DEADZONE || y < -DEADZONE || x > DEADZONE || x < -DEADZONE)
            {
                this.mainPlayer.setAimDirection(x, y);
                this.mainPlayer.fire();
                returnValue = true;
            }

            float shouldShoot = controller.getAxis(TRIGGERS);

            this.mainPlayer.setSprint(false);

            if (shouldShoot < -DEADZONE)
            {
                this.mainPlayer.setSprint(true);
                returnValue = true;
            }
            else if (shouldShoot > DEADZONE)
            {
                this.mainPlayer.slash();
                returnValue = true;
            }
        }

        return returnValue;
    }
}
