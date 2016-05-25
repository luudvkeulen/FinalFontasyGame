/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import static com.ffxvi.game.entities.Direction.*;
import static com.ffxvi.game.entities.PlayerAnimation.*;
import java.io.Serializable;

/**
 *
 * @author Robin
 */
public class SimplePlayer implements Serializable {
	
	protected final String playerName;
	protected int hitPoints;
	protected int score;

	protected int roomId;
	protected float x;
	protected float y;
	protected float speed; 
	
	protected Direction direction;
	protected PlayerCharacter skin;
	protected PlayerAnimation animation;
	public float stateTime;
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public PlayerCharacter getSkin() {
		return skin;
	}
	
	public PlayerAnimation getAnimation() {
		return animation;
	}
	
	public int getRoomId() {
		return roomId;
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
		
		this.stateTime = 0;
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
		
		this.stateTime = 0;
	}
}
