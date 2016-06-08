/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

import com.ffxvi.game.models.Projectile;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.support.Vector;

/**
 *
 * @author gebruiker-pc
 */
public class LibProjectile extends Projectile{
	
	public LibProjectile(Vector position, float speed, float rotation, int roomID, String playerName, GameScreen screen) {
		super(position, speed, rotation, roomID, playerName, screen);
	}
	
}
