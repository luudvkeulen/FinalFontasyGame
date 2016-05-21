package com.ffxvi.game.models;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Map {
	private final int id;
	
	private final TiledMap map;
	
	private final MapObjects doors;
	private final MapObjects objects;
	private final MapObjects wallObjects;
	
	public Map(String fileName, int id) {
		this.id = id;
		
		this.map = new TmxMapLoader().load(fileName);

		this.wallObjects = this.map.getLayers().get("WallObjects").getObjects();
		this.objects = this.map.getLayers().get("Objects").getObjects();
		this.doors = this.map.getLayers().get("Door").getObjects();
	}
	
	public int getId() {
		return id;
	}

	public TiledMap getMap() {
		return map;
	}

	public MapObjects getDoors() {
		return doors;
	}

	public MapObjects getObjects() {
		return objects;
	}

	public MapObjects getWallObjects() {
		return wallObjects;
	}
}
