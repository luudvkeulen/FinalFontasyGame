package com.ffxvi.game.logics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.ArrayList;
import java.util.List;

public class ChatManager
{
    private final List<Label> chatLabels;
    private final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public ChatManager()
    {
        chatLabels = new ArrayList();
    }

    public void addMessage(String playerName, String message)
    {
        chatLabels.add(new Label(String.format("%s: %s", playerName, message), skin));

        if (chatLabels.size() > 10)
        {
            chatLabels.remove(0);
        }
    }

    public List<Label> getMessages()
    {
        return chatLabels;
    }
}
