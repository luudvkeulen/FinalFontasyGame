/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.models;

import org.junit.*;

/**
 *
 * @author Acer
 */
public class MapTypeTest {
    MapType mType;
    
    @Before
    public void init() {
        int id = 1;
        String name = "level1";
        mType = new MapType(id, name);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void constructorIDNegative() {
        int id = -1;
        String name = "level1";
        MapType mType2 = new MapType(id, name);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void constructorNameNull() {
        int id = 1;
        String name = null;
        MapType mType2 = new MapType(id, name);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void constructorNameEmpty() {
        int id = 1;
        String name = " ";
        MapType mType2 = new MapType(id, name);
    }
    
    @Test
    public void testGetID() {
        Assert.assertEquals(1 ,mType.getId());
    }
    
    @Test
    public void testGetName() {
        Assert.assertEquals("level1" ,mType.getName());
    }
}
