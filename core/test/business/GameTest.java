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
package business;

import com.ffxvi.game.business.Game;
import com.ffxvi.game.business.GameManager;
import com.ffxvi.game.models.Player;
import com.ffxvi.game.models.Room;
import com.ffxvi.game.models.RoomObject;
import com.ffxvi.game.models.RoomType;
import com.ffxvi.game.support.Vector;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 *
 * @author Pim Janissen
 */
public class GameTest {

    /**
     * A game object used for testing.
     */
    private Game game;

    /**
     * Initializes the game object before every test.
     */
    @Before
    public void initialize() {
        GameManager.resetInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-05-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = "Official Game";

        this.game = new Game(startTime, name);
    }

    /**
     * Tests if the getPlayers method returns the same playerName as given to it
     * by zero calls to the addPlayer method in this testmethod.
     */
    @Test
    public void testGetPlayersZeroPlayers() {
        /**
         * Gets the players which are currently in the game.
         *
         * @return An ArrayList containing the players.
         */

        ArrayList<String> alExpected = new ArrayList<String>();
        Assert.assertTrue(this.listsPlayersAreEqual(alExpected, this.game.getPlayers()));
    }

    /**
     * Tests if the getPlayers method returns the same playerName as given to it
     * by a single call to the addPlayer method in this testmethod.
     */
    @Test
    public void testGetPlayersSinglePlayer() {
        /**
         * Gets the players which are currently in the game.
         *
         * @return An ArrayList containing the players.
         */

        RoomType roomType = new RoomType(400, 800, new ArrayList<RoomObject>());
        Vector position = new Vector(2000, 2000);
        this.game.addRoom(new Room(position, roomType));

        String playerName = "Erik Janssen";
        this.game.addPlayer(playerName, "testAnimation");

        ArrayList<String> alExpected = new ArrayList<String>();
        alExpected.add(playerName);
        Assert.assertTrue(this.listsPlayersAreEqual(alExpected, this.game.getPlayers()));
    }

    /**
     * Tests if the getPlayers method returns the same playerName as given to it
     * by several calls to the addPlayer method in this testmethod.
     */
    @Test
    public void testGetPlayersMultiplePlayers() {
        /**
         * Gets the players which are currently in the game.
         *
         * @return An ArrayList containing the players.
         */

        RoomType roomType = new RoomType(400, 800, new ArrayList<RoomObject>());
        Vector position = new Vector(2000, 2000);
        this.game.addRoom(new Room(position, roomType));

        String playerName1 = "Erik Janssen";
        String playerName2 = "Erica Janssen";
        String playerName3 = "Peter Peeters";

        this.game.addPlayer(playerName1, "testAnimation");
        this.game.addPlayer(playerName2, "testAnimation");
        this.game.addPlayer(playerName3, "testAnimation");

        ArrayList<String> alExpected = new ArrayList<String>();
        alExpected.add(playerName1);
        alExpected.add(playerName2);
        alExpected.add(playerName3);

        Assert.assertTrue(this.listsPlayersAreEqual(alExpected, this.game.getPlayers()));
    }

    /**
     * Tests if the getRooms method returns the same rooms as given to the game
     * by zero calls to the addRoom method in this testmethod.
     */
    @Test
    public void testGetRoomsZeroRooms() {
        /**
         * Gets the rooms which are currently in the game.
         *
         * @return An ArrayList containing the rooms.
         */

        ArrayList<Room> rooms = new ArrayList<Room>();

        Assert.assertTrue(this.listsRoomsAreEqual(rooms, this.game.getRooms()));
    }

