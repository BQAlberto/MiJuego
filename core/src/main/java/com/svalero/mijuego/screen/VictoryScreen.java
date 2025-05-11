package com.svalero.mijuego.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.svalero.mijuego.MiJuego;

public class VictoryScreen implements Screen {

    private final MiJuego game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;

    private final Rectangle menuButton;
    private final Rectangle exitButton;

    public VictoryScreen(MiJuego game) {
        this.game = game;

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(1.0f);

        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float centerX = Gdx.graphics.getWidth() / 2f;

        menuButton = new Rectangle(centerX - 100, 200, 200, 50);
        exitButton = new Rectangle(centerX - 100, 120, 200, 50);
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibujar botones
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.6f, 0.2f, 1); // botón menú (verde)
        shapeRenderer.rect(menuButton.x, menuButton.y, menuButton.width, menuButton.height);
        shapeRenderer.setColor(0.6f, 0.2f, 0.2f, 1); // botón salir (rojo)
        shapeRenderer.rect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);
        shapeRenderer.end();

        // Dibujar texto
        batch.begin();
        font.draw(batch, "¡Enhorabuena!", Gdx.graphics.getWidth() / 2f - 130, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1.4f);
        font.draw(batch, "Has conseguido todas las monedas", Gdx.graphics.getWidth() / 2f - 210, Gdx.graphics.getHeight() - 150);

        font.getData().setScale(1.2f);
        font.draw(batch, "Volver al menú", menuButton.x + 25, menuButton.y + 35);
        font.draw(batch, "Salir", exitButton.x + 70, exitButton.y + 35);
        batch.end();

        // Detectar clics
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (menuButton.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new MainMenuScreen(game));
            } else if (exitButton.contains(touchPos.x, touchPos.y)) {
                Gdx.app.exit();
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
