package com.sml.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.sml.json.BodiesLoader;

public class PlayerActor extends Actor {
    public static final String ACTOR_NAME = "player";
    private static final int MAX_ROTATION = 45;
    private Texture texture;
    private boolean isDisposed;
    private float rotation;
    private float centerX;
    private float centerY;
    private Body body;

    public PlayerActor(World world, Vector2 position) {
        super(position);
        texture = new Texture(ACTOR_NAME + ".png");

        centerX = texture.getWidth() / 2f;
        centerY = texture.getHeight() / 2f;

        initBody(world);
    }

    private void initBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.angularDamping = 0.9f;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;

        body = BodiesLoader.getInstance().getBody(ACTOR_NAME, world, bodyDef, fixtureDef);
        MassData massData = new MassData();
        massData.center.set(centerX, centerY);
        massData.mass = 1f;
        body.setMassData(massData);

    }


    public Body getBody() {
        return body;
    }

    @Override
    public void computeNext(float delta) {
        rotation = body.getAngle() * MathUtils.radiansToDegrees;
        if (body.getLinearVelocity().y < 0 && body.getAngularVelocity() > 0) {
            body.applyTorque(-900000000, true);
        } else if (Math.abs(rotation) >= MAX_ROTATION) {
            body.setAngularVelocity(0);
        }

        position.set(body.getWorldCenter().x - centerX, body.getWorldCenter().y - centerY);
    }

    @Override
    public void dispose() {
        if (isDisposed) return;

        isDisposed = true;

        texture.dispose();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                texture,
                position.x,
                position.y,
                centerX,
                centerY,
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

    public float getCenterY() {
        return centerY;
    }

    public float getCenterX() {
        return centerX;
    }

}
