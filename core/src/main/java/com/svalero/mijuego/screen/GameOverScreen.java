package com.svalero.mijuego.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.svalero.mijuego.MiJuego;

public class GameOverScreen implements Screen {

    private final MiJuego game;

    public GameOverScreen(MiJuego game) {
        this.game = game;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Game Over", 300, 300);
        game.getFont().draw(game.getBatch(), "Pulsa ENTER para reiniciar", 250, 250);
        game.getBatch().end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game)); // reiniciar nivel
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

}
