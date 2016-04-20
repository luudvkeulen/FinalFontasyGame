package com.ffxvi.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ffxvi.game.MainClass;
import static com.ffxvi.game.MainClass.camera;
import com.ffxvi.game.PlayerCharacter;
import static com.ffxvi.game.PlayerCharacter.SKELETON_DAGGER;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.screens.MenuScreen;

public class Player {
	protected float x;
	protected float y;

	protected float dx;
	protected float dy;

	protected float radians;
	protected static final float WALK_SPEED = 5;
	protected static final float RUN_SPEED = 8;
	protected float rotationSpeed;

	protected int width;
	protected int height;

	protected float[] shapex;
	protected float[] shapey;

	private Direction direction;
	private float animSpeed;
	private float stateTime;
	private Animation currentAnim,
			walkUp, walkLeft, walkDown, walkRight,
			slashUp, slashLeft, slashDown, slashRight;

	private MainClass game;

	private int shootDelay = 0;
	private final int maxShootDelay = 10;
	private int gridsize = 64;
	private float speed;
	private final String playerName;

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
	 * @param walkingAnim Filename of the walking animations, located in assets
	 */
	public Player(MainClass game, PlayerCharacter character, String playerName) {
		animSpeed = 0.05f;
		stateTime = 0f;

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
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("extra.mp3"));
		sound.play();
	}

	/**
	 *
	 * @param walkingAnim Filename of the walking animations, located in assets
	 */
	private void setAnimations(String walkingAnim, String slashingAnim) {
		setWalkingAnimations(walkingAnim);
		setSlashingAnimations(slashingAnim);
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

	/**
	 *
	 * @param x Move this player this much horizontally
	 * @param y Move this player this much vertically
	 */
	public void translate(int x, int y) {
		if (x == 0 && y == 0) {
			currentAnim = new Animation(0, currentAnim.getKeyFrame(0));
			return;
		}
		this.x += x;
		this.y += y;
		if (y == 0) {
			if (x > 0) {
				currentAnim = walkRight;
			} else {
				currentAnim = walkLeft;
			}
		} else if (y > 0) {
			currentAnim = walkUp;
		} else {
			currentAnim = walkDown;
		}
		//TODO
	}

	public void render(ShapeRenderer shape, OrthographicCamera camera) {
		shape.setProjectionMatrix(camera.combined);
		//shape.begin(ShapeType.Line);
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.WHITE);
		shape.box(x, y, 0, gridsize, gridsize, 0);
		shape.end();
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

	public void update() {
		int modifiedgridsizey = gridsize - 12;
		int modifiedgridsizex = gridsize - 32;
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			game.setScreen(new MenuScreen(game));
			return;
		}

		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)
				|| Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
			this.speed = Player.RUN_SPEED;
		}
		if (!Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)
				&& !Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
			this.speed = Player.WALK_SPEED;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			currentAnim = walkLeft;
			direction = Direction.LEFT;
			Rectangle rec = new Rectangle(x + 16 - this.speed, y, modifiedgridsizex, modifiedgridsizey);
			if(!checkCollision(rec, GameScreen.wallObjects, GameScreen.objects)) {
				x -= this.speed;
			}
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			currentAnim = walkRight;
			direction = Direction.RIGHT;
			Rectangle rec = new Rectangle(x + 16 + this.speed, y, modifiedgridsizex, modifiedgridsizey);
			if(!checkCollision(rec, GameScreen.wallObjects, GameScreen.objects)) {
				x += this.speed;
			}
		}

		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
			currentAnim = walkUp;
			direction = Direction.UP;
			Rectangle rec = new Rectangle(x+ 16, y + this.speed, modifiedgridsizex, modifiedgridsizey);
			if(!checkCollision(rec, GameScreen.wallObjects, GameScreen.objects)) {
				y += this.speed;
			}
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
			currentAnim = walkDown;
			direction = Direction.DOWN;
			Rectangle rec = new Rectangle(x+ 16, y - this.speed, modifiedgridsizex, modifiedgridsizey);
			if(!checkCollision(rec, GameScreen.wallObjects, GameScreen.objects)) {
				y -= this.speed;
			}
		}

		if (!Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT)
				&& !Gdx.input.isKeyPressed(Keys.UP) && !Gdx.input.isKeyPressed(Keys.DOWN)
				&& !Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)
				&& !Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S)) {
			currentAnim = new Animation(0, currentAnim.getKeyFrame(0));
		}

		this.shootDelay--;

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)
				&& this.shootDelay <= 0) {
			// Reset the shoot delay
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

		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
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
	}
}
