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
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import support.Vector;

/**
 *
 * @author Acer
 */
public class WeaponTest {

    /**
     * The weapon instance which is used for testing.
     */
    private Weapon weapon;

    /**
     * Initializes the weapon field so the testing can begin.
     *
     * @throws SlickException
     */
    @Before
    public void initialize() {
        GameManager.resetInstance();
        String name = "XxXNoSc0eperGunXxX";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 10;
        String animationString = "AnimationString";
        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);

        weapon = new Weapon(type);

        Vector position = new Vector(100, 100);
        RoomType roomType = new RoomType(50, 50, new ArrayList());
        Vector roomPosition = new Vector(500, 500);
        Room room = new Room(roomPosition, roomType);

        Player owner = new Player("Erik Janssen", 100, position, "AnimationString", room);

        weapon.setOwner(owner);
    }

    /**
     * Tests the constructor and the getters of the variables which are given a
     * value by the constructor.
     */
    @Test
    public void testConstructorNormalValues() {
        String name = "XxXNoSc0eperGunXxX";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 10;
        String animationString = "AnimationString";
        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);

        Weapon newWeapon = new Weapon(type);

        Assert.assertEquals(10, newWeapon.getCurrentAmmo());
        Assert.assertEquals(type, newWeapon.getWeaponType());
    }

    /**
     * Tests if the constructor throws an IllegalArgumentException when the
     * given weapon type is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWeaponTypeNull() {

        Weapon newWeapon = new Weapon(null);
    }

    /**
     * Sets the owner of the weapon and tests if it's the same by using the get
     * which is in the weapon class.
     */
    @Test
    public void testSetAndGetOwner() {
        ArrayList<RoomObject> roomObjects = new ArrayList<RoomObject>();
        RoomType roomType = new RoomType(5, 5, roomObjects);
        Room room = new Room(new Vector(2, 2), roomType);
        Player p1 = new Player("Joel", 100, new Vector(1, 1), "AnimationString", room);

        weapon.setOwner(p1);
        assertEquals(p1, weapon.getOwner());
    }

    /**
     * Tests if the user has ammo, then fire returns false. If weapon has ammo,
     * then the weapon fires and creates an projectile which is placed in the
     * room. So we test if there is an extra projectile spawned and put in the
     * list of projectiles in that room.
     */
    
    @Test
    public void testFireSufficientAmmo() {

        int ammoBefore = weapon.getCurrentAmmo();
        int projectilesBefore = weapon.getOwner().getRoom().getProjectiles().size();
        assertTrue(weapon.fire());
        int ammoAfter = weapon.getCurrentAmmo();
        int projectilesAfter = weapon.getOwner().getRoom().getProjectiles().size();

        assertTrue(ammoBefore < ammoAfter);
        assertTrue(projectilesAfter > projectilesBefore);

    }
     
    /**
     * Tests if the user has ammo, then fire returns false. If weapon has ammo,
     * then the weapon fires and creates an projectile which is placed in the
     * room. So we test if there is an extra projectile spawned and put in the
     * list of projectiles in that room.
     */
    @Test
    public void testFireInsufficientAmmo() {

        while (this.weapon.getCurrentAmmo() > 0) {
            weapon.fire();
        }

        int ammoBefore = weapon.getCurrentAmmo();
        int projectilesBefore = weapon.getOwner().getRoom().getProjectiles().size();

        assertFalse(weapon.fire());
        int ammoAfter = weapon.getCurrentAmmo();
        int projectilesAfter = weapon.getOwner().getRoom().getProjectiles().size();

        assertTrue(ammoBefore == ammoAfter);
        assertTrue(projectilesAfter == projectilesBefore);
    }

    /**
     * Checks if the player can reload, by checking if the currentammo is
     * smaller than the magazine size. If so, the weapon.reload() can be called.
     * Then we check if the currentammo is equal to the magazinesize. If so, the
     * test returns true.
     */
    @Test
    public void testReload() {
        if (weapon.getCurrentAmmo() < weapon.getWeaponType().getMagazineSize()) {
            weapon.reload(100);

            assertEquals(weapon.getCurrentAmmo(),
                    weapon.getWeaponType().getMagazineSize());
        }
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeSameObject() {
        int hashCodeFirst = this.weapon.hashCode();
        int hashCodeSecond = this.weapon.hashCode();

        junit.framework.Assert.assertEquals(hashCodeFirst, hashCodeSecond);
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeDifferentObjectSameValues() {
        String name = "XxXNoSc0eperGunXxX";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 10;
        String animationString = "AnimationString";
        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);

        Weapon newWeapon = new Weapon(type);

        Vector position = new Vector(100, 100);
        RoomType roomType = new RoomType(50, 50, new ArrayList());
        Vector roomPosition = new Vector(500, 500);
        Room room = new Room(roomPosition, roomType);

        Player owner = new Player("Erik Janssen", 100, position, "AnimationString", room);

        newWeapon.setOwner(owner);

        junit.framework.Assert.assertEquals(this.weapon.hashCode(), newWeapon.hashCode());
    }

    /**
     * Tests if the hashcode method's return value is equal when the same object
     * calls them.
     */
    @Test
    public void testHashcodeDifferentObjectDifferentValues() {
        String name = "TestWeapon";
        float fireRate = 1.0f;
        float range = 200f;
        int magazineSize = 40;
        String animationString = "TestString";
        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);

        Weapon newWeapon = new Weapon(type);

        Vector position = new Vector(100, 100);
        RoomType roomType = new RoomType(50, 50, new ArrayList());
        Vector roomPosition = new Vector(500, 500);
        Room room = new Room(roomPosition, roomType);

        Player owner = new Player("Erik Janssen", 100, position, "AnimationString", room);

        newWeapon.setOwner(owner);

        junit.framework.Assert.assertFalse(this.weapon.hashCode() == newWeapon.hashCode());
    }

    /**
     * Tests if the equals method's return value is true when the passed weapon
     * has the same values as this game.
     */
    @Test
    public void testEqualsDifferentObjectSameValues() {
        String name = "XxXNoSc0eperGunXxX";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 10;
        String animationString = "AnimationString";
        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);

        Weapon newWeapon = new Weapon(type);

        Vector position = new Vector(100, 100);
        RoomType roomType = new RoomType(50, 50, new ArrayList());
        Vector roomPosition = new Vector(500, 500);
        Room room = new Room(roomPosition, roomType);

        Player owner = new Player("Erik Janssen", 100, position, "AnimationString", room);

        newWeapon.setOwner(owner);

        junit.framework.Assert.assertTrue(this.weapon.equals(newWeapon));
    }

    /**
     * Tests if the equals method's return value is true when the passed weapon
     * is the same object.
     */
    @Test
    public void testEqualsSameObject() {

        junit.framework.Assert.assertTrue(this.weapon.equals(this.weapon));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is not a Weapon object.
     */
    @Test
    public void testEqualsObjectOtherClass() {

        junit.framework.Assert.assertFalse(this.weapon.equals(GameManager.getInstance()));
    }

    /**
     * Tests if the equals method's return value is false when the passed
     * weapon's current ammo is different.
     */
    @Test
    public void testEqualsCurrentAmmoNotEqual() {
        String name = "XxXNoSc0eperGunXxX";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 9;
        String animationString = "AnimationString";
        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);

        Weapon newWeapon = new Weapon(type);

        Vector position = new Vector(100, 100);
        RoomType roomType = new RoomType(50, 50, new ArrayList());
        Vector roomPosition = new Vector(500, 500);
        Room room = new Room(roomPosition, roomType);

        Player owner = new Player("Erik Janssen", 100, position, "AnimationString", room);

        newWeapon.setOwner(owner);

        junit.framework.Assert.assertFalse(this.weapon.equals(newWeapon));
    }

    /**
     * Tests if the equals method's return value is false when the passed
     * weapon's weapon type is different.
     */
    @Test
    public void testEqualsWeaponTypeNotEqual() {
        String name = "Ranged semi automatic.";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 10;
        String animationString = "AnimationString";
        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);

        Weapon newWeapon = new Weapon(type);

        Vector position = new Vector(100, 100);
        RoomType roomType = new RoomType(50, 50, new ArrayList());
        Vector roomPosition = new Vector(500, 500);
        Room room = new Room(roomPosition, roomType);

        Player owner = new Player("Erik Janssen", 100, position, "AnimationString", room);

        newWeapon.setOwner(owner);

        junit.framework.Assert.assertFalse(this.weapon.equals(newWeapon));
    }

    /**
     * Tests if the equals method's return value is false when the passed
     * weapon's owner is different.
     */
    @Test
    public void testEqualsOwnerNotEqual() {
        String name = "XxXNoSc0eperGunXxX";
        float fireRate = 2.0f;
        float range = 300f;
        int magazineSize = 9;
        String animationString = "AnimationString";
        AmmoType ammoType = new AmmoType(20, 20, animationString);
        WeaponType type = new WeaponType(name, fireRate, range, magazineSize,
                animationString, ammoType);

        Weapon newWeapon = new Weapon(type);

        Vector position = new Vector(100, 100);
        RoomType roomType = new RoomType(50, 50, new ArrayList());
        Vector roomPosition = new Vector(500, 500);
        Room room = new Room(roomPosition, roomType);

        Player owner = new Player("Erica Janssen", 100, position, "AnimationString", room);

        newWeapon.setOwner(owner);

        junit.framework.Assert.assertFalse(this.weapon.equals(newWeapon));
    }

    /**
     * Tests if the equals method's return value is false when the passed object
     * is null.
     */
    @Test
    public void testEqualsObjectNull() {

        junit.framework.Assert.assertFalse(this.weapon.equals(null));
    }
}
