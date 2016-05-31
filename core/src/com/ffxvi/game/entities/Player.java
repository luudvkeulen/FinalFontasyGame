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
package com.ffxvi.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.support.Utils;
import com.ffxvi.game.support.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * Entity which represents a player.
 */
public class Player extends SimplePlayer implements Observable {
    
    protected static Texture WALKSHEET = null;
    
    protected static Texture SLASHSHEET = null;

    /**
     * The amount of coordinates a player moves per tick while walking.
     */
    protected static final float WALK_SPEED = 5f;

    /**
     * The amount of coordinates a player moves per tick while
     * running/sprinting.
     */
    protected static final float RUN_SPEED = 8f;

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
     * The current animation of the player.
     */
    private Animation currentAnimation;

    /**
     * An animation for walking in the up direction.
     */
    private Animation walkUp;

    /**
     * An animation for walking in the left direction.
     */
    private Animation walkLeft;

    /**
     * An animation for walking in the down direction.
     */
    private Animation walkDown;

    /**
     * An animation for walking in the right direction.
     */
    private Animation walkRight;

    /**
     * An animation for slashing in the up direction.
     */
    private Animation slashUp;

    /**
     * An animation for slashing in the left direction.
     */
    private Animation slashLeft;

    /**
     * An animation for slashing in the down direction.
     */
    private Animation slashDown;

    /**
     * An animation for slashing in the right direction.
     */
    private Animation slashRight;

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
     * Default constructor for Player.
     *
     * @param character The player's character.
     * @param playerName The name of this player. This can not be an empty
     * String (excluding spaces).
     * @param position The position of this player.
     * @param roomId The id of the room where the player is in.
     * @param screen the GameScreen that this Player is in. Used to create projectiles
     */
    public Player(PlayerCharacter character, String playerName, Vector position, GameScreen screen, int roomId) {
        super(playerName, position.getX(), position.getY(), roomId, character);

        if (character == null) {
            throw new IllegalArgumentException("Character can not be null.");
        }

        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Character can neither be null nor an empty String.");
        }
        
        if (screen == null) {
            throw new IllegalArgumentException("Screen can not be null.");
        }
        
        this.screen = screen;

        this.speed = Player.WALK_SPEED;

        this.aimDirection = 0f;
        this.animationSpeed = 0.05f;
        this.switchCharacter(super.skin);
        this.currentAnimation = new Animation(0, this.walkDown.getKeyFrame(0));

