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
package com.ffxvi.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ffxvi.game.MainClass;

/**
 * Class responsible for launching the desktop version of the game.
 */
public class DesktopLauncher {

    /**
     * Private constructor for hiding the implicit public one.
     */
    private DesktopLauncher() {

    }

    /**
     * Launches a new desktop version of the game.
     *
     * @param arg The command line arguments.
     */
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Final Fontasy XVI";
        config.width = 1920;
        config.height = 1080;
        config.fullscreen = true;
        config.resizable = false;
        config.vSyncEnabled = true;
        new LwjglApplication(MainClass.getInstance(), config);
    }
}
