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
package models;

import com.ffxvi.game.business.GameManager;
import com.ffxvi.game.models.AmmoType;
import com.ffxvi.game.models.RoomObjectType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Joel
 * @author Pim Janissen
 * @author Guido
 */
public class AmmoTypeTest {

    /**
     * An AmmoType object used for testing.
     */
    private AmmoType ammoType;

    /**
     * Initializes a test method before each test.
     */
    @Before
    public void initialize() {
        GameManager.resetInstance();
        ammoType = new AmmoType(1, 1, "animationString");
    }

    /**
     * Tests if the constructor properly sets this object's fields when the
     * given values are valid.
     */
    @Test
    public void testConstructorValidValues() {
        /**
         * Instance of the AmmoType class which is stored in a dictionary in the
         * GameManager class.
         *
         * @param damage The amount of damage this AmmoType deals.
         * @param speed The speed of this AmmoType. If speed is not a positive
         * value, throw an IllegalArgumentException.
         * @param animationPath The file path of the animation of this AmmoType.
         * can not be empty (excluding spaces).
         */

        int damage = 1;
        int speed = 1;
        String animationPath = "animationString";

        AmmoType newAmmoType = new AmmoType(damage, speed, animationPath);

        Assert.assertEquals(damage, newAmmoType.getDamage());
        Assert.assertEquals(speed, newAmmoType.getSpeed());
        Assert.assertEquals(animationPath, newAmmoType.getAnimationPath());
    }

    /**
     * Tests if the constructor throws an IllegalArgumentException when the
     * given speed is 0.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorSpeedZero() {
        /**
         * Instance of the AmmoType class which is stored in a dictionary in the
         * GameManager class.
         *
         * @param damage The amount of damage this AmmoType deals.
         * @param speed The speed of this AmmoType. If speed is not a positive
         * value, throw an IllegalArgumentException.
         * @param animationPath The file path of the animation of this AmmoType.
         * can not be empty (excluding spaces).
         */

        int damage = 1;
        int speed = 0;
        String animationPath = "animationString";

