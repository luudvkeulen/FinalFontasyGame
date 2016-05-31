/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

import org.junit.*;

/**
 *
 * @author Acer
 */
public class SimpleProjectileTest {                    
    SimpleProjectile simpleProjectile;
    
    @Before
    public void init() {
        float rotation = 180f;
        float speed = 5f;
        float x = 20.0f;
        float y = 20.0f;
        String playerName = "Name";
        int roomID = 5;
        
        simpleProjectile = new SimpleProjectile(rotation, speed, x, y, playerName, roomID);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void bigConstructorRotationTooBig() {
        float rotation = 361f;
        float speed = 5f;
        float x = 20.0f;
        float y = 20.0f;
        String playerName = "Name";
        int roomID = 5;
        
        SimpleProjectile simpleProjectile2 = new SimpleProjectile(rotation, 
                speed, x, y, playerName, roomID);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void bigConstructorRotationTooSmall() {
        float rotation = -1f;
        float speed = 5f;
        float x = 20.0f;
        float y = 20.0f;
        String playerName = "Name";
        int roomID = 5;
        
        SimpleProjectile simpleProjectile2 = new SimpleProjectile(rotation, 
                speed, x, y, playerName, roomID);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void bigConstructorSpeedNegative() {
        float rotation = 1f;
        float speed = -1f;
        float x = 20.0f;
        float y = 20.0f;
        String playerName = "Name";
        int roomID = 5;
        
        SimpleProjectile simpleProjectile2 = new SimpleProjectile(rotation, 
                speed, x, y, playerName, roomID);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void bigConstructorPosXNegative() {
        float rotation = 1f;
        float speed = 1f;
        float x = -20.0f;
        float y = 20.0f;
        String playerName = "Name";
        int roomID = 5;
        
        SimpleProjectile simpleProjectile2 = new SimpleProjectile(rotation, 
                speed, x, y, playerName, roomID);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void bigConstructorPosYNegative() {
        float rotation = 1f;
        float speed = 1f;
        float x = 20.0f;
        float y = -20.0f;
        String playerName = "Name";
        int roomID = 5;
        
        SimpleProjectile simpleProjectile2 = new SimpleProjectile(rotation, 
                speed, x, y, playerName, roomID);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void bigConstructorPlayerNameNull() {
        float rotation = 1f;
        float speed = 1f;
        float x = 20.0f;
        float y = 20.0f;
        String playerName = null;
        int roomID = 5;
        
        SimpleProjectile simpleProjectile2 = new SimpleProjectile(rotation, 
                speed, x, y, playerName, roomID);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void bigConstructorPlayerNameEmpty() {
        float rotation = 1f;
        float speed = 1f;
        float x = 20.0f;
        float y = 20.0f;
        String playerName = " ";
        int roomID = 5;
        
        SimpleProjectile simpleProjectile2 = new SimpleProjectile(rotation, 
                speed, x, y, playerName, roomID);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void bigConstructorRoomIDNegative() {
        float rotation = 1f;
        float speed = 1f;
        float x = 20.0f;
        float y = 20.0f;
        String playerName = "Name";
        int roomID = -1;
        
        SimpleProjectile simpleProjectile2 = new SimpleProjectile(rotation, 
                speed, x, y, playerName, roomID);
    }
    
    @Test
    public void getRotation() {
        Assert.assertTrue(180f == simpleProjectile.getRotation());
    }
    
    @Test
    public void getSpeed() {
        Assert.assertTrue(5f == simpleProjectile.getSpeed());
    }
    
    @Test
    public void getX() {
        Assert.assertTrue(20.0f == simpleProjectile.getX());
    }
    
    @Test
    public void getY() {
        Assert.assertTrue(20.0f == simpleProjectile.getY());
    }
    
    @Test
    public void getShooterName() {
        Assert.assertTrue(simpleProjectile.getShooterName().equals("Name"));
    }
    
    @Test
    public void getRoomID() {
        Assert.assertTrue(5 == simpleProjectile.getRoomID());
    }
    
    @Test
    public void getPlayerName() {
        Assert.assertTrue(simpleProjectile.getPlayerName().equals("Name"));
    }
        
}
