package com.ffxvi.game.logics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.ArrayList;
import java.util.List;

public class ChatManager
{
	/**
	 * The list of chatlabels used to display messages
	 */
    private final List<Label> chatLabels;
	
	/**
	 * the Skin that needs to display the chat messages
	 */
    private final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public ChatManager()
    {
        chatLabels = new ArrayList();
    }

	/**
	 * Adds a new label to the skin and removes the first message from the list if the total message count is bigger than 10.
	 * 
	 * @param playerName The name of the player that owns the message.
	 * @param message The content of the message.
	 */
    public void addMessage(String playerName, String message)
    {
        chatLabels.add(new Label(String.format("%s: %s", playerName, message), skin));

        if (chatLabels.size() > 10)
        {
            chatLabels.remove(0);
        }
    }

	/**
	 * getter for the list of messages
	 * @return List which contains the labels in this chatManager
	 */
    public List<Label> getMessages()
    {
        return chatLabels;
    }
}
