package com.sml.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by alexandrgrizhinku on 30/05/16.
 */

public interface GameObject {


    void draw(SpriteBatch spriteBatch, float delta);

    void update(float delta);


}
