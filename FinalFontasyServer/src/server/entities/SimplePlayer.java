/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.entities;

import static server.entities.Direction.*;
import static server.entities.PlayerAnimation.*;

/**
 *
 * @author Robin
 */
public class SimplePlayer {
	
	protected final String playerName;
	protected int hitPoints;
	protected int score;

	protected float x;
	protected float y;
	protected float speed; 
	protected Direction direction;
	
	protected PlayerAnimation animation;
	private int modifiedgridsizex;
	private int modifiedgridsizey;
	
	public SimplePlayer(String playerName, float posX, float posY, int gridSize) {
		this.playerName = playerName;
		hitPoints = 100;
		score = 0;
		x = posX;
		y = posY;
		speed = 0.0f;
		direction = DOWN;
		animation = IDLE;
		modifiedgridsizex = gridSize - 32;
		modifiedgridsizey = gridSize - 16;
	}
}
