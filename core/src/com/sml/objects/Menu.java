package com.sml.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by alexandrgrizhinku on 31/05/16.
 */

public class Menu implements GameObject {

    private Texture backgroundTexture;

    public void init() {
        backgroundTexture = new Texture(Gdx.files.internal("background2.png"));
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float delta) {
        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void update(float delta) {

    }


}
