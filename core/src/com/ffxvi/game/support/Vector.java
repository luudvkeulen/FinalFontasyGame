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
package com.ffxvi.game.support;

/**
 * Represents a mathmatical vector.
 */
public class Vector {

    /**
     * The x coördinate of the vector.
     */
    private float x;

    /**
     * The y coördinate of the vector.
     */
    private float y;

    /**
     * Gets the x coördinate of the vector.
     *
     * @return the x coördinate.
     */
    public float getX()
    {
        return this.x;
    }

    /**
     * Gets the y coördinate of the vector.
     *
     * @return the y coördinate.
     */
    public float getY()
    {
        return this.y;
    }

    /**
     * Sets the x coördinate of the vector to the given value.
     *
     * @param x the new x coördinate.
     */

    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets the y coördinate of the vector to the given value.
     *
     * @param y the new y coördinate.
     */

    public void setY(float y)
    {
        this.y = y;
    }

    /**
     * Initializes a new vector with the given x and y position.
     *
     * @param x the x coördinate of the vector.
     * @param y the y coördinate of the vector.
     */
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Float.floatToIntBits(this.x);
        hash = 41 * hash + Float.floatToIntBits(this.y);
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
        final Vector other = (Vector) obj;
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        return Float.floatToIntBits(this.y) == Float.floatToIntBits(other.y);
    }
}
