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
import com.ffxvi.game.models.RoomObject;
import com.ffxvi.game.models.RoomObjectType;
import com.ffxvi.game.models.RoomType;
import com.ffxvi.game.support.Vector;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Acer
 */
public class RoomTypeTest {

    /**
     * The RoomType which is used for testing.
     */
    private RoomType roomType;

    /**
     * Initializes a RoomType object.
     */
    @Before
    public void initialize() {
        GameManager.resetInstance();
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        roomType = new RoomType(100, 100, roomObjects);
    }

    /**
     * Tests the constructor of RoomType. Also tests all the getters in this
     * class. There are no setters or other methods so this is the only
     * testmethod which is required for this class.
     */
    @Test
    public void testConstructorZeroRoomObjects() {
        int roomSizeX = 500;
        int roomSizeY = 1000;
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType newRoomType = new RoomType(roomSizeX, roomSizeY, roomObjects);

        Assert.assertEquals(roomSizeX, newRoomType.getRoomSizeX());
        Assert.assertEquals(roomSizeY, newRoomType.getRoomSizeY());
        Assert.assertTrue(this.listsProjectilesAreEqual(roomObjects, newRoomType.getRoomObjects()));
    }

    /**
     * Tests the constructor of RoomType. Also tests all the getters in this
     * class. There are no setters or other methods so this is the only
     * testmethod which is required for this class.
     */
    @Test
    public void testConstructorSingleRoomObject() {
        int roomSizeX = 500;
        int roomSizeY = 1000;
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();

        RoomObjectType roomObjectType = new RoomObjectType(0.5f, "animationString");

        Vector position = new Vector(100, 200);
        RoomObject roomObject = new RoomObject(roomObjectType, position, (float) 0.4);

        roomObjects.add(roomObject);

        RoomType newRoomType = new RoomType(roomSizeX, roomSizeY, roomObjects);

        Assert.assertEquals(roomSizeX, newRoomType.getRoomSizeX());
        Assert.assertEquals(roomSizeY, newRoomType.getRoomSizeY());
        Assert.assertTrue(this.listsProjectilesAreEqual(roomObjects, newRoomType.getRoomObjects()));
    }

    /**
     * Tests the constructor of RoomType. Also tests all the getters in this
     * class. There are no setters or other methods so this is the only
     * testmethod which is required for this class.
     */
    @Test
    public void testConstructorMultipleRoomObjects() {
        int roomSizeX = 500;
        int roomSizeY = 1000;
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();

        RoomObjectType roomObjectType = new RoomObjectType(0.5f, "animationString");

        Vector position = new Vector(100, 200);
        RoomObject roomObject = new RoomObject(roomObjectType, position, (float) 0.4);

        roomObjects.add(roomObject);

        RoomType newRoomType = new RoomType(roomSizeX, roomSizeY, roomObjects);

        Assert.assertEquals(roomSizeX, newRoomType.getRoomSizeX());
        Assert.assertEquals(roomSizeY, newRoomType.getRoomSizeY());
        Assert.assertTrue(this.listsProjectilesAreEqual(roomObjects, newRoomType.getRoomObjects()));
    }

    /**
     * Tests the constructor of RoomType. Also tests all the getters in this
     * class. There are no setters or other methods so this is the only
     * testmethod which is required for this class.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRoomSizeXZero() {
        int roomSizeX = 0;
        int roomSizeY = 1000;
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();

        new RoomType(roomSizeX, roomSizeY, roomObjects);
    }

    /**
     * Tests the constructor of RoomType. Also tests all the getters in this
     * class. There are no setters or other methods so this is the only
     * testmethod which is required for this class.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRoomSizeXNegative() {
        int roomSizeX = -10;
        int roomSizeY = 1000;
        ArrayList<RoomObject> roomObjects = new ArrayList();

        new RoomType(roomSizeX, roomSizeY, roomObjects);
    }

    /**
     * Tests the constructor of RoomType. Also tests all the getters in this
     * class. There are no setters or other methods so this is the only
     * testmethod which is required for this class.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRoomSizeYZero() {
        int roomSizeX = 500;
        int roomSizeY = 0;
        ArrayList<RoomObject> roomObjects = new ArrayList();

        new RoomType(roomSizeX, roomSizeY, roomObjects);
    }

    /**
     * Tests the constructor of RoomType. Also tests all the getters in this
     * class. There are no setters or other methods so this is the only
     * testmethod which is required for this class.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRoomSizeYNegative() {
        int roomSizeX = 500;
        int roomSizeY = -100;
        ArrayList<RoomObject> roomObjects = new ArrayList();

        new RoomType(roomSizeX, roomSizeY, roomObjects);
    }

    /**
     * Tests the constructor of RoomType. Also tests all the getters in this
     * class. There are no setters or other methods so this is the only
     * testmethod which is required for this class.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRoomObjectsNull() {
        int roomSizeX = 500;
        int roomSizeY = 1000;

        new RoomType(roomSizeX, roomSizeY, null);
    }

    /**
     * Tests the constructor of RoomType. Also tests all the getters in this
     * class. There are no setters or other methods so this is the only
     * testmethod which is required for this class.
     */
    @Test
    public void testGetRoomSizeX() {
        Assert.assertEquals(100, this.roomType.getRoomSizeX());
    }

