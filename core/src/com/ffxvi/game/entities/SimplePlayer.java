/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static com.ffxvi.game.MainClass.camera;
import static com.ffxvi.game.entities.Direction.*;
import static com.ffxvi.game.entities.PlayerAnimation.*;
import com.ffxvi.game.support.Utils;
import java.io.Serializable;

/**
 *
 * @author Robin
 */
public class SimplePlayer implements Serializable {
	
	protected final String playerName;
	protected int hitPoints;
	protected int score;

	protected float x;
	protected float y;
	protected float speed; 
	
	protected Direction direction;
	protected PlayerCharacter skin;
	protected PlayerAnimation animation;
	
	protected int roomId;
	
	
	
	protected float stateTime;
	private Animation currentAnimation;
	
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public PlayerCharacter getSkin() {
		return skin;
	}
	
	public PlayerAnimation getAnimation() {
		return animation;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public SimplePlayer(String playerName, float posX, float posY, int roomId) {
		this.playerName = playerName;
		hitPoints = 100;
		score = 0;
		x = posX;
		y = posY;
		speed = 0.0f;
		direction = DOWN;
		animation = IDLE;
		this.roomId = roomId;
	}
	
	public SimplePlayer(Player player){
		playerName = player.playerName;
		hitPoints = player.hitPoints;
		score = player.score;
		x = player.x;
		y = player.y;
		speed = player.speed;
		direction = player.direction;
		skin = player.skin;
		animation = player.animation;
		
		currentAnimation = player.getCurrentAnimation();
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
}
