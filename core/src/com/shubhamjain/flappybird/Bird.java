package com.shubhamjain.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Bird {

    private TextureAtlas atlas;
    private Texture bird;
    private Animation animation;
    private float flappyPosX,flappyPosY;
    private float jumpingSpeed;

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    private float velocity;
    private Rectangle birdRect;


    public void setFlappyPosY(float flappyPosY) {
        this.flappyPosY = flappyPosY;
    }

    public Bird(float flappyPosX, float flappyPosY){

        bird = new Texture("bird.png");
        atlas = new TextureAtlas("flapper.atlas");
        animation=new Animation(4/30f,atlas.getRegions());

        this.flappyPosX = flappyPosX;
        this.flappyPosY = flappyPosY;
        jumpingSpeed = 25f;
        velocity = 0f;


        birdRect = new Rectangle(flappyPosX + bird.getWidth()/2,flappyPosY + bird.getHeight()/2,80f,50f);


    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public Animation getAnimation() {
        return animation;
    }

    public float getFlappyPosX() {
        return flappyPosX;
    }

    public float getFlappyPosY() {
        return flappyPosY;
    }


    public void jump(){
        velocity=-jumpingSpeed;
    }

    public void updateVelocity(){

        velocity++;
        flappyPosY -= velocity;
        birdRect.setPosition(flappyPosX+30f,flappyPosY+30f);

    }

    public void setBoundaries(){
        if(flappyPosY<0){
            flappyPosY=0;
        }

        if(flappyPosY+100f>Gdx.graphics.getHeight()){
            flappyPosY=Gdx.graphics.getHeight()-100f;
        }
    }


    public Rectangle getBirdRect() {
        return birdRect;
    }
}
