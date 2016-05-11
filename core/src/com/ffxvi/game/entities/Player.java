package com.ffxvi.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ffxvi.game.MainClass;
import static com.ffxvi.game.MainClass.camera;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.screens.MenuScreen;

public class Player {

	protected float x;
	protected float y;

	protected static final float WALK_SPEED = 5;
	protected static final float RUN_SPEED = 8;

	public Direction direction;
	private float animSpeed;
	private float stateTime;
	private Animation currentAnim,
			walkUp, walkLeft, walkDown, walkRight,
			slashUp, slashLeft, slashDown, slashRight;

	private MainClass game;

	private int shootDelay = 0;
	private final int maxShootDelay = 10;
	private int gridsize = 64;
	private int modifiedgridsizex;
	private int modifiedgridsizey;
	private float speed;
	private final String playerName;
	private final GameScreen screen;

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public String getName() {
		return this.playerName;
	}

	public Animation getCurrentAnim() {
		return currentAnim;
	}

	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 *
	 * @param game
	 * @param character
	 * @param playerName
	 * @param walkingAnim Filename of the walking animations, located in assets
	 */
	public Player(MainClass game, PlayerCharacter character, String playerName, GameScreen screen) {
		animSpeed = 0.05f;
		stateTime = 0f;
		this.screen = screen;

		switch (character) {
			case SKELETON_DAGGER:
				setAnimations("Units/Skeleton_Dagger/Walk.png", "Units/Skeleton_Dagger/Slash.png");
				break;
			case SKELETON_HOODED_BOW:
				setAnimations("Units/Skeleton_Hooded_Bow/Walk.png", "Units/Skeleton_Hooded_Bow/Slash.png");
				break;
			case SKELETON_HOODED_DAGGER:
				setAnimations("Units/Skeleton_Hooded_Dagger/Walk.png", "Units/Skeleton_Hooded_Dagger/Slash.png");
				break;
		}
		currentAnim = new Animation(0, walkDown.getKeyFrame(0));
		this.game = game;
		direction = Direction.RIGHT;
		this.speed = Player.WALK_SPEED;
		this.playerName = playerName;
		//Sound sound = Gdx.audio.newSound(Gdx.files.internal("extra.mp3"));
		// sound.play();
		modifiedgridsizex = gridsize - 32;
		modifiedgridsizey = gridsize - 16;

	}

	/**
	 *
	 * @param walkingAnim Filename of the walking animations, located in assets
	 */
	private void setAnimations(String walkingAnim, String slashingAnim) {
		if (!walkingAnim.equals("")) {
			setWalkingAnimations(walkingAnim);
		}
		if (!slashingAnim.equals("")) {
			setSlashingAnimations(slashingAnim);
		}
		currentAnim = null;
	}

