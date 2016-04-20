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
public class AmmoType
{
	private int damage;
	private int speed;
	private Animation image;
	
	public AmmoType(int damage, int speed, Animation image) {
		this.damage = damage;
		this.speed = speed;
		this.image = image;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public Animation getImage() {
		return this.image;
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
