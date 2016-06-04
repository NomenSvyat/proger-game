package com.sml.control;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sml.actors.Actor;
import com.sml.actors.PlayerActor;

public class PlayerCollisionListener implements ContactListener {
    private LifeManager lifeManager;

    public PlayerCollisionListener(LifeManager lifeManager) {
        this.lifeManager = lifeManager;
    }

    @Override
    public void beginContact(Contact contact) {
        Body body = contact.getFixtureA().getBody();
        if (body.getUserData() != null) {
            detectIfPlayer(body);
        } else if ((body = contact.getFixtureB().getBody()) != null && body.getUserData() != null) {
            detectIfPlayer(body);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void detectIfPlayer(Body body) {
        if (((Actor) body.getUserData()).getActorName().equals(PlayerActor.ACTOR_NAME)) {
            lifeManager.minusLife();
        }
    }
}
