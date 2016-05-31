package com.sml.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by alexandrgrizhinku on 30/05/16.
 */

public class Player extends GameObject {

    private Sprite sprite;
    private Texture texture;
    private float posX, posY;

    Player(){
        this.texture = new Texture(Gdx.files.internal("player.png"));
        this.sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        this.sprite.setScale(0.5f);

        this.setPosition(0, 0);
    }
    @Override
    public void draw(SpriteBatch spriteBatch) {
        this.sprite.draw(spriteBatch);
    }

    @Override
    public void update(float delta) {

    }

    public void moveUp(float delta){
        this.posY += 200 * delta;
        setPosition(posX, posY);

    }

    public void moveDown(float delta){
        this.posY -= 200 * delta;
        setPosition(posX, posY);
    }

    public void setPosition(float posX, float posY){
        this.posX = posX;
        this.posY = posY;
        sprite.setPosition(posX, posY);
    }



    public Sprite getSprite() {
        return sprite;
    }

    public Texture getTexture() {
        return texture;
    }
}
