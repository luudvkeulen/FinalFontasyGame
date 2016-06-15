/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ffxvi.game.models.Direction;
import com.ffxvi.game.entities.PlayerAnimation;

/**
 *
 * @author Robin
 */
public class SkinManager {
    
    private final PlayerSkin normalSkeleton;
    private final PlayerSkin hoodedSkeleton;
    
    public PlayerSkin getNormalSkeleton() {
        return normalSkeleton;
    }
    
    public PlayerSkin getHoodedSkeleton() {
        return hoodedSkeleton;
    }
    
    public SkinManager() {
        normalSkeleton = new PlayerSkin(0.05f, "Units/Skeleton_Normal");
        hoodedSkeleton = new PlayerSkin(0.05f, "Units/Skeleton_Hooded");
    }
    
    public class PlayerSkin {
        
        private float animationSpeed;
        private String skinDirectoryPath;
        
        private final int walkSheetCols = 9;
        private final int walkSheetRows = 4;
        private Animation walkUp;
        private Animation walkLeft;
        private Animation walkDown;
        private Animation walkRight;
        
        private final int slashSheetCols = 6;
        private final int slashSheetRows = 4;
        private Animation slashUp;
        private Animation slashLeft;
        private Animation slashDown;
        private Animation slashRight;
        
        private final int shootSheetCols = 13;
        private final int shootSheetRows = 4;
        private Animation shootUp;
        private Animation shootLeft;
        private Animation shootDown;
        private Animation shootRight;
		
		private final int deathSheetCols = 6;
		private final int deathSheetRows = 1;
		private Animation death;
        
        
        /**
         * The default constructor for PlayerSkin
         * @param animationSpeed the speed at which the animations will play. A 
         * lower value means a faster animation. Default:0.05 Min:0.01 Max:1.00
         * @param skinDirectoryPath the path where all the skin's textures are
         * stored as seen from the assets directory. Example: "Units/Skeleton_Dagger"
         */
        public PlayerSkin(float animationSpeed, String skinDirectoryPath) {
            if (animationSpeed < 0.01f || animationSpeed > 1.00f) {
                animationSpeed = 0.05f;
            }
            if (skinDirectoryPath == null || skinDirectoryPath.trim().equals("")
                    || skinDirectoryPath.trim().endsWith("/")) {
                throw new IllegalArgumentException("Path can not end with '/' or be empty");
            }
            
            this.animationSpeed = animationSpeed;
            this.skinDirectoryPath = skinDirectoryPath;
            setAnimations();
        }
        
        public Animation getAnimation(PlayerAnimation animation, Direction direction) {
            Animation ret = null;
            switch(animation) {
                case WALKING:
                    switch(direction) {
                        case UP: ret = this.walkUp; break;
                        case LEFT: ret = this.walkLeft; break;
                        case DOWN: ret = this.walkDown; break;
                        case RIGHT: ret = this.walkRight; break;
                    }
                    break;
                case SLASHING:
                    switch(direction) {
                        case UP: ret = this.slashUp; break;
                        case LEFT: ret = this.slashLeft; break;
                        case DOWN: ret = this.slashDown; break;
                        case RIGHT: ret = this.slashRight; break;
                    }
                    break;
                case SHOOTING:
                    switch(direction) {
                        case UP: ret = this.shootUp; break;
                        case LEFT: ret = this.shootLeft; break;
                        case DOWN: ret = this.shootDown; break;
                        case RIGHT: ret = this.shootRight; break;
                    }
                    break;
				case DEATH:
					ret = this.death;
					break;
            }
            return ret;
        }
        
        private void setAnimations() {
            setWalkingAnimations();
            setSlashingAnimations();
            setShootingAnimations();
			setDeathAnimations();
        }
        
        private void setWalkingAnimations() {
            float walkSpeed = this.animationSpeed * 1f;
            
            String path = skinDirectoryPath + "/Walk.png";
            Texture walkSheet = new Texture(Gdx.files.internal(path));
            TextureRegion[][] anims = TextureRegion.split(walkSheet, walkSheet.getWidth()
                    / walkSheetCols, walkSheet.getHeight() / walkSheetRows);
            
            this.walkUp = new Animation(walkSpeed, anims[0]);
            this.walkLeft = new Animation(walkSpeed, anims[1]);
            this.walkDown = new Animation(walkSpeed, anims[2]);
            this.walkRight = new Animation(walkSpeed, anims[3]);
        }
        
        private void setSlashingAnimations() {
            float slashSpeed = this.animationSpeed * 0.5f;
            
            String path = skinDirectoryPath + "/Slash.png";
            Texture slashSheet = new Texture(Gdx.files.internal(path));
            TextureRegion[][] anims = TextureRegion.split(slashSheet, slashSheet.getWidth()
                    / slashSheetCols, slashSheet.getHeight() / slashSheetRows);
            
            this.slashUp = new Animation(slashSpeed, anims[0]);
            this.slashLeft = new Animation(slashSpeed, anims[1]);
            this.slashDown = new Animation(slashSpeed, anims[2]);
            this.slashRight = new Animation(slashSpeed, anims[3]);
        }
        
        private void setShootingAnimations() {
            float shootSpeed = this.animationSpeed * 0.5f;
            
            String path = skinDirectoryPath + "/Shoot.png";
            Texture shootSheet = new Texture(Gdx.files.internal(path));
            TextureRegion[][] anims = TextureRegion.split(shootSheet, shootSheet.getWidth()
                    / shootSheetCols, shootSheet.getHeight() / shootSheetRows);
            
            this.shootUp = new Animation(shootSpeed, anims[0]);
            this.shootLeft = new Animation(shootSpeed, anims[1]);
            this.shootDown = new Animation(shootSpeed, anims[2]);
            this.shootRight = new Animation(shootSpeed, anims[3]);
        }
		
		private void setDeathAnimations() {
			float deathSpeed = this.animationSpeed * 1f;
			
			String path = skinDirectoryPath + "/Death.png";
            Texture deathSheet = new Texture(Gdx.files.internal(path));
            TextureRegion[][] anims = TextureRegion.split(deathSheet, deathSheet.getWidth()
                    / deathSheetCols, deathSheet.getHeight() / deathSheetRows);
            
            this.death = new Animation(deathSpeed, anims[0]);
		}
    }
}
