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
import com.ffxvi.game.customexceptions.GameInProgressException;
import com.ffxvi.game.models.AmmoType;
import com.ffxvi.game.models.Player;
import com.ffxvi.game.models.Room;
import com.ffxvi.game.models.RoomObject;
import com.ffxvi.game.models.RoomObjectType;
import com.ffxvi.game.models.RoomType;
import com.ffxvi.game.models.Weapon;
import com.ffxvi.game.models.WeaponType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.ffxvi.game.support.Vector;

/**
 *
 * @author Joel
 * @author Pim Janissen
 */
public class PlayerTest {

    /**
     * A player object used for testing.
     */
    private Player player;

    /**
     * Initializes a new player object before each test.
     */
    @Before
    public void initialize() {
        GameManager.resetInstance();
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        this.player = new Player("Joel", 100, new Vector(1, 1), "animationString", room);
    }

    /**
     * Test of getName method, of class Player.
     */
    @Test
    public void testGetName() {
        String expResult = "Joel";
        String result = player.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getScore method, of class Player.
     */
    @Test
    public void testGetScore() {
        int expResult = 0;
        int result = player.getScore();
        assertEquals(expResult, result);

    }

    /**
     * Test of setScore method, of class Player.
     */
    @Test
    public void testSetScore() {
        int score = 5;
        player.setScore(score);
        assertEquals(score, player.getScore());
    }

    /**
     * Test of setScore method, of class Player.
     */
    @Test
    public void testSetScoreZero() {
        int score = 0;
        player.setScore(score);
        assertEquals(score, player.getScore());
    }

    /**
     * Test of setScore method, of class Player.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetScoreNegative() {
        int score = -1;
        player.setScore(score);
    }

    /**
     * Test of getHitPoints method, of class Player.
     */
    @Test
    public void testGetHitPoints() {
        int expResult = 100;
        int result = player.getHitPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPosition method, of class Player.
     */
    @Test
    public void testGetPosition() {
        Vector expResult = new Vector(1, 1);
        Vector result = player.getPosition();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPosition method, of class Player.
     */
    @Test
    public void testSetPosition() {
        Vector expResult = new Vector(3, 3);
        player.setPosition(expResult);
        Vector result = player.getPosition();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMoveSpeed method, of class Player.
     */
    @Test
    public void testGetMoveSpeed() {
        float expResult = 0.0f;
        player.setMoveSpeed(expResult);
        float result = player.getMoveSpeed();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of setMoveSpeed method, of class Player.
     */
    @Test
    public void testSetMoveSpeed() {
        float expResult = 1.0f;
        player.setMoveSpeed(expResult);
        float result = player.getMoveSpeed();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of setMoveSpeed method, of class Player.
     */
    @Test
    public void testSetMoveSpeedZero() {
        float expResult = 0f;
        player.setMoveSpeed(expResult);
        float result = player.getMoveSpeed();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of setMoveSpeed method, of class Player.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetMoveSpeedNegative() {
        float expResult = -1f;
        player.setMoveSpeed(expResult);
    }

    /**
     * Test of getRotation method, of class Player.
     */
    @Test
    public void testGetRotation() {
        float expResult = 0.0f;
        float result = player.getRotation();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setRotation method, of class Player.
     */
    @Test
    public void testSetRotation() {
        float expResult = 1.0f;
        player.setRotation(1.0f);
        float result = player.getRotation();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of setRotation method, of class Player.
     */
    @Test
    public void testSetRotationMinimumValue() {
        float expResult = 0f;
        player.setRotation(expResult);
        float result = player.getRotation();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of setRotation method, of class Player.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetRotationNegative() {
        float expResult = -1f;
        player.setRotation(expResult);
    }

    /**
     * Test of setRotation method, of class Player.
     */
    @Test
    public void testSetRotationMaximumValue() {
        float expResult = 359f;
        player.setRotation(expResult);
        float result = player.getRotation();
        Assert.assertEquals(expResult, result, 0);
    }

    /**
     * Test of setRotation method, of class Player.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetRotationTooHighValue() {
        float expResult = 360f;
        player.setRotation(expResult);
    }

    /**
     * Test of getAnimationPath method, of class Player.
     */
    @Test
    public void testGetAnimationPath() {
        String expResult = "animationString";
        player.setAnimationPath(expResult);
        String result = player.getAnimationPath();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAnimationPath method, of class Player.
     */
    @Test
    public void testSetAnimationPath() {
        String expResult = "animationString";
        player.setAnimationPath(expResult);
        String result = player.getAnimationPath();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAnimationPath method, of class Player.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetAnimationPathNull() {
        player.setAnimationPath(null);
    }

    /**
     * Test of setAnimationPath method, of class Player.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetAnimationPathEmptyString() {
        player.setAnimationPath("");
    }

    /**
     * Test of setAnimationPath method, of class Player.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetAnimationPathExclusivelySpaces() {
        player.setAnimationPath("   ");
    }

    /**
     * Tests if the constructor of this class properly sets it's fields by
     * checking if the get methods return the values passed to the constructor.
     */
    @Test
    public void testConstructorValid() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "Joel";
        int hitpoints = 100;
        Vector position = new Vector(1, 1);
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);

        Assert.assertEquals(name, newPlayer.getName());
        Assert.assertEquals(hitpoints, newPlayer.getHitPoints());
        Assert.assertEquals(position, newPlayer.getPosition());
        Assert.assertEquals(animationPath, newPlayer.getAnimationPath());
        Assert.assertEquals(room, newPlayer.getRoom());
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the given name is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameNull() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = null;
        int hitpoints = 100;
        Vector position = new Vector(1, 1);
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the given name is an empty string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameEmptyString() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "";
        int hitpoints = 100;
        Vector position = new Vector(1, 1);
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the given name is a string exclusively containing spaces.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameExclusivelySpaces() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "   ";
        int hitpoints = 100;
        Vector position = new Vector(1, 1);
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the amount of given hitpoints is zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorHitpointsZero() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "Joel";
        int hitpoints = 0;
        Vector position = new Vector(1, 1);
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the amount of given hitpoints is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorHitpointsNegative() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "Joel";
        int hitpoints = -100;
        Vector position = new Vector(1, 1);
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the given starting room is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorStartingRoomNull() {
        Room room = null;
        String name = "Joel";
        int hitpoints = 100;
        Vector position = new Vector(1, 1);
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the given position is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositionNull() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "Joel";
        int hitpoints = 100;
        Vector position = null;
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Tests if the constructor of this class properly sets its fields when the
     * given position's x is zero.
     */
    @Test
    public void testConstructorPositionXZero() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "Joel";
        int hitpoints = 100;
        Vector position = new Vector(0, 1);
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);

        Assert.assertEquals(name, newPlayer.getName());
        Assert.assertEquals(hitpoints, newPlayer.getHitPoints());
        Assert.assertEquals(position, newPlayer.getPosition());
        Assert.assertEquals(animationPath, newPlayer.getAnimationPath());
        Assert.assertEquals(room, newPlayer.getRoom());
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the given position's x is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositionXNegative() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "Joel";
        int hitpoints = 100;
        Vector position = new Vector(-1, 1);
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Tests if the constructor of this class properly sets its fields when the
     * given position's y is zero.
     */
    @Test
    public void testConstructorPositionYZero() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "Joel";
        int hitpoints = 100;
        Vector position = new Vector(1, 0);
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);

        Assert.assertEquals(name, newPlayer.getName());
        Assert.assertEquals(hitpoints, newPlayer.getHitPoints());
        Assert.assertEquals(position, newPlayer.getPosition());
        Assert.assertEquals(animationPath, newPlayer.getAnimationPath());
        Assert.assertEquals(room, newPlayer.getRoom());
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the given position's y is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositionYNegative() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "Joel";
        int hitpoints = 100;
        Vector position = new Vector(1, -1);
        String animationPath = "animationString";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the given animation path is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnimationPathNull() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "Joel";
        int hitpoints = 100;
        Vector position = new Vector(1, 1);
        String animationPath = null;
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the given animation path is an empty string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnimationPathEmptyString() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "Joel";
        int hitpoints = 100;
        Vector position = new Vector(1, 1);
        String animationPath = "";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Tests if the constructor of this class throws an IllegalArgumentException
     * when the given animation path is a string exclusively containing spaces.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnimationPathExclusivelySpaces() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        String name = "Joel";
        int hitpoints = 100;
        Vector position = new Vector(1, 1);
        String animationPath = "   ";
        Player newPlayer = new Player(name, hitpoints, position, animationPath, room);
    }

    /**
     * Test of move method, of class Player.
     */
    @Test
    public void testMoveUp() {
        float angle = 0f;
        player.setMoveSpeed(10);
        Vector expectedResult = new Vector(1, 11);
        Vector result = player.getPosition();
        assertEquals("Unit test works, player.move not yet implemented", expectedResult.getX(), result.getX(), 0);
        assertEquals("Unit test works, player.move not yet implemented", expectedResult.getY(), result.getY(), 0);
    }

    /**
     * Test of move method, of class Player.
     */
    @Test
    public void testMoveDown() {
        float angle = 180f;
        player.setMoveSpeed(10);
        Vector expectedResult = new Vector(1, -9);
        player.move(angle);
        Vector result = player.getPosition();
        assertEquals("Unit test works, player.move not yet implemented", expectedResult.getX(), result.getX(), 0);
        assertEquals("Unit test works, player.move not yet implemented", expectedResult.getY(), result.getY(), 0);
    }

    /**
     * Test of move method, of class Player.
     */
    @Test
    public void testMoveRight() {
        float angle = 90f;
        player.setMoveSpeed(10);
        Vector expectedResult = new Vector(11, 1);
        player.move(angle);
        Vector result = player.getPosition();
        assertEquals("Unit test works, player.move not yet implemented", expectedResult.getX(), result.getX(), 0);
        assertEquals("Unit test works, player.move not yet implemented", expectedResult.getY(), result.getY(), 0);
    }

    /**
     * Test of move method, of class Player.
     */
    @Test
    public void testMoveLeft() {
        float angle = 270f;
        player.setMoveSpeed(10);
        Vector expectedResult = new Vector(-9, 1);
        player.move(angle);
        Vector result = player.getPosition();
        assertEquals("Unit test works, player.move not yet implemented", expectedResult.getX(), result.getX(), 0);
        assertEquals("Unit test works, player.move not yet implemented", expectedResult.getY(), result.getY(), 0);
    }

    /**
     * Test of Move method, of class Player. Testing if invalid values throw
     * exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMoveTooHigh() {
        float angle = 360f;
        player.setMoveSpeed(10);
        Vector expectedResult = new Vector(11, 1);
        player.move(angle);
        Vector result = player.getPosition();
        assertEquals(expectedResult.getX(), result.getX(), 0);
        assertEquals(expectedResult.getY(), result.getY(), 0);
    }

    /**
     * Test of Move method, of class Player. Testing if invalid values throw
     * exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMoveTooLow() {
        float angle = -1f;
        player.setMoveSpeed(10);
        Vector expectedResult = new Vector(11, 1);
        player.move(angle);
        Vector result = player.getPosition();
        assertEquals(expectedResult.getX(), result.getX(), 0);
        assertEquals(expectedResult.getY(), result.getY(), 0);
    }
    
    /**
     * Test of takeDamage method, of class Player.
     */
    @Test
    public void testTakeDamage() {
        int damage = 10;
        int expResult = 90;
        player.takeDamage(damage);
        int result = player.getHitPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of takeDamage method, of class Player.
     */
    @Test
    public void testTakeDamageZero() {
        int damage = 0;
        int expResult = 100;
        player.takeDamage(damage);
        int result = player.getHitPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of takeDamage method, of class Player.
     */
    @Test
    public void testTakeDamageNegative() {
        int damage = 1000;
        int expected = 0;
        player.takeDamage(damage);
        assertEquals(expected, player.getHitPoints());
    }

    /**
     * Test of fire method, of class Player.
     */
    @Test
    public void testFireSufficientAmmo() {
        String name = "XxXNoSc0eperGunXxX";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 10;
        String animationString = "animationString";

        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);
        Weapon weapon = new Weapon(type);

        player.setWeapon(weapon);

        int before = player.getRoom().getProjectiles().size();
        assertTrue(player.fire());
        int after = player.getRoom().getProjectiles().size();

        assertTrue((before + 1) == after);
    }

    /**
     * Tests fire method when player has too little ammo, of class Player.
     */
    @Test
    public void testFireInsufficientAmmo() {
        try {
            GameManager.getInstance().startGame();
        } catch (GameInProgressException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = "XxXNoSc0eperGunXxX";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 10;
        String animationString = "animationString";

        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);
        Weapon weapon = new Weapon(type);

        player.setWeapon(weapon);

        int before = player.getRoom().getProjectiles().size();

        while (player.getWeapon().getCurrentAmmo() > 0) {
            player.getWeapon().fire();
        }

        assertFalse(player.fire());
        int after = player.getRoom().getProjectiles().size();

        assertEquals(before + 10, after);
    }

    /**
     * Test of reload method, of class Player.
     */
    @Test
    public void testReload() {
        String name = "XxXNoSc0eperGunXxX";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);
        Weapon testWeapon = new Weapon(type);

        player.setWeapon(testWeapon);
        player.fire();
        player.reload();

        assertEquals(player.getWeapon().getCurrentAmmo(),
                testWeapon.getWeaponType().getMagazineSize());
    }
    /**
     * Test of setRoom method, of class Player.
     */
    @Test
    public void setRoom() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(8000, 4000, roomObjects);
        Vector position = new Vector(2000, 3200);
        Room expResult = new Room(position, roomType);
        player.setRoom(expResult);
        Room result = player.getRoom();
        assertEquals(expResult, result);

    }

    /**
     * Test of equals method, of class Player.
     */
    @Test
    public void testEqualsNull() {
        assertFalse(player.equals(null));
    }

    /**
     * Test of equals method, of class Player.
     */
    @Test
    public void testEqualsDifferentClass() {
        RoomObjectType test = new RoomObjectType(1f, "animationString");
        assertFalse(player.equals(test));

    }

    /**
     * Test of equals method, of class Player.
     */
    @Test
    public void testEqualsHitpoints() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        Player newPlayer = new Player("Joel", 90, new Vector(1, 1), "animationString", room);
        assertFalse(player.equals(newPlayer));

    }

    /**
     * Test of equals method, of class Player.
     */
    @Test
    public void testEqualsMovespeed() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        Player newPlayer = new Player("Joel", 100, new Vector(1, 1), "animationString", room);
        newPlayer.setMoveSpeed(100f);
        assertFalse(player.equals(newPlayer));

    }

    /**
     * Test of equals method, of class Player.
     */
    @Test
    public void testEqualsRotation() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        Player newPlayer = new Player("Joel", 100, new Vector(1, 1), "animationString", room);
        newPlayer.setRotation(267f);
        assertFalse(player.equals(newPlayer));
    }

    /**
     * Test of equals method, of class Player.
     */
    @Test
    public void testEqualsName() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        Player newPlayer = new Player("NietJoel", 100, new Vector(1, 1), "animationString", room);

        assertFalse(player.equals(newPlayer));
    }

    /**
     * Test of equals method, of class Player.
     */
    @Test
    public void testEqualsAnimation() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        Player newPlayer = new Player("Joel", 100, new Vector(1, 1), "TestString", room);

        assertFalse(player.equals(newPlayer));
    }

    /**
     * Test of equals method, of class Player.
     */
    @Test
    public void testEqualsScore() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        Player newPlayer = new Player("Joel", 100, new Vector(1, 1), "animationString", room);
        newPlayer.setScore(1234);

        assertFalse(player.equals(newPlayer));
    }

    /**
     * Test of equals method, of class Player.
     */
    @Test
    public void testEqualsAmmoReserves() {
        RoomType roomType = new RoomType(5, 5, new ArrayList());
        Room room = new Room(new Vector(2, 2), roomType);
        Player newPlayer = new Player("Joel", 100, new Vector(1, 1), "animationString", room);

        String name = "XxXNoSc0eperGunXxX";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);

        Weapon weapon = new Weapon(type);

        Vector position = new Vector(100, 100);

        newPlayer.setWeapon(weapon);
        newPlayer.fire();

        Assert.assertFalse(this.player.equals(newPlayer));

    }

    /**
     * Test of equals method, of class Player.
     */
    @Test
    public void testEqualsRoom() {
        RoomType roomType = new RoomType(9, 9, new ArrayList());
        Room room2 = new Room(new Vector(9, 9), roomType);
        Player newPlayer = new Player("Joel", 100, new Vector(1, 1), "animationString", room2);
        assertFalse(player.equals(newPlayer));
    }

    /**
     * Tests the fire method without a weapon. Testcase should return false.
     */
    @Test
    public void testFireWithoutWeapon() {
        Assert.assertFalse(player.fire());
    }

    /**
     * Tests the fire method with a weapon. Testcase should return true.
     */
    @Test
    public void testFireWithWeapon() {
        String name = "XxXNoSc0eperGunXxX";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 10;
        String animationString = "animationString";
        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);

        Weapon weapon = new Weapon(type);
        
        player.setWeapon(weapon);

        Assert.assertTrue(player.fire());
    }

    /**
     * Tests if hashcode returns same number on same object. Testcase should
     * return true.
     */
    @Test
    public void testHashCodeSameObject() {
        Player player2 = this.player;
        int first = this.player.hashCode();
        int second = player2.hashCode();

        Assert.assertTrue(first == second);
    }

    /**
     * Test if hashcode returns same number on different object with same values
     * are equals should return true
     */
    @Test
    public void testHashCodeDifferentObjectSameValues() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        Player player2 = new Player("Joel", 100, new Vector(1, 1), "animationString", room);
        int first = player.hashCode();
        int second = player2.hashCode();
        Assert.assertTrue(first == second);
    }

    /**
     * Tests if hashcode returns same number on different object. Testcase
     * should return false.
     */
    @Test
    public void testHashCodeDifferentObjectDifferentValues() {
        RoomType roomType = new RoomType(5, 5, new ArrayList<RoomObject>());
        Room room = new Room(new Vector(2, 2), roomType);
        Player player2 = new Player("Pim", 90, new Vector(2, 1), "testString", room);
        int first = player.hashCode();
        int second = player2.hashCode();

        Assert.assertFalse(first == second);
    }
}
