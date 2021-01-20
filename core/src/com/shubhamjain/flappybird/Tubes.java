package com.shubhamjain.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Tubes {

    private Texture topTube,bottomTube;
    private Sprite topTubeSprite,bottomTubeSprite;
    private float gap;
    private Random rand;
    private Rectangle topTubeRect;
    private Rectangle bottomTubeRect;
    private float topTubePosX,topTubePosY,bottomTubePosX,bottomTubePosY;


    public Tubes(float topTubePosX,float topTubePosY,float bottomTubePosX,float bottomTubePosY){

        this.topTubePosX = topTubePosX;
        this.topTubePosY = topTubePosY;
        this.bottomTubePosX = bottomTubePosX;
        this.bottomTubePosY = bottomTubePosY;

        rand = new Random();
        gap = (float)rand.nextInt(650);

        topTube = new Texture("toptube.png");
        topTubeSprite = new Sprite(topTube);
        topTubeSprite.setPosition(topTubePosX ,topTubePosY -gap);
        topTubeSprite.setSize(208f,1280f);
        topTubeRect = new Rectangle(topTubePosX,topTubePosY,topTubeSprite.getWidth(),topTubeSprite.getHeight());

        bottomTube = new Texture("bottomtube.png");
        bottomTubeSprite = new Sprite(bottomTube);
        bottomTubeSprite.setPosition(bottomTubePosX  ,bottomTubePosY - gap);
        bottomTubeSprite.setSize(208f,1280f);
        bottomTubeRect = new Rectangle(bottomTubePosX,bottomTubePosY,bottomTubeSprite.getWidth(),bottomTubeSprite.getHeight());




    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Sprite getTopTubeSprite() {
        return topTubeSprite;
    }

    public Sprite getBottomTubeSprite() {
        return bottomTubeSprite;
    }

    public void moveTubes(){
        topTubeSprite.translateX(-5f);
        bottomTubeSprite.translateX(-5f);
        bottomTubeRect.setPosition(bottomTubePosX,bottomTubePosY);
        topTubeRect.setPosition(topTubePosX,topTubePosY);
    }


    public Rectangle getBottomTubeRect() {
        return bottomTubeRect;
    }

    public Rectangle getTopTubeRect() {
        return topTubeRect;
    }

    public void collided(Rectangle birdRect){


          if(Intersector.overlaps(bottomTubeSprite.getBoundingRectangle(),birdRect) ||
                Intersector.overlaps(topTubeSprite.getBoundingRectangle(),birdRect) ||
                   birdRect.getY()<0){

              FlappyBird.failSound.play();

             FlappyBird.selectedScreen = FlappyBird.Screens.END_SCREEN;


        }


    }

    public float getBottomTubePosX() {
        return bottomTubePosX;
    }

    public float getTopTubePosY() {
        return topTubePosY;
    }

    public float getTopTubePosX() {
        return topTubePosX;
    }

    public float getBottomTubePosY() {
        return bottomTubePosY;
    }

    public boolean scoring(){

        if(bottomTubeSprite.getX() == 0){

           return true;


        }
        else
            {
            return false;
         }

    }

    public void disposeTubes(){
        if(topTubeSprite.getX() + 200f < 0){
            topTube.dispose();
            bottomTube.dispose();
        }
    }




}
