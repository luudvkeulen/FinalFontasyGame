package com.ffxvi.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import static com.ffxvi.game.MainClass.camera;
import com.ffxvi.game.models.AmmoType;
import com.ffxvi.game.models.Projectile;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.support.Utils;
import com.ffxvi.game.support.Vector;

public class Player extends SimplePlayer {

	/**
	 * The amount of coordinates a player moves per tick while walking.
	 */
	protected static final float WALK_SPEED = 5;

	/**
	 * The amount of coordinates a player moves per tick while
	 * running/sprinting.
	 */
	protected static final float RUN_SPEED = 8;

	/**
	 * The cooldown between firing.
	 */
	private static final float SHOOTCOOLDOWN = 0.5f;

	/**
	 * The direction where the player is aiming his/her weapon in degrees.
	 */
	private float aimDirection;

	/**
	 * The speed at which the animation runs.
	 */
	private final float animationSpeed;

	/**
	 * The time the animation has currently been running.
	 */
	private float stateTime;

	/**
	 * The animations of a player.
	 */
	private Animation currentAnimation,
			walkUp, walkLeft, walkDown, walkRight,
			slashUp, slashLeft, slashDown, slashRight;

	/**
	 * The grid size of the player in width.
	 */
	private final int modifiedGridSizeX;

	/**
	 * The grid size of the player in height.
	 */
	private final int modifiedGridSizeY;

	/**
	 * The game screen.
	 */
	private final GameScreen screen;

	/**
	 * The time before a next shot can be fired.
	 */
	private long shootStart;

	/**
	 * Gets the x position of this player.
	 *
	 * @return The x position of the player.
	 */
	public float getX() {
		return this.x;
	}

	/**
	 * Gets the y position of this player.
	 *
	 * @return The y position of the player.
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * Gets the current animation of this player.
	 *
	 * @return The animation of the player.
	 */
	public Animation getCurrentAnimation() {
		return this.currentAnimation;
	}

	/**
	 * Sets the position of this player.
	 *
	 * @param x The new x position of this player.
	 * @param y The new y position of this player.
	 */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the players x and y to the given position.
	 *
	 * @param position The player's position.
	 */
	public void setPosition(Vector position) {
		if (position == null) {
			throw new IllegalArgumentException("Position can not be null.");
		}

		this.x = position.getX();
		this.y = position.getY();
	}

	/**
	 * Gets the players movement speed.
	 *
	 * @return The movementspeed of the player.
	 */
	public float getMoveSpeed() {
		return this.speed;
	}

	/**
	 * Sets the players movement speed.
	 *
	 * @param moveSpeed The movement speed of the player. When negative, throws
	 * an IllegalArgumentException.
	 */
	public void setMoveSpeed(float moveSpeed) {
		if (moveSpeed < 0) {
			throw new IllegalArgumentException();
		}

		this.speed = moveSpeed;
	}

	/**
	 * Sets whether the player is sprinting.
	 *
	 * @param isSprinting A boolean indicating whether the player is sprinting.
	 */
	public void setSprint(boolean isSprinting) {
		if (isSprinting) {
			this.speed = Player.RUN_SPEED;
		} else {
			this.speed = Player.WALK_SPEED;
		}
	}

	/**
	 * Sets the aim direction of this player by the given coördinates of the
	 * mouse.
	 *
	 * @param mousePosition The position of the mouse, relative to the player's
	 * position.
	 */
	public void setAimDirection(Vector mousePosition) {
		if (mousePosition == null) {
			throw new IllegalArgumentException("Mouse position can not be null.");
		}

		// Create a vector3 with the player's coordinates
		Vector3 playerPosition = new Vector3(this.x, this.y, 0);

		// Project the position to the camera
		camera.project(playerPosition);

		// Calculate the direction of the bullet using arctan
		float dir = (float) Math.toDegrees(Math.atan2(mousePosition.getY() - playerPosition.y - (this.currentAnimation.getKeyFrame(stateTime).getRegionHeight()) - (Utils.gridSize / 3), mousePosition.getX() - playerPosition.x - (this.currentAnimation.getKeyFrame(stateTime).getRegionWidth() / 2)));

		if (dir < 0) {
			dir += 360;
		}

		this.aimDirection = dir;
	}

	/**
	 * Sets the aim direction of this player by the given axis input from the
	 * controller.
	 *
	 * @param controllerInputX The value of the x-axis.
	 * @param controllerInputY The value of the y-axis.
	 */
	public void setAimDirection(float controllerInputX, float controllerInputY) {
		if (controllerInputX == 0 && controllerInputY == 0) {
			throw new IllegalArgumentException("inputX and inputY can't both be 0.");
		}

		float angle = (float) Math.toDegrees(Math.atan2(controllerInputX, controllerInputY));

		angle -= 90;

		if (angle < 0) {
			angle += 360;
		}

		this.aimDirection = angle;
	}
	
	public void setRoomId(int id) {
		roomId = id;
	}

