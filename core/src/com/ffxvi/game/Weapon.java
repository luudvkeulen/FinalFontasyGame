/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game;

/**
 *
 * @author Gebruiker
 */
public class Weapon
{
	private WeaponType weaponType;
	private int currentAmmo;
	private Player owner;
	
	public Weapon(WeaponType weaponType) {
		this.weaponType = weaponType;
	}
	
	public WeaponType getWeaponType() {
		return this.weaponType;
	}
	
	public int getCurrentAmmo() {
		return this.currentAmmo;
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	protected void setOwner(Player player) {
		this.owner = player;
	}
	
	public boolean fire() {
		return false;
	}
	
	public int reload(int playerAmmoSupply) {
		return 0;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		return false;
	}
}
