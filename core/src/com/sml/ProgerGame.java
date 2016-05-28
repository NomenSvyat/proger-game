package com.sml;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class ProgerGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private OrthographicCamera orthographicCamera;
    private float deltaTime;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player(0, 0, new Texture("player.png"));
        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthographicCamera.update();

        deltaTime = Gdx.graphics.getDeltaTime();
        player.computeNext(deltaTime);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        batch.draw(
                player.getTexture(),
                player.getX(),
                player.getY(),
                player.getTexture().getWidth() / 2,
                player.getTexture().getHeight() / 2,
                player.getTexture().getWidth(),
                player.getTexture().getHeight(),
                1,
                1,
                45,
                0,
                0,
                player.getTexture().getWidth(),
                player.getTexture().getHeight(),
                false,
                false
                );
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        player.dispose();
    }
}
