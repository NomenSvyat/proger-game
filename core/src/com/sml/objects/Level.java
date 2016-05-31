package com.sml.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.sml.GameWorldConsts;
import com.sml.control.PlayerLifeManager;
import com.sml.listeners.IBackgroundCode;

import java.io.File;

public class Level implements GameObject, IBackgroundCode, PlayerLifeManager.OnLifeChangeListener{

    private final float screenWidth = GameWorldConsts.SCREEN_WIDTH;
    private final float screenHeight = GameWorldConsts.SCREEN_HEIGHT;

    /**
     * User git file(s) need to be loaded in init() function
     */
    private Texture backgroundTexture;
    private BitmapFont font;
    private Matrix4 matrix4Rotate;
    private boolean pause = false;
    private File codeFile;
    private int scores = -1;
    private String scoresStr = "Scores : 0";
    private BackgroundCode backgroundCode;
    private UIPlayerLife uiPlayerLife;
    private boolean gameOver = false;
    public void init() {

        /** Loading font(s) */
        font = new BitmapFont(Gdx.files.internal("numberFont.fnt"));

        /** Setting up matrix to rotate text vertically */
        matrix4Rotate = new Matrix4();
        matrix4Rotate.translate(new Vector3(screenWidth, 0, 0));
        matrix4Rotate.rotate(new Vector3(0, 0, 1), 90);

        /** Background loading */
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundCode = new BackgroundCode(font, screenWidth / 2, this);

        uiPlayerLife = new UIPlayerLife(screenWidth - 100.0f, screenHeight - 100.0f);

    }

    @Override
    public void draw(SpriteBatch spriteBatch, float delta) {
        if (!gameOver) {
            spriteBatch.draw(backgroundTexture, 0, 0, screenWidth, screenHeight);
            spriteBatch.end();

            /** Rotate Matrix to render background code vertically */
            spriteBatch.setTransformMatrix(matrix4Rotate);
            spriteBatch.begin();
            backgroundCode.draw(spriteBatch, delta);
            spriteBatch.end();

            /** Reset Matrix to render other graphics normally */
            spriteBatch.setTransformMatrix(new Matrix4());
            spriteBatch.begin();
            font.setColor(Color.BLACK);
            font.draw(spriteBatch, scoresStr, 50.0f, screenHeight - 25.0f);

            //uiPlayerLife.draw(spriteBatch, delta);

        }
    }

    @Override
    public void update(float delta) {
        if(!gameOver) {
            backgroundCode.update(delta);
        }
    }

    @Override
    public void onScoreGained() {
        scores++;
        scoresStr = "Scores: " + scores;
    }

    @Override
    public void onLifeMinus() {
       uiPlayerLife.removeLife();
    }

    @Override
    public void onLifeCountEnded() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScores() {
        return scores;
    }
}
