/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Joel
 */
public class GameManager {

	/**
	 * The main player of the game.
	 */
	private Player mainPlayer;

	//Multiplayer
	/**
	 * The players which are in the room.
	 */
	private List<SimplePlayer> multiplayers;

	/**
	 * The received player's data that is currently being rendered.
	 */
	private Player multiplayer;

	/**
	 * An ArrayList containing all projectiles which are in the room.
	 */
	private List<Projectile> projectiles;

	public GameManager() {
		projectiles = new ArrayList<Projectile>();
		multiplayers = new ArrayList<SimplePlayer>();

	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}

	public List<SimplePlayer> getMultiplayers() {
		return Collections.unmodifiableList(multiplayers);
	}

	public void addToMultiplayers(SimplePlayer multiplayer) {
		multiplayers.add(multiplayer);
	}

	public void setMultiplayer(Player player) {
		multiplayer = player;
	}

	public Player getMainPlayer() {
		return mainPlayer;
	}

	public void setMainPlayer(Player mainPlayer) {
		this.mainPlayer = mainPlayer;
	}

	/**
	 * Adds a list of other players to this game.
	 *
	 * @param multiplayers A list of the other players. An empty list is
	 * allowed.
	 */
	public void addMultiPlayers(Collection<SimplePlayer> multiplayers) {

		if (multiplayers == null) {
			throw new IllegalArgumentException("The multiplayers can not be null.");
		}

		this.multiplayers.clear();
		this.multiplayers = (List<SimplePlayer>) multiplayers;
	}

	public Player getMultiplayer() {
		return multiplayer;
	}

	public void removeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
	}

	public void setMultiplayers(ArrayList arrayList) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
