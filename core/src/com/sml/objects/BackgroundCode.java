package com.sml.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sml.GameWorldConsts;
import com.sml.listeners.IBackgroundCode;

import java.util.LinkedList;

/**
 * Created by alexandrgrizhinku on 31/05/16.
 */

public class BackgroundCode implements GameObject{
    private LinkedList<CodeStroke> strokes = new LinkedList<CodeStroke>();
    private BitmapFont bitmapFont;
    private IBackgroundCode backgroundCode;
    private float positionToCountX = 0.0f;
    private int strokeCount = 0;
    private float spawnTimer;
    private float startTimer = 3;

    public BackgroundCode(BitmapFont font, float positionToCountX, IBackgroundCode backgroundCode){
        this.bitmapFont = font;
        this.positionToCountX = positionToCountX;
        this.backgroundCode = backgroundCode;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float delta) {
        if (strokes.size() > 0) {
            float fixedDelta = delta * GameWorldConsts.TEXT_VELOCITY;
            fixedDelta = (int)(fixedDelta * 1000) / 1000f;
            for (CodeStroke codeStroke : strokes) {
                if (!codeStroke.isChecked() && codeStroke.getPosX() >= positionToCountX) {
                    codeStroke.setChecked(true);
                    backgroundCode.onScoreGained();
                }
                codeStroke.update(fixedDelta);
                codeStroke.draw(spriteBatch, fixedDelta);
            }
        }
    }

    @Override
    public void update(float delta) {
        spawnTimer += delta;
        if (spawnTimer >= startTimer) {
            if (spawnTimer >= GameWorldConsts.SPAWN_VELOCITY) {
                strokes.add(new CodeStroke(bitmapFont, "CODE SAMPLE", String.valueOf(strokeCount)));
                strokeCount++;
                CodeStroke codeStroke = strokes.getFirst();
                if (codeStroke.getPosX() >= Gdx.graphics.getWidth()) {
                    strokes.pop();
                }
                spawnTimer -= GameWorldConsts.SPAWN_VELOCITY;
            }
        }
    }
}
