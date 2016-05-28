package com.sml;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class ProgerGame extends ApplicationAdapter {
    SpriteBatch batch;
    Matrix4 mx4Font = new Matrix4();
    Texture img;
    BitmapFont bitmapFont;
    String codeSample;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        bitmapFont = new BitmapFont(Gdx.files.internal("codeFont.fnt"));
        codeSample = "batch = new SpriteBatch();\n"
                + "bitmapFont = new BitmapFont(Gdx.files.internal(\"codeFont.fnt\")); \n"
                + "Gdx.gl.glClearColor(1, 0, 0, 1);";

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        bitmapFont.draw(batch, codeSample, 0, Gdx.graphics.getHeight());
        batch.end();
    }
}
