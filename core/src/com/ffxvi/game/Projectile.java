/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Gebruiker
 */
public class Projectile
{
	private Vector2 position;
	private float rotation;
	private AmmoType ammoType;
	
	public Projectile(Vector2 position, float rotation, AmmoType ammoType) {
		this.position = position;
		this.rotation = rotation;
		this.ammoType = ammoType;
	}
	
	public Vector2 getPosition() {
		return this.position;
	}
	
	public float getRotation() {
		return this.rotation;
	}
	
	public AmmoType getAmmoType() {
		return this.ammoType;
	}
	
	public void updatePosition() {
		// updatePosition logic here
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
