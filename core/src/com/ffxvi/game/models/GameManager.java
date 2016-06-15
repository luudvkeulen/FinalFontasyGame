/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.models;

import com.ffxvi.game.MainClass;
import com.ffxvi.game.client.Client;
import com.ffxvi.game.logics.ChatManager;
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
	 * The code for this client.
	 */
	public final Client client;

	public final ChatManager chatManager;

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
	private final List<Projectile> projectiles;

	public GameManager(boolean isSpectating) {
		this.chatManager = new ChatManager(this);

		this.projectiles = new ArrayList<Projectile>();
		this.multiplayers = new ArrayList<SimplePlayer>();

		MainClass game = MainClass.getInstance();

		if (!game.selectedIp.equals("")) {

			String fulltext = game.selectedIp.replaceAll("\\s+", "");
			String fullip = fulltext.substring(fulltext.indexOf("-") + 1);
			System.out.println(fullip);
			this.client = new Client(fullip.substring(0, fullip.indexOf(":")), Integer.parseInt(fullip.substring(fullip.indexOf(":") + 1)), 1337, this, isSpectating);
			System.out.println(fullip.substring(0, fullip.indexOf(":")) + Integer.parseInt(fullip.substring(fullip.indexOf(":") + 1)));
		} else {
			this.client = null;
			System.out.println("Error no ip selected");
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void addProjectile(Projectile projectile, boolean receivedFromServer) {
		projectiles.add(projectile);

		if (!receivedFromServer) {
			// Send projectile to other players
			client.sendProjectile(new SimpleProjectile(projectile));
		}
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

		this.client.sendPlayer(new SimplePlayer(this.getMainPlayer()));
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

	public void sendPlayer(SimplePlayer player) {
		this.client.sendPlayer(player);
	}

	public void stop(boolean isSpectating) {
		this.client.stop(isSpectating);
	}
}
