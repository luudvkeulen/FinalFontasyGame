package com.ffxvi.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
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
			//double tempx = this.speed * Math.cos(this.dir * Math.PI/180);
			//double tempy = this.speed * Math.sin(this.dir * Math.PI/180);
			this.x += this.speed * Math.cos(this.dir * Math.PI/180);
			this.y += this.speed * Math.sin(this.dir * Math.PI/180);
		
			Rectangle rec = new Rectangle(this.x, this.y, 10, 10);
			boolean collision = checkCollision(rec, GameScreen.wallObjects);
			if(collision) {
				this.x -= this.speed * Math.cos(this.dir * Math.PI/180);
				this.y -= this.speed * Math.sin(this.dir * Math.PI/180);
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
		shape.circle(x, y, 5);
		shape.end();
	}
}