	/**
	 * Initializes a player.
	 *
	 * @param character The player's character.
	 * @param playerName The name of this player. This can not be an empty
	 * String (excluding spaces).
	 * @param position The position of this player.
	 * @param screen The gameScreen which is used.
	 */
	public Player(PlayerCharacter character, String playerName, Vector position, GameScreen screen, int roomId) {
		super(playerName, position.getX(), position.getY(), roomId);

		if (character == null) {
			throw new IllegalArgumentException("Character can not be null.");
		}

		if (playerName == null || playerName.trim().isEmpty()) {
			throw new IllegalArgumentException("Character can neither be null nor an empty String.");
		}

		if (position == null) {
			throw new IllegalArgumentException("Position can not be null.");
		}

		if (screen == null) {
			throw new IllegalArgumentException("Screen can not be null.");
		}

		this.x = position.getX();
		this.y = position.getY();
		this.screen = screen;

		this.aimDirection = 0;
		this.animationSpeed = 0.05f;
		this.stateTime = 0f;
		this.switchCharacter(character);
		this.currentAnimation = new Animation(0, this.walkDown.getKeyFrame(0));

		int gridsize = Utils.gridSize;
		this.modifiedGridSizeX = gridsize - 32;
		this.modifiedGridSizeY = gridsize - 16;

		this.speed = Player.WALK_SPEED;
		this.direction = Direction.RIGHT;
	}

	/**
	 * Gets the name of this player.
	 *
	 * @return The name of the player.
	 */
	public String getName() {
		return this.playerName;
	}

	/**
	 * Gets the player's score.
	 *
	 * @return The score of the player.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the player's score.
	 *
	 * @param score The score to set. When smaller than 0, throws an
	 * IllegalArgumentException.
	 */
	public void setScore(int score) {
		if (score < 0) {
			throw new IllegalArgumentException("The score can not be a negative value.");
		}

		this.score = score;

	}

	/**
	 * Gets the player's hitpoints.
	 *
	 * @return Returns the amount of hitpoints of the player.
	 */
	public int getHitPoints() {
		return this.hitPoints;
	}

	/**
	 * Gets the players position.
	 *
	 * @return The position of the player.
	 */
	public Vector getPosition() {
		return new Vector(this.x, this.y);
	}

	/**
	 * A boolean indicating whether the player can fire, based on whether the
	 * cooldown since the last shot has passed.
	 *
	 * @return A boolean indicating whether the playre can fire.
	 */
	private boolean canFire() {
		return System.nanoTime() - this.shootStart > SHOOTCOOLDOWN * 1000000000;
	}

	/**
	 * Sets the animations of the walking and slashing animations.
	 *
	 * @param walkingAnimation File name of the walking animations, located in
	 * assets.
	 * @param slashingAnimation File name of the slashing animations, located in
	 * assets.
	 */
	private void setAnimations(String walkingAnimation, String slashingAnimation) {
		if (walkingAnimation == null || walkingAnimation.trim().isEmpty()) {
			throw new IllegalArgumentException("Walking animation can not be null.");
		}

		if (slashingAnimation == null || slashingAnimation.trim().isEmpty()) {
			throw new IllegalArgumentException("Slashing animation can not be null.");
		}

		setWalkingAnimations(walkingAnimation);
		setSlashingAnimations(slashingAnimation);

		this.currentAnimation = null;
	}

	/**
	 * Switches the character of this player to the given character.
	 *
	 * @param character The character to switch to.
	 */
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