        AmmoType newAmmoType = new AmmoType(damage, speed, animationPath);
    }

    /**
     * Tests if the constructor throws an IllegalArgumentException when the
     * given speed is a negative value.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorSpeedNegative() {
        /**
         * Instance of the AmmoType class which is stored in a dictionary in the
         * GameManager class.
         *
         * @param damage The amount of damage this AmmoType deals.
         * @param speed The speed of this AmmoType. If speed is not a positive
         * value, throw an IllegalArgumentException.
         * @param animationPath The file path of the animation of this AmmoType.
         * can not be empty (excluding spaces).
         */

        int damage = 1;
        int speed = -10;
        String animationPath = "animationString";

        AmmoType newAmmoType = new AmmoType(damage, speed, animationPath);
    }

    /**
     * Tests if the constructor throws an IllegalArgumentException when the
     * given animationPath is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnimationPathNull() {
        /**
         * Instance of the AmmoType class which is stored in a dictionary in the
         * GameManager class.
         *
         * @param damage The amount of damage this AmmoType deals.
         * @param speed The speed of this AmmoType. If speed is not a positive
         * value, throw an IllegalArgumentException.
         * @param animationPath The file path of the animation of this AmmoType.
         * can not be empty (excluding spaces).
         */

        int damage = 1;
        int speed = 1;
        String animationPath = null;

        AmmoType newAmmoType = new AmmoType(damage, speed, animationPath);
    }

    /**
     * Tests if the constructor throws an IllegalArgumentException when the
     * given animationPath is an empty string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnimationPathEmptyString() {
        /**
         * Instance of the AmmoType class which is stored in a dictionary in the
         * GameManager class.
         *
         * @param damage The amount of damage this AmmoType deals.
         * @param speed The speed of this AmmoType. If speed is not a positive
         * value, throw an IllegalArgumentException.
         * @param animationPath The file path of the animation of this AmmoType.
         * can not be empty (excluding spaces).
         */

        int damage = 1;
        int speed = 1;
        String animationPath = "";

        AmmoType newAmmoType = new AmmoType(damage, speed, animationPath);
    }

    /**
     * Tests if the constructor throws an IllegalArgumentException when the
     * given animationPath is a string exclusively containing spaces.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnimationPathExclusivelySpaces() {
        /**
         * Instance of the AmmoType class which is stored in a dictionary in the
         * GameManager class.
         *
         * @param damage The amount of damage this AmmoType deals.
         * @param speed The speed of this AmmoType. If speed is not a positive
         * value, throw an IllegalArgumentException.
         * @param animationPath The file path of the animation of this AmmoType.
         * can not be empty (excluding spaces).
         */

        int damage = 1;
        int speed = 1;
        String animationPath = "   ";

        AmmoType newAmmoType = new AmmoType(damage, speed, animationPath);
    }

    /**
     * Tests if the constructor properly sets this object's fields when the
     * given damage is zero.
     */
    @Test
    public void testConstructorDamageZero() {
        /**
         * Instance of the AmmoType class which is stored in a dictionary in the
         * GameManager class.
         *
         * @param damage The amount of damage this AmmoType deals.
         * @param speed The speed of this AmmoType. If speed is not a positive
         * value, throw an IllegalArgumentException.
         * @param animationPath The file path of the animation of this AmmoType.
         * can not be empty (excluding spaces).
         */

        int damage = 0;
        int speed = 1;
        String animationPath = "animationString";

        AmmoType newAmmoType = new AmmoType(damage, speed, animationPath);

        Assert.assertEquals(damage, newAmmoType.getDamage());
        Assert.assertEquals(speed, newAmmoType.getSpeed());
        Assert.assertEquals(animationPath, newAmmoType.getAnimationPath());
    }

    /**
     * Tests if the constructor properly sets this object's fields when the
     * given damage is negative.
     */
    @Test
    public void testConstructorDamageNegative() {
        /**
         * Instance of the AmmoType class which is stored in a dictionary in the
         * GameManager class.
         *
         * @param damage The amount of damage this AmmoType deals.
         * @param speed The speed of this AmmoType. If speed is not a positive
         * value, throw an IllegalArgumentException.
         * @param animationPath The file path of the animation of this AmmoType.
         * can not be empty (excluding spaces).
         */

        int damage = -1;
        int speed = 1;
        String animationPath = "animationString";

        AmmoType newAmmoType = new AmmoType(damage, speed, animationPath);

        Assert.assertEquals(damage, newAmmoType.getDamage());
        Assert.assertEquals(speed, newAmmoType.getSpeed());
        Assert.assertEquals(animationPath, newAmmoType.getAnimationPath());
    }

    /**
     * Tests if the getDamage method returns the same Name as given to it
     */
    @Test
    public void testGetDamage() {
        int result = ammoType.getDamage();
        Assert.assertEquals(1, result);
    }

    /**
     * Tests if the getSpeed method returns the same speed as given to it
     */
    @Test
    public void testGetSpeed() {
        int result = ammoType.getSpeed();
        Assert.assertEquals(1, result);
    }

    /**
     * Tests if the getAnimationPath method returns the same image as given to
     * it
     */
    @Test
    public void testgetAnimationPath() {
        Assert.assertNotNull(ammoType.getAnimationPath());
    }

    /**
     * Test the hashcode with the same object.
     */
    @Test
    public void testHashCodeSameObject() {
        AmmoType ammoType2 = ammoType;
        int first = ammoType.hashCode();
        int second = ammoType2.hashCode();

        Assert.assertEquals(second, first);
    }

    /**
     * Test the hashcode with a different object with the same values.
     */
    @Test
    public void testHashCodeDifferentObjectSameValues() {
        AmmoType ammoType2 = new AmmoType(1, 1, "animationString");
        int first = ammoType.hashCode();
        int second = ammoType2.hashCode();

        Assert.assertEquals(second, first);
    }

    /**
     * Test hashcode with different objects.
     */
    @Test
    public void testHashCodeDifferentObject() {
        AmmoType ammoType2 = new AmmoType(200, 50, "animationString");

        int first = ammoType.hashCode();
        int second = ammoType2.hashCode();

        Assert.assertFalse(first == second);
    }

    /**
     * Test equals with parameter which is null. Testcase should return false.
     */
    @Test
    public void testEqualsParameterNull() {
        Object obj = null;
        Assert.assertFalse(ammoType.equals(obj));
    }

    /**
     * Test equals with parameter which is another class. Testcase should return
     * false.
     */
    @Test
    public void testEqualsOtherClass() {
        RoomObjectType roomObjectType = new RoomObjectType(0.5f, "animationString");
        Assert.assertFalse(ammoType.equals(roomObjectType));
    }

    /**
     * Test equals with other damage. Testcase should return false.
     */
    @Test
    public void testEqualsOtherDamage() {
        AmmoType ammoType2 = new AmmoType(2, 1, ammoType.getAnimationPath());
        Assert.assertFalse(ammoType.equals(ammoType2));
    }

    /**
     * Test equals with other speed. Testcase should return false.
     */
    @Test
    public void testEqualsOtherSpeed() {
        AmmoType ammoType2 = new AmmoType(1, 2, ammoType.getAnimationPath());
        Assert.assertFalse(ammoType.equals(ammoType2));
    }

    /**
     * Test equals with other speed. Testcase should return false.
     */
    @Test
    public void testEqualsOtherAnimationPath() {
        AmmoType ammoType2 = new AmmoType(1, 2, "TestString");
        Assert.assertFalse(ammoType.equals(ammoType2));
    }
}
