/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.support;

import com.ffxvi.game.support.*;
import org.junit.*;

/**
 *
 * @author Acer
 */
public class UtilsTest {
    
    @Test
    public void makeInstance() {
        Utils u = new Utils();
        Assert.assertNotNull(u);
    }
    
    @Test
    public void setGridSize() {
        Utils.gridSize = 1;
        
        Assert.assertEquals(1, Utils.gridSize);
    }
}
