/*
 * (C) Copyright 2016 - S33A
 * Final Fontasy XVI, Version 1.0.
 * 
 * Contributors:
 *   Pim Janissen
 *   Luud van Keulen
 *   Robin de Kort
 *   Koen Schilders
 *   Guido Thomasse
 *   Joel Verbeek
 */
package com.ffxvi.game.models;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Represents a tiledMap of the room.
 */
public class Map {

    /**
     * The id of the tiledMap.
     */
    private final int id;

    /**
     * The tiled tiledMap design for this tiledMap.
     */
    private final TiledMap tiledMap;

    /**
     * A collection of doors.
     */
    private final MapObjects doors;

    /**
     * A collection of objects.
     */
    private final MapObjects objects;

    /**
     * A collection of objects which represent walls.
     */
    private final MapObjects wallObjects;

    /**
     * Initializes a new tiledMap.
     *
     * @param fileName The filename of the tiledMap's design. When an empty String
 (excluding spaces), throw an IllegalArgumentException.
     * @param id The id of the tiledMap. When negative, throw an
 IllegalArgumentException.
     */
    public Map(String fileName, int id) {

        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("FileName can neither be null nor an empty String (excluding spaces).");
        }
        
        if (id < 0) {
            throw new IllegalArgumentException("ID can not be a negative value.");
        }

        this.id = id;
        this.tiledMap = new TmxMapLoader().load(fileName);

        this.wallObjects = this.tiledMap.getLayers().get("WallObjects").getObjects();
        this.objects = this.tiledMap.getLayers().get("Objects").getObjects();
        this.doors = this.tiledMap.getLayers().get("Door").getObjects();
    }

    /**
     * Gets the id of this tiledMap.
     *
     * @return The id of this tiledMap.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the tiled tiledMap of this tiledMap.
     *
     * @return The tiled tiledMap.
     */
    public TiledMap getMap() {
        return this.tiledMap;
    }

    /**
     * Gets the doors of this tiledMap.
     *
     * @return A collection of MapObjects which represent the doors of this tiledMap.
     */
    public MapObjects getDoors() {
        return doors;
    }

    /**
     * Gets the objects of this tiledMap.
     *
     * @return A collection of MapObjects which represent the normal objects of
 this tiledMap.
     */
    public MapObjects getObjects() {
        return objects;
    }

    /**
     * Gets the walls of this tiledMap.
     *
     * @return A collection of MapObjects which represent the walls of this tiledMap.
     */
    public MapObjects getWallObjects() {
        return wallObjects;
    }
}
