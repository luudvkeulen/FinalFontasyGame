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
package com.ffxvi.game.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.support.Vector;
import java.io.Console;
import java.util.Objects;

/**
 * This class contains all the properties for the Projectile. It contains the
 * position, rotation and AmmoType.
 */
public class Projectile {

    /**
     * Vector2f containing the position of the projectile.
     */
    private Vector position;

    /**
     * The rotation of the projectile.
     */
    private float rotation;

    /**
     * The ammo type of this projectile.
     */
    private AmmoType ammoType;

    /**
     * The speed of this projectile.
     */
    private float speed;

    public long startTime;
    public long despawnDelay = 30;
    public boolean doRemove;
    public boolean canCollide;

    /**
     * Initializes a new projectile object.
     *
     * @param position the position in the room.
     * @param rotation the rotation of the projectile in degrees. If the
     * rotation is not in the range 0-359, throws an IllegalArgumentException.
     * @param ammoType the type of ammo this projectile is.
     */
    public Projectile(Vector position, float rotation, AmmoType ammoType) {
        if (position == null) {
            throw new IllegalArgumentException("The position of this projectile can not be null.");
        }

        if (rotation < 0 || rotation > 360) {
            System.out.println(rotation);
            throw new IllegalArgumentException("The rotation of the projectile must be in the range 0-360");
            
        }

        if (ammoType == null) {
            throw new IllegalArgumentException("The ammotype of the projectile can not be null.");
        }

        this.position = position;
        this.rotation = rotation;
        this.ammoType = ammoType;
        this.speed = ammoType.getSpeed();
        this.canCollide = true;
        this.startTime = System.nanoTime();
    }

    public void update() {
        /* Only run this event when the bullet can still exist */
        if (!this.doRemove) {
            /* Update the coordinates based on the bullet speed and direction */
            position.setX(position.getX() + (speed * (float) Math.cos(rotation * Math.PI / 180)));
            position.setY(position.getY() + (speed * (float) Math.sin(rotation * Math.PI / 180)));

            /* Only check collisions if the bullet is allowed to collide */
            if (this.canCollide) {
                Rectangle rec = new Rectangle(position.getX(), position.getY(), 10, 10);
                boolean collision = checkCollision(rec, GameScreen.wallObjects);

                /* If to check if there's a collision */
                if (collision) {
                    position.setX(position.getX() - (speed * (float) Math.cos(rotation * Math.PI / 180)));
                    position.setY(position.getY() - (speed * (float) Math.sin(rotation * Math.PI / 180)));
                    this.canCollide = false;
                    this.speed = 0;
                }
            }

            /* Check if the bullet should be despawned */
            if (System.nanoTime() - startTime > despawnDelay * 1000000000) {
                this.doRemove = true;
            }
        }
    }
    
    public void render(ShapeRenderer shape, OrthographicCamera camera){
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(Color.WHITE);
		float length = 20;
		float loc1x = position.getX();
		float loc1y = position.getY();
		float loc2x = (float)(loc1x + (length * (Math.cos(rotation * Math.PI/180))));
		float loc2y = (float)(loc1y + (length * (Math.sin(rotation * Math.PI/180))));
		shape.circle(loc1x, loc1y, 5);
		shape.circle(loc2x, loc2y, 4);
		shape.line(loc1x, loc1y, loc2x, loc2y);
		shape.end();
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

    /**
     * Gets the position in the room of this projectile.
     *
     * @return A Vector2f containing the position in the room of this
     * projectile.
     */
    public Vector getPosition() {
        return this.position;
    }

    /**
     * Gets the rotation in the room of this projectile.
     *
     * @return A float containing the rotation of this projectile.
     */
    public float getRotation() {
        return this.rotation;
    }

    /**
     * Gets the position in the room of this projectile.
     *
     * @return An AmmoType containing the ammo type of this projectile.
     */
    public AmmoType getAmmoType() {
        return this.ammoType;
    }

    /**
     * Changes the position with the speed found in ammoType and the rotation
     * found in this object. When the calculated new position is outside the
     * room, keeps the position at nearest border of the room.
     */
    public void updatePosition() {

        float newX = (float) (this.position.getX() + Math.cos(this.rotation) * speed);
        float newY = (float) (this.position.getY() + Math.sin(this.rotation) * speed);

        this.position = new Vector(newX, newY);

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.position);
        hash = 13 * hash + Float.floatToIntBits(this.rotation);
        hash = 13 * hash + Objects.hashCode(this.ammoType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Projectile other = (Projectile) obj;
        if (Float.floatToIntBits(this.rotation) != Float.floatToIntBits(other.rotation)) {
            return false;
        }
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        return this.ammoType.equals(other.ammoType);
    }

}
