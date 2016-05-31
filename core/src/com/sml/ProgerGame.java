package com.sml;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sml.objects.Level;
import com.sml.objects.Menu;
import com.sml.utils.ICodeRepository;

public class ProgerGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private float screenWidth, screenHeight;
    private Level level;
    private Menu menu;
    private boolean pause = false;
    private ICodeRepository codeRepository;

    public ProgerGame(ICodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public void create() {

        menu = new Menu();
        menu.init();

        level = new Level();
        level.init(codeRepository);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//        if(!pause) {
//            level.update(Gdx.graphics.getDeltaTime());
//            level.draw(batch);
//        } else {
//            menu.update(Gdx.graphics.getDeltaTime());
//            menu.draw(batch);
//        }

        level.draw(batch);
        level.update(Gdx.graphics.getDeltaTime());

        batch.end();

//        if (Gdx.input.isTouched()) {
//            pause = true;
//        }

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

}
