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
import com.ffxvi.game.models.Projectile;
import com.ffxvi.game.models.RoomObjectType;
import com.ffxvi.game.support.Vector;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Pim Janissen
 */
public class ProjectileTest {

    /**
     * A projectile object used for testing.
     */
    private Projectile projectile;

    /**
     * Initializes the projectile object before every test.
     */
    @Before
    public void initialize() {
        GameManager.resetInstance();
        Vector position = new Vector(10, 100);
        AmmoType ammoType = new AmmoType(5, 15, "animation");

        this.projectile = new Projectile(position, 40, ammoType);
    }

    /**
     * Tests if the constructor properly sets the fields of this object to the
     * given values by checking if the return methods of this object return the
     * same values as given to them.
     */
    @Test
    public void testConstructorNormalValues() {
        /**
         * Initializes a new projectile object.
         *
         * @param position the position in the room.
         * @param rotation the rotation of the projectile in degrees. If the
         * rotation is not in the range 0-359, throws an
         * IllegalArgumentException.
         * @param ammoType the type of ammo this projectile is.
         */

        Vector position = new Vector(10, 100);
        float rotation = 40;
        AmmoType ammoType = new AmmoType(5, 15, "animation");

        Projectile newProjectile = new Projectile(position, rotation, ammoType);

        Assert.assertEquals(position, newProjectile.getPosition());
        Assert.assertEquals(rotation, newProjectile.getRotation(), 0);
        Assert.assertEquals(ammoType, newProjectile.getAmmoType());
    }

    /**
     * Tests if the constructor properly throws an Exception when the given
     * rotation is a negative valuem by checking if an IllegalArgumentException
     * is thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRotationNegative() {
        /**
         * Initializes a new projectile object.
         *
         * @param position the position in the room.
         * @param rotation the rotation of the projectile in degrees. If the
         * rotation is not in the range 0-359, throws an
         * IllegalArgumentException.
         * @param ammoType the type of ammo this projectile is.
         */

        Vector position = new Vector(10, 100);
        float rotation = -1;
        AmmoType ammoType = new AmmoType(5, 15, "animation");

