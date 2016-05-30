package com.sml.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.sml.json.BodiesLoader;

public class PlayerActor extends Actor {
    public static final String ACTOR_NAME = "player";
    private static final int MAX_ROTATION = 45;

    public PlayerActor(World world, Vector2 position) {
        super(world, position);
    }

    @Override
    protected void initBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.angularDamping = 0.9f;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;

        body = BodiesLoader.getInstance().getBody(ACTOR_NAME, world, bodyDef, fixtureDef);
        MassData massData = new MassData();
        massData.center.set(center.x, center.y);
        massData.mass = 1f;
        body.setMassData(massData);

    }

    @Override
    protected String getActorName() {
        return ACTOR_NAME;
    }

    @Override
    public void computeNext(float delta) {
        rotation = body.getAngle() * MathUtils.radiansToDegrees;

        body.setAngularVelocity((MAX_ROTATION - Math.abs(rotation)) * body.getLinearVelocity().y / 100);

        if (Math.abs(rotation) >= MAX_ROTATION) {
            body.setAngularVelocity(0);
        }

        position.set(body.getWorldCenter().x - center.x, body.getWorldCenter().y - center.y);
    }
}
