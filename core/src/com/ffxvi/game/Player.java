/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import java.util.HashMap;

/**
 *
 * @author Gebruiker
 */
public class Player
{
	private String name;
	private int score;
	private int hitPoints;
	private Vector2 position;
	private float moveSpeed;
	private float rotation;
	private Animation animation;
	private HashMap<AmmoType, Integer> ammoReserves;
	private Weapon currentWeapon;
	private Room currentRoom;
	
	public Player(String name, int hitPoints, Vector2 position, Animation animation, Room startingRoom) {
		this.name = name;
		this.hitPoints = hitPoints;
		this.position = position;
		this.animation = animation;
		this.currentRoom = startingRoom;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getHitPoints() {
		return this.hitPoints;
	}
	
	public Vector2 getPosition() {
		return this.position;
	}
	
	public void SetPosition(Vector2 position) {
		this.position = position;
	}
	
	public float getMoveSpeed() {
		return this.moveSpeed;
	}
	
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	
	public float getRotation() {
		return this.rotation;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public Room getRoom() {
		return this.currentRoom;
	}
	
	public void setRoom(Room room) {
		this.currentRoom = room;
	}
	
	public Animation getAnimation() {
		return this.animation;
	}
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	public Weapon getWeapon() {
		return this.currentWeapon;
	}
	
	public void setWeapon(Weapon weapon) {
		this.currentWeapon = weapon;
	}
	
	public void move(float angle) {
		// move logic here
	}
	
	public void takeDamage(int damage) {
		// damage logic here
	}
	
	public boolean fire() {
		// fire logic here
		return false;
	}
	
	public void reload() {
		// reload logic here
	}
	
	@Override
	public int hashCode() {
		// hashcode logic here
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		// equals logic here
		return false;
	}
}
