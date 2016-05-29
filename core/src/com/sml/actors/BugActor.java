package com.sml.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sml.GameWorldConsts;
import com.sml.json.BodiesLoader;

public class BugActor extends Actor {
    public static final String ACTOR_NAME = "bug";
    private Texture texture;
    private Body body;
    private float centerX;
    private float centerY;
    private float rotation = 0f;

    public BugActor(World world, Vector2 position) {
        super(position);

        texture = new Texture(ACTOR_NAME + ".png");

        centerX = texture.getWidth() / 2f;
        centerY = texture.getHeight() / 2f;

        initBody(world);

    }

    public Body getBody() {
        return body;
    }

    public float getCenterY() {

        return centerY;
    }

    public float getCenterX() {
        return centerX;
    }

    private void initBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.linearVelocity.set(GameWorldConsts.BACKGROUND_VELOCITY, 0);

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;

        body = BodiesLoader.getInstance().getBody(ACTOR_NAME, world, bodyDef, fixtureDef);

        body.setLinearVelocity(GameWorldConsts.BACKGROUND_VELOCITY, 0);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void computeNext(float delta) {
        position.set(body.getWorldCenter().x, body.getWorldCenter().y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // TODO: 29.05.2016 to parent
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
}
