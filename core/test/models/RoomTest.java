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
import com.ffxvi.game.models.Player;
import com.ffxvi.game.models.Projectile;
import com.ffxvi.game.models.Room;
import com.ffxvi.game.models.RoomObject;
import com.ffxvi.game.models.RoomObjectType;
import com.ffxvi.game.models.RoomType;
import com.ffxvi.game.support.Vector;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author Pim Janissen
 */
public class RoomTest {

    /**
     * A room object used for testing.
     */
    private Room room;

    /**
     * Initializes the room object before every test.
     */
    @Before
    public void initialize() {
        GameManager.resetInstance();
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(8000, 4000, roomObjects);
        Vector position = new Vector(2000, 3200);
        this.room = new Room(position, roomType);
    }

    /**
     * Tests if the constructor properly sets the private fields of this object
     * by checking if the get methods return the same values as given to the
     * object.
     */
    @Test
    public void testConstructorNormalValues() {
        /**
         *
         * @param position The position of this room in the Game.
         * @param roomType The pre-constructed room layout that this room uses.
         */
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(400, 800, roomObjects);
        Vector position = new Vector(2000, 3200);

        Room newRoom = new Room(position, roomType);

        Assert.assertEquals(position, newRoom.getPosition());
        Assert.assertEquals(roomType, newRoom.getRoomType());
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException when
     * the given position is null by checking if the desired exception is
     * thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositionNull() {
        /**
         *
         * @param position The position of this room in the Game.
         * @param roomType The pre-constructed room layout that this room uses.
         */
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(400, 800, roomObjects);

        new Room(null, roomType);
    }

    /**
     * Tests if the constructor properly throws an IllegalArgumentException when
     * the given room type is null by checking if the desired exception is
     * thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRoomTypeNull() {
        /**
         *
         * @param position The position of this room in the Game.
         * @param roomType The pre-constructed room layout that this room uses.
         */

        Vector position = new Vector(2000, 3200);

        new Room(position, null);
    }

    /**
     * Tests if the getPosition method properly returns its object's value by
     * checking if the return value of getPosition is equal to the value given
     * to it by the constructor in the initialize method.
     */
    @Test
    public void testGetPosition() {
        /**
         *
         * @return The position of this room in the Game.
         */

        Vector expectedPosition = new Vector(2000, 3200);
        Assert.assertEquals(expectedPosition, this.room.getPosition());
    }

    /**
     * Tests if the getRoomType method properly returns its object's value by
     * checking if the return value of getRoomType is equal to the value given
     * to it by the constructor in the initialize method.
     */
    @Test
    public void testGetRoomType() {
        /**
         *
         * @return The pre-constructed room layout that this room uses.
         */

        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType expectedRoomType = new RoomType(400, 200, roomObjects);
        Assert.assertEquals(400, expectedRoomType.getRoomSizeX());
        Assert.assertEquals(200, expectedRoomType.getRoomSizeY());
        Assert.assertEquals(roomObjects, expectedRoomType.getRoomObjects());
    }

    /**
     * Tests if the getRoomObjects method returns the same room objects as added
     * to it by zero calls to the addRoomObject method and to a new list and
     * checking if the lists are equal with the arrayListsRoomObjectsAreEqual
     * method.
     */
    @Test
    public void testGetRoomObjectsZeroObjects() {
        /**
         *
         * @return The objects in this room.
         */

        RoomObjectType roomObjectType = new RoomObjectType((float) 0.4, "AnimationString");
        Vector position = new Vector(100, 80);
        new RoomObject(roomObjectType, position, 40);
    }

    /**
     * Tests if the getRoomObjects method returns the same room objects as added
     * to it by a single call to the addRoomObject method and to a new list and
     * checking if the lists are equal with the arrayListsRoomObjectsAreEqual
     * method.
     */
    @Test
    public void testGetRoomObjectsSingleObject() {
        /**
         *
         * @return The objects in this room.
         */

        RoomObjectType roomObjectType = new RoomObjectType((float) 0.4, "AnimationString");
        Vector position = new Vector(100, 80);
        RoomObject roomObject = new RoomObject(roomObjectType, position, 40);
        this.room.addRoomObject(roomObject);

        Assert.assertEquals(roomObject, this.room.getRoomObjects().get(0));
    }

