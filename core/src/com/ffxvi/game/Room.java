/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game;

import com.badlogic.gdx.math.Vector2;
import java.util.List;

/**
 *
 * @author Gebruiker
 */
public class Room
{
	private Vector2 position;
	private RoomType roomType;
	private List<RoomObject> roomObjects;
	private List<Player> players;
	private List<Projectile> projectiles;
	
	public Room(Vector2 position, RoomType roomType) {
		this.position = position;
		this.roomType = roomType;
	}
	
	public Vector2 getPosition() {
		return this.position;
	}
	
	public RoomType getRoomType() {
		return this.roomType;
	}
	
	public List<RoomObject> getRoomObjects() {
		return this.roomObjects;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public List<Projectile> getProjectiles() {
		return this.projectiles;
	}
	
	public boolean spawnProjectile(Projectile projectile) {
		return false;
	}
	
	public boolean spawnPlayer(Player player) {
		return false;
	}
	
	public boolean addRoomObject(RoomObject roomObject) {
		return false;
	}
	
	private boolean validPlacement(Vector2 position) {
		return false;
	}
	
	private boolean isPositionBlocked(Vector2 position) {
		return false;
	}
	
	public Vector2 getSpawnPositionForPlayer() {
		return null;
	}
	
	public boolean doesPositonOverlapWith(Room room) {
		return false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		return false;
	}
}
