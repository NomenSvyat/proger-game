package com.sml.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.sml.GameWorldConsts;

import java.io.File;
import java.util.LinkedList;

public class Level extends GameObject {
    private final float screenWidth = GameWorldConsts.SCREEN_WIDTH;
    private final float screenHeight = GameWorldConsts.SCREEN_HEIGHT;
    /**
     * User git file(s) need to be loaded in init() function
     */

    private Texture backgroundTexture;
    private BitmapFont font;
    private Matrix4 matrix4Rotate;
    private boolean pause = false;
    private LinkedList<CodeStroke> strokes = new LinkedList<CodeStroke>();
    private String codeSample;
    private File codeFile;
    private int scores = -1;
    private String scoresStr = "Scores : 0";
    private int strokeCount;
    private float spawnTimer;
    private float startTimer = 3.0f;

    private float lineSizeWithSpace;

    public void init() {

        /** Getting Screen info */

        /** Loading font(s) */
        font = new BitmapFont(Gdx.files.internal("numberFont.fnt"));
        lineSizeWithSpace = font.getCapHeight() * 1.5f;
        codeSample = "batch = new SpriteBatch();";

        /** Setting up matrix to rotate text vertically */
        matrix4Rotate = new Matrix4();
        matrix4Rotate.translate(new Vector3(screenWidth, 0, 0));
        matrix4Rotate.rotate(new Vector3(0, 0, 1), 90);

        /** Background texture loading */
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float delta) {
        if (!pause) {

            spriteBatch.draw(backgroundTexture, 0, 0, screenWidth, screenHeight);
            spriteBatch.end();

            spriteBatch.setTransformMatrix(matrix4Rotate);
            spriteBatch.begin();
            if (strokes.size() > 0) {
                for (CodeStroke codeStroke : strokes) {
                    if (!codeStroke.isChecked() && codeStroke.getPosX() >= screenWidth / 2) {
                        codeStroke.setChecked(true);
                        scores++;
                        scoresStr = String.valueOf("Scores : " + scores);
                    }
                    codeStroke.draw(spriteBatch, delta);
                }
            }
            spriteBatch.end();

            spriteBatch.setTransformMatrix(new Matrix4());
            spriteBatch.begin();
            font.setColor(Color.BLACK);
            font.draw(spriteBatch, scoresStr, 50.0f, screenHeight - 25.0f);
        }
    }

    @Override
    public void update(float delta) {
        spawnTimer += delta;
        if (spawnTimer >= startTimer) {
            if (spawnTimer >= GameWorldConsts.SPAWN_VELOCITY) {
                strokes.add(new CodeStroke(font, codeSample, String.valueOf(strokeCount)));
                strokeCount++;
                spawnTimer -= GameWorldConsts.SPAWN_VELOCITY;
                CodeStroke codeStroke = strokes.getFirst();
                if (codeStroke.getPosX() >= screenWidth) {
                    strokes.pop();
                }

            }

        }
    }

}