    /**
     * Tests if the getRoomObjects method returns the same room objects as added
     * to it by multiple calls to the addRoomObject method and to a new list and
     * checking if the lists are equal with the arrayListsRoomObjectsAreEqual
     * method.
     */
    @Test
    public void testGetRoomObjectsMultipleObjects() {
        /**
         *
         * @return The objects in this room.
         */

        RoomObjectType roomObjectType = new RoomObjectType((float) 0.4, "AnimationString");
        Vector position1 = new Vector(100, 80);
        Vector position2 = new Vector(120, 30);
        Vector position3 = new Vector(10, 50);

        RoomObject roomObject1 = new RoomObject(roomObjectType, position1, 40);
        RoomObject roomObject2 = new RoomObject(roomObjectType, position2, 20);
        RoomObject roomObject3 = new RoomObject(roomObjectType, position3, 240);

        this.room.addRoomObject(roomObject1);
        this.room.addRoomObject(roomObject2);
        this.room.addRoomObject(roomObject3);

        Assert.assertEquals(roomObject1, this.room.getRoomObjects().get(0));
        Assert.assertEquals(roomObject2, this.room.getRoomObjects().get(1));
        Assert.assertEquals(roomObject3, this.room.getRoomObjects().get(2));
    }

    /**
     * Tests if the getPlayers method returns the same players as added to it by
     * zero calls to the spawnPlayer method and to a new list and checking if
     * the lists are equal with the arrayListsPlayersAreEqual method.
     */
    @Test
    public void testGetPlayersZeroPlayers() {
        /**
         *
         * @return The players in this room.
         */
        Assert.assertEquals(0, this.room.getPlayers().size());
    }

    /**
     * Tests if the getPlayers method returns the same players as added to it by
     * a single call to the spawnPlayer method and to a new list and checking if
     * the lists are equal with the arrayListsPlayersAreEqual method.
     */
    @Test
    public void testGetPlayersSinglePlayer() {
        /**
         *
         * @return The players in this room.
         */
        String playerName = "Erik Janssen";
        Vector position = new Vector(20, 40);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        this.room.spawnPlayer(player);

        Assert.assertEquals(player, this.room.getPlayers().get(0));
    }

    /**
     * Tests if the getPlayers method returns the same players as added to it by
     * multiple calls to the spawnPlayer method and to a new list and checking
     * if the lists are equal with the arrayListsPlayersAreEqual method.
     */
    @Test
    public void testGetPlayersMultiplePlayers() {
        /**
         *
         * @return The players in this room.
         */
        String playerName1 = "Erik Janssen";
        Vector position1 = new Vector(20, 40);
        Player player1 = new Player(playerName1, 100, position1, "AnimationString", this.room);

        String playerName2 = "Erica Janssen";
        Vector position2 = new Vector(50, 5);
        Player player2 = new Player(playerName2, 100, position2, "AnimationString", this.room);

        String playerName3 = "Henk Simons";
        Vector position3 = new Vector(5, 5);
        Player player3 = new Player(playerName3, 100, position3, "AnimationString", this.room);

        this.room.spawnPlayer(player1);
        this.room.spawnPlayer(player2);
        this.room.spawnPlayer(player3);

        Assert.assertEquals(player1, this.room.getPlayers().get(0));
        Assert.assertEquals(player2, this.room.getPlayers().get(1));
        Assert.assertEquals(player3, this.room.getPlayers().get(2));
    }

    /**
     * Tests if the getProjectiles method returns the same projectiles as added
     * to it by zero calls to the spawnProjectile method and to a new list and
     * checking if the lists are equal with the arrayListsProjectilesAreEqual
     * method.
     */
    @Test
    public void testGetProjectilesZeroProjectiles() {
        /**
         *
         * @return The projectiles in this room.
         */
        Assert.assertEquals(0, this.room.getProjectiles().size());

    }

    /**
     * Tests if the getProjectiles method returns the same projectiles as added
     * to it by a single call to the spawnProjectile method and to a new list
     * and checking if the lists are equal with the
     * arrayListsProjectilesAreEqual method.
     */
    @Test
    public void testGetProjectilesSingleProjectile() {
        /**
         *
         * @return The projectiles in this room.
         */
        Vector position = new Vector(20, 50);
        AmmoType ammoType = new AmmoType(10, 15, "AnimationString");
        Projectile projectile = new Projectile(position, 40, ammoType);

        this.room.spawnProjectile(projectile);

        Assert.assertEquals(projectile, this.room.getProjectiles().get(0));
    }

    /**
     * Tests if the getProjectiles method returns the same projectiles as added
     * to it by multiple calls to the spawnProjectile method and to a new list
     * and checking if the lists are equal with the
     * arrayListsProjectilesAreEqual method.
     */
    @Test
    public void testGetProjectilesMultipleProjectiles() {
        /**
         *
         * @return The projectiles in this room.
         */
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector position1 = new Vector(20, 50);
        Projectile projectile1 = new Projectile(position1, 40, ammoType);

        Vector position2 = new Vector(20, 50);
        Projectile projectile2 = new Projectile(position2, 40, ammoType);

        Vector position3 = new Vector(20, 50);
        Projectile projectile3 = new Projectile(position3, 40, ammoType);

        this.room.spawnProjectile(projectile1);
        this.room.spawnProjectile(projectile2);
        this.room.spawnProjectile(projectile3);

        Assert.assertEquals(projectile1, this.room.getProjectiles().get(0));
        Assert.assertEquals(projectile2, this.room.getProjectiles().get(1));
        Assert.assertEquals(projectile3, this.room.getProjectiles().get(2));

    }

