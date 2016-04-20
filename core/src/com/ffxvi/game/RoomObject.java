/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Gebruiker
 */
public class RoomObject
{
	private RoomObjectType roomObjectType;
	private Vector2 position;
	private float rotation;
	
	public RoomObject(RoomObjectType roomObjectType, Vector2 position, float rotation) {
		this.roomObjectType = roomObjectType;
		this.position = position;
		this.rotation = rotation;
	}
	
	public RoomObjectType getRoomObjectType() {
		return this.roomObjectType;
	}
	
	public void setRoomObjectType(RoomObjectType roomObjectType) {
		this.roomObjectType = roomObjectType;
	}
	
	public Vector2 getPosition() {
		return this.position;
	}
	
	public float getRotation() {
		return this.rotation;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
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