    /**
     * Tests the constructor of RoomType. Also tests all the getters in this
     * class. There are no setters or other methods so this is the only
     * testmethod which is required for this class.
     */
    @Test
    public void testGetRoomSizeY() {
        Assert.assertEquals(100, this.roomType.getRoomSizeY());
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeSameObject() {
        int hashCodeFirst = this.roomType.hashCode();
        int hashCodeSecond = this.roomType.hashCode();

        junit.framework.Assert.assertEquals(hashCodeFirst, hashCodeSecond);
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeDifferentObjectSameValues() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType newRoomType = new RoomType(100, 100, roomObjects);

        junit.framework.Assert.assertEquals(this.roomType.hashCode(), newRoomType.hashCode());
    }

    @Test
    public void testHashcodeDifferentObjectDifferentValues() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType newRoomType = new RoomType(90, 80, roomObjects);

        junit.framework.Assert.assertFalse(this.roomType.hashCode() == newRoomType.hashCode());
    }

    /**
     * Tests if the equals method's return value is true when the passed weapon
     * has the same values as this game.
     */
    @Test
    public void testEqualsDifferentObjectSameValues() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType newRoomType = new RoomType(100, 100, roomObjects);

        junit.framework.Assert.assertTrue(this.roomType.equals(newRoomType));
    }

    /**
     * Tests if the equals method's return value is true when the passed weapon
     * is the same object.
     */
    @Test
    public void testEqualsSameObject() {

        junit.framework.Assert.assertTrue(this.roomType.equals(this.roomType));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is not a Weapon object.
     */
    @Test
    public void testEqualsObjectOtherClass() {

        junit.framework.Assert.assertFalse(this.roomType.equals(GameManager.getInstance()));
    }

    /**
     * Tests if the equals method's return value is false when the passed room
     * type's room size y is different.
     */
    @Test
    public void testEqualsRoomSizeYNotEqual() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType newRoomType = new RoomType(100, 110, roomObjects);

        junit.framework.Assert.assertFalse(this.roomType.equals(newRoomType));
    }

    /**
     * Tests if the equals method's return value is false when the passed room
     * type's room objects are different.
     */
    @Test
    public void testEqualsRoomObjectsNotEqual() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomObjectType roomObjectType = new RoomObjectType(0.5f, "animationString");
        Vector position = new Vector(100, 100);
        RoomObject roomObject = new RoomObject(roomObjectType, position, 230f);
        roomObjects.add(roomObject);
        RoomType newRoomType = new RoomType(100, 100, roomObjects);

        junit.framework.Assert.assertFalse(this.roomType.equals(newRoomType));
    }

    /**
     * Tests if the equals method's return value is false when the passed room
     * type's room size x is different.
     */
    @Test
    public void testEqualsRoomSizeXNotEqual() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType newRoomType = new RoomType(110, 100, roomObjects);

        junit.framework.Assert.assertFalse(this.roomType.equals(newRoomType));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is null.
     */
    @Test
    public void testEqualsObjectNull() {

        junit.framework.Assert.assertFalse(this.roomType.equals(null));
    }

    /**
     * Compares two arraylists of room objects to see if their length and their
     * items are equal.
     *
     * @param alExpected an ArrayList containing the expected projectiles.
     * @param alActual an ArrayList containing the actual projectiles.
     * @return A boolean indicating if the array lists were equal according to
     * this method.
     */
    private boolean listsProjectilesAreEqual(List<RoomObject> alExpected, List<RoomObject> alActual) {
        if (alExpected.size() != alActual.size()) {
            return false;
        }

        for (RoomObject roExpected : alExpected) {
            RoomObject actualProjectile = alActual.get(alExpected.indexOf(roExpected));
            if (!actualProjectile.equals(roExpected)) {
                return false;
            }
        }

        return true;
    }

}