    /**
     * Tests if the spawnProjectile method adds the given projectile to the
     * room's projectiles by checking if afterwards the list returned by the
     * getProjectiles method contains the spawned projectile.
     */
    @Test
    public void testSpawnProjectilePositionInRoom() {
        /**
         * Spawns a projectile in the room.
         *
         * @param projectile The projectile that is fired from within this room.
         * @return A boolean containing: If the projectile's position was
         * invalid, false. Else, true.
         */
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector position = new Vector(300, 700);
        Projectile projectile = new Projectile(position, 40, ammoType);

        Assert.assertTrue(this.room.spawnProjectile(projectile));
        Assert.assertTrue(this.room.getProjectiles().contains(projectile));
    }

    /**
     * Tests if the spawnProjectile method adds the given projectile to the
     * room's projectiles when the position of the projectile is at the room's
     * left border by checking if afterwards the list returned by the
     * getProjectiles method contains the spawned projectile.
     */
    @Test
    public void testSpawnProjectilePositionRoomLeftBorder() {
        /**
         * Spawns a projectile in the room.
         *
         * @param projectile The projectile that is fired from within this room.
         * @return A boolean containing: If the projectile's position was
         * invalid, false. Else, true.
         */
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector position = new Vector(0, 700);
        Projectile projectile = new Projectile(position, 40, ammoType);

        Assert.assertTrue(this.room.spawnProjectile(projectile));
        Assert.assertTrue(this.room.getProjectiles().contains(projectile));
    }

    /**
     * Tests if the spawnProjectile method adds the given projectile to the
     * room's projectiles when the position of the projectile is at the room's
     * top border by checking if afterwards the list returned by the
     * getProjectiles method contains the spawned projectile.
     */
    @Test
    public void testSpawnProjectilePositionRoomTopBorder() {
        /**
         * Spawns a projectile in the room.
         *
         * @param projectile The projectile that is fired from within this room.
         * @return A boolean containing: If the projectile's position was
         * invalid, false. Else, true.
         */
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector position = new Vector(300, 0);
        Projectile projectile = new Projectile(position, 40, ammoType);

        Assert.assertTrue(this.room.spawnProjectile(projectile));
        Assert.assertTrue(this.room.getProjectiles().contains(projectile));
    }

    /**
     * Tests if the spawnProjectile method adds the given projectile to the
     * room's projectiles when the position of the projectile is at the room's
     * right border by checking if afterwards the list returned by the
     * getProjectiles method contains the spawned projectile.
     */
    @Test
    public void testSpawnProjectilePositionRoomRightBorder() {
        /**
         * Spawns a projectile in the room.
         *
         * @param projectile The projectile that is fired from within this room.
         * @return A boolean containing: If the projectile's position was
         * invalid, false. Else, true.
         */
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector position = new Vector(399, 700);
        Projectile projectile = new Projectile(position, 40, ammoType);

        Assert.assertTrue(this.room.spawnProjectile(projectile));
        Assert.assertTrue(this.room.getProjectiles().contains(projectile));
    }

    /**
     * Tests if the spawnProjectile method adds the given projectile to the
     * room's projectiles when the position of the projectile is at the room's
     * bottom border by checking if afterwards the list returned by the
     * getProjectiles method contains the spawned projectile.
     */
    @Test
    public void testSpawnProjectilePositionRoomBottomBorder() {
        /**
         * Spawns a projectile in the room.
         *
         * @param projectile The projectile that is fired from within this room.
         * @return A boolean containing: If the projectile's position was
         * invalid, false. Else, true.
         */
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector position = new Vector(300, 799);
        Projectile projectile = new Projectile(position, 40, ammoType);

        Assert.assertTrue(this.room.spawnProjectile(projectile));
        Assert.assertTrue(this.room.getProjectiles().contains(projectile));
    }

