package com.svalero.mijuego.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.svalero.mijuego.MiJuego;

public class InstructionsScreen implements Screen {

    private MiJuego game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Rectangle backButton;

    public InstructionsScreen(MiJuego game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(1.2f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer = new ShapeRenderer();

        backButton = new Rectangle(Gdx.graphics.getWidth() / 2f - 100, 100, 200, 50);
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Botón "Volver"
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.3f, 0.6f, 0.8f, 1);
        shapeRenderer.rect(backButton.x, backButton.y, backButton.width, backButton.height);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, "INSTRUCCIONES", 100, Gdx.graphics.getHeight() - 80);
        font.getData().setScale(1f);
        font.draw(batch, "Usa las flechas para moverte, y la tecla UP para saltar.", 100, Gdx.graphics.getHeight() - 120);
        font.draw(batch, "Recoge todas las monedas para pasar de nivel.", 100, Gdx.graphics.getHeight() - 160);
        font.draw(batch, "Pulsa ESC para pausar la partida.", 100, Gdx.graphics.getHeight() - 200);
        font.draw(batch, "Volver", backButton.x + 60, backButton.y + 35);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if (backButton.contains(touch.x, touch.y)) {
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }

    @Override public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
