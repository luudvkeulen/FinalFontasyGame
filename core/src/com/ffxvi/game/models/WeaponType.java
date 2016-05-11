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

import java.util.Objects;

/**
 * This contains all the properties for the WeaponType class. It contains the
 * name, firerate, range, magazinsize, animation and the ammotype.
 */
public class WeaponType
{

    /**
     * The name of the WeaponType.
     */
    private String name;

    /**
     * The fire rate of the WeaponType. This value represents the delay between
     * two shots of this WeaponType.
     */
    private float fireRate;

    /**
     * The range of this WeaponType's shooting. All the projectiles fired from
     * this WeaponType should only be allowed to travel untill the range has
     * been ranged.
     */
    private float range;

    /**
     * The magazineSize of this WeaponType.
     */
    private int magazineSize;

    /**
     * The file path animation/image of this WeaponType.
     */
    private String animationPath;

    /**
     * The AmmoType which corresponds with this WeaponType.
     */
    private AmmoType ammoType;

    /**
     * Instance of the WeaponType class which is stored in a dictionary in the
     * GameManager class.
     *
     * @param name The name of the WeaponType. When an empty string (excluding
     * spaces) throws an IllegalArgumentException.
     * @param fireRate The fire rate of the WeaponType. When not a positive
     * value throws an IllegalArgumentException.
     * @param range The range of this WeaponType's shooting. When not a positive
     * value throws an IllegalArgumentException.
     * @param magazineSize The magazineSize of this WeaponType. When not a
     * positive value throws an IllegalArgumentException.
     * @param animationPath The animationPath to the animation of this WeaponType.
     * @param ammoType The AmmoType which corresponds with this WeaponType.
     */
    public WeaponType(String name, float fireRate, float range, int magazineSize, String animationPath, AmmoType ammoType)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("Name can not be null.");
        }

        if (name.trim().equals(""))
        {
            throw new IllegalArgumentException("Name can not be an empty string (excluding spaces).");
        }

        if (fireRate <= 0)
        {
            throw new IllegalArgumentException("Fire rate must be a positive value.");
        }

        if (range <= 0)
        {
            throw new IllegalArgumentException("Range must be a positive value.");
        }

        if (magazineSize <= 0)
        {
            throw new IllegalArgumentException("Magazine size must be a positive value.");
        }

        if (animationPath == null || animationPath.trim().isEmpty())
        {
            throw new IllegalArgumentException("AnimationPath can not be null or an empty string (excluding spaces).");
        }

        if (ammoType == null)
        {
            throw new IllegalArgumentException("AmmoType can not be null.");
        }

        this.name = name;
        this.fireRate = fireRate;
        this.range = range;
        this.magazineSize = magazineSize;
        this.animationPath = animationPath;
        this.ammoType = ammoType;
    }

    /**
     * Get the name of this WeaponType.
     *
     * @return The name of this WeaponType.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the firerate of this WeaponType.
     *
     * @return The firerate of this WeaponType.
     */
    public float getFireRate()
    {
        return fireRate;
    }

    /**
     * Get the range of this WeaponType.
     *
     * @return The range of this WeaponType.
     */
    public float getRange()
    {
        return range;
    }

    /**
     * Get the magazine size of this WeaponType.
     *
     * @return The magazineSize of this WeaponType.
     */
    public int getMagazineSize()
    {
        return magazineSize;
    }

    /**
     * Gets the animation/image of this WeaponType.
     *
     * @return The file path of the animation of this WeaponType.
     */
    public String getAnimationPath()
    {
        return this.animationPath;
    }

    /**
     * Gets the AmmoType of this WeaponType.
     *
     * @return The AmmoType of this WeaponType.
     */
    public AmmoType getAmmoType()
    {
        return ammoType;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Float.floatToIntBits(this.fireRate);
        hash = 71 * hash + Float.floatToIntBits(this.range);
        hash = 71 * hash + this.magazineSize;
        hash = 71 * hash + Objects.hashCode(this.ammoType);
        hash = 71 * hash + this.animationPath.hashCode();
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
        final WeaponType other = (WeaponType) obj;
        if (Float.floatToIntBits(this.fireRate) != Float.floatToIntBits(other.fireRate))
        {
            return false;
        }
        if (Float.floatToIntBits(this.range) != Float.floatToIntBits(other.range))
        {
            return false;
        }
        if (this.magazineSize != other.magazineSize)
        {
            return false;
        }
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        if (!this.animationPath.equals(other.animationPath))
        {
            return false;
        }

        return Objects.equals(this.ammoType, other.ammoType);
    }

}
