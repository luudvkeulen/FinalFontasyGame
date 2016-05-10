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
import org.junit.*;
import static org.junit.Assert.*;
import support.Vector;

/**
 *
 * @author Acer
 */
public class RoomObjectTest {

    /**
     * The roomObject instance which will be used for testing.
     */
    private RoomObject roomObject;

    /**
     * Initializes the RoomObject instance in the fields.
     */
    @Before
    public void initialize() {
        GameManager.resetInstance();
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(20, 50);
        float rotation = 20f;
        this.roomObject = new RoomObject(type, position, rotation);
    }

    /**
     * Tests if the constructor properly sets the fields of the object when the
     * given values are valid.
     */
    @Test
    public void testConstructor() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(20, 50);
        float rotation = 20f;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);

        Assert.assertEquals(type, newRoomObject.getRoomObjectType());
        Assert.assertEquals(position, newRoomObject.getPosition());
        Assert.assertEquals(rotation, newRoomObject.getRotation(), 0);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException when
     * the given RoomObjectType is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRoomObjectTypeNull() {
        RoomObjectType type = null;

        Vector position = new Vector(10, 10);
        float rotation = 20f;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException when
     * the given position is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositionNull() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = null;
        float rotation = 20f;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);
    }

    /**
     * Tests if the constructor properly sets its object's fields when the given
     * position's x is zero.
     */
    @Test
    public void testConstructorPositionXZero() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(0, 50);
        float rotation = 20f;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);

        Assert.assertEquals(type, newRoomObject.getRoomObjectType());
        Assert.assertEquals(position, newRoomObject.getPosition());
        Assert.assertEquals(rotation, newRoomObject.getRotation(), 0);
    }

    /**
     * Tests if the constructor properly sets its object's fields when the given
     * position's x is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositionXNegative() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(-20, 50);
        float rotation = 20f;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);
    }

    /**
     * Tests if the constructor properly sets its object's fields when the given
     * position's y is zero.
     */
    @Test
    public void testConstructorPositionYZero() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(20, 0);
        float rotation = 20f;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);

        Assert.assertEquals(type, newRoomObject.getRoomObjectType());
        Assert.assertEquals(position, newRoomObject.getPosition());
        Assert.assertEquals(rotation, newRoomObject.getRotation(), 0);
    }

    /**
     * Tests if the constructor properly sets its object's fields when the given
     * position's y is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositionYNegative() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(20, -50);
        float rotation = 20f;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException when
     * the given rotation is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRotationNegative() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(20, 50);
        float rotation = -5f;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);
    }

    /**
     * Tests if the constructor properly sets its object's fields when the given
     * rotation is zero.
     */
    @Test
    public void testConstructorRotationZero() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(20, 50);
        float rotation = 0;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);

        Assert.assertEquals(type, newRoomObject.getRoomObjectType());
        Assert.assertEquals(position, newRoomObject.getPosition());
        Assert.assertEquals(rotation, newRoomObject.getRotation(), 0);
    }

    /**
     * Tests if the constructor properly sets its object's fields when the given
     * rotation is 359.
     */
    @Test
    public void testConstructorRotationMaxValue() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(20, 50);
        float rotation = 359;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);

        Assert.assertEquals(type, newRoomObject.getRoomObjectType());
        Assert.assertEquals(position, newRoomObject.getPosition());
        Assert.assertEquals(rotation, newRoomObject.getRotation(), 0);
    }

    /**
     * Tests if the constructor properly sets its object's fields when the given
     * rotation exceeds the maximum allowed value.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRotationTooHighValue() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(20, 50);
        float rotation = 360;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);
    }

    /**
     * Set the rotation of the roomObject and check if it has changed via the
     * getter of the same variable.
     */
    @Test
    public void testSetRotation() {
        roomObject.setRotation(20f);
        assertEquals(20f, roomObject.getRotation(), 0);
    }

    /**
     * Set the rotation of the roomObject too high.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetRotationTooHigh() {
        roomObject.setRotation(360f);
    }

    /**
     * Set the rotation of the roomObject too low.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetRotationTooLow() {
        roomObject.setRotation(-1f);
    }

    /**
     * Set the RoomObjectType to null. Testcase expects an
     * IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetRoomObjectTypeNull() {
        RoomObjectType r = null;
        this.roomObject.setRoomObjectType(r);
    }

    /**
     * Set the RoomObjectType to a valid object. Testcase should return true.
     */
    @Test
    public void testSetRoomObjectType() {
        RoomObjectType r = new RoomObjectType(1.0f, "AnimationString");
        this.roomObject.setRoomObjectType(r);

        Assert.assertEquals(r, this.roomObject.getRoomObjectType());
    }

    /**
     * Tests the hashcode with same object. Testcase should return true.
     */
    @Test
    public void testHashCodeSameObject() {
        RoomObject r2 = this.roomObject;
        Assert.assertTrue(this.roomObject.hashCode() == r2.hashCode());
    }

    /**
     * Tests the hashcode method with a different object. Testcase should return
     * false.
     */
    @Test
    public void testHashCodeDifferentObjectSameValues() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(20, 50);
        float rotation = 20f;
        RoomObject roomObject2 = new RoomObject(type, position, rotation);

        Assert.assertTrue(roomObject2.hashCode() == this.roomObject.hashCode());
    }

    /**
     * Tests the hashcode method with a different object. Testcase should return
     * false.
     */
    @Test
    public void testHashCodeDifferentObject() {
        RoomObjectType rt = new RoomObjectType(1.0f, "AnimationString");
        RoomObject r = new RoomObject(rt, new Vector(10, 10), 90f);

        Assert.assertFalse(r.hashCode() == this.roomObject.hashCode());
    }

    /**
     * Tests the equals method with a parameter which is null. Testcase should
     * return false.
     */
    @Test
    public void testEqualsParameterNull() {
        Object obj = null;
        Assert.assertFalse(this.roomObject.equals(obj));
    }

    /**
     * Tests the equals method with a different object. Testcase should return
     * false.
     */
    @Test
    public void testEqualsDifferentObject() {
        int differentObject = 1;
        Assert.assertFalse(this.roomObject.equals(differentObject));
    }

    /**
     * Tests the equals method with different RoomObjectType. Testcase should
     * return false.
     */
    @Test
    public void testEqualsDifferenRoomObjectType() {
        float density = 0.2f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(20, 50);
        float rotation = 20f;
        RoomObject newRoomObject = new RoomObject(type, position, rotation);
        newRoomObject.setRoomObjectType(type);

        Assert.assertFalse(this.roomObject.equals(newRoomObject));
    }

    /**
     * Tests the equals method with a different postion. Testcase should return
     * false.
     */
    @Test
    public void testEqualsDifferentPosition() {
        float density = 0.5f;
        String anim = "AnimationString";
        RoomObjectType type = new RoomObjectType(density, anim);

        Vector position = new Vector(1, 50);
        float rotation = 20f;
        RoomObject roomObject2 = new RoomObject(type, position, rotation);

        Assert.assertFalse(this.roomObject.equals(roomObject2));
    }
}
