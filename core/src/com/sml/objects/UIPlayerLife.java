package com.sml.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sml.GameWorldConsts;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by alexandrgrizhinku on 31/05/16.
 */

public class UIPlayerLife implements GameObject {

    private Texture texture;
    private int lifeCount = GameWorldConsts.PLAYER_LIFE_COUNT;
    private float iconDistance = 50.0f;
    private float positionX = GameWorldConsts.SCREEN_WIDTH;
    private ArrayList<Sprite> icons= new ArrayList<Sprite>();

    public UIPlayerLife(float posX, float posY){
        texture = new Texture(Gdx.files.internal("bug.png"));
        positionX = posX;

        for(int i = 0; i < lifeCount; i++){
            Sprite sprite = new Sprite(texture);
            sprite.rotate(-90);
            sprite.setPosition(positionX, posY);
            positionX -= iconDistance;
            icons.add(sprite);
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float delta) {
        if(icons != null && icons.size() > 0) {
            for (int i = 0; i < icons.size(); i++) {
                icons.get(i).draw(spriteBatch);
            }
        }
    }

    @Override
    public void update(float delta) {

    }

    public void removeLife(){
        if(icons != null && icons.size() > 0){
            icons.remove(icons.size() - 1);
            System.out.println("ICONS size " + icons.size() + " ");
        }
    }
}
