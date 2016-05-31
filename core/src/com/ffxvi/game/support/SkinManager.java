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
import com.ffxvi.game.entities.Direction;
import com.ffxvi.game.entities.PlayerAnimation;

/**
 *
 * @author Robin
 */
public class SkinManager {
    
    private PlayerSkin normalSkeleton;
    private PlayerSkin hoodedSkeleton;
    
    public PlayerSkin getNormalSkeleton() {
        return normalSkeleton;
    }
    
    public PlayerSkin getHoodedSkeleton() {
        return hoodedSkeleton;
    }
    
    public SkinManager() {
        normalSkeleton = new PlayerSkin(0.05f, "Units/Normal_Skeleton");
        hoodedSkeleton = new PlayerSkin(0.05f, "Units/Hooded_Skeleton");
    }
    
    private class PlayerSkin {
        
        private float animationSpeed;
        private String skinDirectoryPath;
        
        private Animation idle;
        
        private int walkSheetCols = 9;
        private int walkSheetRows = 4;
        private Animation walkUp;
        private Animation walkLeft;
        private Animation walkDown;
        private Animation walkRight;
        
        private int slashSheetCols = 6;
        private int slashSheetRows = 4;
        private Animation slashUp;
        private Animation slashLeft;
        private Animation slashDown;
        private Animation slashRight;
        
        private int shootSheetCols = 13;
        private int shootSheetRows = 4;
        private Animation shootUp;
        private Animation shootLeft;
        private Animation shootDown;
        private Animation shootRight;
        
        
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
                case IDLE:
                    ret = this.idle;
                case WALKING:
                    switch(direction) {
                        case UP: ret = this.walkUp;
                        case LEFT: ret = this.walkLeft;
                        case DOWN: ret = this.walkDown;
                        case RIGHT: ret = this.walkRight;
                    }
                case SLASHING:
                    switch(direction) {
                        case UP: ret = this.slashUp;
                        case LEFT: ret = this.slashLeft;
                        case DOWN: ret = this.slashDown;
                        case RIGHT: ret = this.slashRight;
                    }
                case SHOOTING:
                    switch(direction) {
                        case UP: ret = this.shootUp;
                        case LEFT: ret = this.shootLeft;
                        case DOWN: ret = this.shootDown;
                        case RIGHT: ret = this.shootRight;
                    }
            }
            this.idle = new Animation(0, ret.getKeyFrame(0));
            return ret;
        }
        
        private void setAnimations() {
            setWalkingAnimations();
            setSlashingAnimations();
            setShootingAnimations();
            this.idle = new Animation(0, walkDown.getKeyFrame(0));
        }
        
        private void setWalkingAnimations() {
            float walkSpeed = this.animationSpeed * 1f;
            
            Texture walkSheet = new Texture(Gdx.files.internal(skinDirectoryPath + "/Walk.png"));
            TextureRegion[][] anims = TextureRegion.split(walkSheet, walkSheet.getWidth()
                    / walkSheetCols, walkSheet.getHeight() / walkSheetRows);
            
            this.walkUp = new Animation(walkSpeed, anims[0]);
            this.walkLeft = new Animation(walkSpeed, anims[1]);
            this.walkDown = new Animation(walkSpeed, anims[2]);
            this.walkRight = new Animation(walkSpeed, anims[3]);
        }
        
        private void setSlashingAnimations() {
            float slashSpeed = this.animationSpeed * 0.5f;
            
            Texture slashSheet = new Texture(Gdx.files.internal(skinDirectoryPath + "/Slash.png"));
            TextureRegion[][] anims = TextureRegion.split(slashSheet, slashSheet.getWidth()
                    / slashSheetCols, slashSheet.getHeight() / slashSheetRows);
            
            this.slashUp = new Animation(slashSpeed, anims[0]);
            this.slashLeft = new Animation(slashSpeed, anims[1]);
            this.slashDown = new Animation(slashSpeed, anims[2]);
            this.slashRight = new Animation(slashSpeed, anims[3]);
        }
        
        private void setShootingAnimations() {
            float shootSpeed = this.animationSpeed * 0.5f;
            
            Texture shootSheet = new Texture(Gdx.files.internal(skinDirectoryPath + "/Shoot.png"));
            TextureRegion[][] anims = TextureRegion.split(shootSheet, shootSheet.getWidth()
                    / shootSheetCols, shootSheet.getHeight() / shootSheetRows);
            
            this.shootUp = new Animation(shootSpeed, anims[0]);
            this.shootLeft = new Animation(shootSpeed, anims[1]);
            this.shootDown = new Animation(shootSpeed, anims[2]);
            this.shootRight = new Animation(shootSpeed, anims[3]);
        }
    }
}
