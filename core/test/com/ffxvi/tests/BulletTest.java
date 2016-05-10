package com.ffxvi.tests;

import com.ffxvi.game.entities.Bullet;
import org.junit.Assert;
import org.junit.Test;
public class BulletTest {
	@Test
	public void testConstructor() {
		Bullet bullet = new Bullet(1, 2, 3, 4);
		Assert.assertEquals(3, bullet.dir, 0);
	}
}
