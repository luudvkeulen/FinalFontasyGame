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
 * Represents a map of the room.
 */
public class Map {

    /**
     * The id of the map.
     */
    private final int id;

    /**
     * The tiled map design for this map.
     */
    private final TiledMap map;

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
     * Initializes a new map.
     *
     * @param fileName The filename of the map's design. When an empty String
     * (excluding spaces), throw an IllegalArgumentException.
     * @param id The id of the map. When negative, throw an
     * IllegalArgumentException.
     */
    public Map(String fileName, int id) {

        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("FileName can neither be null nor an empty String (excluding spaces).");
        }

        if (id < 0) {
            throw new IllegalArgumentException("ID can not be a negative value.");
        }

        this.id = id;
        this.map = new TmxMapLoader().load(fileName);

        this.wallObjects = this.map.getLayers().get("WallObjects").getObjects();
        this.objects = this.map.getLayers().get("Objects").getObjects();
        this.doors = this.map.getLayers().get("Door").getObjects();
    }

    /**
     * Gets the id of this map.
     *
     * @return The id of this map.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the tiled map of this map.
     *
     * @return The tiled map.
     */
    public TiledMap getMap() {
        return this.map;
    }

    /**
     * Gets the doors of this map.
     *
     * @return A collection of MapObjects which represent the doors of this map.
     */
    public MapObjects getDoors() {
        return doors;
    }

    /**
     * Gets the objects of this map.
     *
     * @return A collection of MapObjects which represent the normal objects of
     * this map.
     */
    public MapObjects getObjects() {
        return objects;
    }

    /**
     * Gets the walls of this map.
     *
     * @return A collection of MapObjects which represent the walls of this map.
     */
    public MapObjects getWallObjects() {
        return wallObjects;
    }
}
