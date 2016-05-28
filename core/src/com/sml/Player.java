package com.sml;

import com.badlogic.gdx.graphics.Texture;

public class Player {
    private int x;
    private int y;
    private int angle;
    private int force;
    private Texture texture;
    private boolean isDisposed;

    public void computeNext(float time) {
        if (y > 0) {
            y -= 10 * time;
        }
    }

    public Player(int x, int y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void dispose(){
        if (isDisposed) return;

        isDisposed = true;

        texture.dispose();
    }
}
