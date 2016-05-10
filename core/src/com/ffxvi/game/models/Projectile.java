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
import support.Vector;

/**
 * This class contains all the properties for the Projectile. It contains the
 * position, rotation and AmmoType.
 */
public class Projectile
{

    /**
     * Vector2f containing the position of the projectile.
     */
    private Vector position;

    /**
     * The rotation of the projectile.
     */
    private float rotation;

    /**
     * The ammo type of this projectile.
     */
    private AmmoType ammoType;

    /**
     * Initializes a new projectile object.
     *
     * @param position the position in the room.
     * @param rotation the rotation of the projectile in degrees. If the
     * rotation is not in the range 0-359, throws an IllegalArgumentException.
     * @param ammoType the type of ammo this projectile is.
     */
    public Projectile(Vector position, float rotation, AmmoType ammoType)
    {
        if (position == null)
        {
            throw new IllegalArgumentException("The position of this projectile can not be null.");
        }

        if (rotation < 0 || rotation > 359)
        {
            throw new IllegalArgumentException("The rotation of the projectile must be in the range 0-359");
        }

        if (ammoType == null)
        {
            throw new IllegalArgumentException("The ammotype of the projectile can not be null.");
        }

        this.position = position;
        this.rotation = rotation;
        this.ammoType = ammoType;
    }

    /**
     * Gets the position in the room of this projectile.
     *
     * @return A Vector2f containing the position in the room of this
     * projectile.
     */
    public Vector getPosition()
    {
        return this.position;
    }

    /**
     * Gets the rotation in the room of this projectile.
     *
     * @return A float containing the rotation of this projectile.
     */
    public float getRotation()
    {
        return this.rotation;
    }

    /**
     * Gets the position in the room of this projectile.
     *
     * @return An AmmoType containing the ammo type of this projectile.
     */
    public AmmoType getAmmoType()
    {
        return this.ammoType;
    }

    /**
     * Changes the position with the speed found in ammoType and the rotation
     * found in this object. When the calculated new position is outside the
     * room, keeps the position at nearest border of the room.
     */
    public void updatePosition()
    {
        int speed = this.ammoType.getSpeed();

        float newX = (float) (this.position.getX() + Math.cos(this.rotation) * speed);
        float newY = (float) (this.position.getY() + Math.sin(this.rotation) * speed);

        this.position = new Vector(newX, newY);

    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.position);
        hash = 13 * hash + Float.floatToIntBits(this.rotation);
        hash = 13 * hash + Objects.hashCode(this.ammoType);
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
        final Projectile other = (Projectile) obj;
        if (Float.floatToIntBits(this.rotation) != Float.floatToIntBits(other.rotation))
        {
            return false;
        }
        if (!Objects.equals(this.position, other.position))
        {
            return false;
        }
        return this.ammoType.equals(other.ammoType);
    }

}
