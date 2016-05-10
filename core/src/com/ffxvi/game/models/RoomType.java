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

import java.util.*;

/**
 * This class contains the properties for a RoomType. It contains the X size, Y
 * size and all the RoomObjects.
 */
public class RoomType {

    /**
     * The X roomsize of this RoomType.
     */
    private int roomSizeX;

    /**
     * The Y roomsize of this RoomType.
     */
    private int roomSizeY;

    /**
     * An ArrayList filled with RoomObjects. This list represents all the
     * RoomObjects which are in this RoomType.
     */
    private ArrayList<RoomObject> roomObjects;

    /**
     * Instance of the RoomType class which is stored in a dictionary in the
     * GameManager class.
     *
     * @param roomSizeX The X roomsize of this RoomType. Must be larger than 1.
     * @param roomSizeY The Y roomsize of this RoomType. Must be larget than 1.
     * @param list An ArrayList filled with RoomObjects.
     */
    public RoomType(int roomSizeX, int roomSizeY, ArrayList<RoomObject> list) {
        
        if (roomSizeX <= 0)
        {
            throw new IllegalArgumentException();
        }
        
        if (roomSizeY <= 0)
        {
            throw new IllegalArgumentException();
        }
        
        if (list == null)
        {
            throw new IllegalArgumentException();
        }
        
        this.roomSizeX = roomSizeX;
        this.roomSizeY = roomSizeY;
        
        this.roomObjects = list;
    }

    /**
     * Get the X roomsize.
     *
     * @return The width of this RoomType.
     */
    public int getRoomSizeX() {
        return roomSizeX;
    }

    /**
     * Get the Y roomsize.
     *
     * @return the height of this RoomType.
     */
    public int getRoomSizeY() {
        return roomSizeY;
    }

    /**
     * Get all the RoomObjects which are in this RoomType.
     *
     * @return A list filled with RoomObjects.
     */
    public List<RoomObject> getRoomObjects() {
        return roomObjects;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.roomSizeX;
        hash = 37 * hash + this.roomSizeY;
        hash = 37 * hash + Objects.hashCode(this.roomObjects);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoomType other = (RoomType) obj;
        if (this.roomSizeX != other.roomSizeX) {
            return false;
        }
        if (this.roomSizeY != other.roomSizeY) {
            return false;
        }
        return Objects.equals(this.roomObjects, other.roomObjects);
    }

}
