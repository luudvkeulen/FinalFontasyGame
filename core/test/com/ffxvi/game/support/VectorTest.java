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

import com.ffxvi.game.business.GameManager;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The class which we use to test the vector class.
 */
public class VectorTest {

    /**
     * The vector which we use for testing
     */
    private Vector vector;

    @Before
    public void initialize() {
        GameManager.resetInstance();
        float x = 50;
        float y = 20;

        this.vector = new Vector(x, y);
    }

    /**
     * Tests if the getX method returns the same value as given to the vector by
     * the constructor in the initialize method.
     */
    @Test
    public void testGetX() {
        /**
         * Gets the x coördinate of the vector.
         *
         * @return the x coördinate.
         */

        Assert.assertEquals((float) 50, this.vector.getX());
    }

    /**
     * Tests if the getY method returns the same value as given to the vector by
     * the constructor in the initialize method.
     */
    @Test
    public void testGetY() {
        /**
         * Gets the y coördinate of the vector.
         *
         * @return the y coördinate.
         */

        Assert.assertEquals((float) 20, this.vector.getY());
    }

    /**
     * Tests if the setX method properly sets the value of the vector when the
     * given x is positive.
     */
    @Test
    public void testSetXPositive() {
        /**
         * Sets the x coördinate of the vector to the given value.
         *
         * @param x the new x coördinate.
         */

        float newX = 25;

        this.vector.setX(newX);

        Assert.assertEquals(newX, this.vector.getX());
    }

    /**
     * Tests if the setX method properly sets the value of the vector when the
     * given x is zero.
     */
    @Test
    public void testSetXZero() {
        /**
         * Sets the x coördinate of the vector to the given value.
         *
         * @param x the new x coördinate.
         */

        float newX = 0;

        this.vector.setX(newX);

        Assert.assertEquals(newX, this.vector.getX());
    }

    /**
     * Tests if the setX method properly sets the value of the vector when the
     * given x is negative.
     */
    @Test
    public void testSetXNegative() {
        /**
         * Sets the x coördinate of the vector to the given value.
         *
         * @param x the new x coördinate.
         */

        float newX = -10;

        this.vector.setX(newX);

        Assert.assertEquals(newX, this.vector.getX());
    }

    /**
     * Tests if the setX method properly sets the value of the vector when the
     * given y is positive.
     */
    @Test
    public void setYPositive() {
        /**
         * Sets the y coördinate of the vector to the given value.
         *
         * @param y the new y coördinate.
         */

        float newY = 45;

        this.vector.setY(newY);

        Assert.assertEquals(newY, this.vector.getY());
    }

    /**
     * Tests if the setX method properly sets the value of the vector when the
     * given y is zero.
     */
    @Test
    public void setYZero() {
        /**
         * Sets the y coördinate of the vector to the given value.
         *
         * @param y the new y coördinate.
         */

        float newY = 0;

        this.vector.setY(newY);

        Assert.assertEquals(newY, this.vector.getY());
    }

    /**
     * Tests if the setX method properly sets the value of the vector when the
     * given y is negative.
     */
    @Test
    public void setYNegative() {
        /**
         * Sets the y coördinate of the vector to the given value.
         *
         * @param y the new y coördinate.
         */

        float newY = -10;

        this.vector.setY(newY);

        Assert.assertEquals(newY, this.vector.getY());
    }

    /**
     * Tests if the constructor properly sets the fields to the given values
     * when both x and y are positive.
     */
    @Test
    public void testConstructorXPositiveYPositive() {
        /**
         * Initializes a new vector with the given x and y position.
         *
         * @param x the x coördinate of the vector.
         * @param y the y coördinate of the vector.
         */

        float x = 50;
        float y = 20;

        Vector newVector = new Vector(x, y);

        Assert.assertEquals(x, newVector.getX());
        Assert.assertEquals(y, newVector.getY());
    }

