/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ffxvi.game.models.GameManager;
import com.ffxvi.game.models.Projectile;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.support.Vector;

/**
 *
 * @author gebruiker-pc
 */
public class LibProjectile extends Projectile {

	private GameScreen screen;

	public LibProjectile(Vector position, float speed, float rotation, int roomID, String playerName, GameScreen screen) {
		super(position, speed, rotation, roomID, playerName, screen.getGameManager());
		this.screen = screen;
	}

	public LibProjectile(Projectile projectile, GameScreen gameScreen) {
		super(projectile.getPosition(), projectile.getSpeed(), projectile.getRotation(), projectile.getRoomID(), projectile.getPlayerName(), gameScreen.getGameManager());

		this.screen = gameScreen;
	}

	/**
	 * Updates the position of this projectile when it still hasn't despawned.
	 */
	public void update() {
		// Only run this event when the bullet can still exist
		if (!this.shouldRemove()) {
			// Update the coordinates based on the bullet speed and direction
			this.position.setX(this.position.getX() + (this.speed * (float) Math.cos(this.rotation * Math.PI / 180)));
			this.position.setY(this.position.getY() + (this.speed * (float) Math.sin(this.rotation * Math.PI / 180)));

			// Only check collisions if the bullet is allowed to collide
			if (this.canCollide) {

				super.checkCollision();
			}
		}
	}

	/**
	 * Method which is executed when the screen is redrawn. The code concerns
	 * the drawing commands regarding projectiles.
	 *
	 * @param shape The shaperenderer.
	 * @param camera The camera.
	 */
	public void render(ShapeRenderer shape, OrthographicCamera camera) {
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(Color.WHITE);
		float length = 20;
		float loc1x = this.position.getX();
		float loc1y = this.position.getY();
		float loc2x = (float) (loc1x + (length * (Math.cos(this.rotation * Math.PI / 180))));
		float loc2y = (float) (loc1y + (length * (Math.sin(this.rotation * Math.PI / 180))));
		shape.circle(loc1x, loc1y, 5);
		shape.circle(loc2x, loc2y, 4);
		shape.line(loc1x, loc1y, loc2x, loc2y);
		shape.end();
	}

}
