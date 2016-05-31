package com.sml.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.sml.GameWorldConsts;
import com.sml.json.BodiesLoader;

public class PlayerActor extends Actor implements ForceAppliable {
    public static final String ACTOR_NAME = "player";
    private static final float MAX_ROTATION = 45f;
    private static final float MAX_VELOCITY = 10f;
    private static final float MIN_VELOCITY = -MAX_VELOCITY;

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
        massData.mass = 50.1f;
        body.setMassData(massData);

        body.setUserData(this);
    }

    @Override
    public String getActorName() {
        return ACTOR_NAME;
    }

    @Override
    public void computeNext(float delta) {
        position.set(body.getWorldCenter().x - center.x, body.getWorldCenter().y - center.y);

        if (body.getPosition().y <= 0) {
            body.setLinearVelocity(0f, 10.5f);
        } else if (body.getPosition().y + texture.getHeight() >= GameWorldConsts.SCREEN_HEIGHT) {
            body.setLinearVelocity(0f, -10.5f);
        }
        System.out.println("Velocity = " + body.getLinearVelocity().y);
        System.out.println("Position = " + body.getPosition().y);
        rotation = body.getAngle() * MathUtils.radiansToDegrees;
        if (rotation == 0f) {
            rotation = 1f;
        }

//        if (Math.signum(rotation) != Math.signum(body.getLinearVelocity().y)) {
//            body.setAngularVelocity((Math.abs(rotation)) * (body.getLinearVelocity().y / 1000f));
//        } else {
//            body.setAngularVelocity((MAX_ROTATION - Math.abs(rotation)) * (body.getLinearVelocity().y / 1000f));
//        }
//        if (Math.abs(rotation) > MAX_ROTATION) {
//            body.setAngularVelocity(-Math.signum(body.getAngularVelocity()) * 0.00001f);
//        }

    }

    private void limitVelocity() {
        if (body.getLinearVelocity().y > MAX_VELOCITY) {
            body.getLinearVelocity().set(0f, MAX_VELOCITY);
        } else if (body.getLinearVelocity().y < MIN_VELOCITY) {
            body.getLinearVelocity().set(0f, MIN_VELOCITY);
        }
    }

    @Override
    public void applyForce(float force) {

        body.applyLinearImpulse(0f, force, center.x, center.y, true);
        if (force < 100 && body.getLinearVelocity().y > 0) {
            body.setLinearVelocity(0f, body.getLinearVelocity().y - 1f);
        }
//        body.getLinearVelocity().y
//        body.applyForceToCenter(0f, force, true);
        limitVelocity();
    }
}
