package com.sml.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by alexandrgrizhinku on 30/05/16.
 */

abstract class GameObject {


    public abstract void draw(SpriteBatch spriteBatch, float delta);

    public abstract void update(float delta);


}
