package com.sml.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.LinkedList;

/**
 * Created by alexandrgrizhinku on 31/05/16.
 */

public class Level extends GameObject {
    /**
     * User git file(s) need to be loaded in init() function
     */

    private Player player;
    private Texture backgroundTexture;
    private BitmapFont font;
    private Matrix4 matrix4Rotate;
    private float screenWidth, screenHeight;
    private boolean pause = false;
    //Need to implement to class
    private LinkedList<CodeStroke> strokes = new LinkedList<CodeStroke>();
    private String codeSample;
    private int scores = -1;
    private String scoresStr = "Scores : 0";
    private int strokeCount;
    private float spawnTimer;

    public void init() {

        /** Getting Screen info */
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        /** Player Settings */
        player = new Player();
        player.setPosition(screenWidth / 2 - player.getSprite().getWidth() / 2,
                screenHeight / 2 - player.getSprite().getHeight() / 2); 

        /** Loading font(s) */
        font = new BitmapFont(Gdx.files.internal("numberFont.fnt"));
        codeSample = "batch = new SpriteBatch();";

        /** Setting up matrix to rotate text vertically */
        matrix4Rotate = new Matrix4();
        matrix4Rotate.translate(new Vector3(screenWidth, 0, 0));
        matrix4Rotate.rotate(new Vector3(0, 0, 1), 90);

        /** Background texture loading */
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));

    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
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
                    codeStroke.draw(spriteBatch);
                }
            }
            spriteBatch.end();

            spriteBatch.setTransformMatrix(new Matrix4());
            spriteBatch.begin();
            player.draw(spriteBatch);
            font.setColor(Color.BLACK);
            font.draw(spriteBatch, scoresStr, 50.0f, screenHeight - 25.0f);
        }
    }

    @Override
    public void update(float delta) {
        spawnTimer += delta;
        if (spawnTimer >= 0.5f) {
            strokes.add(new CodeStroke(font, codeSample, String.valueOf(strokeCount)));
            strokeCount++;
            spawnTimer -= 0.5f;
            CodeStroke codeStroke = strokes.getFirst();
            if (codeStroke.getPosX() >= screenWidth) {
                strokes.pop();
            }

        }

        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > screenWidth / 2) {
                player.moveUp(Gdx.graphics.getDeltaTime());
            } else {
                player.moveDown(Gdx.graphics.getDeltaTime());
            }
        }
    }

}
