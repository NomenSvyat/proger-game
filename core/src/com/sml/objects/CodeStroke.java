package com.sml.objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sml.GameWorldConsts;

/**
 * Created by alexandrgrizhinku on 30/05/16.
 */

public class CodeStroke extends GameObject {

    private BitmapFont codeFont;
    private BitmapFont numberFont;
    private String code;
    private String number;
    private Vector2 position = new Vector2();
    private boolean checked = false;
    private float fadeAlpha = 1.0f;

    public CodeStroke(BitmapFont codeFont, String code, String number) {
        this.codeFont = codeFont;
        this.numberFont = codeFont;

        this.code = code;
        this.number = number;
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        if (checked) {
            numberFont.setColor(255.0f, 255.0f, 255.0f, fadeAlpha);
            numberFont.draw(batch, number, position.y + 10.0f, position.x);
            if (fadeAlpha > 0.5f) {
                fadeAlpha -= 0.005f;
            }
        } else {
            numberFont.setColor(255.0f, 255.0f, 255.0f, 0.5f);
            numberFont.draw(batch, number, position.y + 10.0f, position.x);
        }
        codeFont.setColor(255.0f, 200.0f, 0.0f, 0.7f);
        codeFont.draw(batch, code, position.y + 100.0f, position.x);

    }

    @Override
    public void update(float delta) {
        position.add(delta * GameWorldConsts.TEXT_VELOCITY, 0);
    }


    public void setChecked(boolean checked) {
        numberFont.setColor(255.0f, 255.0f, 255.0f, 1.0f);
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public float getPosX() {
        return position.x;
    }
}
