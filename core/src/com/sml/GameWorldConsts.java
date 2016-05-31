package com.sml;

public class GameWorldConsts {
    public static final int PLAYER_LIFE_COUNT = 3;
    public static final float TIME_STEP = 1 / 300f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    public static final int BACKGROUND_VELOCITY = -60;

    public static final int SCREEN_WIDTH = 960;
    public static final int SCREEN_HEIGHT = 600;

    public static final float TEXT_VELOCITY = -BACKGROUND_VELOCITY;
    public static final float SPAWN_VELOCITY = 1f;

    public static final float GRAVITY = -10f;
}
