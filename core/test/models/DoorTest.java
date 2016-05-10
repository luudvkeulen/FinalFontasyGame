/*
 * (C) Copyright 2016 - S33A
 * Final Fontasy XVI, Version 1.0.
 * 
 * Contributors:
 *   Pim Janissen
 *   Luud van Keulen
 *   Robin de Kort
 *   Koen Schilders
 *   Guido Thomassen
 *   Joel Verbeek
 */
package models;

import com.ffxvi.game.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import support.Vector;

/**
 * This class tests the door class
 *
 * @author Guido
 */
public class DoorTest {

    /**
     * A door object used for testing.
     */
    private Door door;

    /**
     * Initializes the door object field.
     */
    @Before
    public void initialize() {
                GameManager.resetInstance();
        Vector vector = new Vector(10, 20);
        float rotation = 90f;
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(40, 20, roomObjects);
        Room room = new Room(vector, roomType);
        this.door = new Door(vector, rotation, room);
    }

    /**
     * Set the connected door in another room.
     */
    @Test
    public void testSetAndGetConnectedDoor() {
        Vector vector = new Vector(50, 70);
        float rotation = 0f;
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(40, 20, roomObjects);
        Room room2 = new Room(vector, roomType);
        Door newDoor = new Door(vector, rotation, room2);

        try {
            this.door.setConnectedDoor(newDoor);
        } catch (DoorInSameRoomException ex) {
            Logger.getLogger(DoorTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Get the door from the field object and check
        //if it is not null.
        Assert.assertNotNull(door.getConnectedDoor());
    }

    /**
     * Set a door object to null, and set it as connectedDoor. This testcase
     * expects an IllegalArgumentException.
     *
     * @throws DoorInSameRoomException when door is in the same room.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetConnectedDoorNull() throws DoorInSameRoomException {
        Door parameter = null;
        door.setConnectedDoor(parameter);
    }

    /**
     * Set connected door to the same door. This testcase expects a
     * DoorInSameRoomException.
     *
     * @throws customexceptions.DoorInSameRoomException when door is in same
     * room.
     */
    @Test(expected = DoorInSameRoomException.class)
    public void testSetConnectedDoorSameRoom() throws DoorInSameRoomException {
        Door door2 = door;
        door.setConnectedDoor(door2);
    }

    /**
     * Test constructor with empty room. This testcase expects an
     * IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void ConstructorWithEmptyRoom() {
        Room room = null;
        Vector position = new Vector(20, 20);
        float rotation = 90f;

        Door door2 = new Door(position, rotation, room);
    }

    /**
     * Gets the room, and checks if it's not null.
     */
    @Test
    public void testGetRoom() {
        Assert.assertNotNull(door.getRoom());
    }

    /**
     * Tests if the constructor sets all the fields.
     */
    @Test
    public void testConstructor() {
        Assert.assertNotNull(door.getRoom());
    }

    /**
     * Tests if the room of the player has changed when he opens a door. Checks
     * if the player is in the new room.
     * @throws DoorInSameRoomException
     */
    @Test
    public void testOpenDoor() throws DoorInSameRoomException {
        String name = "Pleeyer1";
        Vector pos = new Vector(20, 50);
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(40, 20, roomObjects);
        Room room = new Room(pos, roomType);
        String animationString = "animationString";
        Player p1 = new Player(name, 100, pos, animationString, room);

        Vector vector = new Vector(20, 20);
        float rotation = 180f;
        ArrayList<RoomObject> roomObjects2 = new ArrayList<RoomObject>();
        RoomType roomType2 = new RoomType(40, 20, roomObjects2);
        Room room2 = new Room(vector, roomType2);
        Door door2 = new Door(vector, rotation, room2);

        door.setConnectedDoor(door2);
        door.openDoor(p1);

        Assert.assertEquals(this.door.getConnectedDoor().getRoom(), p1.getRoom());
        Assert.assertEquals(this.door.getConnectedDoor().getPosition(), p1.getPosition());
    }

    /**
     * Open a door with a player which is null. This testcase expects an
     * IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testOpenDoorPlayerNull() {
        Player p = null;
        door.openDoor(p);
    }

    /**
     * Test the openDoor method when the connectedDoor is still null. This
     * testcase expects the openDoor method returns false.
     */
    @Test
    public void testOpenDoorConnectedDoorNull() {
        String name = "Pleeyer1";
        Vector pos = new Vector(20, 50);
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(40, 20, roomObjects);
        Room room = new Room(pos, roomType);
        String animationString = "animationString";
        Player p1 = new Player(name, 100, pos, animationString, room);

        Assert.assertFalse(this.door.openDoor(p1));
    }

    /**
     * Tests if hashcode returns same number on same object. Testcase should
     * return true.
     */
    @Test
    public void testHashCodeSameObject() {
        Door door2 = door;
        int first = door.hashCode();
        int second = door2.hashCode();

        Assert.assertTrue(first == second);
    }
    
    /**
     * Test if hashcode returns same number on different object with same values are equals
     *  should return true
     */
    @Test
    public void testHashCodeDifferentObjectSameValues(){
        Vector vector = new Vector(10, 20);
        float rotation = 90f;
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(40, 20, roomObjects);
        Room room = new Room(vector, roomType);
        Door door2 = new Door(vector, rotation, room);
        int first = door.hashCode();
        int second = door2.hashCode();
        Assert.assertTrue(first == second);
    }

    /**
     * Tests if hashcode returns same number on different object. Testcase
     * should return false.
     */
    @Test
    public void testHashCodeDifferentObjectDifferentValues() {
        Vector position = new Vector(20, 10);
        float rotation = 90f;
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(40, 20, roomObjects);
        Room room = new Room(position, roomType);

        Door door2 = new Door(position, rotation, room);
        int first = door.hashCode();
        int second = door2.hashCode();

        Assert.assertFalse(first == second);
    }

    /**
     * Tests the equals method with the same object. Testcase should return
     * true.
     */
    @Test
    public void testEqualsSameObject() {
        Door door2 = door;
        Assert.assertTrue(door.equals(door2));
    }

    /**
     * Tests the equals method with null. Testcase should return false.
     */
    @Test
    public void testEqualsParameterNull() {
        Door obj = null;
        Assert.assertFalse(this.door.equals(obj));
    }

    /**
     * Tests the equals method with different class. Testcase should return
     * false.
     */
    @Test
    public void testEqualsDifferentClass() {
        RoomObjectType roomObjectType = new RoomObjectType(0.5f, "animationString");
        Assert.assertFalse(door.equals(roomObjectType));
    }

    /**
     * Tests the equals method with different door. Testcase should return
     * false.
     * @throws DoorInSameRoomException
     */
    @Test
    public void testEqualsOtherConnectedDoor() throws DoorInSameRoomException {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(40, 20, roomObjects);
        Vector position = new Vector(20, 10);
        Room room = new Room(position, roomType);

        float rotation = 90f;
        Vector doorPosition = new Vector(5, 3);
        Door newDoor = new Door(position, rotation, room);

        newDoor.setRotation(door.getRotation());
        newDoor.setConnectedDoor(door);

        Assert.assertFalse(door.equals(newDoor));
    }

    /**
     * Tests the equals method with another room. Testcase should return false.
     */
    @Test
    public void testEqualsOtherRoom() {
        Vector position = new Vector(20, 10);
        float rotation = 90f;
        ArrayList<RoomObject> roomObjects2 = new ArrayList<RoomObject>();
        RoomType roomType2 = new RoomType(40, 20, roomObjects2);
        Room room2 = new Room(position, roomType2);

        Door door2 = new Door(door.getPosition(), door.getRotation(), room2);

        Assert.assertFalse(door.equals(door2));
    }
}
