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
package com.ffxvi.game.entities;

import com.ffxvi.game.support.Vector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Joel
 */
public class PlayerTest {

    private Player player;

    @Before
    public void initialize() {
        player = new Player(PlayerCharacter.SKELETON_DAGGER, "Test", new Vector(0f, 0f), 0);
    }

    @Test
    public void playerConstructor() {
        PlayerCharacter expectedCharacter = PlayerCharacter.SKELETON_DAGGER;
        PlayerCharacter actualCharacter = player.skin;
        Assert.assertEquals(expectedCharacter, actualCharacter);

        //waiting for pull of robins, koens and luuds mess
    }

    @Test
    public void playersetAimDirectionZero() {
        float expected = 0;
        player.setAimDirection(new Vector(1, 0));
        //float actual = player.g
    }
}
