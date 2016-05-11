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
package com.ffxvi.game.models;

import java.util.Objects;

/**
 * This contains all the properties of Weapon. It contains the WeaponType,
 * currentammo and owner.
 */
public class Weapon {

    /**
     * The WeaponType of this weapon.
     */
    private WeaponType weaponType;

    /**
     * The amount of amount of the weapon.
     */
    private int currentAmmo;

    /**
     * The owner of the weapon. This can be null if the weapon is on the ground.
     */
    private Player owner;

    /**
     * The cunstructor of the class. Gives all the fields in this class a value,
     * except for owner. Owner can be null if the weapon is on the ground.
     *
     * @param weaponType The weaponType of this weapon.
     */
    public Weapon(WeaponType weaponType) {
        if (weaponType == null) {
            throw new IllegalArgumentException();
        }

        this.weaponType = weaponType;
        this.currentAmmo = weaponType.getMagazineSize();
        this.owner = null;
    }

    /**
     * Returns the WeaponType of the instance of this weapon.
     *
     * @return This weaponType of this Weapon.
     */
    public WeaponType getWeaponType() {
        return weaponType;
    }

    /**
     * Returns the ammunition-amount of the instance of this weapon.
     *
     * @return The ammunition left in this Weapon.
     */
    public int getCurrentAmmo() {
        return currentAmmo;
    }

    /**
     * Returns the owner of the instance of this weapon. This method can return
     * null if the weapon is on the ground.
     *
     * @return The player that is currently using this Weapon. Can also return
     * null.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the owner of this instance of weapon.
     *
     * @param player The player which has picked up the weapon.
     */
    protected void setOwner(Player player) {
        this.owner = player;
    }

    /**
     * Fires an projectile from this weapon.
     *
     * @return True if the Weapon has sufficient ammunition (more than 0),
     * otherwise False
     */
    public boolean fire() {
        if (currentAmmo <= 0) {
            return false;
        } else {
            Projectile newProjectile = new Projectile(owner.getPosition(), owner.getRotation(), weaponType.getAmmoType());
            getOwner().getRoom().spawnProjectile(newProjectile);
            currentAmmo -= 1;
            return true;
        }
    }

    /**
     * Reloads this Weapon with the amount of ammunition given by the reload
     * method in player.
     *
     * @param playerAmmoSupply the amount of ammo the player has left in his/her
     * reserves. When a negative value, an IllegalArgumentException is thrown.
     * @return An int containing the amount of ammo that was reloaded.
     */
    public int reload(int playerAmmoSupply) {

        if (playerAmmoSupply < 0) {
            throw new IllegalArgumentException("PlayerAmmoSupply can not be a negative value.");
        }

        int spaceInWeapon = this.weaponType.getMagazineSize() - this.currentAmmo;
        int ammoToReload = Math.min(playerAmmoSupply, spaceInWeapon);

        this.currentAmmo += ammoToReload;

        return ammoToReload;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.weaponType);
        hash = 13 * hash + this.currentAmmo;
        hash = 13 * hash + Objects.hashCode(this.owner);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Weapon other = (Weapon) obj;
        if (this.currentAmmo != other.currentAmmo) {
            return false;
        }
        if (!this.weaponType.equals(other.weaponType)) {
            return false;
        }

        return Objects.equals(this.owner, other.owner);
    }
}