    /**
     * Tests if the getRooms method returns the same rooms as given to the game
     * by a single call to the addRoom method in this testmethod.
     */
    @Test
    public void testGetRoomsSingleRoom() {
        /**
         * Gets the rooms which are currently in the game.
         *
         * @return An ArrayList containing the rooms.
         */
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(600, 900, roomObjects);
        Vector location = new Vector(2000, 3200);

        Room room = new Room(location, roomType);
        this.game.addRoom(room);

        ArrayList<Room> rooms = new ArrayList<Room>();
        rooms.add(room);

        Assert.assertTrue(this.listsRoomsAreEqual(rooms, this.game.getRooms()));
    }

    /**
     * Tests if the getRooms method returns the same rooms as given to the game
     * by several calls to the addRoom method in this testmethod.
     */
    @Test
    public void testGetRoomsMultipleRooms() {
        /**
         * Gets the rooms which are currently in the game.
         *
         * @return An ArrayList containing the rooms.
         */
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(600, 900, roomObjects);

        Room room1 = new Room(new Vector(2000, 3200), roomType);
        Room room2 = new Room(new Vector(4000, 3000), roomType);
        Room room3 = new Room(new Vector(4500, 300), roomType);
        this.game.addRoom(room1);
        this.game.addRoom(room2);
        this.game.addRoom(room3);

        List<Room> rooms = new ArrayList<Room>();
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        Assert.assertTrue(this.listsRoomsAreEqual(rooms, this.game.getRooms()));
    }

    /**
     * Tests if the getStartTime method returns the same value as given to it by
     * the constructor in the initalize method.
     */
    @Test
    public void testGetStartTime() {
        /**
         * Gets the start date and time of the game.
         *
         * @return The start date and time of the game.
         */

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-05-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assert.assertEquals(startTime, this.game.getStartTime());
    }

    /**
     * Tests if the getName method returns the same value as given to it by the
     * constructor in the initalize method.
     */
    @Test
    public void testGetName() {
        /**
         * Gets the name of the game.
         *
         * @return The name of the game.
         */

        String name = "Official Game";

        Assert.assertEquals(name, this.game.getName());
    }

    /**
     * Tests if the setName method sets a new value which is acceptable (meaning
     * it is neither null nor is an empty string (excluding spaces)) by setting
     * it and then checking if the getName method returns the same value.
     */
    @Test
    public void testSetNameAcceptableValue() {
        /**
         * Sets the name of the game.
         *
         * @param name the new name of the game.
         */

        String newName = "Unofficial Server";

        this.game.setName(newName);

        Assert.assertEquals(newName, this.game.getName());
    }

    /**
     * Tests if the setName method does not set a new value with an empty String
     * by checking if it throws an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetNameEmptyString() {
        /**
         * Sets the name of the game.
         *
         * @param name the new name of the game.
         */

        String newName = "";

