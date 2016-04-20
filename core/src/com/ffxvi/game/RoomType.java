/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gebruiker
 */
public class RoomType
{
	private int roomSizeX;
	private int roomSizeY;
	private ArrayList<RoomObject> roomObjects;
	
	public RoomType(int roomSizeX, int roomSizeY, ArrayList<RoomObject> list) {
		this.roomSizeX = roomSizeX;
		this.roomSizeY = roomSizeY;
		this.roomObjects = list;
	}
	
	public int getRoomSizeX() {
		return this.roomSizeX;
	}
	
	public int getRoomSizeY() {
		return this.roomSizeY;
	}
	
	public List<RoomObject> getRoomObjects() {
		return this.roomObjects;
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
