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
 * This class contains all properties for RoomObjectType. It contains density
 * and animation.
 */
public class RoomObjectType
{

    /**
     * This float represents how much RoomObjectType slows the player down. If
     * it's 1.0 the player cannot walk through it. If it is 0, the player can
     * walk at full movement speed through this object. This float should always
     * be between 0 and 1.0
     */
    private float density;

    /**
     * The file path of the animation/sprite of this RoomObjectType.
     */
    private String animationPath;

    /**
     * Instance of the RoomObjectType class which is stored in a dictionary in
     * the GameManager class.
     *
     * @param density The density of the roomObjectType. When it is not in the
     * range 0-1, throws an IllegalArgumentException.
     * @param animationPath The file path of the animation of the
     * roomObjectType. When an empty string (excluding spaces), throws an
     * IllegalArgumentException.
     */
    public RoomObjectType(float density, String animationPath)
    {
        if (density < 0 || density > 1)
        {
            throw new IllegalArgumentException();
        }

        if (animationPath == null)
        {
            throw new IllegalArgumentException();
        }

        this.density = density;
        this.animationPath = animationPath;
    }

    /**
     * Gets the density.
     *
     * @return The density of this RoomObjectType.
     */
    public float getDensity()
    {
        return density;
    }

    /**
     * Gets the animation.
     *
     * @return The file path of the animation of this RoomObjectType.
     */
    public String getAnimation()
    {
        return this.animationPath;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + Float.floatToIntBits(this.density);
        hash = 37 * hash + this.animationPath.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final RoomObjectType other = (RoomObjectType) obj;

        if (!this.animationPath.equals(other.animationPath))
        {
            return false;
        }

        return Float.floatToIntBits(this.density) == Float.floatToIntBits(other.density);
    }

}
