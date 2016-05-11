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
import com.ffxvi.game.models.RoomObjectType;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Acer
 */
public class RoomObjectTypeTest {

    /**
     * The instance of RoomObjectType which we use for testing.
     */
    RoomObjectType type;

    /**
     * Initializes the RoomOjectType instance of the field.
     */
    @Before
    public void initalize() {
        GameManager.resetInstance();
        float density = 1.0f;
        String a = "animationString";
        type = new RoomObjectType(density, a);
    }

    /**
     * Tests the constructor of RoomObjectType by getting all the fields. Also
     * tests the getters which are the class. So, this method tests all the
     * methods in the class.
     */
    @Test
    public void testRoomObjectType() {
        assertNotNull(type.getAnimation());
        assertNotNull(type.getDensity());
    }

    /**
     * Test the constructor with a too high density.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorDensityTooHigh() {
        float density = 2.0f;
        String a = "animationString";
        type = new RoomObjectType(density, a);
    }

    /**
     * Test the constructor with a too low density.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorDensityTooLow() {
        float density = -1f;
        String a = "animationString";
        type = new RoomObjectType(density, a);
    }

    /**
     * Test the constructor with a String which is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnimationPathNull() {
        float density = 0.5f;
        type = new RoomObjectType(density, null);
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeSameObject() {
        int hashCodeFirst = this.type.hashCode();
        int hashCodeSecond = this.type.hashCode();

        junit.framework.Assert.assertEquals(hashCodeFirst, hashCodeSecond);
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeDifferentObjectSameValues() {
        float density = 1.0f;
        String a = "animationString";
        RoomObjectType newType = new RoomObjectType(density, a);

        junit.framework.Assert.assertEquals(this.type.hashCode(), newType.hashCode());
    }

    /**
     * Tests if the hashcode method's return value is not equal when a different
     * object calls them.
     */
    @Test
    public void testHashcodeDifferentObjectDifferentValues() {
        float density = 1.0f;
        String a = "ababa";
        RoomObjectType newType = new RoomObjectType(density, a);

        junit.framework.Assert.assertFalse(this.type.hashCode() == newType.hashCode());
    }

    /**
     * Tests if the equals method's return value is true when the passed room
     * object type has the same values as this game.
     */
    @Test
    public void testEqualsDifferentObjectSameValues() {
        float density = 1.0f;
        String a = "animationString";
        RoomObjectType newType = new RoomObjectType(density, a);

        junit.framework.Assert.assertTrue(this.type.equals(newType));
    }

    /**
     * Tests if the equals method's return value is true when the passed room
     * object type is the same object.
     */
    @Test
    public void testEqualsSameObject() {

        junit.framework.Assert.assertTrue(this.type.equals(this.type));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is not a RoomObjectType object.
     */
    @Test
    public void testEqualsObjectOtherClass() {

        junit.framework.Assert.assertFalse(this.type.equals(GameManager.getInstance()));
    }

    /**
     * Tests if the equals method's return value is false when the passed room
     * object type's density is different.
     */
    @Test
    public void testEqualsRoomSizeDensityNotEqual() {
        float density = 0.2f;
        String a = "animationString";
        RoomObjectType newType = new RoomObjectType(density, a);

        junit.framework.Assert.assertFalse(this.type.equals(newType));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is null.
     */
    @Test
    public void testEqualsObjectNull() {
        junit.framework.Assert.assertFalse(this.type.equals(null));
    }
}