    /**
     * Tests if the spawnProjectile method does not add the given projectile to
     * the room's projectiles when the position of the projectile is outside the
     * room's left border by checking if afterwards the list returned by the
     * getProjectiles method contains the spawned projectile.
     */
    @Test
    public void testSpawnProjectilePositionOutsideRoomLeftBorder() {
        /**
         * Spawns a projectile in the room.
         *
         * @param projectile The projectile that is fired from within this room.
         * @return A boolean containing: If the projectile's position was
         * invalid, false. Else, true.
         */
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector position = new Vector(-1, 700);
        Projectile projectile = new Projectile(position, 40, ammoType);

        Assert.assertFalse(this.room.spawnProjectile(projectile));
        Assert.assertFalse(this.room.getProjectiles().contains(projectile));
    }

    /**
     * Tests if the spawnProjectile method does not add the given projectile to
     * the room's projectiles when the position of the projectile is outside the
     * room's top border by checking if afterwards the list returned by the
     * getProjectiles method contains the spawned projectile.
     */
    @Test
    public void testSpawnProjectilePositionOutsideRoomTopBorder() {
        /**
         * Spawns a projectile in the room.
         *
         * @param projectile The projectile that is fired from within this room.
         * @return A boolean containing: If the projectile's position was
         * invalid, false. Else, true.
         */
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector position = new Vector(300, -1);
        Projectile projectile = new Projectile(position, 40, ammoType);

        Assert.assertFalse(this.room.spawnProjectile(projectile));
        Assert.assertFalse(this.room.getProjectiles().contains(projectile));
    }

    /**
     * Tests if the spawnProjectile method does not add the given projectile to
     * the room's projectiles when the position of the projectile is outside the
     * room's right border by checking if afterwards the list returned by the
     * getProjectiles method contains the spawned projectile.
     */
    @Test
    public void testSpawnProjectilePositionOutsideRoomRightBorder() {
        /**
         * Spawns a projectile in the room.
         *
         * @param projectile The projectile that is fired from within this room.
         * @return A boolean containing: If the projectile's position was
         * invalid, false. Else, true.
         */
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector position = new Vector(9000, 700);
        Projectile projectile = new Projectile(position, 40, ammoType);

        Assert.assertFalse(this.room.spawnProjectile(projectile));
    }

    /**
     * Tests if the spawnProjectile method does not add the given projectile to
     * the room's projectiles when the position of the projectile is outside the
     * room's bottom border by checking if afterwards the list returned by the
     * getProjectiles method contains the spawned projectile.
     */
    @Test
    public void testSpawnProjectilePositionOutsideRoomBottomBorder() {
        /**
         * Spawns a projectile in the room.
         *
         * @param projectile The projectile that is fired from within this room.
         * @return A boolean containing: If the projectile's position was
         * invalid, false. Else, true.
         */
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector position = new Vector(9000, 700);
        Projectile projectile = new Projectile(position, 40, ammoType);

        Assert.assertFalse(this.room.spawnProjectile(projectile));
    }

    /**
     * Tests if the spawnProjectile method throws an exception when the given
     * projectile is null by checking if an IlegalArgumentException is thrown
     * after calling the spawnProjectile method.
     */
    @Test(expected = NullPointerException.class)
    public void testSpawnProjectileNull() {
        /**
         * Spawns a projectile in the room.
         *
         * @param projectile The projectile that is fired from within this room.
         * @return A boolean containing: If the projectile's position was
         * invalid, false. Else, true.
         */

        this.room.spawnProjectile(null);
    }

