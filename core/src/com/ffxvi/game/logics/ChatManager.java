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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ffxvi.game.models.GameManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class which is responsible for the chat messages in the game.
 *
 */
public class ChatManager {

	/**
	 * The list of chatlabels used to display messages
	 */
	private final List<String> chatMessages;
	
	/**
	 * The gamescreen of the current session.
	 */
	private final GameManager manager;

	/**
	 * Initializes a new chat manager.
	 * @param manager the game manager.
	 */
	public ChatManager(GameManager manager) {
		this.chatMessages = new ArrayList();
		this.manager = manager;
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
	public void sendMessage(String playerName, String message) {

		if (playerName == null || playerName.trim().isEmpty()) {
			throw new IllegalArgumentException("PlayerName can neither be null nor an empty string (excluding spaces).");
		}

		if (message == null || message.trim().isEmpty()) {
			throw new IllegalArgumentException("PlayerName can neither be null nor an empty string (excluding spaces).");
		}
		
		this.manager.client.sendMessage(this.generateMessage(playerName, message, new Date()));
	}

	public void receiveMessage(String message) {

		if (message == null || message.trim().isEmpty()) {
			throw new IllegalArgumentException("Message can neither be null nor an empty string (excluding spaces).");
		}
		
		this.chatMessages.add(message);
	
		if (this.chatMessages.size() > 10) {
			this.chatMessages.remove(0);
		}
	}
	/**
	 * getter for the list of messages
	 *
	 * @return List which contains the labels in this chatManager
	 */
	public List<String> getMessages() {
		return Collections.unmodifiableList(this.chatMessages);
	}
	
	private String generateMessage(String playerName, String messageContent, Date sendDate)
	{
		String hour = Integer.toString(sendDate.getHours());
        String minute = Integer.toString(sendDate.getMinutes());
        String seconds = Integer.toString(sendDate.getSeconds());

        return hour + ":" + minute + ":" + seconds + ": - "
                + playerName + ": " + messageContent;
	}
}
