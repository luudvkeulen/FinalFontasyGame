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
package com.ffxvi.game.support;

import org.junit.*;

/**
 * Test the utils class.
 *
 * @author Acer
 */
public class UtilsTest {

    /**
     * Get the gridsize field of the Utils class. If this test fails, change the
     * int 'size'.
     */
    @Test
    public void getGridSize() {
        int size = 64;

        Assert.assertTrue(size == Utils.GRIDSIZE);
    }
}
