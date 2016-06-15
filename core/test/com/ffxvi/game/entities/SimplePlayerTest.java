/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

import com.ffxvi.game.models.PlayerCharacter;
import com.ffxvi.game.models.SimplePlayer;
import com.ffxvi.game.models.Direction;
import org.junit.*;

/**
 *
 * @author Acer
 */
public class SimplePlayerTest {
    SimplePlayer simplePlayer;
    
    @Before
    public void init() {
        String name = "Neem";
        float posX = 20f;
        float posY = 20f;
        int roomID = 1;
        PlayerCharacter pc = PlayerCharacter.SKELETON_HOODED;
        
        simplePlayer = new SimplePlayer(name, posX, posY, roomID, pc);
    }
    
    @Test
    public void getName() {
        Assert.assertTrue(simplePlayer.getName().equals("Neem"));
    }
    
    @Test
    public void getHitPoints() {
        Assert.assertTrue(simplePlayer.getHitPoints() == 100);
    }
    
    @Test
    public void getScore() {
        Assert.assertTrue(simplePlayer.getScore() == 0);
    }
    
    @Test
    public void getRoomID() {
        Assert.assertTrue(simplePlayer.getRoomId() == 1);
    }
    
    @Test
    public void getSpeed() {
        Assert.assertTrue(simplePlayer.getSpeed() == 0.0f);
    }
    
    @Test
    public void getDirection() {
        Assert.assertTrue(simplePlayer.getDirection() == Direction.DOWN);
    }
    
    @Test
    public void getSkin() {
        Assert.assertTrue(simplePlayer.getSkin() == PlayerCharacter.SKELETON_HOODED);
    }
    
    @Test
    public void getAnimation() {
        Assert.assertTrue(simplePlayer.getAnimation() == PlayerAnimation.IDLE);
    }
    
    @Test
    public void getStateTime() {
        Assert.assertTrue(simplePlayer.getStateTime() == 0);
    }
    
    @Test
    public void getPosX() {
        Assert.assertTrue(simplePlayer.getX() == 20.0f);
    }
    
    @Test
    public void getPosY() {
        Assert.assertTrue(simplePlayer.getY() == 20.0f);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigConstructorSkinNull() {
        String name = "Neem";
        float posX = 20f;
        float posY = 20f;
        int roomID = 1;
        PlayerCharacter pc = null;

        SimplePlayer simplePlayer2 = new SimplePlayer(name, posX, posY, roomID, pc);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigConstructorNameNull() {
        String name = null;
        float posX = 20f;
        float posY = 20f;
        int roomID = 1;
        PlayerCharacter pc = PlayerCharacter.SKELETON_NORMAL;

        SimplePlayer simplePlayer2 = new SimplePlayer(name, posX, posY, roomID, pc);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigConstructorNameEmpty() {
        String name = " ";
        float posX = 20f;
        float posY = 20f;
        int roomID = 1;
        PlayerCharacter pc = PlayerCharacter.SKELETON_HOODED;

        SimplePlayer simplePlayer2 = new SimplePlayer(name, posX, posY, roomID, pc);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigConstructorPosXNegative() {
        String name = "Test";
        float posX = -20f;
        float posY = 20f;
        int roomID = 1;
        PlayerCharacter pc = PlayerCharacter.SKELETON_NORMAL;

        SimplePlayer simplePlayer2 = new SimplePlayer(name, posX, posY, roomID, pc);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigConstructorPosYNegative() {
        String name = "Test";
        float posX = 20f;
        float posY = -20f;
        int roomID = 1;
        PlayerCharacter pc = PlayerCharacter.SKELETON_NORMAL;

        SimplePlayer simplePlayer2 = new SimplePlayer(name, posX, posY, roomID, pc);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigConstructorRoomIDZero() {
        String name = "Test";
        float posX = 20f;
        float posY = 20f;
        int roomID = 0;
        PlayerCharacter pc = PlayerCharacter.SKELETON_NORMAL;

        SimplePlayer simplePlayer2 = new SimplePlayer(name, posX, posY, roomID, pc);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigConstructorRoomIDNegative() {
        String name = "Test";
        float posX = 20f;
        float posY = 20f;
        int roomID = -1;
        PlayerCharacter pc = PlayerCharacter.SKELETON_HOODED;

        SimplePlayer simplePlayer2 = new SimplePlayer(name, posX, posY, roomID, pc);
    }
}
