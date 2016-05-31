package com.sml.objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by alexandrgrizhinku on 30/05/16.
 */

public class CodeStroke extends GameObject {

    private BitmapFont codeFont;
    private BitmapFont numberFont;
    private String code;
    private String number;
    private float PosX, PosY;
    private boolean checked = false;
    private float fadeAlpha = 1.0f;

    public CodeStroke(BitmapFont codeFont, String code, String number) {
        this.codeFont = codeFont;
        this.numberFont = codeFont;

        this.code = code;
        this.number = number;
    }

    @Override
    public void draw(SpriteBatch batch) {
        PosX += 2;
        if (checked) {
            numberFont.setColor(255.0f, 255.0f, 255.0f, fadeAlpha);
            numberFont.draw(batch, number, PosY + 10.0f, PosX);
            if (fadeAlpha > 0.5f) {
                fadeAlpha -= 0.005f;
            }
        } else {
            numberFont.setColor(255.0f, 255.0f, 255.0f, 0.5f);
            numberFont.draw(batch, number, PosY + 10.0f, PosX);
        }
        codeFont.setColor(255.0f, 200.0f, 0.0f, 0.7f);
        codeFont.draw(batch, code, PosY + 100.0f, PosX);

    }

    @Override
    public void update(float delta) {

    }


    public void setChecked(boolean checked) {
        numberFont.setColor(255.0f, 255.0f, 255.0f, 1.0f);
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public float getPosX() {
        return PosX;
    }
}
