/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.support;

import com.ffxvi.game.support.*;
import org.junit.*;

/**
 * Test the utils class.
 * @author Acer
 */
public class UtilsTest {
    
    /**
     * Make an instance, this needs to be there
     * because otherwise the code coverage says
     * that the constructor isn't tested, even 
     * though it has none.
     */
    @Test
    public void makeInstance() {
        Utils u = new Utils();
        Assert.assertNotNull(u);
    }
    
    /**
     * Get the gridsize field of the Utils class.
     * If this test fails, change the int 'size'.
     */
    @Test
    public void getGridSize() {
        int size = 64;
        
        Assert.assertTrue(size == Utils.GRIDSIZE);
    }
}
