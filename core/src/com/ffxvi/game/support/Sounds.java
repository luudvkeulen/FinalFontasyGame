/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Provides the sounds for the game.
 */
public class Sounds {

	/**
	 * The sound of a bow firing.
	 */
	public static final Sound BOW = Gdx.audio.newSound(Gdx.files.internal("arrow.mp3"));

	/**
	 * The sound of a sword slash.
	 */
	public static final Sound SLASH = Gdx.audio.newSound(Gdx.files.internal("slash.mp3"));
}