        Projectile newProjectile = new Projectile(position, rotation, ammoType);
    }

    /**
     * Tests if the constructor properly sets the fields of this object to the
     * given values when the given rotation is zero by checking if the return
     * methods of this object return the same values as given to them.
     */
    @Test
    public void testConstructorRotationZero() {
        /**
         * Initializes a new projectile object.
         *
         * @param position the position in the room.
         * @param rotation the rotation of the projectile in degrees. If the
         * rotation is not in the range 0-359, throws an
         * IllegalArgumentException.
         * @param ammoType the type of ammo this projectile is.
         */

        Vector position = new Vector(10, 100);
        float rotation = 0;
        AmmoType ammoType = new AmmoType(5, 15, "animation");

        Projectile newProjectile = new Projectile(position, rotation, ammoType);

        Assert.assertEquals(position, newProjectile.getPosition());
        Assert.assertEquals(rotation, newProjectile.getRotation(), 0);
        Assert.assertEquals(ammoType, newProjectile.getAmmoType());
    }

    /**
     * Tests if the constructor properly sets the fields of this object to the
     * given values when the given rotation is 359 by checking if the return
     * methods of this object return the same values as given to them.
     */
    @Test
    public void testConstructorRotationMaximumValidValue() {
        /**
         * Initializes a new projectile object.
         *
         * @param position the position in the room.
         * @param rotation the rotation of the projectile in degrees. If the
         * rotation is not in the range 0-359, throws an
         * IllegalArgumentException.
         * @param ammoType the type of ammo this projectile is.
         */

        Vector position = new Vector(10, 100);
        float rotation = 359;
        AmmoType ammoType = new AmmoType(5, 15, "animation");

        Projectile newProjectile = new Projectile(position, rotation, ammoType);

        Assert.assertEquals(position, newProjectile.getPosition());
        Assert.assertEquals(rotation, newProjectile.getRotation(), 0);
        Assert.assertEquals(ammoType, newProjectile.getAmmoType());
    }

    /**
     * Tests if the constructor properly throws an Exception when the given
     * rotation is higher than 359 valuem by checking if an
     * IllegalArgumentException is thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRotationTooHighValue() {
        /**
         * Initializes a new projectile object.
         *
         * @param position the position in the room.
         * @param rotation the rotation of the projectile in degrees. If the
         * rotation is not in the range 0-359, throws an
         * IllegalArgumentException.
         * @param ammoType the type of ammo this projectile is.
         */

        Vector position = new Vector(10, 100);
        float rotation = 360;
        AmmoType ammoType = new AmmoType(5, 15, "animation");

        Projectile newProjectile = new Projectile(position, rotation, ammoType);
    }

    /**
     * Tests if the constructor properly throws an Exception when the given
     * position is null by checking if an IllegalArgumentException is thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositionNull() {
        /**
         * Initializes a new projectile object.
         *
         * @param position the position in the room.
         * @param rotation the rotation of the projectile in degrees. If the
         * rotation is not in the range 0-359, throws an
         * IllegalArgumentException.
         * @param ammoType the type of ammo this projectile is.
         */

        Vector position = null;
        float rotation = 40;
        AmmoType ammoType = new AmmoType(5, 15, "animation");

        Projectile newProjectile = new Projectile(position, rotation, ammoType);
    }

    /**
     * Tests if the constructor properly throws an Exception when the given ammo
     * type is null by checking if an IllegalArgumentException is thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAmmoTypeNull() {
        /**
         * Initializes a new projectile object.
         *
         * @param position the position in the room.
         * @param rotation the rotation of the projectile in degrees. If the
         * rotation is not in the range 0-359, throws an
         * IllegalArgumentException.
         * @param ammoType the type of ammo this projectile is.
         */

        Vector position = new Vector(10, 100);
        float rotation = 40;
        AmmoType ammoType = null;

        Projectile newProjectile = new Projectile(position, rotation, ammoType);
    }

    /**
     * Tests if the getPosition method properly returns it's fields value by
     * consecutively setting the position field via the constructor and calling
     * the getPosition method and checking if those two values are equal.
     */
    @Test
    public void testGetPosition() {
        /**
         * Gets the position in the room of this projectile.
         *
         * @return A Vector containing the position in the room of this
         * projectile.
         */

        Assert.assertEquals(new Vector(10, 100), this.projectile.getPosition());
    }

    /**
     * Tests if the getRotation method properly returns it's fields value by
     * consecutively setting the rotation field via the constructor and calling
     * the getRotation method and checking if those two values are equal.
     */
    @Test
    public void testGetRotation() {
        /**
         * Gets the rotation in the room of this projectile.
         *
         * @return A float containing the rotation of this projectile.
         */

        Assert.assertEquals((float) 40, this.projectile.getRotation(), 0);
    }

    /**
     * Tests if the getAmmoType method properly returns it's fields value by
     * consecutively setting the ammo type field via the constructor and calling
     * the getAmmoType method and checking if those two values are equal.
     */
    @Test
    public void testGetAmmoType() {
        /**
         * Gets the position in the room of this projectile.
         *
         * @return An AmmoType containing the ammo type of this projectile.
         */

        AmmoType expectedAmmoType = new AmmoType(5, 15, "animation");
        Assert.assertEquals(expectedAmmoType.getDamage(),
                this.projectile.getAmmoType().getDamage());
        Assert.assertEquals(expectedAmmoType.getSpeed(),
                this.projectile.getAmmoType().getSpeed());
    }

    /**
     * Tests if the updatePosition method properly calculates and sets the new
     * position of the projectile by checking if the position matches the
     * expected value after a call to updatePosition.
     */
    @Test
    public void testUpdatePositionNewPositionInRoom() {
        /**
         * Changes the position with the speed found in ammoType and the
         * rotation found in this object. When the calculated new position is
         * outside the room, keeps the position at nearest border of the room.
         */

        int speed = 15;
        AmmoType ammoType = new AmmoType(5, speed, "animation");
        Vector position = new Vector(200, 400);
        float rotation = 88;
        Projectile newProjectile = new Projectile(position, rotation, ammoType);

        Vector oldPosition = newProjectile.getPosition();
        newProjectile.updatePosition();
        Vector newPosition = newProjectile.getPosition();

        Assert.assertNotSame(oldPosition, newPosition);
    }

    /**
     * Tests if the updatePosition method properly calculates and sets the new
     * position of the projectile when the speed is zero by checking if the
     * position matches the expected value after a call to updatePosition.
     */

    @Test
    public void testUpdatePositionSpeedZero() {
        /**
         * Changes the position with the speed found in ammoType and the
         * rotation found in this object. When the calculated new position is
         * outside the room, keeps the position at nearest border of the room.
         */

        int speed = 1;
        AmmoType ammoType = new AmmoType(5, speed, "animation");
        Vector position = new Vector(200, 400);
        float rotation = 88;
        Projectile newProjectile = new Projectile(position, rotation, ammoType);

        newProjectile.updatePosition();

        Vector newPosition = new Vector(200, 400);
    }

    /**
     * Tests if the updatePosition method properly calculates and sets the new
     * position of the projectile when the new location is at the left border of
     * the room by checking if the position matches the expected value after a
     * call to updatePosition.
     */

    @Test
    public void testUpdatePositionNewPositionLeftBorder() {
        /**
         * Changes the position with the speed found in ammoType and the
         * rotation found in this object. When the calculated new position is
         * outside the room, keeps the position at nearest border of the room.
     */

        int speed = 1;
        AmmoType ammoType = new AmmoType(5, speed, "animation");
        Vector position = new Vector(0, 400);
        float rotation = 88;
        Projectile newProjectile = new Projectile(position, rotation, ammoType);

        newProjectile.updatePosition();

        Vector newPosition = new Vector(0, 400);
    }

    /**
     * Tests if the updatePosition method properly calculates and sets the new
     * position of the projectile when the new location is at the top border of
     * the room by checking if the position matches the expected value after a
     * call to updatePosition.
     */

    @Test
    public void testUpdatePositionNewPositionTopBorder() {
        /**
         * Changes the position with the speed found in ammoType and the
         * rotation found in this object. When the calculated new position is
         * outside the room, keeps the position at nearest border of the room.
        */

        int speed = 1;
        AmmoType ammoType = new AmmoType(5, speed, "animation");
        Vector position = new Vector(200, 0);
        float rotation = 88;
        Projectile newProjectile = new Projectile(position, rotation, ammoType);

        newProjectile.updatePosition();

        Vector newPosition = new Vector(200, 0);
    }

    /**
     * Tests if the updatePosition method properly calculates and sets the new
     * position of the projectile when the new location is at the right border
     * of the room by checking if the position matches the expected value after
     * a call to updatePosition.
     */

    @Test
    public void testUpdatePositionNewPositionRightBorder() {
        /**
         * Changes the position with the speed found in ammoType and the
         * rotation found in this object. When the calculated new position is
         * outside the room, keeps the position at nearest border of the room.
     */

        int speed = 1;
        AmmoType ammoType = new AmmoType(5, speed, "animation");
        Vector position = new Vector(200, 0);
        float rotation = 88;
        Projectile newProjectile = new Projectile(position, rotation, ammoType);
        
        Vector oldPosition = newProjectile.getPosition();
        newProjectile.updatePosition();
        Vector newPosition = newProjectile.getPosition();
        
        Assert.assertNotSame(oldPosition, newPosition);
    }
    
    /**
     * Test the equals method with the same object.
     * Testcase should return true.
     */
    @Test
    public void testEqualsSameObject() {
        Projectile p2 = this.projectile;
        Assert.assertTrue(this.projectile.equals(p2));
    }

    /**
     * Test the equals method with a null object. Testcase should return false.
     */
    @Test
    public void testEqualsParameterNull() {
        Object obj = null;
        Assert.assertFalse(this.projectile.equals(obj));
    }

    /**
     * Test the equals method with a different class. Testcase should return
     * false.
     */
    @Test
    public void testEqualsOtherClass() {
        float density = 1.0f;
        String animationString = "animation";
        RoomObjectType r = new RoomObjectType(density, animationString);

        Assert.assertFalse(this.projectile.equals(r));
    }

    /**
     * Test the equals method with different rotation. Testcase should return
     * false.
     */
    @Test
    public void testEqualsOtherRotation() {
        Projectile p2;

        Vector position = new Vector(10, 100);
        AmmoType ammoType = new AmmoType(5, 15, "animation");

        p2 = new Projectile(position, 200, ammoType);

        Assert.assertFalse(this.projectile.equals(p2));
    }

    /**
     * Test the equals method with different position. Testcase should return
     * false.
     */
    @Test
    public void testEqualsOtherPosition() {
        Projectile p2;

        Vector position = new Vector(20, 100);
        AmmoType ammoType = new AmmoType(5, 15, "animation");

        p2 = new Projectile(position, 40, ammoType);

        Assert.assertFalse(this.projectile.equals(p2));
    }

    /**
     * Test the equals method with different AmmoType. Testcase should return
     * false.
     */
    @Test
    public void testEqualsOtherAmmoType() {
        Projectile p2;

        Vector position = new Vector(10, 100);
        AmmoType ammoType = new AmmoType(20, 15, "animation");

        p2 = new Projectile(position, 40, ammoType);

        Assert.assertFalse(this.projectile.equals(p2));
    }

    /**
     * Tests the hashcode method with same object. This testcase should return
     * true.
     */
    @Test
    public void testHashCodeSameObject() {
        Projectile p2 = this.projectile;

        Assert.assertTrue(p2.hashCode() == this.projectile.hashCode());
    }

    /**
     * Tests the hashcode method with different objects. Testcase should return
     * false.
     */
    @Test
    public void testHashCodeDifferenObjectSameValues() {
        Vector position = new Vector(10, 100);
        AmmoType ammoType = new AmmoType(5, 15, "animation");

        Projectile p2 = new Projectile(position, 40, ammoType);
        Assert.assertTrue(p2.hashCode() == this.projectile.hashCode());
    }

    /**
     * Tests the hashcode method with different objects. Testcase should return
     * false.
     */
    @Test
    public void testHashCodeOtherObject() {
        Projectile p2;

        Vector position = new Vector(50, 100);
        AmmoType ammoType = new AmmoType(15, 15, "animation");

        p2 = new Projectile(position, 70, ammoType);

        Assert.assertFalse(p2.hashCode() == this.projectile.hashCode());
    }

}
