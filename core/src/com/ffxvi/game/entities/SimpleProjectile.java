/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

/**
 *
 * @author Joel
 */
public class SimpleProjectile {
    
    
    /**
     * The rotation of the projectile.
     */
    
    protected float rotation;
    
    /**
     * The speed of this projectile.
     */
    protected float speed;
    
    protected float x;
    protected float y;
    protected String playerName;
    protected int roomId;

    public SimpleProjectile(float rotation, float speed, float x, float y, String playerName, int roomId) {
        this.rotation = rotation;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.playerName = playerName;
        this.roomId = roomId;
    }
}
