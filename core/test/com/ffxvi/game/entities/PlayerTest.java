/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

import com.ffxvi.game.support.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
