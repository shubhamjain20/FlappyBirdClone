package com.shubhamjain.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;


import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends ApplicationAdapter implements InputProcessor {


	SpriteBatch batch;
	float timePassed;
    static Sound passSound,jumpSound,failSound;

    Bird bird;
    ArrayList<Tubes> tubeList;
    float tubeGap;

    Rectangle birdRect;

    static int score;

    MainMenu mainMenu;


    enum Screens
	{
		MAIN_MENU,
   	    GAME_SCREEN,
		END_SCREEN;
	}

	static Screens selectedScreen;



	@Override
	public void create () {

         tubeList = new ArrayList<Tubes>();
         batch=new SpriteBatch();


         Gdx.input.setInputProcessor(this);

         tubeGap = 50f;
         score = 0;


	     bird = new Bird(100f,Gdx.graphics.getHeight()/2);

		 birdRect = bird.getBirdRect();


         generatePipe();

         selectedScreen = Screens.MAIN_MENU;

         passSound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
         jumpSound = Gdx.audio.newSound(Gdx.files.internal("jumpsound.wav"));
         failSound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));

         mainMenu = new MainMenu();

}

	@Override
	public void render () {

   	if(selectedScreen == Screens.MAIN_MENU) {

   		batch.begin();
		mainMenu.backSprite.draw(batch);
		mainMenu.flappyHead.draw(batch);
		mainMenu.buttonSprite.draw(batch);
		batch.end();

   	}

   	else if(selectedScreen == Screens.GAME_SCREEN){

		timePassed += Gdx.graphics.getDeltaTime();


		batch.begin();

		mainMenu.backSprite.draw(batch);


		for (int i = 0; i < tubeList.size(); i++) {

			tubeList.get(i).getTopTubeSprite().draw(batch);
			tubeList.get(i).getBottomTubeSprite().draw(batch);
			tubeList.get(i).moveTubes();
			tubeList.get(i).collided(birdRect);

			if (tubeList.get(i).scoring()) {
				passSound.play();
				score++;
			}

			tubeList.get(i).disposeTubes();


		}

		batch.draw((TextureRegion) bird.getAnimation().getKeyFrame(timePassed, true), bird.getFlappyPosX(), bird.getFlappyPosY());
		mainMenu.font.draw(batch,String.valueOf(score),Gdx.graphics.getWidth()/2-60f,Gdx.graphics.getHeight()-400f-120f);


		batch.end();


		bird.updateVelocity();
		bird.setFlappyPosY(bird.getFlappyPosY());
		bird.setBoundaries();

	}

   	else if(selectedScreen == Screens.END_SCREEN){
		batch.begin();
		mainMenu.backSprite.draw(batch);
		mainMenu.font.draw(batch,String.valueOf(score),Gdx.graphics.getWidth()/2-60f,Gdx.graphics.getHeight()-400f-120f);
		mainMenu.buttonSprite.draw(batch);
		batch.end();
   	}


    if(birdRect.getY()<0){

	     selectedScreen = Screens.END_SCREEN;

     }




	}

	private void generatePipe() {
		tubeGap+=400f;
		tubeList.add(new Tubes(Gdx.graphics.getWidth() - 500f + tubeGap, Gdx.graphics.getHeight() - 650f  , Gdx.graphics.getWidth() - 500f + tubeGap, 0 - 380f));
	}



	@Override
	public void dispose () {
	    batch.dispose();
	    mainMenu.backSprite.getTexture().dispose();
	    bird.getAtlas().dispose();
	}

	@Override
	public boolean keyDown(int keycode) { return false; }

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if(selectedScreen == Screens.GAME_SCREEN) {
			bird.jump();
			generatePipe();
			jumpSound.play();
		}

	    if(selectedScreen == Screens.MAIN_MENU){
			if(screenX >= mainMenu.buttonSprite.getX() && screenX <= mainMenu.buttonSprite.getX()+600f && Gdx.graphics.getHeight() - screenY  >= mainMenu.buttonSprite.getY() && Gdx.graphics.getHeight() - screenY <= mainMenu.buttonSprite.getY() + 300f){
				jumpSound.play();
				selectedScreen = Screens.GAME_SCREEN;
			}
	    }

	    if(selectedScreen == Screens.END_SCREEN) {

	    	if (screenX >= mainMenu.buttonSprite.getX() && screenX <= mainMenu.buttonSprite.getX() + 600f && Gdx.graphics.getHeight() - screenY >= mainMenu.buttonSprite.getY() && Gdx.graphics.getHeight() - screenY <= mainMenu.buttonSprite.getY() + 300f) {
	    		jumpSound.play();
	    		tubeList.clear();
				tubeGap = 50f;
				score = 0;
				bird.setFlappyPosY(Gdx.graphics.getHeight()/2);
				bird.setVelocity(0f);
				selectedScreen = Screens.GAME_SCREEN;
	    	}

		}

        return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}


}
