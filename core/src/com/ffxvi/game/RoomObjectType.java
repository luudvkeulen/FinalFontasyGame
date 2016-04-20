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
public class RoomObjectType
{
	private float density;
	private Animation animation;
	
	public RoomObjectType(float density, Animation animation) {
		this.density = density;
		this.animation = animation;
	}
	
	public float getDensity() {
		return this.density;
	}
	
	public Animation getAnimation() {
		return this.animation;
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
