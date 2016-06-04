package com.sml.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class BodiesLoader {
    private final Gson gson = new Gson();

    private BodiesLoader() {
    }

    public static BodiesLoader getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private BodyFixtures readBodyShape(String name) {
        BodyFixtures bodyFixtures;
        Reader reader = null;
        try {
            reader = Gdx.files.internal(name + ".json").reader();
            bodyFixtures = gson.fromJson(reader, BodyFixtures.class);
        } catch (GdxRuntimeException e) {
            throw new IllegalStateException("No body files");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return bodyFixtures;
    }

    public Body getBody(String bodyName, World world, BodyDef bodyDef, FixtureDef fixtureDef) {
        BodyFixtures bodyFixtures = readBodyShape(bodyName);

        ArrayList<ArrayList<Vector2>> polygons = bodyFixtures.getPolygons();

        Body body = world.createBody(bodyDef);

        Vector2[] a = new Vector2[2];
        PolygonShape polygonShape;
        for (ArrayList<Vector2> polygon : polygons) {

            polygonShape = new PolygonShape();
            polygonShape.set(polygon.toArray(a));

            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);

            polygonShape.dispose();
        }

        return body;
    }

    private static class InstanceHolder {
        public static final BodiesLoader INSTANCE = new BodiesLoader();
    }
}
