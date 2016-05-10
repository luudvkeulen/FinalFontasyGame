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
 *   Joel Bleeker
 */
package com.ffxvi.game.models;

/**
 * This class contains all the properties of the AmmoType. This class contains
 * the damage, speed and animation of the ammo.
 */
public class AmmoType
{

    /**
     * The damage which this kind of AmmoType deals on a hit. So this decides
     * how much damage it deals on impact with a player or monster.
     */
    private final int damage;

    /**
     * The speed of this kind of AmmoType.
     */
    private final int speed;

    /**
     * The file path of the animation/sprite of this kind of AmmoType.
     */
    private final String animationPath;

    /**
     * Instance of the AmmoType class which is stored in a dictionary in the
     * GameManager class.
     *
     * @param damage The amount of damage this AmmoType deals.
     * @param speed The speed of this AmmoType. If speed is not a positive value, throw an IllegalArgumentException.
     * @param animationPath The file path of the animation of this AmmoType. can not be empty (excluding spaces).
     */
    public AmmoType(int damage, int speed, String animationPath)
    {
        if (speed <= 0)
        {
            throw new IllegalArgumentException("Speed must be a positive value.");
        }
        
        if (animationPath == null || animationPath.trim().isEmpty())
        {
            throw new IllegalArgumentException("The animation path can neither be null nor an empty string (excluding spaces).");
        }
        
        this.damage = damage;
        this.speed = speed;
        this.animationPath = animationPath;
    }

    /**
     * Returns the damage which this AmmoType deals.
     *
     * @return The damage of this AmmoType.
     */
    public int getDamage()
    {
        return damage;
    }

    /**
     * Returns the speed which this AmmoType has.
     *
     * @return The speed of this AmmoType.
     */
    public int getSpeed()
    {
        return speed;
    }

    /**
     * Returns the animation/image of this AmmoType.
     *
     * @return The image of this AmmoType.
     */
    public String getAnimationPath()
    {
        return this.animationPath;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + this.damage;
        hash = 97 * hash + this.speed;
        hash = 97 * hash + this.animationPath.hashCode();
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

        final AmmoType other = (AmmoType) obj;
        if (this.damage != other.damage)
        {
            return false;
        }

        if (!this.animationPath.equals(other.animationPath))
        {
            return false;
        }

        return this.speed == other.speed;
    }

}
