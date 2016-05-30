package com.sml;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ProgerGame extends ApplicationAdapter {

    private int screenWidth;
    private int screenHeight;

	SpriteBatch batch;
	Texture img;
	Label label;
    Label label2;
    Stage stage;
    Table table;
    Table table2;

	@Override
	public void create () {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("font.json"));
        String code = "table = new Table(skin);\n" +
                "table.add(label);\n" +
                "table.setTransform(true);\n" +
                "table.row().expand().fill();\n" +
                "table.addAction(Actions.rotateBy(90));\n" +
                "table.setPosition(screenWidth + label.getHeight() / 2, 0 + label.getWidth() / 2);\n" +
                "stage.addActor(table);" +
                "@Override\n" +
                "\tpublic void render () {\n" +
                "\t\tGdx.gl.glClearColor(1, 0, 0, 1);\n" +
                "\t\tGdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);\n" +
                "\n" +
                "if (Gdx.input.isTouched()) {\n" +
                "    table.setX(table.getX() - (50 * Gdx.graphics.getDeltaTime()));\n" +
                "        if (table.getX() - label.getHeight() / 2 <= 0) {\n" +
                "            table2.setX(table2.getX() - (50 * Gdx.graphics.getDeltaTime()));\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "\t\tbatch.begin();\n" +
                "        stage.draw();\n" +
                "\t\tbatch.end();\n" +
                "        stage.act();\n" +
                "\t}";
        label = new Label(code, skin);
        label2 = new Label(code, skin);

        table = new Table(skin);
        table.add(label);
        table.setTransform(true);
        table.row().expand().fill();
        table.addAction(Actions.rotateBy(90));
        table.setPosition(screenWidth + label.getHeight() / 2, 10 + label.getWidth() / 2);
        stage.addActor(table);

        table2 = new Table(skin);
        table2.add(label2);
        table2.setTransform(true);
        table2.row().expand().fill();
        table2.addAction(Actions.rotateBy(90));
        table2.setPosition(screenWidth + label.getHeight() / 2, 10 + label.getWidth() / 2);
        stage.addActor(table2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isTouched()) {
            table.setX(table.getX() - (150 * Gdx.graphics.getDeltaTime()));
            if (table.getX() + label.getHeight() / 2 <= screenWidth) {
                table2.setX(table2.getX() - (150 * Gdx.graphics.getDeltaTime()));
            }
        }

		batch.begin();
        stage.draw();
		batch.end();
        stage.act();
	}
}
