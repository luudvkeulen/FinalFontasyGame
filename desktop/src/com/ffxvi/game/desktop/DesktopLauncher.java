package com.ffxvi.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ffxvi.game.MainClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();		
		config.title = "Final Fontasy XVI";
		config.width = 1920;
		config.height = 1080;
		config.fullscreen = true;
		config.resizable = false;
		config.vSyncEnabled = true;
		LwjglApplication lwjglApplication = new LwjglApplication(new MainClass(), config);
	}
}