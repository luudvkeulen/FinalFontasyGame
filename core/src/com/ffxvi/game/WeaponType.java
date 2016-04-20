/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 *
 * @author Gebruiker
 */
public class WeaponType
{
	private String name;
	private float fireRate;
	private float range;
	private int magazineSize;
	private Animation animation;
	private AmmoType ammoType;
	
	public WeaponType(String name, float fireRate, float range, int magazineSize, Animation animation, AmmoType ammoType) {
		this.name = name;
		this.fireRate = fireRate;
		this.range = range;
		this.magazineSize = magazineSize;
		this.animation = animation;
		this.ammoType = ammoType;
	}
	
	public String getName() {
		return this.name;
	}
	
	public float getFireRate() {
		return this.fireRate;
	}
	
	public float getRange() {
		return this.range;
	}
	
	public int getMagazineSize() {
		return this.magazineSize;
	}
	
	public Animation getAnimation() {
		return this.animation;
	}
	
	public AmmoType getAmmoType() {
		return this.ammoType;
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