        this.game.setName(newName);
    }

    /**
     * Tests if the setName method does not set a new value with a String
     * exclusively containing spaces by checking if it throws an
     * IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetNameOnlySpaces() {
        /**
         * Sets the name of the game.
         *
         * @param name the new name of the game.
         */

        String newName = "  ";

        this.game.setName(newName);
    }

    /**
     * Tests if the setName method does not set a new value when given null
     * instead of a String by checking if it throws an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetNameNull() {
        /**
         * Initializes a new game object.
         *
         * @param startTime the start date and time of the game.
         * @param name the name of the game. If the name of the game is an empty
         * string (excluding spaces), throws an IllegalArgumentException
         */

        this.game.setName(null);
    }

    /**
     * Tests if the constructor actually sets the values of the given parameters
     * correctly to it's private fields by checking if the get method returns
     * the same values as given to the object by its constructor.
     */
    @Test
    public void testConstructorNormalValues() {
        /**
         * Initializes a new game object.
         *
         * @param startTime the start date and time of the game.
         * @param name the name of the game.
         */

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-05-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = "Official Game";

        Game newGame = new Game(startTime, name);

        Assert.assertEquals(startTime, newGame.getStartTime());
        Assert.assertEquals(name, newGame.getName());
    }

    /**
     * Tests if the constructor actually throws an IllegalArgumentException when
     * the given name is an empty string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameEmptyString() {
        /**
         * Initializes a new game object.
         *
         * @param startTime the start date and time of the game.
         * @param name the name of the game. If the name of the game is an empty
         * string (excluding spaces), throws an IllegalArgumentException
         */

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-05-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = "";

        new Game(startTime, name);
    }

    /**
     * Tests if the constructor actually throws an IllegalArgumentException when
     * the given name exclusievely contains spaces.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameExclusivelySpaces() {
        /**
         * Initializes a new game object.
         *
         * @param startTime the start date and time of the game.
         * @param name the name of the game. If the name of the game is an empty
         * string (excluding spaces), throws an IllegalArgumentException
         */

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-05-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = "         ";

        new Game(startTime, name);
    }

    /**
     * Tests if the constructor actually throws an IllegalArgumentException when
     * the given start time is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorStartTimeNull() {
        /**
         * Initializes a new game object.
         *
         * @param startTime the start date and time of the game.
         * @param name the name of the game. If the name of the game is an empty
         * string (excluding spaces), throws an IllegalArgumentException
         */

        String name = "Unofficial Game Server";

        new Game(null, name);
    }

    /**
     * Tests if the constructor actually throws an IllegalArgumentException when
     * the given start time is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameNull() {
        /**
         * Initializes a new game object.
         *
         * @param startTime the start date and time of the game.
         * @param name the name of the game. If the name of the game is an empty
         * string (excluding spaces), throws an IllegalArgumentException
         */

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-05-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        new Game(startTime, null);
    }

    /**
     * Tests if a player with a given playername that is not yet used by a
     * player that is already in the game is succesfully added to the list of
     * players in the game by checking if the addPlayer method returns true and
     * that a player object with the given name is found in the list returned by
     * the getPlayers method.
     */
    @Test
    public void testAddPlayerDifferentName() {
        /**
         * Adds a new player to the game.
         *
         * @param name the name of the player. Has to be unique within the game.
         * When the given playerName is an empty string (excluding
         * spaces), throws a new IllegalArgumentException. @return boolean
         * indicating whether the player was succesfully added.
         * @return boolean indicating wheth
         * e
         * r the player was successfully added. >>>>>>> origin/master If a
         * player with the given name already exists, returns false. Otherwise,
         * return true.
         */

        RoomType roomType = new RoomType(400, 800, new ArrayList<RoomObject>());
        Vector position = new Vector(2000, 2000);
        this.game.addRoom(new Room(position, roomType));

        String playerName = "Erik Janssen";
        Assert.assertTrue(this.game.addPlayer(playerName,"testAnimation"));
        Assert.assertTrue(this.playerNameExistsInList(playerName, this.game.getPlayers()));
    }

    /**
     * Tests if a player with a given playername that is already used by a
     * player that is already in the game is not added to the list of players in
     * the game by checking if the addPlayer method returns false and that a
     * player object with the given name is not found in the list returned by
     * the getPlayers method.
     */
    @Test
    public void testAddPlayerExistingName() {
        /**
         * Adds a new player to the game.
         *
         * @param name the name of the player. Has to be unique within the game.
         * When the given playerName is an empty string (excluding spaces),
         * throws a new IllegalArgumentException.
         * @return boolean indicating whether the player was succesfully added.
         * If a player with the given name already exists, returns false.
         * Otherwise, return true.
         */

        RoomType roomType = new RoomType(400, 800, new ArrayList<RoomObject>());
        Vector position = new Vector(2000, 2000);
        this.game.addRoom(new Room(position, roomType));

        String playerName = "Erik Janssen";
        this.game.addPlayer(playerName,"testAnimation");

        Assert.assertFalse(this.game.addPlayer(playerName,"testAnimation"));
        Assert.assertTrue(this.playerNameExistsInList(playerName, this.game.getPlayers()));
    }

    /**
     * Tests if a player with a given playername that is different only by a
     * couple of lowercase/uppercase differences from a player with a playername
     * who is already in the game is succesfully added to the list of players in
     * the game by checking if the addPlayer method returns true and that a
     * player object with the given name is found in the list returned by the
     * getPlayers method.
     */
    @Test
    public void testAddPlayerExistingNameWithDifferentCaps() {
        /**
         * Adds a new player to the game.
         *
         * @param name the name of the player. Has to be unique within the game.
         * When the given playerName is an empty string (excluding spaces),
         * throws a new IllegalArgumentException.
         * @return boolean indicating whether the player was succesfully added.
         * If a player with the given name already exists, returns false.
         * Otherwise, return true.
         */
        RoomType roomType = new RoomType(400, 800, new ArrayList<RoomObject>());
        Vector position = new Vector(2000, 2000);
        this.game.addRoom(new Room(position, roomType));

        String playerName = "erik JANssen";
        Assert.assertTrue(this.game.addPlayer(playerName,"testAnimation"));
        Assert.assertTrue(this.playerNameExistsInList(playerName, this.game.getPlayers()));
    }

    /**
     * Tests if a player with a given playername that is an empty string is not
     * added to the list of players in the game by checking if the addPlayer
     * method throws an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddPlayerNameExclusivelyEmptyString() {
        /**
         * Adds a new player to the game.
         *
         * @param name the name of the player. Has to be unique within the game.
         * When the given playerName is an empty string (excluding spaces),
         * throws a new IllegalArgumentException.
         * @return boolean indicating whether the player was succesfully added.
         * If a player with the given name already exists, returns false.
         * Otherwise, return true.
         */

        String playerName = "";
        this.game.addPlayer(playerName,"testAnimation");
    }

    /**
     * Tests if a player with a given playername that is a string exclusively
     * containing spaces is not added to the list of players in the game by
     * checking if the addPlayer method throws an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddPlayerNameExclusivelySpaces() {
        /**
         * Adds a new player to the game.
         *
         * @param name the name of the player. Has to be unique within the game.
         * When the given playerName is an empty string (excluding spaces),
         * throws a new IllegalArgumentException.
         * @return boolean indicating whether the player was succesfully added.
         * If a player with the given name already exists, returns false.
         * Otherwise, return true.
         */

        String playerName = "   ";
        this.game.addPlayer(playerName,"testAnimation");
    }

    /**
     * Tests if a player with a given playername that is null is not added to
     * the list of players in the game by checking if the addPlayer method
     * throws an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddPlayerNameNull() {
        /**
         * Adds a new player to the game.
         *
         * @param name the name of the player. Has to be unique within the game.
         * When the given playerName is an empty string (excluding spaces),
         * throws a new IllegalArgumentException.
         * @return boolean indicating whether the player was succesfully added.
         * If a player with the given name already exists, returns false.
         * Otherwise, return true.
         */

        this.game.addPlayer(null,null);
    }

    /**
     * Tests if a player with a given playername is removed from the list of
     * players by the removePlayer method when the given name is of an existing
     * playerby checking if the player is not present in the list returned by
     * the getPlayers method.
     */
    @Test
    public void testRemovePlayerExistingPlayer() {
        /**
         * Removes a player with the given name from this game.
         *
         * @param name the name of the player which is to be removed. If the
         * name of the game is an empty string (excluding spaces), throws an
         * IllegalArgumentException
         */

        RoomType roomType = new RoomType(400, 800, new ArrayList<RoomObject>());
        Vector position = new Vector(2000, 2000);
        this.game.addRoom(new Room(position, roomType));

        String playerName = "Erik Janssen";

        this.game.addPlayer(playerName,"testAnimation");
        this.game.removePlayer(playerName);

        Assert.assertFalse(this.playerNameExistsInList(playerName, this.game.getPlayers()));
    }

    /**
     * Tests if a player with a given playername is removed from the list of
     * players by the removePlayer method when the given name is of a
     * non-existing player by checking if the player is not present in the list
     * returned by the getPlayers method.
     */
    @Test
    public void testRemovePlayerNonExistingPlayer() {
        /**
         * Removes a player with the given name from this game.
         *
         * @param name the name of the player which is to be removed. If the
         * name of the game is an empty string (excluding spaces), throws an
         * IllegalArgumentException
         */

        String playerName = "Erik Janssen";

        this.game.removePlayer(playerName);

        Assert.assertFalse(this.playerNameExistsInList(playerName, this.game.getPlayers()));
    }

    /**
     * Tests if a player with a given playername is not removed from the list of
     * players by the removePlayer method when the given name is an empty string
     * by checking if an IllegalArgumentException is thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemovePlayerNameEmptyString() {
        /**
         * Removes a player with the given name from this game.
         *
         * @param name the name of the player which is to be removed. If the
         * name of the game is an empty string (excluding spaces), throws an
         * IllegalArgumentException
         */

        String playerName = "";

        this.game.removePlayer(playerName);
    }

    /**
     * Tests if a player with a given playername is not removed from the list of
     * players by the removePlayer method when the given name is a string
     * exclusively containing spaces by checking if an IllegalArgumentException
     * is thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemovePlayerNameExclusivelySpaces() {
        /**
         * Removes a player with the given name from this game.
         *
         * @param name the name of the player which is to be removed. If the
         * name of the game is an empty string (excluding spaces), throws an
         * IllegalArgumentException
         */

        String playerName = "   ";

        this.game.removePlayer(playerName);
    }

    /**
     * Tests if a player with a given playername is not removed from the list of
     * players by the removePlayer method when the given name is null by
     * checking if an IllegalArgumentException is thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemovePlayerNameNull() {
        /**
         * Removes a player with the given name from this game.
         *
         * @param name the name of the player which is to be removed. If the
         * name of the game is an empty string (excluding spaces), throws an
         * IllegalArgumentException
         */

        this.game.removePlayer(null);
    }

    /**
     * Tests if a room is added to the list of rooms by the addRoom method when
     * the given room's coordinates do not overlap the coordinates of a room
     * which is already added to the game since there are no other rooms in the
     * game by checking if the addRoom method returns true and the room is
     * present in the list returned by the getRooms method.
     */
    @Test
    public void testAddRoomNonOverlappingCoordinatesNoRooms() {
        /**
         * Adds a new room to the game.
         *
         * @param room the room to add.
         * @return A boolean returning: if the given room's coordinates overlap
         * with the coordinates of a room which is already in the game.
         */

        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(600, 900, roomObjects);

        Room room = new Room(new Vector(2000, 3200), roomType);

        Assert.assertTrue(this.game.addRoom(room));
        Assert.assertTrue(this.game.getRooms().contains(room));
    }

    /**
     * Tests if a room is added to the list of rooms by the addRoom method when
     * the given room's coordinates do not overlap the coordinates of a room
     * which is already added to the game when there is a room directly adjacent
     * to it at it's top side by checking if the addRoom method returns true and
     * the room is present in the list returned by the getRooms method.
     */
    @Test
    public void testAddRoomNonOverlappingCoordinatesDirectlyAdjacentRoomTop() {
        /**
         * Adds a new room to the game.
         *
         * @param room the room to add.
         * @return A boolean returning: if the given room's coordinates overlap
         * with the coordinates of a room which is already in the game.
         */

        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(600, 900, roomObjects);

        Room existingRoom = new Room(new Vector(2000, 3200), roomType);
        this.game.addRoom(existingRoom);

        Room newRoom = new Room(new Vector(2000, 2300), roomType);

        Assert.assertTrue(this.game.addRoom(newRoom));
        Assert.assertTrue(this.game.getRooms().contains(newRoom));
    }

    /**
     * Tests if a room is added to the list of rooms by the addRoom method when
     * the given room's coordinates do not overlap the coordinates of a room
     * which is already added to the game when there is a room directly adjacent
     * to it at it's right side by checking if the addRoom method returns true
     * and the room is present in the list returned by the getRooms method.
     */
    @Test
    public void testAddRoomNonOverlappingCoordinatesDirectlyAdjacentRoomRight() {
        /**
         * Adds a new room to the game.
         *
         * @param room the room to add.
         * @return A boolean returning: if the given room's coordinates overlap
         * with the coordinates of a room which is already in the game.
         */

        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(600, 900, roomObjects);

        Room existingRoom = new Room(new Vector(2000, 3200), roomType);
        this.game.addRoom(existingRoom);

        Room newRoom = new Room(new Vector(2600, 3200), roomType);

        Assert.assertTrue(this.game.addRoom(newRoom));
        Assert.assertTrue(this.game.getRooms().contains(newRoom));
    }

    /**
     * Tests if a room is added to the list of rooms by the addRoom method when
     * the given room's coordinates do not overlap the coordinates of a room
     * which is already added to the game when there is a room directly adjacent
     * to it at it's bottom side by checking if the addRoom method returns true
     * and the room is present in the list returned by the getRooms method.
     */
    @Test
    public void testAddRoomNonOverlappingCoordinatesDirectlyAdjacentRoomBottom() {
        /**
         * Adds a new room to the game.
         *
         * @param room the room to add.
         * @return A boolean returning: if the given room's coordinates overlap
         * with the coordinates of a room which is already in the game.
         */

        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(600, 900, roomObjects);

        Room existingRoom = new Room(new Vector(2000, 3200), roomType);
        this.game.addRoom(existingRoom);

        Room newRoom = new Room(new Vector(2000, 4100), roomType);

        Assert.assertTrue(this.game.addRoom(newRoom));
        Assert.assertTrue(this.game.getRooms().contains(newRoom));
    }

    /**
     * Tests if a room is added to the list of rooms by the addRoom method when
     * the given room's coordinates do not overlap the coordinates of a room
     * which is already added to the game when there is a room directly adjacent
     * to it at it's left side by checking if the addRoom method returns true
     * and the room is present in the list returned by the getRooms method.
     */
    @Test
    public void testAddRoomNonOverlappingCoordinatesDirectlyAdjacentRoomLeft() {
        /**
         * Adds a new room to the game.
         *
         * @param room the room to add.
         * @return A boolean returning: if the given room's coordinates overlap
         * with the coordinates of a room which is already in the game.
         */

        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(600, 900, roomObjects);

        Room existingRoom = new Room(new Vector(2000, 3200), roomType);
        this.game.addRoom(existingRoom);

        Room newRoom = new Room(new Vector(1400, 3200), roomType);

        Assert.assertTrue(this.game.addRoom(newRoom));
        Assert.assertTrue(this.game.getRooms().contains(newRoom));
    }

    /**
     * Tests if a room is added to the list of rooms by the addRoom method when
     * the given room's coordinates overlap at the room's top side with the
     * coordinates of a room which is already added to the game by checking if
     * the addRoom method returns true and the room is present in the list
     * returned by the getRooms method.
     */
    @Test
    public void testAddRoomOverlappingCoordinatesTop() {
        /**
         * Adds a new room to the game.
         *
         * @param room the room to add.
         * @return A boolean returning: if the given room's coordinates overlap
         * with the coordinates of a room which is already in the game.
         */

        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(600, 900, roomObjects);

        Room existingRoom = new Room(new Vector(2000, 3200), roomType);
        this.game.addRoom(existingRoom);

        Room newRoom = new Room(new Vector(2000, 2301), roomType);

        Assert.assertFalse(this.game.addRoom(newRoom));
        Assert.assertFalse(this.game.getRooms().contains(newRoom));
    }

    /**
     * Tests if a room is added to the list of rooms by the addRoom method when
     * the given room's coordinates overlap at the room's right side with the
     * coordinates of a room which is already added to the game by checking if
     * the addRoom method returns true and the room is present in the list
     * returned by the getRooms method.
     */
    @Test
    public void testAddRoomOverlappingCoordinatesRight() {
        /**
         * Adds a new room to the game.
         *
         * @param room the room to add.
         * @return A boolean returning: if the given room's coordinates overlap
         * with the coordinates of a room which is already in the game.
         */

        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(600, 900, roomObjects);

        Room existingRoom = new Room(new Vector(2000, 3200), roomType);
        this.game.addRoom(existingRoom);

        Room newRoom = new Room(new Vector(2599, 3200), roomType);

        Assert.assertFalse(this.game.addRoom(newRoom));
        Assert.assertFalse(this.game.getRooms().contains(newRoom));
    }

    /**
     * Tests if a room is added to the list of rooms by the addRoom method when
     * the given room's coordinates overlap at the room's bottom side with the
     * coordinates of a room which is already added to the game by checking if
     * the addRoom method returns true and the room is present in the list
     * returned by the getRooms method.
     */
    @Test
    public void testAddRoomOverlappingCoordinatesBottom() {
        /**
         * Adds a new room to the game.
         *
         * @param room the room to add.
         * @return A boolean returning: if the given room's coordinates overlap
         * with the coordinates of a room which is already in the game.
         */

        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(600, 900, roomObjects);

        Room existingRoom = new Room(new Vector(2000, 3200), roomType);
        this.game.addRoom(existingRoom);

        Room newRoom = new Room(new Vector(2000, 4099), roomType);

        Assert.assertFalse(this.game.addRoom(newRoom));
        Assert.assertFalse(this.game.getRooms().contains(newRoom));
    }

    /**
     * Tests if a room is added to the list of rooms by the addRoom method when
     * the given room's coordinates overlap at the room's left side with the
     * coordinates of a room which is already added to the game by checking if
     * the addRoom method returns true and the room is present in the list
     * returned by the getRooms method.
     */
    @Test
    public void testAddRoomOverlappingCoordinatesLeft() {
        /**
         * Adds a new room to the game.
         *
         * @param room the room to add.
         * @return A boolean returning: if the given room's coordinates overlap
         * with the coordinates of a room which is already in the game.
         */

        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(600, 900, roomObjects);

        Room existingRoom = new Room(new Vector(2000, 3200), roomType);
        this.game.addRoom(existingRoom);

        Room newRoom = new Room(new Vector(1401, 3200), roomType);

        Assert.assertFalse(this.game.addRoom(newRoom));
        Assert.assertFalse(this.game.getRooms().contains(newRoom));
    }

    /**
     * Tests if a room is added to the list of rooms by the addRoom method when
     * the given room is null by checking if the addRoom method throws an
     * IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddRoomNull() {
        /**
         * Adds a new room to the game.
         *
         * @param room the room to add.
         * @return A boolean returning: if the given room's coordinates overlap
         * with the coordinates of a room which is already in the game.
         */

        this.game.addRoom(null);
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeSameObject() {
        int hashCodeFirst = this.game.hashCode();
        int hashCodeSecond = this.game.hashCode();

        Assert.assertEquals(hashCodeFirst, hashCodeSecond);
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeDifferentObjectSameValues() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-05-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = "Official Game";

        Game newGame = new Game(startTime, name);

        Assert.assertEquals(this.game.hashCode(), newGame.hashCode());
    }

    /**
     * Tests if the equals method's return value is true when the passed game
     * has the same values as this game.
     * <p>
     * NOTE : WILL NO LONGER WORK WHEN ROOMS ARE RANDOMLY GENERATED
     */
    @Test
    public void testEqualsDifferentObjectSameValues() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-05-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = "Official Game";

        Game newGame = new Game(startTime, name);

        Assert.assertTrue(this.game.equals(newGame));
    }

    /**
     * Tests if the equals method's return value is true when the passed game is
     * the same object.
     */
    @Test
    public void testEqualsSameObject() {

        Assert.assertTrue(this.game.equals(this.game));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is not a Game object.
     */
    @Test
    public void testEqualsObjectOtherClass() {

        Assert.assertFalse(this.game.equals(GameManager.getInstance()));
    }

    /**
     * Tests if the equals method's return value is false when the passed game's
     * name is different.
     */
    @Test
    public void testEqualsNameNotEqual() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-05-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = "Unofficial Game";

        Game newGame = new Game(startTime, name);

        Assert.assertFalse(this.game.equals(newGame));
    }

    /**
     * Tests if the equals method's return value is false when the passed game's
     * list of players is different.
     */
    @Test
    public void testEqualsPlayersNotEqual() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-05-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = "Official Game";

        RoomType roomType = new RoomType(500, 900, new ArrayList());
        Vector position = new Vector(200, 400);
        Room room = new Room(position, roomType);

        Game newGame = new Game(startTime, name);
        newGame.addRoom(room);
        newGame.addPlayer("Erik Janssen","testAnimation");

        Assert.assertFalse(this.game.equals(newGame));
    }

    /**
     * Tests if the equals method's return value is false when the passed game's
     * list of rooms is different.
     */
    @Test
    public void testEqualsRoomsNotEqual() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-05-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = "Official Game";

        Game newGame = new Game(startTime, name);

        RoomType roomType = new RoomType(500, 900, new ArrayList());
        Vector position = new Vector(200, 400);
        Room room = new Room(position, roomType);

        newGame.addRoom(room);

        Assert.assertFalse(this.game.equals(newGame));
    }

    /**
     * Tests if the equals method's return value is false when the passed game's
     * list of rooms is different.
     */
    @Test
    public void testEqualsStartTimeNotEqual() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date startTime = null;

        try {
            startTime = format.parse("04-06-2016 19:55:32");
        } catch (ParseException ex) {
            Logger.getLogger(GameTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = "Official Game";

        Game newGame = new Game(startTime, name);

        Assert.assertFalse(this.game.equals(newGame));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is null.
     */
    @Test
    public void testEqualsObjectNull() {

        Assert.assertFalse(this.game.equals(null));
    }

    /**
     * Compares a list of playernames with an arrayList of players to see if the
     * names of the players equal the names in the player.
     *
     * @param alExpected a List containing the expected names of the players.
     * @param alActual a List containing the actual players.
     * @return A boolean indicating if the array lists were equal according to
     * this method.
     */
    private boolean listsPlayersAreEqual(List<String> alExpected, List<Player> alActual) {
        if (alExpected.size() != alActual.size()) {
            return false;
        }

        for (String plExpected : alExpected) {
            Player actualPlayer = alActual.get(alExpected.indexOf(plExpected));
            if (!actualPlayer.getName().equals(plExpected)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Compares two lists of room objects to see if their length and their items
     * are equal.
     *
     * @param alExpected a List containing the expected rooms.
     * @param alActual a List containing the actual rooms.
     * @return A boolean indicating if the array lists were equal according to
     * this method.
     */
    private boolean listsRoomsAreEqual(List<Room> alExpected, List<Room> alActual) {
        if (alExpected.size() != alActual.size()) {
            return false;
        }

        for (Room rExpected : alExpected) {
            Room actualRoom = alActual.get(alExpected.indexOf(rExpected));
            if (!actualRoom.equals(rExpected)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a player with a given name exists within the given list of
     * players.
     *
     * @param playerName the name of the player to find.
     * @param players the players to search.
     * @return A boolean indicating if a player with the given name was found.
     */
    private boolean playerNameExistsInList(String playerName, List<Player> players) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return true;
            }
        }

        return false;
    }
}
