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

	protected float x;
	protected float y;
	protected float speed; 
	
	protected Direction direction;
	protected PlayerCharacter skin;
	protected PlayerAnimation animation;
	
	protected int roomId;
	
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
	}
}