	private void switchCharacter(PlayerCharacter character) {
		switch (character) {
			case SKELETON_DAGGER:
				setAnimations("Units/Skeleton_Dagger/Walk.png", "Units/Skeleton_Dagger/Slash.png");
				break;
			case SKELETON_HOODED_BOW:
				setAnimations("Units/Skeleton_Hooded_Bow/Walk.png", "");
				break;
			case SKELETON_HOODED_DAGGER:
				setAnimations("Units/Skeleton_Hooded_Dagger/Walk.png", "Units/Skeleton_Hooded_Dagger/Slash.png");
				break;
		}
	}

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = currentAnim.getKeyFrame(stateTime, true);
		batch.draw(currentFrame, x, y, gridsize, gridsize);
	}

	private boolean checkCollision(Rectangle rec, MapObjects objects, MapObjects wallobjects) {
		for (RectangleMapObject mapObject : objects.getByType(RectangleMapObject.class)) {
			Rectangle rectangleMapObject = mapObject.getRectangle();
			if (rec.overlaps(rectangleMapObject)) {
				return true;
			}
		}

		for (RectangleMapObject mapObject : wallobjects.getByType(RectangleMapObject.class)) {
			Rectangle rectangleMapObject = mapObject.getRectangle();
			if (rec.overlaps(rectangleMapObject)) {
				return true;
			}
		}

		return false;
	}
	
	private void checkDoorCollision(Rectangle rec, MapObjects objects) {
		for(RectangleMapObject mapObject : objects.getByType(RectangleMapObject.class)) {
			Rectangle rectangleMapObject = mapObject.getRectangle();
			if(rec.overlaps(rectangleMapObject)) {
				screen.setLevel(mapObject.getName());
			}
		}
	}

	public void update() {

		checkInputs();
		this.shootDelay--;

	}

	private void checkInputs() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			game.setScreen(new MenuScreen(game));
			return;
		}

		if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			switchCharacter(PlayerCharacter.SKELETON_HOODED_DAGGER);
			setCurrentWalkingAnimation(direction);
			return;
		}

		if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
			switchCharacter(PlayerCharacter.SKELETON_HOODED_BOW);
			setCurrentWalkingAnimation(direction);
			return;
		}

		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)
				|| Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
			sprint(true);
		}
		if (!Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)
				&& !Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
			sprint(false);
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			DirectionInput(Direction.LEFT);

		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			DirectionInput(Direction.RIGHT);
		}

		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
			DirectionInput(Direction.UP);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
			DirectionInput(Direction.DOWN);
		}

		if (!Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT)
				&& !Gdx.input.isKeyPressed(Keys.UP) && !Gdx.input.isKeyPressed(Keys.DOWN)
				&& !Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)
				&& !Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S)) {
			currentAnim = new Animation(0, currentAnim.getKeyFrame(0));
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)
				&& this.shootDelay <= 0) {
			// Reset the shoot delay
			fire();
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			setCurrentSlashingAnimation(direction);
		}
	}

	private void fire() {
		this.shootDelay = this.maxShootDelay;

		// Create a vector3 with the player's coordinates
		Vector3 playerPos = new Vector3(this.x, this.y, 0);

		// Project the position to the camera
		camera.project(playerPos);

		// Create a vector2 with the mouse coordinates
		Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

		// Set the speed of the bullet
		float speed2 = 15.0f;

		// Calculate the direction of the bullet using arctan
		float dir = (float) Math.toDegrees(Math.atan2(mousePos.y - playerPos.y, mousePos.x - playerPos.x));

		// Create a bullet inside the player with the direction and speed
		GameScreen.addBullet(new Bullet(this.x + (gridsize / 2), this.y + (gridsize / 2), dir, speed2));
	}

	private void DirectionInput(Direction direction) {
		this.direction = direction;
		setCurrentWalkingAnimation(direction);
		if (!checkCollision(movingCollisionBox(direction), GameScreen.wallObjects, GameScreen.objects)) {
			Move(direction);
		}
		
		checkDoorCollision(movingCollisionBox(direction), GameScreen.doors);
	}

	private void Move(Direction direction) {
		switch (direction) {
			case LEFT:
				x -= this.speed;
				break;
			case RIGHT:
				x += this.speed;
				break;
			case UP:
				y += this.speed;
				break;
			case DOWN:
				y -= this.speed;
				break;
		}
	}

	private void sprint(boolean toggle) {
		if (toggle) {
			this.speed = Player.RUN_SPEED;
		} else {
			this.speed = Player.WALK_SPEED;
		}
	}

	private void setCurrentWalkingAnimation(Direction direction) {
		switch (direction) {
			case LEFT:
				currentAnim = walkLeft;
				break;
			case RIGHT:
				currentAnim = walkRight;
				break;
			case UP:
				currentAnim = walkUp;
				break;
			case DOWN:
				currentAnim = walkDown;
				break;
		}
	}

	private void setCurrentSlashingAnimation(Direction direction) {
		switch (direction) {
			case LEFT:
				currentAnim = slashLeft;
				break;
			case RIGHT:
				currentAnim = slashRight;
				break;
			case UP:
				currentAnim = slashUp;
				break;
			case DOWN:
				currentAnim = slashDown;
				break;
		}
	}

	/**
	 *
	 * @param walkingAnim Filename of the walking animations, located in assets
	 */
	private void setWalkingAnimations(String walkingAnim) {
		float walkSpeed = animSpeed * 1f;
		int walkSheet_Cols = 9;
		int walkSheet_Rows = 4;
		Texture walkSheet = new Texture(Gdx.files.internal(walkingAnim));
		TextureRegion[][] anims = TextureRegion.split(walkSheet, walkSheet.getWidth() / walkSheet_Cols, walkSheet.getHeight() / walkSheet_Rows);
		walkUp = new Animation(walkSpeed, anims[0]);
		walkLeft = new Animation(walkSpeed, anims[1]);
		walkDown = new Animation(walkSpeed, anims[2]);
		walkRight = new Animation(walkSpeed, anims[3]);
	}

	private void setSlashingAnimations(String slashingAnim) {
		float slashSpeed = animSpeed * 0.5f;
		int slashSheet_Cols = 6;
		int slashSheet_Rows = 4;
		Texture slashSheet = new Texture(Gdx.files.internal(slashingAnim));
		TextureRegion[][] anims = TextureRegion.split(slashSheet, slashSheet.getWidth() / slashSheet_Cols, slashSheet.getHeight() / slashSheet_Rows);
		slashUp = new Animation(slashSpeed, anims[0]);
		slashLeft = new Animation(slashSpeed, anims[1]);
		slashDown = new Animation(slashSpeed, anims[2]);
		slashRight = new Animation(slashSpeed, anims[3]);
	}

	private Rectangle movingCollisionBox(Direction direction) {
		Rectangle rec = null;
		switch (direction) {
			case LEFT:
				rec = new Rectangle(x + 16 - this.speed, y, modifiedgridsizex, modifiedgridsizey);
				break;
			case RIGHT:
				rec = new Rectangle(x + 16 + this.speed, y, modifiedgridsizex, modifiedgridsizey);
				break;
			case UP:
				rec = new Rectangle(x + 16, y + this.speed, modifiedgridsizex, modifiedgridsizey);
				break;
			case DOWN:
				rec = new Rectangle(x + 16, y - this.speed, modifiedgridsizex, modifiedgridsizey);
				break;
		}
		return rec;
	}
}