    /**
     * Tests if the spawnPlayer method succesfully adds a player to the room
     * when the player has a valid position and does not yet exist by checking
     * if the spawnPlayer method returns true and the player exists in the list
     * returned by the getPlayers method.
     */
    @Test
    public void testSpawnPlayerNonExisting() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        String playerName = "Erik Janssen";
        Vector position = new Vector(10, 100);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        Assert.assertTrue(this.room.spawnPlayer(player));
        Assert.assertTrue(this.room.getPlayers().contains(player));
    }

    /**
     * Tests if the spawnPlayer method does not add a player to the room when
     * the player has a valid position but a player with that name already
     * exists by checking if the spawnPlayer method returns false.
     */
    @Test
    public void testSpawnPlayerExisting() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        String playerName = "Test";
        Vector position = new Vector(10, 100);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        String playerName2 = "Test";
        Vector position2 = new Vector(50, 100);
        Player player2 = new Player(playerName2, 100, position2, "AnimationString", this.room);

        Assert.assertTrue(this.room.spawnPlayer(player));
        Assert.assertFalse(this.room.spawnPlayer(player2));
    }

    /**
     * Tests if the spawnPlayer method adds a player to the room when the player
     * is positioned at the left border of the room and does not yet exist by
     * checking if the spawnPlayer method returns false and the player does not
     * exist in the list returned by the getPlayers method.
     */
    @Test
    public void testSpawnPlayerPositionAtLeftBorder() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        String playerName = "Erik Janssen";
        Vector position = new Vector(0, 100);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        Assert.assertTrue(this.room.spawnPlayer(player));
        Assert.assertTrue(this.room.getPlayers().contains(player));
    }

    /**
     * Tests if the spawnPlayer method adds a player to the room when the player
     * is positioned at the top border of the room and does not yet exist by
     * checking if the spawnPlayer method returns false and the player does not
     * exist in the list returned by the getPlayers method.
     */
    @Test
    public void testSpawnPlayerPositionAtTopBorder() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        String playerName = "Erik Janssen";
        Vector position = new Vector(10, 0);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        Assert.assertTrue(this.room.spawnPlayer(player));
        Assert.assertTrue(this.room.getPlayers().contains(player));
    }

    /**
     * Tests if the spawnPlayer method adds a player to the room when the player
     * is positioned at the right border of the room and does not yet exist by
     * checking if the spawnPlayer method returns false and the player does not
     * exist in the list returned by the getPlayers method.
     */
    @Test
    public void testSpawnPlayerPositionAtRightBorder() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        String playerName = "Erik Janssen";
        Vector position = new Vector(399, 100);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        Assert.assertTrue(this.room.spawnPlayer(player));
        Assert.assertTrue(this.room.getPlayers().contains(player));
    }

    /**
     * Tests if the spawnPlayer method adds a player to the room when the player
     * is positioned at the bottom border of the room and does not yet exist by
     * checking if the spawnPlayer method returns false and the player does not
     * exist in the list returned by the getPlayers method.
     */
    @Test
    public void testSpawnPlayerPositionAtBottomBorder() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        String playerName = "Erik Janssen";
        Vector position = new Vector(10, 899);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        Assert.assertTrue(this.room.spawnPlayer(player));
        Assert.assertTrue(this.room.getPlayers().contains(player));
    }

    /**
     * Tests if the spawnPlayer method does not add a player to the room when
     * the player is positioned outside the left border of the room and does not
     * yet exist by checking if the spawnPlayer method throws an
     * IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSpawnPlayerPositionOutsideLeftBorder() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        String playerName = "Erik Janssen";
        Vector position = new Vector(-1, 100);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        Assert.assertFalse(this.room.spawnPlayer(player));
        Assert.assertFalse(this.room.getPlayers().contains(player));
    }

    /**
     * Tests if the spawnPlayer method does not add a player to the room when
     * the player is positioned outside the top border of the room and does not
     * yet exist by checking if the spawnPlayer method returns false and the
     * player does not exist in the list returned by the getPlayers method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSpawnPlayerPositionOutsideTopBorder() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        String playerName = "Erik Janssen";
        Vector position = new Vector(10, -1);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        Assert.assertFalse(this.room.spawnPlayer(player));
        Assert.assertFalse(this.room.getPlayers().contains(player));
    }

    /**
     * Tests if the spawnPlayer method does not add a player to the room when
     * the player is positioned outside the top border of the room and does not
     * yet exist by checking if the spawnPlayer method returns false and the
     * player does not exist in the list returned by the getPlayers method.
     */
    @Test
    public void testSpawnPlayerPositionOutsideRightBorder() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        String playerName = "Erik Janssen";
        Vector position = new Vector(9000, 100);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        Assert.assertFalse(this.room.spawnPlayer(player));
    }

    /**
     * Tests if the spawnPlayer method does not add a player to the room when
     * the player is positioned outside the top border of the room and does not
     * yet exist by checking if the spawnPlayer method returns false and the
     * player does not exist in the list returned by the getPlayers method.
     */
    @Test
    public void testSpawnPlayerPositionOutsideBottomBorder() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        String playerName = "Erik Janssen";
        Vector position = new Vector(9000, 700);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        Assert.assertFalse(this.room.spawnPlayer(player));
    }

    /**
     * Tests if the spawnPlayer method does not add a player to the room when
     * the player is positioned at an already existing room object's position by
     * checking if the spawnPlayer method returns false and the player does not
     * exist in the list returned by the getPlayers method.
     */
    @Test
    public void testSpawnPlayerPositionOverlapsRoomObject() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        RoomObjectType rot = new RoomObjectType((float) 0.4, "AnimationString");
        Vector roomObjectPosition = new Vector(10, 100);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        String playerName = "Erik Janssen";
        Vector position = new Vector(10, 100);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        this.room.addRoomObject(roomObject);

        Assert.assertFalse(this.room.spawnPlayer(player));
        Assert.assertFalse(this.room.getPlayers().contains(player));
    }

    /**
     * Tests if the spawnPlayer method does not add a player to the room when
     * the player is positioned at an already existing player's position by
     * checking if the spawnPlayer method returns false and the player does not
     * exist in the list returned by the getPlayers method.
     */
    @Test
    public void testSpawnPlayerPositionOverlapsPlayer() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        String playerName1 = "Erica Janssen";
        Vector position1 = new Vector(10, 100);
        Player player1 = new Player(playerName1, 100, position1, "AnimationString", this.room);

        String playerName2 = "Erik Janssen";
        Vector position2 = new Vector(10, 100);
        Player player2 = new Player(playerName2, 100, position2, "AnimationString", this.room);

        this.room.spawnPlayer(player1);

        Assert.assertFalse(this.room.spawnPlayer(player2));
        Assert.assertFalse(this.room.getPlayers().contains(player2));
    }

    /**
     * Tests if the spawnPlayer method does not add a player to the room when
     * the player is positioned at an already existing projectile's position by
     * checking if the spawnPlayer method returns false and the player does not
     * exist in the list returned by the getPlayers method.
     */
    @Test
    public void testSpawnPlayerPositionOverlapsProjectile() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector projectilePosition = new Vector(10, 100);
        Projectile projectile = new Projectile(projectilePosition, 50, ammoType);

        String playerName = "Erik Janssen";
        Vector position = new Vector(10, 100);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        this.room.spawnProjectile(projectile);

        Assert.assertFalse(this.room.spawnPlayer(player));
        Assert.assertFalse(this.room.getPlayers().contains(player));
    }

    /**
     * Tests if the spawnPlayer method does not add a player to the room when
     * the player is null by checking if the spawnPlayer method throws an
     * IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSpawnPlayerNull() {
        /**
         * Adds a player to this room.
         *
         * @param player
         * @return A boolean containing: If the player was already in the room,
         * false. Else, true.
         */

        this.room.spawnPlayer(null);
    }

    /**
     * Tests if the addRoomObject adds a new room object to this room when the
     * room object is located in the room and there are no existing room objects
     * already loated at the given position by checking if the addRoomObject
     * method returns true and the list returned by the getRoomObjects method
     * contains the added room object.
     */
    @Test
    public void testAddRoomObject() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");
        Vector roomObjectPosition = new Vector(10, 100);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        Assert.assertTrue(this.room.addRoomObject(roomObject));
        Assert.assertTrue(this.room.getRoomObjects().contains(roomObject));
    }

    /**
     * Tests if the addRoomObject adds a new room object to this room when the
     * room object is located at the left border of the room and there are no
     * existing room objects already loated at the given position by checking if
     * the addRoomObject method returns true and the list returned by the
     * getRoomObjects method contains the added room object.
     */
    @Test
    public void testAddRoomObjectAtLeftBorder() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");
        Vector roomObjectPosition = new Vector(0, 100);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        Assert.assertTrue(this.room.addRoomObject(roomObject));
        Assert.assertTrue(this.room.getRoomObjects().contains(roomObject));
    }

    /**
     * Tests if the addRoomObject adds a new room object to this room when the
     * room object is located at the left border of the room and there are no
     * existing room objects already loated at the given position by checking if
     * the addRoomObject method returns true and the list returned by the
     * getRoomObjects method contains the added room object.
     */
    @Test
    public void testAddRoomObjectAtTopBorder() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");
        Vector roomObjectPosition = new Vector(10, 0);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        Assert.assertTrue(this.room.addRoomObject(roomObject));
        Assert.assertTrue(this.room.getRoomObjects().contains(roomObject));
    }

    /**
     * Tests if the addRoomObject adds a new room object to this room when the
     * room object is located at the left border of the room and there are no
     * existing room objects already loated at the given position by checking if
     * the addRoomObject method returns true and the list returned by the
     * getRoomObjects method contains the added room object.
     */
    @Test
    public void testAddRoomObjectAtRightBorder() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");
        Vector roomObjectPosition = new Vector(399, 100);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        Assert.assertTrue(this.room.addRoomObject(roomObject));
        Assert.assertTrue(this.room.getRoomObjects().contains(roomObject));
    }

    /**
     * Tests if the addRoomObject adds a new room object to this room when the
     * room object is located at the left border of the room and there are no
     * existing room objects already loated at the given position by checking if
     * the addRoomObject method returns true and the list returned by the
     * getRoomObjects method contains the added room object.
     */
    @Test
    public void testAddRoomObjectAtBottomBorder() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");
        Vector roomObjectPosition = new Vector(10, 799);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        Assert.assertTrue(this.room.addRoomObject(roomObject));
        Assert.assertTrue(this.room.getRoomObjects().contains(roomObject));
    }

    /**
     * Tests if the addRoomObject does not add a new room object to this room
     * when the room object is located outside the left border of the room and
     * there are no existing room objects already loated at the given position
     * by checking if the addRoomObject method returns true and the list
     * returned by the getRoomObjects method contains the added room object.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddRoomObjectOutsideLeftBorder() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");
        Vector roomObjectPosition = new Vector(-1, 100);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        Assert.assertFalse(this.room.addRoomObject(roomObject));
        Assert.assertFalse(this.room.getRoomObjects().contains(roomObject));
    }

    /**
     * Tests if the addRoomObject does not add a new room object to this room
     * when the room object is located outside the top border of the room and
     * there are no existing room objects already loated at the given position
     * by checking if the addRoomObject method returns true and the list
     * returned by the getRoomObjects method contains the added room object.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddRoomObjectOutsideTopBorder() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");
        Vector roomObjectPosition = new Vector(10, -1);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        Assert.assertFalse(this.room.addRoomObject(roomObject));
        Assert.assertFalse(this.room.getRoomObjects().contains(roomObject));
    }

    /**
     * Tests if the addRoomObject does not add a new room object to this room
     * when the room object is located outside the right border of the room and
     * there are no existing room objects already loated at the given position
     * by checking if the addRoomObject method returns true and the list
     * returned by the getRoomObjects method contains the added room object.
     */
    @Test
    public void testAddRoomObjectOutsideRightBorder() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");
        Vector roomObjectPosition = new Vector(9000, 100);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        Assert.assertFalse(this.room.addRoomObject(roomObject));
        Assert.assertFalse(this.room.getRoomObjects().contains(roomObject));
    }

    /**
     * Tests if the addRoomObject does not add a new room object to this room
     * when the room object is located outside the bottom border of the room and
     * there are no existing room objects already loated at the given position
     * by checking if the addRoomObject method returns true and the list
     * returned by the getRoomObjects method contains the added room object.
     */
    @Test
    public void testAddRoomObjectOutsideBottomBorder() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");
        Vector roomObjectPosition = new Vector(9000, 700);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        Assert.assertFalse(this.room.addRoomObject(roomObject));
    }

    /**
     * Tests if the addRoomObject does not add a new room object to this room
     * when the room object is positioned at an already existing room object's
     * position by checking if the addRoomObject method returns true and the
     * list returned by the getRoomObjects method contains the added room
     * object.
     */
    @Test
    public void testAddRoomObjectOverlapsRoomObject() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");

        Vector roomObjectPosition1 = new Vector(10, 100);
        RoomObject roomObject1 = new RoomObject(rot, roomObjectPosition1, 30);

        Vector roomObjectPosition2 = new Vector(10, 100);
        RoomObject roomObject2 = new RoomObject(rot, roomObjectPosition2, 40);

        this.room.addRoomObject(roomObject1);

        Assert.assertFalse(this.room.addRoomObject(roomObject2));
        Assert.assertFalse(this.room.getRoomObjects().contains(roomObject2));
    }

    /**
     * Tests if the addRoomObject does not add a new room object to this room
     * when the room object is positioned at an already existing player's
     * position by checking if the addRoomObject method returns true and the
     * list returned by the getRoomObjects method contains the added room
     * object.
     */
    @Test
    public void testAddRoomObjectOverlapsPlayer() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");

        String playerName = "Erik Janssen";
        Vector position = new Vector(10, 100);
        Player player = new Player(playerName, 100, position, "AnimationString", this.room);

        Vector roomObjectPosition = new Vector(10, 100);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        this.room.spawnPlayer(player);

        Assert.assertFalse(this.room.addRoomObject(roomObject));
        Assert.assertFalse(this.room.getRoomObjects().contains(roomObject));
    }

    /**
     * Tests if the addRoomObject does not add a new room object to this room
     * when the room object is positioned at an already existing projectile's
     * position by checking if the addRoomObject method returns true and the
     * list returned by the getRoomObjects method contains the added room
     * object.
     */
    @Test
    public void testAddRoomObjectOverlapsProjectile() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        RoomObjectType rot = new RoomObjectType(0.5f, "AnimationString");
        AmmoType ammoType = new AmmoType(5, 15, "AnimationString");

        Vector position = new Vector(10, 100);
        Projectile projectile = new Projectile(position, 50, ammoType);

        Vector roomObjectPosition = new Vector(10, 100);
        RoomObject roomObject = new RoomObject(rot, roomObjectPosition, 40);

        this.room.spawnProjectile(projectile);

        Assert.assertFalse(this.room.addRoomObject(roomObject));
        Assert.assertFalse(this.room.getRoomObjects().contains(roomObject));
    }

    /**
     * Tests if the addRoomObject throws an exception when the room object is
     * null by checking if the addRoomObject method throws an
     * IllegalArgumentException.
     */
    @Test(expected = NullPointerException.class)
    public void testAddRoomObjectNull() {
        /**
         * Adds a room object to this room.
         *
         * @param obj The roomObject that's being placed in this room.
         * @return A boolean containing: if the room object's location is equal
         * to
         */

        this.room.addRoomObject(null);
    }

    /**
     * Tests the overlap method with an empty room object. Testcase expects an
     * IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDoesPositionOverlapWithParameterNull() {
        Room r = null;
        this.room.doesPositionOverlapWith(r);
    }

    /**
     * Tests the equals method with the same object. Testcase should return
     * false.
     */
    @Test
    public void testEqualsSameObject() {
        Room r = this.room;
        Assert.assertTrue(room.equals(r));
    }

    /**
     * Tests the equals method with an object which is null. Testcase should
     * return false.
     */
    @Test
    public void testEqualsParameterNull() {
        Object obj = null;
        Assert.assertFalse(this.room.equals(obj));
    }

    /**
     * Tests the equals method with a other class. Testcase should return false.
     */
    @Test
    public void testEqualsOtherClass() {
        int test = 1;
        Assert.assertFalse(this.room.equals(test));
    }

    /**
     * Tests the equals method with a different position. Testcase should return
     * false.
     */
    @Test
    public void testEqualsDifferentPosition() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(8000, 4000, roomObjects);
        Vector position = new Vector(0, 3200);
        Room room2 = new Room(position, roomType);

        Assert.assertFalse(room.equals(room2));
    }

    /**
     * Tests the equals method with a differen roomtype. Testcase should return
     * false.
     */
    @Test
    public void testEqualsDifferentRoomType() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(2000, 4000, roomObjects);
        Vector position = new Vector(2000, 3200);
        Room room2 = new Room(position, roomType);

        Assert.assertFalse(room.equals(room2));
    }

    /**
     * Tests the equals method with different RoomObjects. Testcase should
     * return false.
     */
    @Test
    public void testEqualsDifferentRoomObjects() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomObjectType rType = new RoomObjectType(0.5f, "AnimationString");

        RoomType roomType = new RoomType(8000, 4000, roomObjects);
        Vector position = new Vector(2000, 3200);
        Room room2 = new Room(position, roomType);
        RoomObject r = new RoomObject(rType, new Vector(20, 20), 180f);
        room2.addRoomObject(r);

        Assert.assertFalse(room.equals(room2));
    }

    /**
     * Tests the equals method with different players. Testcase should return
     * false.
     */
    @Test
    public void testEqualsDifferentPlayers() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(8000, 4000, roomObjects);
        Vector position = new Vector(2000, 3200);
        Room room2 = new Room(position, roomType);

        RoomType roomType3 = new RoomType(5, 5, new ArrayList());
        Room room3 = new Room(new Vector(2, 2), roomType3);
        Player player = new Player("Joel", 100, new Vector(1, 1),
                "AnimationString", room3);

        room2.spawnPlayer(player);
        player.setRoom(room2);

        Assert.assertFalse(room.equals(room2));
    }

    /**
     * Tests the equals method with different Projectiles. Testcase should
     * return false.
     */
    @Test
    public void testEqualsDifferentProjectiles() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(8000, 4000, roomObjects);
        Vector position = new Vector(2000, 3200);
        Room room2 = new Room(position, roomType);

        AmmoType at = new AmmoType(10, 10, "AnimationString");
        Projectile p = new Projectile(new Vector(10, 10), 90.0f, at);

        room2.spawnProjectile(p);

        Assert.assertFalse(room.equals(room2));
    }

    /**
     * Tests if hashcode returns same number on same object. Testcase should
     * return true.
     */
    @Test
    public void testHashCodeSameObject() {
        Room room2 = room;
        int first = room.hashCode();
        int second = room2.hashCode();

        org.junit.Assert.assertTrue(first == second);
    }

    /**
     * Test if hashcode returns same number on different object with same values
     * are equals should return true
     */
    @Test
    public void testHashCodeDifferentObjectSameValues() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(8000, 4000, roomObjects);
        Vector position = new Vector(2000, 3200);
        Room room2 = new Room(position, roomType);
        int first = room.hashCode();
        int second = room2.hashCode();
        org.junit.Assert.assertTrue(first == second);
    }

    /**
     * Tests if hashcode returns same number on different object. Testcase
     * should return false.
     */
    @Test
    public void testHashCodeDifferentObjectDifferentValues() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(7000, 3000, roomObjects);
        Vector position = new Vector(1000, 3000);
        Room room2 = new Room(position, roomType);
        int first = room.hashCode();
        int second = room2.hashCode();

        org.junit.Assert.assertFalse(first == second);
    }
}
