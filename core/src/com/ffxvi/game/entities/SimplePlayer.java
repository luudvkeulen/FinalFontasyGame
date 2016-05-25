/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

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
	
	public String getName() {
		return playerName;
	}
	
	public int getHitPoints() {
		return hitPoints;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getSpeed() {
		return speed;
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
	
	public float getStateTime() {
		return stateTime;
	}
	
	public SimplePlayer(String playerName, float posX, float posY, int roomId, PlayerCharacter skin) {
		this.playerName = playerName;
		hitPoints = 100;
		score = 0;
		
		this.roomId = roomId;
		x = posX;
		y = posY;
		speed = 0.0f;
		
		direction = DOWN;
		this.skin = skin;
		animation = IDLE;
		this.stateTime = 0;
	}
	
	public SimplePlayer(Player player){
		playerName = player.playerName;
		hitPoints = player.hitPoints;
		score = player.score;
		
		roomId = player.roomId;
		x = player.x;
		y = player.y;
		speed = player.speed;
		
		direction = player.direction;
		skin = player.skin;
		animation = player.animation;
		this.stateTime = 0;
	}
}
