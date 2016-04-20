package com.ffxvi.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ffxvi.game.MainClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Final Fontasy XVI";
		config.width = 800;
		config.height = 600;
		config.resizable = false;
		config.vSyncEnabled = true;
		LwjglApplication lwjglApplication = new LwjglApplication(new MainClass(), config);
	}
}
