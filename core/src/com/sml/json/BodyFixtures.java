package com.sml.json;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class BodyFixtures {
    private String name;
    private String imagePath;
    private ArrayList<ArrayList<Vector2>> polygons;

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ArrayList<Vector2>> getPolygons() {
        return polygons;
    }
}
