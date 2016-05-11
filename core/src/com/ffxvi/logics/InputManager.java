package com.ffxvi.logics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.entities.Player;
import com.ffxvi.game.screens.MenuScreen;

public class InputManager {
	private final MainClass game;
	private final Player mainPlayer;
	
	public InputManager(MainClass game, Player mainPlayer) {
		this.game = game;
		this.mainPlayer = mainPlayer;
	}
	
	public void checkInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new MenuScreen(game));
			return;
		}

		/*if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			//switchCharacter(PlayerCharacter.SKELETON_HOODED_DAGGER);
			//setCurrentWalkingAnimation(direction);
			return;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			//switchCharacter(PlayerCharacter.SKELETON_HOODED_BOW);
			//setCurrentWalkingAnimation(direction);
			return;
		}*/

		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
				|| Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
			//sprint(true);
		}
		if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
				&& !Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
			//sprint(false);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			//DirectionInput(Direction.LEFT);

		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			//DirectionInput(Direction.RIGHT);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			//DirectionInput(Direction.UP);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
			//DirectionInput(Direction.DOWN);
		}

		if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)
				&& !Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)
				&& !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)
				&& !Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S)) {
			//currentAnim = new Animation(0, currentAnim.getKeyFrame(0));
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			// Reset the shoot delay
			//System.nanoTime() - this.shootStart > this.shootCooldown * 1000000000
			//fire();
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			//setCurrentSlashingAnimation(direction);
		}
	}
}