	/**
	 * Method that is performed each tick and focusses on drawing.
	 *
	 * @param batch The Sprite Batch to use.
	 */
	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		this.stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = this.currentAnimation.getKeyFrame(this.stateTime, true);
		batch.draw(currentFrame, this.x, this.y, Utils.gridSize, Utils.gridSize);
	}

	/**
	 * Checks the given rec for collision withthe given (wall)objects.
	 *
	 * @param rec The rectangle to check.
	 * @param objects The objects to make sure are not in the rectangle.
	 * @param wallobjects The wall objects to make sure are not in the
	 * rectangle.
	 * @return
	 */
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

	/**
	 * Checks if the given rectangle collides with a door.
	 *
	 * @param rec The rectangle to check for collision.
	 * @param objects The objects (doors) of which should be looked within the
	 * rectangle.
	 */
	private void checkDoorCollision(Rectangle rec, MapObjects objects) {
		for (RectangleMapObject mapObject : objects.getByType(RectangleMapObject.class)) {
			Rectangle rectangleMapObject = mapObject.getRectangle();
			if (rec.overlaps(rectangleMapObject)) {
				//int mapId = Integer.parseInt(mapObject.getName().replaceAll("\\D", ""));
				//this.screen.setLevel(mapObject.getName() + ".tmx", mapId, direction);
				this.screen.setLevel(Integer.parseInt(mapObject.getName()), direction);
			}
		}
	}

	/**
	 * Fires a new projectile at the aim direction, given the player can fire.
	 */
	public void fire() {
		if (this.canFire()) {
			// Reset the shoot delay
			this.shootStart = System.nanoTime();

			// Create a bullet inside the player with the direction and speed
			GameScreen.addProjectile(new Projectile(new Vector(this.x + (modifiedGridSizeX), this.y + (modifiedGridSizeY / 2)), 30, this.aimDirection, this.roomId, this.playerName));
		}
	}

	/**
	 * Slashes in the given direction, given the player can slash.
	 */
	public void slash() {
		this.setCurrentSlashingAnimation();
	}

	/**
	 * Sets the direction to the given direction.
	 *
	 * @param direction The new direction.
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;

		this.setCurrentWalkingAnimation();

		if (!this.checkCollision(this.getCollisionBox(), GameScreen.getCurrentMap().getWallObjects(), GameScreen.getCurrentMap().getObjects())) {
			this.move();
		}

		this.checkDoorCollision(this.getCollisionBox(), GameScreen.getCurrentMap().getDoors());
	}

	/**
	 * Moves in the current direction.
	 */
	private void move() {
		switch (this.direction) {
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

	/**
	 * Sets the current walking animation.
	 */
	private void setCurrentWalkingAnimation() {
		this.animation = PlayerAnimation.WALKING;

		switch (this.direction) {
			case LEFT:
				this.currentAnimation = this.walkLeft;
				break;
			case RIGHT:
				this.currentAnimation = this.walkRight;
				break;
			case UP:
				this.currentAnimation = this.walkUp;
				break;
			case DOWN:
				this.currentAnimation = this.walkDown;
				break;
		}
	}

	/**
	 * Sets the animation for slashing in this player's direction.
	 */
	private void setCurrentSlashingAnimation() {
		this.animation = PlayerAnimation.SLASHING;

		switch (this.direction) {
			case LEFT:
				this.currentAnimation = this.slashLeft;
				break;
			case RIGHT:
				this.currentAnimation = this.slashRight;
				break;
			case UP:
				this.currentAnimation = this.slashUp;
				break;
			case DOWN:
				this.currentAnimation = this.slashDown;
				break;
		}
	}

	/**
	 * Sets the player's animation to idle.
	 */
	public void setIdle() {
		this.animation = PlayerAnimation.IDLE;
		this.currentAnimation = new Animation(0, this.currentAnimation.getKeyFrame(0));
	}

	/**
	 * This methods gets called once to load the animations
	 *
	 * @param walkingAnimation Filename of the walking animations, located in
	 * assets.
	 */
	private void setWalkingAnimations(String walkingAnimation) {
		float walkSpeed = animationSpeed * 1f;
		int walkSheet_Cols = 9;
		int walkSheet_Rows = 4;

		Texture walkSheet = new Texture(Gdx.files.internal(walkingAnimation));
		TextureRegion[][] anims = TextureRegion.split(walkSheet, walkSheet.getWidth() / walkSheet_Cols, walkSheet.getHeight() / walkSheet_Rows);

		this.walkUp = new Animation(walkSpeed, anims[0]);
		this.walkLeft = new Animation(walkSpeed, anims[1]);
		this.walkDown = new Animation(walkSpeed, anims[2]);
		this.walkRight = new Animation(walkSpeed, anims[3]);
	}

	/**
	 * This method gets called once to load the animations.
	 *
	 * @param slashingAnimation Filename of the walking animations, located in
	 * assets.
	 */
	private void setSlashingAnimations(String slashingAnimation) {
		float slashSpeed = this.animationSpeed * 0.5f;
		int slashSheet_Cols = 6;
		int slashSheet_Rows = 4;

		Texture slashSheet = new Texture(Gdx.files.internal(slashingAnimation));
		TextureRegion[][] anims = TextureRegion.split(slashSheet, slashSheet.getWidth() / slashSheet_Cols, slashSheet.getHeight() / slashSheet_Rows);

		this.slashUp = new Animation(slashSpeed, anims[0]);
		this.slashLeft = new Animation(slashSpeed, anims[1]);
		this.slashDown = new Animation(slashSpeed, anims[2]);
		this.slashRight = new Animation(slashSpeed, anims[3]);
	}

	/**
	 * Gets a collision box for the direction in which the player is moving.
	 *
	 * @return
	 */
	private Rectangle getCollisionBox() {
		Rectangle rec = null;

		switch (this.direction) {
			case LEFT:
				rec = new Rectangle(x + 16 - this.speed, y, this.modifiedGridSizeX, this.modifiedGridSizeY);
				break;
			case RIGHT:
				rec = new Rectangle(x + 16 + this.speed, y, this.modifiedGridSizeX, this.modifiedGridSizeY);
				break;
			case UP:
				rec = new Rectangle(x + 16, y + this.speed, this.modifiedGridSizeX, this.modifiedGridSizeY);
				break;
			case DOWN:
				rec = new Rectangle(x + 16, y - this.speed, this.modifiedGridSizeX, this.modifiedGridSizeY);
				break;
		}

		return rec;
	}

	public void update() {

	}
}
