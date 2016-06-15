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
package com.ffxvi.game.chat;

import java.util.Date;

/**
 * This class represents a text message. It contains the name of the playername,
 * the content of the message, and the date of the message.
 *
 * This class is made to save the data only, it does not have any functionality.
 *
 * @author Guido
 */
public class ChatTextMessage {

    /**
     * The name of the player who made the message. This may not be null.
     */
    private String playerName;

    /**
     * The content of the message. This may not be null or empty.
     */
    private String messageContent;

    /**
     * The date of the message. This gets initialized with the right value.
     */
    private Date date;

    /**
     * The constructor of the class ChatTextMessage. The constructor gives all
     * the variables in this class a value.
     *
     * @param playerName The name of the player, this may not be null.
     *
     * @param messageContent The content of the message, this may not be null or
     * empty.
     *
     * @throws IllegalArgumentException When a parameter does not reach the
     * requirements this method will give a exception.
     */
    public ChatTextMessage(String playerName, String messageContent, Date date) {
        if (playerName == null || messageContent == null
                || messageContent.trim().isEmpty()) {

            throw new IllegalArgumentException("One of the parameters was null "
                    + "at the ChatTextMessage constructor");
        }

        this.playerName = playerName;
        this.messageContent = messageContent;
        this.date = date;
    }
	
	public ChatTextMessage(String receivedMessage)
	{
		
	}

    /**
     * This method is the toString method. It returns the message like how it
     * should appear in the chatbox.
     *
     * @return Returns the complete message of this object.
     */
    @Override
    public String toString() {
        String hour = Integer.toString(this.date.getHours());
        String minute = Integer.toString(this.date.getMinutes());
        String seconds = Integer.toString(this.date.getSeconds());

        return hour + ":" + minute + ":" + seconds + ": - "
                + this.playerName + ": " + this.messageContent;
    }

}
