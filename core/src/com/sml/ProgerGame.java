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
import com.sml.objects.Player;

import java.util.LinkedList;

public class ProgerGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture backgroundTexture;
    private Sprite sprite;
    private boolean pause;
    private Matrix4 mx4Font = new Matrix4();
    private BitmapFont bitmapFontStroke;
    private String codeSample;
    private LinkedList<CodeStroke> strokes = new LinkedList<CodeStroke>();
    private Player player;
    float timer;
    float screenWidth, screenHeight;
    int count = 0;

    @Override
    public void create() {

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        player = new Player();
        player.setPosition(screenWidth / 2 - player.getSprite().getWidth() / 2,
                screenHeight / 2 - player.getSprite().getHeight() / 2);

        bitmapFontStroke = new BitmapFont(Gdx.files.internal("numberFont.fnt"));

        mx4Font.translate(new Vector3(screenWidth, 0, 0));
        mx4Font.rotate(new Vector3(0, 0, 1), 90);

        codeSample = "batch = new SpriteBatch();";

        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        sprite = new Sprite(backgroundTexture, 0, 0, (int) screenWidth, (int) screenHeight);

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

        if (!pause) {

            batch.begin();
            batch.draw(backgroundTexture, 0, 0, screenWidth, screenHeight);
            batch.end();

            batch.setTransformMatrix(mx4Font);
            batch.begin();

            if (strokes.size() > 0) {
                for (CodeStroke codeStroke : strokes) {
                    if (codeStroke.getPosX() >= screenWidth / 2)
                        codeStroke.setChecked(true);
                    codeStroke.update(Gdx.graphics.getDeltaTime());
                    codeStroke.draw(batch);
                }
            }
            batch.end();


            batch.setTransformMatrix(new Matrix4());
            batch.begin();
            player.draw(batch);
            batch.end();


            timer += Gdx.graphics.getDeltaTime();
            if (timer >= 0.5f) {
                strokes.add(new CodeStroke(bitmapFontStroke, codeSample, String.valueOf(count)));
                count++;
                timer -= 0.5f;
            }
        }


        /* === Update === */



        /* === Control === */

        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > screenWidth / 2) {
                player.moveUp(Gdx.graphics.getDeltaTime());
            } else {
                player.moveDown(Gdx.graphics.getDeltaTime());
            }
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

}
