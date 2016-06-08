/*
 * (C) Copyright 2016 - S33A
 * Final Fontasy XVI, Version 1.0.
 * 
 * Contributors:
 *   Pim Janissen
 *   Luud van Keulen
 *   Robin de Kort
 *   Koen Schilders
 *   Guido Thomasse
 *   Joel Verbeek
 */
package com.ffxvi.game.logics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ffxvi.game.chat.ChatTextMessage;
import com.ffxvi.game.screens.GameScreen;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which is responsible for the chat messages in the game.
 *
 */
public class ChatManager {

	/**
	 * The list of chatlabels used to display messages
	 */
	private final List<Label> chatLabels;

	/**
	 * the Skin that needs to display the chat messages
	 */
	private final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
	
	/**
	 * The gamescreen of the current session.
	 */
	private final GameScreen gameScreen;

	/**
	 * Initializes a new chat manager.
	 */
	public ChatManager(GameScreen gameScreen) {
		this.chatLabels = new ArrayList();
		this.gameScreen = gameScreen;
	}

	/**
	 * Adds a new label to the skin and removes the first message from the list
	 * if the total message count is bigger than 10.
	 *
	 * @param playerName The name of the player that owns the message. When an
	 * empty String (excluding spaces), throw an IllegalArgumentException.
	 * @param message The content of the message. When an empty String
	 * (excluding spaces), throw an IllegalArgumentException.
	 */
	public void addMessage(String playerName, String message) {

		if (playerName == null || playerName.trim().isEmpty()) {
			throw new IllegalArgumentException("PlayerName can neither be null nor an empty string (excluding spaces).");
		}

		if (message == null || message.trim().isEmpty()) {
			throw new IllegalArgumentException("PlayerName can neither be null nor an empty string (excluding spaces).");
		}

		this.chatLabels.add(new Label(String.format("%s: %s", playerName, message), skin));
	
		ChatTextMessage ctm = new ChatTextMessage(playerName, message);
		gameScreen.client.sendMessage(ctm);
		

		if (this.chatLabels.size() > 10) {
			this.chatLabels.remove(0);
		}
	}

	public void receiveMessage(String message) {

		if (message == null || message.trim().isEmpty()) {
			throw new IllegalArgumentException("Message can neither be null nor an empty string (excluding spaces).");
		}
		
		this.chatLabels.add(new Label(String.format("%s: %s", message), skin));
	
		if (this.chatLabels.size() > 10) {
			this.chatLabels.remove(0);
		}
	}
	/**
	 * getter for the list of messages
	 *
	 * @return List which contains the labels in this chatManager
	 */
	public List<Label> getMessages() {
		return this.chatLabels;
	}
}
