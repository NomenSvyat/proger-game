package com.sml.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public abstract class Actor implements Disposable {

    protected final Vector2 position;


    public Actor(Vector2 position) {
        this.position = position;
    }

    public abstract void computeNext(float delta);

    public abstract void draw(SpriteBatch batch);

    final public Vector2 getPosition() {
        return position;
    }
}
