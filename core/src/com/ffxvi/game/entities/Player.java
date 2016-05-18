package com.ffxvi.game.entities;

import com.badlogic.gdx.Gdx;
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
import com.ffxvi.game.models.AmmoType;
import com.ffxvi.game.models.Projectile;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.support.Utils;
import com.ffxvi.game.support.Vector;<<<<<<< HEAD
public class Player extends SimplePlayer {

    protected static final float WALK_SPEED = 5;
    protected static final float RUN_SPEED = 8;


    private float aimDirection;
    private float animSpeed;
    private float stateTime;
    private Animation currentAnim,
            walkUp, walkLeft, walkDown, walkRight,
            slashUp, slashLeft, slashDown, slashRight;

    private int shootDelay = 0;

    private int modifiedgridsizex;
    private int modifiedgridsizey;

    private final GameScreen screen;
    private float shootCooldown = 0.5f;
    private long shootStart = 0;
    private Vector position;
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    

    public Animation getCurrentAnim() {
        return currentAnim;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Sets the players position.
     *
     * @param position The player's position.
     */
    public void setPosition(Vector position) {
        this.position = position;
    }
    
    /**
     * Gets the players movement speed.
     *
     * @return The movementspeed of the player.
     */
    public float getMoveSpeed() {
        return speed;
    }
    
    /**
     * Sets the players movement speed.
     *
     * @param moveSpeed The movementspeed of the player.
     */
    public void setMoveSpeed(float moveSpeed) {
        if (moveSpeed < 0) {
            throw new IllegalArgumentException();
        }

        this.speed = moveSpeed;
    }

    public void setSprint(boolean isSprinting) {
        if (isSprinting) {
            this.speed = Player.RUN_SPEED;
        } else {
            this.speed = Player.WALK_SPEED;
        }


    public boolean setAimDirection(Vector position)
    {
        // Create a vector3 with the player's coordinates
        Vector3 playerPos = new Vector3(this.x, this.y, 0);

        // Project the position to the camera
        camera.project(playerPos);
        float mouseX = position.getX();
        float mouseY = position.getY();
        Vector mousePos = new Vector(mouseX, mouseY);

        // Calculate the direction of the bullet using arctan
        float dir = (float) Math.toDegrees(Math.atan2(mousePos.getY() - playerPos.y - (this.currentAnim.getKeyFrame(stateTime).getRegionHeight()) - (gridsize / 3), mousePos.getX() - playerPos.x - (this.currentAnim.getKeyFrame(stateTime).getRegionWidth() / 2)));

        if (this.aimDirection == dir)
        {
            return false;
        }

        if (dir < 0)
        {
            dir += 360;
        }

        this.aimDirection = dir;

        return true;
    }

    public void setAimDirection(float controllerInputX, float controllerInputY)
    {
        if (controllerInputX == 0 && controllerInputY == 0)
        {
            throw new IllegalArgumentException("inputX and inputY can't both be 0.");
        }

        float angle = (float) Math.toDegrees(Math.atan2(controllerInputX, controllerInputY));

        angle -= 90;

        if (angle < 0)
        {
            angle += 360;
        }

        this.aimDirection = angle;
    }

    /**
     *
     * @param game
     * @param character
     * @param playerName
     * @param walkingAnim Filename of the walking animations, located in assets
     */
    public Player(PlayerCharacter character, String playerName, Vector position, GameScreen screen) {
        super(playerName, position.getX(), position.getY());
        this.position = position;
        animSpeed = 0.05f;
        stateTime = 0f;
        this.screen = screen;
        switchCharacter(character);
        currentAnim = new Animation(0, walkDown.getKeyFrame(0));
        direction = Direction.RIGHT;
        this.speed = Player.WALK_SPEED;

        //Sound sound = Gdx.audio.newSound(Gdx.files.internal("extra.mp3"));
        // sound.play();
        int gridsize = Utils.gridSize;
        modifiedgridsizex = gridsize - 32;
        modifiedgridsizey = gridsize - 16;
		this.aimDirection = 0;

    }
    
    public String getName() {
        return playerName;
    }
    /**
     * Gets the player score.
     *
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Sets the players score.
     *
     * @param score The score to set.
     */
    public void setScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException();
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
        return this.position;
    }

    private boolean canFire()
    {
        return System.nanoTime() - this.shootStart > this.shootCooldown * 1000000000;
    }

    /**
     *
     * @param walkingAnim Filename of the walking animations, located in assets
     */
    private void setAnimations(String walkingAnim, String slashingAnim)
    {
        if (!walkingAnim.equals(""))
        {
            setWalkingAnimations(walkingAnim);
        }
        if (!slashingAnim.equals(""))
        {
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
        batch.draw(currentFrame, x, y, Utils.gridSize, Utils.gridSize);
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

    private void checkDoorCollision(Rectangle rec, MapObjects objects, Direction dir) {
        for (RectangleMapObject mapObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangleMapObject = mapObject.getRectangle();
            if (rec.overlaps(rectangleMapObject)) {
                screen.setLevel(mapObject.getName(), dir);
            }
        }
    }

    public void update()
    {
        this.shootDelay--;
    }

    public void fire()
    {
        if (canFire())
        {
            // Reset the shoot delay
            this.shootStart = System.nanoTime();

            // Create a bullet inside the player with the direction and speed
            GameScreen.addProjectile(new Projectile(new Projectile(new Vector(this.x + (modifiedgridsizex), this.y + (modifiedgridsizey / 2)), dir, new AmmoType(10, 30, "animationstring")));
        }
    }

    public void slash()
    {
        setCurrentSlashingAnimation(direction);
    }

    public void DirectionInput(Direction direction)
    {
        this.direction = direction;
        setCurrentWalkingAnimation(direction);
        if (!checkCollision(movingCollisionBox(direction), GameScreen.wallObjects, GameScreen.objects))
        {
            Move(direction);
        }

        checkDoorCollision(movingCollisionBox(direction), GameScreen.doors, direction);
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

    private void setCurrentWalkingAnimation(Direction direction) {
        animation = PlayerAnimation.WALKING;
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
        animation = PlayerAnimation.SLASHING;
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


    public void setIdle() {
        animation = PlayerAnimation.IDLE;
        currentAnim = new Animation(0, currentAnim.getKeyFrame(0));
    }

    /**
     * This methods gets called once to load the animations
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

    /**
     * This methods gets called once to load the animations
     *
     * @param slashingAnim Filename of the walking animations, located in assets
     */
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


    private Rectangle movingCollisionBox(Direction direction)
    {
        Rectangle rec = null;
        switch (direction)
        {
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
