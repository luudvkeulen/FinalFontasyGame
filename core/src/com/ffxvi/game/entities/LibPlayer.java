/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.ffxvi.game.models.Player;
import com.ffxvi.game.models.PlayerCharacter;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.support.SkinManager;
import com.ffxvi.game.support.Vector;

/**
 *
 * @author gebruiker-pc
 */
public class LibPlayer extends Player {

	/**
	 * The textures (skin) that this player is using
	 */
	private SkinManager.PlayerSkin playerSkin;

	/**
	 * The animation that this player is currently in
	 */
	private Animation currentAnimation;

	/**
	 * The speed at which the animation runs.
	 */
	private final float animationSpeed;

	public LibPlayer(PlayerCharacter character, String playerName, Vector position, GameScreen screen, int roomId) {
		super(character, playerName, position, screen, roomId);
	}

}
