package com.sml.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sml.GameWorldConsts;
import com.sml.json.BodiesLoader;

public class BugActor extends Actor {
    public static final String ACTOR_NAME = "bug";

    public BugActor(World world, Vector2 position) {
        super(world, position);
    }

    @Override
    protected void initBody(World world) {
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
    public String getActorName() {
        return ACTOR_NAME;
    }

    @Override
    public void computeNext(float delta) {
        position.set(body.getWorldCenter().x, body.getWorldCenter().y);
    }
}
