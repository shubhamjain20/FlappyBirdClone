package com.shubhamjain.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MainMenu {

    Texture background,flappyTex,button;
    Sprite backSprite,flappyHead,buttonSprite;
    FreeTypeFontGenerator freeTypeFontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameters;
    BitmapFont font;

    public MainMenu(){
        background= new Texture("bg.png");
        flappyTex=new Texture("head.png");
        button = new Texture("button.png");
        backSprite=new Sprite(background);
        flappyHead = new Sprite(flappyTex);
        buttonSprite = new Sprite(button);
        backSprite.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        flappyHead.setPosition(50f,Gdx.graphics.getHeight()/2);
        flappyHead.setSize(Gdx.graphics.getWidth()-100f,Gdx.graphics.getHeight()/2-750f);
        buttonSprite.setPosition(flappyHead.getX()+150f,flappyHead.getY()-400f);
        buttonSprite.setSize(600f,300f);

        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("roboto.ttf"));
        parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 150;
        parameters.borderColor = Color.BLACK;
        parameters.borderWidth = 6f;
        parameters.color = Color.GOLD;
        font = freeTypeFontGenerator.generateFont(parameters);
    }


}
