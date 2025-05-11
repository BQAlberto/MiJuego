package com.svalero.mijuego.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.svalero.mijuego.characters.Drop;
import com.svalero.mijuego.characters.Enemy;
import com.svalero.mijuego.characters.Item;

import static com.svalero.mijuego.util.Constants.*;

public class RenderManager {

    private LogicManager logicManager;
    private CameraManager cameraManager;
    private OrthographicCamera hudCamera;

    private Batch batch;
    private OrthogonalTiledMapRenderer mapRenderer;
    private BitmapFont font;

    public RenderManager(LogicManager logicManager, TiledMap map) {
        this.logicManager = logicManager;


        mapRenderer = new OrthogonalTiledMapRenderer(map);
        batch = mapRenderer.getBatch();

        cameraManager = new CameraManager(map);
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        hudCamera.update();

        font = new BitmapFont();
    }

    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        cameraManager.update(logicManager.player);
        mapRenderer.setView(cameraManager.getCamera());
        mapRenderer.render();

        batch.setProjectionMatrix(cameraManager.getCamera().combined);
        batch.begin();
        //batch.draw(R.getTexture("background"), 0, 0);
        batch.draw(logicManager.player.getCurrentFrame(), logicManager.player.getX(), logicManager.player.getY());
        for (Item item : logicManager.items)
            batch.draw(item.getCurrentFrame(), item.getX(), item.getY());
        for (Drop drop : logicManager.drops)
            batch.draw(drop.getCurrentFrame(), drop.getX(), drop.getY());
        for (Enemy enemy : logicManager.enemies)
            batch.draw(enemy.getCurrentFrame(), enemy.getX(), enemy.getY());
        batch.end();

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        batch.draw(R.getTexture("coin"), 15, SCREEN_HEIGHT - 55);
        font.draw(batch, String.valueOf(logicManager.player.getScore()), 40, SCREEN_HEIGHT - 40);
        font.draw(batch, "Lives: " + logicManager.player.getLives(), 100, SCREEN_HEIGHT - 40);
        font.draw(batch, "Level: " + logicManager.currentLevel, 180, SCREEN_HEIGHT - 40);
        batch.end();
    }
}
