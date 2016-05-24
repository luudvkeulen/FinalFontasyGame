package com.ffxvi.game.models;

public class MapType {
	private int id;
	private String name;
	
	public MapType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
}
