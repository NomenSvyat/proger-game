package com.sml;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.sml.objects.CodeStroke;
import com.sml.objects.Level;
import com.sml.objects.Player;

import java.util.LinkedList;

public class ProgerGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture backgroundTexture;
    private boolean pause;
    private Matrix4 mx4Font = new Matrix4();
    private BitmapFont bitmapFontStroke;
    private String codeSample;
    private LinkedList<CodeStroke> strokes = new LinkedList<CodeStroke>();
    private Player player;
    float timer;
    float screenWidth, screenHeight;
    int count = 0;

    private Level level;

    @Override
    public void create() {
        level = new Level();
        level.init();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
//
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
//        player = new Player();
//        player.setPosition(screenWidth / 2 - player.getSprite().getWidth() / 2,
//                screenHeight / 2 - player.getSprite().getHeight() / 2);
//
//        bitmapFontStroke = new BitmapFont(Gdx.files.internal("numberFont.fnt"));
//
//        mx4Font.translate(new Vector3(screenWidth, 0, 0));
//        mx4Font.rotate(new Vector3(0, 0, 1), 90);
//
//        codeSample = "batch = new SpriteBatch();";
//
//        backgroundTexture = new Texture(Gdx.files.internal("background.png"));

    }

    @Override
    public void resume() {
        super.resume();
        pause = false;
    }

    @Override
    public void pause() {
        super.pause();
        pause = true;

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        level.update(Gdx.graphics.getDeltaTime());
        level.draw(batch);

        batch.end();

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

}
