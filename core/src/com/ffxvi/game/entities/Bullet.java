package com.ffxvi.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.ffxvi.game.screens.GameScreen;

public class Bullet {
	private float x;
	private float y;
	public float dir;
	public float speed;
	public boolean doRemove;
	public boolean canCollide;
	public long startTime;
	/* delay in seconds */
	public long despawnDelay = 10;
	
	public Bullet(float x, float y, float dir, float speed) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.speed = speed;
		this.canCollide = true;
		this.startTime = 0;
	}
	
	public void update() {
		/* Only run this event when the bullet can still exist */
		if (!this.doRemove) {
			/* Update the coordinates based on the bullet speed and direction */
			this.x += this.speed * Math.cos(this.dir * Math.PI/180);
			this.y += this.speed * Math.sin(this.dir * Math.PI/180);
			
			/* Only check collisions if the bullet is allowed to collide */
			if (this.canCollide) {
				Rectangle rec = new Rectangle(this.x, this.y, 10, 10);
				boolean collision = checkCollision(rec, GameScreen.wallObjects);
				
				/* If to check if there's a collision */
				if(collision) {
					this.x -= this.speed * Math.cos(this.dir * Math.PI/180);
					this.y -= this.speed * Math.sin(this.dir * Math.PI/180);
					this.canCollide = false;
					this.speed = 0;
					this.startTime = System.nanoTime();
				}
			}
			
			/* Check if the bullet should be despawned */
			if (startTime > 0 &&
				System.nanoTime() - startTime > despawnDelay * 1000000000) {
				this.doRemove = true;
			}
		}
	}
        
        
	
	private boolean checkCollision(Rectangle rec, MapObjects objects) {
		for (RectangleMapObject mapObject : objects.getByType(RectangleMapObject.class)) {
			Rectangle rectangleMapObject = mapObject.getRectangle();
			if (rec.overlaps(rectangleMapObject)) {
				return true;
			} 
		}
		return false;
	}
	
	public void render(ShapeRenderer shape, OrthographicCamera camera){
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(Color.WHITE);
		float length = 20;
		float loc1x = x;
		float loc1y = y;
		float loc2x = (float)(loc1x + (length * (Math.cos(this.dir * Math.PI/180))));
		float loc2y = (float)(loc1y + (length * (Math.sin(this.dir * Math.PI/180))));
		shape.circle(loc1x, loc1y, 5);
		shape.circle(loc2x, loc2y, 4);
		shape.line(loc1x, loc1y, loc2x, loc2y);
		shape.end();
	}
}