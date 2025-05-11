package com.svalero.mijuego.manager;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.svalero.mijuego.characters.Coin;
import com.svalero.mijuego.characters.Enemy;
import com.svalero.mijuego.characters.Heart;
import com.svalero.mijuego.characters.Player;
import com.badlogic.gdx.math.Rectangle;


import static com.svalero.mijuego.util.Constants.TILE_HEIGHT;
import static com.svalero.mijuego.util.Constants.TILE_WIDTH;

public class LevelManager {

    public TiledMap map;
    public static TiledMapTileLayer groundLayer;
    private MapLayer enemiesLayer;
    private MapLayer itemsLayer;
    private TiledMapTileLayer backgroundLayer;

    private LogicManager logicManager;

    public LevelManager(LogicManager logicManager) {
        this.logicManager = logicManager;

        loadCurrentLevel();
    }

    public void loadCurrentLevel() {
        map = new TmxMapLoader().load("levels/level1.tmx");
        groundLayer = (TiledMapTileLayer) map.getLayers().get("ground");
        backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background");
        itemsLayer = map.getLayers().get("items");
        enemiesLayer = map.getLayers().get("enemies");

        logicManager.player = new Player(R.getTexture("guy_idle"));


        initilizeLevel();
        loadItems();
        loadEnemies();
    }

    public void loadLevel(String levelName) {
        map = new TmxMapLoader().load("levels/" + levelName);
        groundLayer = (TiledMapTileLayer) map.getLayers().get("ground");
        backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background");
        itemsLayer = map.getLayers().get("items");
        enemiesLayer = map.getLayers().get("enemies");

        logicManager.player = new Player(R.getTexture("guy_idle"));

        initilizeLevel();

        logicManager.totalCoins = 0;
        logicManager.collectedCoins = 0;

        loadItems();
        loadEnemies();
    }

    private void initilizeLevel() {
        logicManager.items = new Array<>();
        logicManager.drops = new Array<>();
        logicManager.enemies = new Array<>();
    }

    private void loadEnemies() {
        for (MapObject mapObject : enemiesLayer.getObjects()) {
            String type = mapObject.getProperties().get("type").toString();
            TiledMapTileMapObject object = (TiledMapTileMapObject) mapObject;
            switch (type) {
                case "enemy":
                    Enemy enemy = new Enemy(R.getTexture("turtle"), new Vector2(object.getX(), object.getY()));
                    logicManager.enemies.add(enemy);
                    break;
                case "bomb":
                    break;
                default:
            }
        }
    }

    private void loadItems() {
        for (MapObject mapObject : itemsLayer.getObjects()) {
            String type = mapObject.getProperties().get("type").toString();
            TiledMapTileMapObject object = (TiledMapTileMapObject) mapObject;
            switch (type) {
                case "coin":
                    Coin coin = new Coin(R.getTexture("coin"), new Vector2(object.getX(), object.getY()));
                    logicManager.items.add(coin);
                    logicManager.totalCoins++;
                    break;
                case "heart":
                    Heart heart = new Heart(R.getTexture("heart"), new Vector2(object.getX(), object.getY()));
                    logicManager.items.add(heart);
                    break;
                default:
            }
        }
    }

    public static Array<Rectangle> getCollisionTiles(int startX, int endX, int startY, int endY) {
        Array<Rectangle> tiles = new Array<>();

        int xCell, yCell;
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                xCell = x / TILE_WIDTH;
                yCell = y / TILE_HEIGHT;
                TiledMapTileLayer.Cell cell = groundLayer.getCell(xCell, yCell);
                if ((cell != null) && (cell.getTile().getProperties().containsKey("ground"))) {
                    // TODO optimizar con RectPool
                    Rectangle rect = new Rectangle();
                    rect.set(
                        (x / TILE_WIDTH) * TILE_WIDTH ,
                        (y / TILE_HEIGHT) * TILE_HEIGHT,
                        TILE_WIDTH, TILE_HEIGHT);

                    tiles.add(rect);
                }
            }
        }

        return tiles;
    }
}
