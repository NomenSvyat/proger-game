package com.sml.objects.collections;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.sml.actors.BugActor;
import com.sml.objects.GameObject;

public class Bugs implements GameObject, Disposable {
    public static final int BUG_COUNT = 5;
    private BugActor bugActors[] = new BugActor[BUG_COUNT];

    public Bugs(World world, Camera camera) {
        for (int i = 0; i < BUG_COUNT; i++) {
            bugActors[i] = new BugActor
                    (world,
                            new Vector2(
                                    camera.viewportWidth + MathUtils.random(150f, 200f) * i,
                                    MathUtils.random(50f, camera.viewportHeight - 50f))
                    );
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float delta) {
        for (BugActor actor : bugActors) {
            actor.draw(spriteBatch, delta);
        }
    }

    @Override
    public void update(float delta) {
        for (BugActor actor : bugActors) {
            actor.update(delta);
        }
    }

    @Override
    public void dispose() {
        for (BugActor actor : bugActors) {
            actor.dispose();
        }
    }
}