    @Test
    public void testConstructorXZero() {
        /**
         * Initializes a new vector with the given x and y position.
         *
         * @param x the x coördinate of the vector.
         * @param y the y coördinate of the vector.
         */

        float x = 0;
        float y = 20;

        Vector newVector = new Vector(x, y);

        Assert.assertEquals(x, newVector.getX());
        Assert.assertEquals(y, newVector.getY());
    }

    @Test
    public void testConstructorXNegative() {
        /**
         * Initializes a new vector with the given x and y position.
         *
         * @param x the x coördinate of the vector.
         * @param y the y coördinate of the vector.
         */

        float x = -10;
        float y = 20;

        Vector newVector = new Vector(x, y);

        Assert.assertEquals(x, newVector.getX());
        Assert.assertEquals(y, newVector.getY());
    }

    @Test
    public void testConstructoYZero() {
        /**
         * Initializes a new vector with the given x and y position.
         *
         * @param x the x coördinate of the vector.
         * @param y the y coördinate of the vector.
         */

        float x = 50;
        float y = 0;

        Vector newVector = new Vector(x, y);

        Assert.assertEquals(x, newVector.getX());
        Assert.assertEquals(y, newVector.getY());
    }

    @Test
    public void testConstructorYNegative() {
        /**
         * Initializes a new vector with the given x and y position.
         *
         * @param x the x coördinate of the vector.
         * @param y the y coördinate of the vector.
         */

        float x = 50;
        float y = -10;

        Vector newVector = new Vector(x, y);

        Assert.assertEquals(x, newVector.getX());
        Assert.assertEquals(y, newVector.getY());
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeSameObject() {
        int hashCodeFirst = this.vector.hashCode();
        int hashCodeSecond = this.vector.hashCode();

        Assert.assertEquals(hashCodeFirst, hashCodeSecond);
    }

    /**
     * Tests if the hashcode method's return value is equal when two different
     * objects with the same values call them.
     */
    @Test
    public void testHashcodeDifferentObjectSameValues() {
        float x = 50;
        float y = 20;

        Vector newVector = new Vector(x, y);

        Assert.assertEquals(this.vector.hashCode(), newVector.hashCode());
    }

    /**
     * Tests if the hashcode method's return value is false when two different
     * objects with different values call them.
     */
    @Test
    public void testHashcodeDifferentObjectDifferentValues() {
        float x = 50;
        float y = 0;

        Vector newVector = new Vector(x, y);

        Assert.assertNotSame(this.vector.hashCode(), newVector.hashCode());
    }

    /**
     * Tests if the equals method's return value is true when the passed game
     * has the same values as this game.
     */
    @Test
    public void testEqualsDifferentObjectSameValues() {
        float x = 50;
        float y = 20;

        Vector newVector = new Vector(x, y);

        Assert.assertTrue(this.vector.equals(newVector));
    }

    /**
     * Tests if the equals method's return value is true when the passed game is
     * the same object.
     */
    @Test
    public void testEqualsSameObject() {

        Assert.assertTrue(this.vector.equals(this.vector));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is not a Game object.
     */
    @Test
    public void testEqualsObjectOtherClass() {

        Assert.assertFalse(this.vector.equals(GameManager.getInstance()));
    }

    /**
     * Tests if the equals method's return value is false when the passed game's
     * name is different.
     */
    @Test
    public void testEqualsXNotEqual() {
        float x = 40;
        float y = 20;

        Vector newVector = new Vector(x, y);

        Assert.assertFalse(this.vector.equals(newVector));
    }

    /**
     * Tests if the equals method's return value is false when the passed game's
     * list of players is different.
     */
    @Test
    public void testEqualsYNotEqual() {
        float x = 50;
        float y = 30;

        Vector newVector = new Vector(x, y);

        Assert.assertFalse(this.vector.equals(newVector));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is null.
     */
    @Test
    public void testEqualsObjectNull() {

        Assert.assertFalse(this.vector.equals(null));
    }

}
