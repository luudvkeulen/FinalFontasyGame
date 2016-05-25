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

/**
 * Represents a map type.
 *
 */
public class MapType {

    /**
     * The id of this map type.
     */
    private final int id;

    /**
     * The name of this map type.
     */
    private final String name;

    /**
     * Initializes a new map type.
     *
     * @param id The id of the map type. When negative, an
     * IllegalArgumentException is thrown.
     * @param name The name of the map type. When an empty String (excluding
     * spaces), an IllegalArgumentException is thrown.
     */
    public MapType(int id, String name) {
        if (id < 0) {
            throw new IllegalArgumentException("ID can not be a negative value.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name can neither be null nor an empty String (excluding spaces).");
        }

        this.id = id;
        this.name = name;
    }

    /**
     * Gets the id of this maptype.
     *
     * @return The id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the name of this maptype.
     *
     * @return The name.
     */
    public String getName() {
        return this.name;
    }
}