        int gridsize = Utils.GRIDSIZE;
        this.modifiedGridSizeX = gridsize - 32;
        this.modifiedGridSizeY = gridsize - 16;
    }

    /**
     * Special constructor for Player, only use this for playerdata received from
     * the server. 
     * 
     * @param screen the GameScreen that this Player is in. Used to create projectiles
     */
    public Player(GameScreen screen) {
        super("blank", 0, 0, 1, PlayerCharacter.SKELETON_DAGGER);

        this.screen = screen;
        
        this.aimDirection = 0f;
        this.animationSpeed = 0.05f;

        int gridsize = Utils.GRIDSIZE;
        this.modifiedGridSizeX = gridsize - 32;
        this.modifiedGridSizeY = gridsize - 16;
    }
    
    /**
     * Special method for setting all of this Player's data to the given SimplePlayer
     * data. Use this to prevent having to create a new Player object every time. 
     * 
     * @param player the SimplePlayer's data that is to replace this Player's data
     */
    public void setData(SimplePlayer player) {
        super.playerName = player.playerName;
        super.hitPoints = player.hitPoints;
        super.score = player.score;
        super.roomId = player.roomId;
        super.x = player.x;
        super.y = player.y;
        super.speed = player.speed;
        super.direction = player.direction;
        super.skin = player.skin;
        super.animation = player.animation;
        super.stateTime = player.stateTime;
        
        this.switchCharacter(super.skin);
        this.currentAnimation = new Animation(0, this.walkDown.getKeyFrame(0));
        switch (super.animation) {
            case IDLE :
                this.setIdle();
            case WALKING :
                this.setCurrentWalkingAnimation();
            case SLASHING :
                this.setCurrentSlashingAnimation();
            case SHOOTING :
//                TODO setCurrentShootingAnimation();
        }
    }

    /**
     * Gets the x position of this player.
     *
     * @return The x position of the player.
     */
    @Override
    public float getX() {
        return this.x;
    }

    /**
     * Gets the y position of this player.
     *
     * @return The y position of the player.
     */
    @Override
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
        MainClass.getInstance().camera.project(playerPosition);

        // Calculate the direction of the bullet using arctan
        float dir = (float) Math.toDegrees(Math.atan2(mousePosition.getY()
                - playerPosition.y - (this.currentAnimation.getKeyFrame(stateTime)
                .getRegionHeight()) - (Utils.GRIDSIZE / 3),
                mousePosition.getX() - playerPosition.x
                - (this.currentAnimation.getKeyFrame(stateTime)
                .getRegionWidth() / 2)));

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
        if (Float.compare(controllerInputX, 0f) == 0 && Float.compare(controllerInputY, 0f) == 0) {
            throw new IllegalArgumentException("inputX and inputY can't both be 0.");
        }

        float angle = (float) Math.toDegrees(Math.atan2(controllerInputX,
                controllerInputY));

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
     * Gets the name of this player.
     *
     * @return The name of the player.
     */
    @Override
    public String getName() {
        return this.playerName;
    }

    /**
     * Gets the player's score.
     *
     * @return The score of the player.
     */
    @Override
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
     * Receives damage which is subtracted from the player's hitpoints.
     * 
     * @param amount The amount of damage.
     */
    public void receiveDamage(int amount) {
        this.hitPoints -= amount;
        
        if (this.hitPoints <= 0) {
            // Set the amount of hitPoints to 0 to prevent negative values
            // being shown
            this.hitPoints = 0;
            this.die();
            this.screen.sendChatMessage("[SERVER]", this.playerName.toLowerCase() + " HAS DIED");
        }
        
        // Update the health labels
        this.screen.updatePlayerHealthLabels(this.hitPoints);
    }
    
    
    /**
     * Gets the collision rectangle of this player.
     * 
     * @return The collision rectangle of this player.
     */
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.modifiedGridSizeX, this.modifiedGridSizeY);
    }
    
    /**
     * Lets the player die.
     */
    public void die() {
        hitPoints = 100;
        // Set dialog message
        screen.Respawn();
        // Wait for X time
        
        // Respawn player 
        // Hide dialog
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

        this.setWalkingAnimations(walkingAnimation);
        this.setSlashingAnimations(slashingAnimation);

        this.currentAnimation = null;
    }

    /**
     * Switches the character of this player to the given character.
     *
     * @param character The character to switch to.
     */
    private void switchCharacter(PlayerCharacter character) {
        switch (character) {
            default:
            case SKELETON_DAGGER:
                setAnimations("Units/Skeleton_Dagger/Walk.png",
                        "Units/Skeleton_Dagger/Slash.png");
                break;
            case SKELETON_HOODED_BOW:
                setAnimations("Units/Skeleton_Hooded_Bow/Walk.png",
                        "");
                break;
            case SKELETON_HOODED_DAGGER:
                setAnimations("Units/Skeleton_Hooded_Dagger/Walk.png",
                        "Units/Skeleton_Hooded_Dagger/Slash.png");
                break;
        }
    }

    /**
     * Method that is performed each tick and focuses on drawing.
     *
     * @param batch The Sprite Batch to use.
     */
    public void render(SpriteBatch batch) {
        this.stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = this.currentAnimation.getKeyFrame(this.stateTime, true);
        batch.draw(currentFrame, this.x, this.y, Utils.GRIDSIZE, Utils.GRIDSIZE);
    }

    /**
     * Checks the given rec for collision with the given (wall)objects.
     *
     * @param rec The rectangle to check.
     * @param objects The objects to make sure are not in the rectangle.
     * @param wallobjects The wall objects to make sure are not in the
     * rectangle.
     * @return true if collision, false if not.
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
                int mapId = Integer.parseInt(mapObject.getName().replaceAll("\\D", ""));
                
                try {
                    this.screen.setLevel(mapId, this.direction);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            screen.addProjectile(new Projectile(new Vector(this.x
                    + (modifiedGridSizeX), this.y + (modifiedGridSizeY / 2)),
                    30, this.aimDirection, this.roomId, this.playerName, this.screen), false);
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
            default:
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
        screen.client.sendPlayer(new SimplePlayer(this));
//        System.out.println("Sending SimplePlayer data");
    }

    /**
     * Sets the current walking animation.
     */
    private void setCurrentWalkingAnimation() {
        this.animation = PlayerAnimation.WALKING;

        switch (this.direction) {
            default:
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
            default:
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
        int walkSheetCols = 9;
        int walkSheetRows = 4;

        if(WALKSHEET == null) {
            WALKSHEET = new Texture(Gdx.files.internal(walkingAnimation));
        }
        
        TextureRegion[][] anims = TextureRegion.split(WALKSHEET, WALKSHEET.getWidth()
                / walkSheetCols, WALKSHEET.getHeight() / walkSheetRows);
        
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
        int slashSheetCols = 6;
        int slashSheetRows = 4;

        if (SLASHSHEET == null) {
            SLASHSHEET = new Texture(Gdx.files.internal(slashingAnimation));
        }
        TextureRegion[][] anims = TextureRegion.split(SLASHSHEET, SLASHSHEET.getWidth()
                / slashSheetCols, SLASHSHEET.getHeight() / slashSheetRows);
        

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
            default:
            case LEFT:
                rec = new Rectangle(x + 16 - this.speed, y,
                        this.modifiedGridSizeX, this.modifiedGridSizeY);
                break;
            case RIGHT:
                rec = new Rectangle(x + 16 + this.speed, y,
                        this.modifiedGridSizeX, this.modifiedGridSizeY);
                break;
            case UP:
                rec = new Rectangle(x + 16, y + this.speed,
                        this.modifiedGridSizeX, this.modifiedGridSizeY);
                break;
            case DOWN:
                rec = new Rectangle(x + 16, y - this.speed,
                        this.modifiedGridSizeX, this.modifiedGridSizeY);
                break;
        }

        return rec;
    }
    
    /**
     * Check if player gets slashed.
     */
    public void checkGetSlashed() {   
        for (SimplePlayer p : this.screen.getMultiplayers()) { 
            if(p.animation == PlayerAnimation.SLASHING &&
                    !p.getName().equals(this.playerName)) {
                
                Player player = new Player(p, this.screen);
                Circle cEnemy = new Circle();
                cEnemy.x = p.getX();
                cEnemy.y = p.getY();
                cEnemy.radius = 50.0f;

                Circle cThis = new Circle();
                cThis.x = this.getX();
                cThis.y = this.getY();
                cThis.radius = 50.0f;

                if (cEnemy.overlaps(cThis)) {
                    this.hitPoints =- 10;
                }
            }
        }
    }
    
    public void destroy() {
        this.currentAnimation = null;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
