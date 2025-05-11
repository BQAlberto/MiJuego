package com.svalero.mijuego.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.svalero.mijuego.characters.Player;
import com.badlogic.gdx.maps.tiled.TiledMap;

import static com.svalero.mijuego.util.Constants.*;

public class CameraManager {

    private OrthographicCamera camera;
    private TiledMap map;

    public CameraManager(TiledMap map) {
        this.map = map;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 20 * TILE_WIDTH, 15 * TILE_HEIGHT);
        camera.update();
    }

    public void update(Player player) {
        float halfWidth = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;
        float mapWidth = map.getProperties().get("width", Integer.class) * TILE_WIDTH;
        float mapHeight = map.getProperties().get("height", Integer.class) * TILE_HEIGHT;

        float cameraX = Math.max(halfWidth, Math.min(player.getX(), mapWidth - halfWidth));
        float cameraY = Math.max(halfHeight, Math.min(player.getY(), mapHeight - halfHeight));

        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
