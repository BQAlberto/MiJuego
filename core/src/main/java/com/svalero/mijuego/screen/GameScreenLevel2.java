package com.svalero.mijuego.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.svalero.mijuego.MiJuego;
import com.svalero.mijuego.manager.ConfigurationManager;
import com.svalero.mijuego.manager.LevelManager;
import com.svalero.mijuego.manager.LogicManager;
import com.svalero.mijuego.manager.RenderManager;
import com.svalero.mijuego.manager.R;

public class GameScreenLevel2 implements Screen {

    private LogicManager logicManager;
    private RenderManager renderManager;
    private LevelManager levelManager;
    private MiJuego game;

    public GameScreenLevel2(MiJuego game) {
        this.game = game;

        ConfigurationManager.loadPreferences();

        logicManager = new LogicManager(game, 2);

        levelManager = new LevelManager(logicManager);
        levelManager.loadLevel("level2.tmx");

        renderManager = new RenderManager(logicManager, levelManager.map);
    }

    @Override
    public void show() {
        game.pause = false;
        if (ConfigurationManager.isSoundEnabled()) {
            R.getMusic("music").play();
        }
    }

    @Override
    public void render(float delta) {
        if (!game.pause) {
            logicManager.update(delta);
        }
        renderManager.render();
    }

    @Override
    public void resize(int width, int height) {
        // Puedes añadir lógica si es necesario
    }

    @Override
    public void pause() {
        // Puedes pausar aquí si lo deseas
    }

    @Override
    public void resume() {
        // Al reanudar el juego
    }

    @Override
    public void hide() {
        // Limpieza cuando se cambia de pantalla
    }

    @Override
    public void dispose() {
        R.dispose();
    }
}
