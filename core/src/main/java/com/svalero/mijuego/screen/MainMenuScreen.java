package com.svalero.mijuego.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.svalero.mijuego.MiJuego;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private MiJuego game;

    public MainMenuScreen(MiJuego game) {
        this.game = game;
    }

    private void loadStage() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        VisLabel title = new VisLabel("MiJuego");
        title.setFontScale(2.5f);

        VisTextButton playButton = new VisTextButton("PLAY");
        playButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               dispose();
               ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game));
           }
        });

        VisTextButton instructionsButton = new VisTextButton("INSTRUCCIONES");
        instructionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InstructionsScreen(game));
            }
        });

        VisTextButton configButton = new VisTextButton("CONFIGURE");
        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ConfigurationScreen(game, game.getScreen()));
            }
        });

        VisTextButton quitButton = new VisTextButton("EXIT");
        quitButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dispose();
                    System.exit(0);
                }
        });

        table.row();
        table.add(title).center();
        table.row();
        table.add(playButton).center();
        table.row();
        table.add(instructionsButton).center().padTop(10);
        table.row();
        table.add(configButton).center();
        table.row();
        table.add(quitButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        loadStage();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
