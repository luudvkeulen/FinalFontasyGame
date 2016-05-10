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

import com.ffxvi.game.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joel
 */
public class WeaponTypeTest {

    /**
     * A WeaponType object used for testing.
     */
    private WeaponType weaponType;

    /**
     * Initializes a new WeaponType object.
     */
    @Before
    public void initialize() {
        GameManager.resetInstance();
        this.weaponType = new WeaponType("shotgun", 0.6f, 1.0f, 10, "animationString", new AmmoType(1, 1, "randomString"));
    }

    /**
     * Tests if the constructor properly sets the private fields of this class
     * when the given values are valid.s
     */
    @Test
    public void testConstructorNormalValues() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animationString The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "shotgun";
        float fireRate = 0.6f;
        float range = 1.0f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        WeaponType newWeaponType = new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);

        Assert.assertEquals(name, newWeaponType.getName());
        Assert.assertEquals(fireRate, newWeaponType.getFireRate());
        Assert.assertEquals(range, newWeaponType.getRange());
        Assert.assertEquals(magazineSize, newWeaponType.getMagazineSize());
        Assert.assertEquals(animationString, newWeaponType.getAnimationPath());
        Assert.assertEquals(ammoType, newWeaponType.getAmmoType());
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given name is an empty string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameEmpyString() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animationString The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "";
        float fireRate = 0.6f;
        float range = 1.0f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given name is a string exclusively containing spaces.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameExclusivelySpaces() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "   ";
        float fireRate = 0.6f;
        float range = 1.0f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given fire rate is zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorFireRateZero() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "shotgun";
        float fireRate = 0f;
        float range = 1.0f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given fire rate is -1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorFireRateNegative() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "shotgun";
        float fireRate = -1f;
        float range = 1.0f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given range is zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRangeZero() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "shotgun";
        float fireRate = 0.6f;
        float range = 0f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given range is -1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRangeNegative() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "shotgun";
        float fireRate = 0.6f;
        float range = -1f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given magazine size is zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorMagazineSizeZero() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "shotgun";
        float fireRate = 0.6f;
        float range = 1.0f;
        int magazineSize = 0;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given magazine size is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorMagazineSizeNegative() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "shotgun";
        float fireRate = 0.6f;
        float range = 1.0f;
        int magazineSize = -1;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given animation path is an empty string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnimationPathEmptyString() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "shotgun";
        float fireRate = 0.6f;
        float range = 1.0f;
        int magazineSize = -1;
        String animationString = "";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given animation path is a string exclusively containing spaces.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnimationPathExclusivelySpaces() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "shotgun";
        float fireRate = 0.6f;
        float range = 1.0f;
        int magazineSize = -1;
        String animationString = "   ";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given name is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameNull() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = null;
        float fireRate = 0.6f;
        float range = 1.0f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given animationPath is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnimationPathNull() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "shotgun";
        float fireRate = 0.6f;
        float range = 1.0f;
        int magazineSize = 10;
        String animationString = null;
        AmmoType ammoType = new AmmoType(1, 1, "randomString");

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException if
     * the given ammo type is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAmmoTypeNull() {
        /**
         * Instance of the WeaponType class which is stored in a dictionary in
         * the GameManager class.
         *
         * @param name The name of the WeaponType. When an empty string
         * (excluding spaces) throws an IllegalArgumentException.
         * @param fireRate The fire rate of the WeaponType. When not a positive
         * value throws an IllegalArgumentException.
         * @param range The range of this WeaponType's shooting. When not a
         * positive value throws an IllegalArgumentException.
         * @param magazineSize The magazineSize of this WeaponType. When not a
         * positive value throws an IllegalArgumentException.
         * @param animation The animation/image of this WeaponType.
         * @param ammoType The AmmoType which corresponds with this WeaponType.
         */

        String name = "shotgun";
        float fireRate = 0.6f;
        float range = 1.0f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = null;

        new WeaponType(name, fireRate, range, magazineSize, animationString, ammoType);
    }

    /**
     * Tests if the getName method returns the same Name as given to it
     */
    @Test
    public void testGetName() {
        String result = weaponType.getName();
        assertEquals("shotgun", result);
    }

    /**
     * Tests if the getFireRate method returns the same fire rate as given to it
     */
    @Test
    public void testFireRate() {
        float result = weaponType.getFireRate();
        assertEquals(0.6f, result, 0);
    }

    /**
     * Tests if the getRange method returns the same range as given to it
     */
    @Test
    public void testGetRange() {
        float result = weaponType.getRange();
        assertEquals(1.0f, result, 0);
    }

    /**
     * Tests if the getMagazineSize method returns the same magazine size as
     * given to it
     */
    @Test
    public void testGetMagazineSize() {
        int result = weaponType.getMagazineSize();
        assertEquals(10, result);
    }

    /**
     * Tests if the getAnimationPath method returns an String by checking if the
     * returned value is not null.
     */
    @Test
    public void testGetAnimationPath() {
        assertNotNull(weaponType.getAnimationPath());
    }

    /**
     * Tests if the getAmmoType method returns the same AmmoType as given to it.
     */
    @Test
    public void testGetAmmoType() {
        AmmoType result = weaponType.getAmmoType();
        assertEquals(new AmmoType(1, 1, "randomString"), result);
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeSameObject() {
        int hashCodeFirst = this.weaponType.hashCode();
        int hashCodeSecond = this.weaponType.hashCode();

        Assert.assertEquals(hashCodeFirst, hashCodeSecond);
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeDifferentObjectSameValues() {
        WeaponType newWeaponType = new WeaponType("shotgun", 0.6f, 1.0f, 10, 
                "animationString", new AmmoType(1, 1, "randomString"));

        Assert.assertEquals(this.weaponType.hashCode(), newWeaponType.hashCode());
    }

    /**
     * Tests if the hashcode method's return value is not equal when a different
     * object calls them.
     */
    @Test
    public void testHashcodeDifferentObjectDifferentValues() {
        WeaponType newWeaponType = new WeaponType("Sniper", 0.7f, 2.0f, 11, 
                "animationString", new AmmoType(1, 1, "randomString"));

        Assert.assertFalse(this.weaponType.hashCode() == newWeaponType.hashCode());
    }

    /**
     * Tests if the equals method's return value is true when the passed game
     * has the same values as this game.
     */
    @Test
    public void testEqualsDifferentObjectSameValues() {
        WeaponType newWeaponType = new WeaponType("shotgun", 0.6f, 1.0f, 10, 
                "animationString", new AmmoType(1, 1, "randomString"));

        Assert.assertTrue(this.weaponType.equals(newWeaponType));
    }

    /**
     * Tests if the equals method's return value is true when the passed game is
     * the same object.
     */
    @Test
    public void testEqualsSameObject() {

        Assert.assertTrue(this.weaponType.equals(this.weaponType));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is not a WeaponType object.
     */
    @Test
    public void testEqualsObjectOtherClass() {

        Assert.assertFalse(this.weaponType.equals(GameManager.getInstance()));
    }

    /**
     * Tests if the equals method's return value is false when the passed weapon
     * type's fire rate is different.
     */
    @Test
    public void testEqualsFireRateNotEqual() {
        WeaponType newWeaponType = new WeaponType("shotgun", 0.5f, 1.0f, 10, 
                "animationString", new AmmoType(1, 1, "randomString"));

        Assert.assertFalse(this.weaponType.equals(newWeaponType));
    }

    /**
     * Tests if the equals method's return value is false when the passed weapon
     * type's fire rate is different.
     */
    @Test
    public void testEqualsRangeNotEqual() {
        WeaponType newWeaponType = new WeaponType("shotgun", 0.6f, 1.3f, 10, 
                "animationString", new AmmoType(1, 1, "randomString"));

        Assert.assertFalse(this.weaponType.equals(newWeaponType));
    }

    /**
     * Tests if the equals method's return value is false when the passed weapon
     * type's magazine size is different.
     */
    @Test
    public void testEqualsMagazineSizeNotEqual() {
        WeaponType newWeaponType = new WeaponType("shotgun", 0.6f, 1.0f, 12, 
                "animationString", new AmmoType(1, 1, "randomString"));

        Assert.assertFalse(this.weaponType.equals(newWeaponType));
    }

    /**
     * Tests if the equals method's return value is false when the passed weapon
     * type's name is different.
     */
    @Test
    public void testEqualsNameNotEqual() {
        WeaponType newWeaponType = new WeaponType("desert eagle", 0.6f, 1.0f, 10, 
                "animationString", new AmmoType(1, 1, "randomString"));

        Assert.assertFalse(this.weaponType.equals(newWeaponType));
    }

    /**
     * Tests if the equals method's return value is false when the passed weapon
     * type's ammo type is different.
     */
    @Test
    public void testEqualsAmmoTypeNotEqual() {
        WeaponType newWeaponType = new WeaponType("shotgun", 0.6f, 1.0f, 10, 
                "animationString", new AmmoType(2, 1, "randomString"));

        Assert.assertFalse(this.weaponType.equals(newWeaponType));
    }

    /**
     * Tests if the equals method's return value is false when the passed weapon
     * type's ammo type is different.
     */
    @Test
    public void testEqualsAnimationPathNotEqual() {
        WeaponType newWeaponType = new WeaponType("shotgun", 0.6f, 1.0f, 10, 
                "NietString", new AmmoType(1, 1, "randomString"));

        Assert.assertFalse(this.weaponType.equals(newWeaponType));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is null.
     */
    @Test
    public void testEqualsObjectNull() {

        Assert.assertFalse(this.weaponType.equals(null));
    }

}
