package com.ffxvi.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.ffxvi.game.screens.GameScreen;

public class Bullet {
	private float x;
	private float y;
	public float dir;
	public float speed;
	public boolean doRemove;
	
	public Bullet(float x, float y, float dir, float speed) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.speed = speed;
	}
	
	public void update() {
		if (!this.doRemove) {
			this.x += this.speed * Math.cos(this.dir * Math.PI/180);
			this.y += this.speed * Math.sin(this.dir * Math.PI/180);
		
			this.checkCollision();
		}
	}
	
	public void checkCollision() {
		// Check collision here
		Cell cell = GameScreen.wallLayer.getCell((int)this.x / 64, (int)this.y / 64);
		
		if (cell != null) {
			this.doRemove = true;
		}
	}
	
	public void render(ShapeRenderer shape, OrthographicCamera camera){
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(Color.BLACK);
		shape.circle(x, y, 5);
        shape.end();
	}
}
