package com.sml.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public abstract class Actor implements Disposable {
    @Override
    public void dispose() {
        if (isDisposed) return;

        isDisposed = true;

        texture.dispose();
    }

    protected final Vector2 position;
    protected Vector2 center = new Vector2();
    protected boolean isDisposed;
    protected Texture texture;
    protected float rotation;
    protected Body body;



    public Actor(World world, Vector2 position) {
        this.position = position;
        texture = new Texture(getActorName() + ".png");

        center.set(texture.getWidth() / 2f, texture.getHeight() / 2f);

        initBody(world);
    }

    protected abstract void initBody(World world);

    protected abstract String getActorName();

    public abstract void computeNext(float delta);

    public void draw(SpriteBatch batch) {
        batch.draw(
                texture,
                position.x,
                position.y,
                center.x,
                center.y,
                texture.getWidth(),
                texture.getHeight(),
                1,
                1,
                rotation,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false
        );
    }


    final public Vector2 getPosition() {
        return position;
    }

    public float getCenterY() {
        return center.y;
    }

    public float getCenterX() {
        return center.x;
    }

    public Body getBody() {
        return body;
    }
}
