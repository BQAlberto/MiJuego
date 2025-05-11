package com.svalero.mijuego.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.svalero.mijuego.MiJuego;
import com.svalero.mijuego.characters.*;
import com.svalero.mijuego.screen.*;

import static com.svalero.mijuego.characters.Player.State.*;
import static com.svalero.mijuego.util.Constants.*;

public class LogicManager {

    public long lastDrop;
    public Array<Drop> drops;
    public Array<Enemy> enemies;
    public Array<Item> items;
    public Player player;
    public int currentLevel;
    private MiJuego game;
    public int totalCoins;
    public int collectedCoins;
    private boolean gameFinished = false;

    public LogicManager(MiJuego game, int level) {
        this.game = game;
        this.currentLevel = level;

        initialize();
    }

    private void initialize() {
        lastDrop = TimeUtils.millis();
        totalCoins = 0;
        collectedCoins = 0;
    }

    private void generateDrop() {
        //TODO Controlar el tiempo al estar en pausa
        if (TimeUtils.timeSinceMillis(lastDrop) > TIME_BETWEEN_DROPS) {
            int x = MathUtils.random(0, Gdx.graphics.getWidth());
            int y = Gdx.graphics.getHeight() -10;
            Drop drop = new Drop(R.getTexture("drop"), new Vector2(x, y));
            drops.add(drop);
            lastDrop = TimeUtils.millis();
        }
    }

    private void moveDrops(float dt) {
        for (Drop drop : drops) {
            drop.move(0, -DROP_SPEED * dt);
            if ((drop.getY() + drop.getHeight()) < 0) {
                drops.removeValue(drop, true);
            }
        }
    }

    private void managePlayerInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.state = RIGHT;
            player.move(PLAYER_RUNNING_SPEED * dt, 0);
        }else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.state = LEFT;
            player.move(-PLAYER_RUNNING_SPEED * dt, 0);
        } else {
            if (player.state == RIGHT || player.state == LEFT) {
                player.state = GUY_IDLE;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.jump();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            //TODO Pendiente parar la partida antes de volver al menu
            game.pause = true;
            R.getMusic("music").stop();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new PauseScreen(game, game.getScreen()));
        }
    }

    private void manageColision() {
        for (Drop drop : drops) {
            if (drop.getRect().overlaps(player.getRect())) {
                drops.removeValue(drop, true);
                player.takeDrop();
                if (ConfigurationManager.isSoundEnabled()) {
                    R.getSound("drop").play();
                }
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.getRect().overlaps(player.getRect())) {

                if (ConfigurationManager.isEasyMode()) {
                    continue; // En modo fácil, las tortugas no hacen daño nunca
                }

                // En modo difícil, cualquier contacto con tortuga quita vida
                player.taketurtle();
                if (ConfigurationManager.isSoundEnabled()) {
                    R.getSound("drop").play();
                }
            }
        }

        for (Item item : items) {
            if (item.getRect().overlaps(player.getRect())) {
                items.removeValue(item, true);

                if (item instanceof Coin) {
                    player.addScore(COIN_VALUE);
                    collectedCoins++;

                    if (collectedCoins == totalCoins && !gameFinished) {
                        gameFinished = true;
                        if (currentLevel == 1) {
                            game.setScreen(new GameScreenLevel2(game));
                        } else if (currentLevel == 2) {
                            game.setScreen(new VictoryScreen(game));
                        }
                    }
                }

                if (item instanceof Heart) {
                    player.increaseLife();
                    //TODO Sonido corazón
                }
            }
        }
    }

    public void update(float dt) {
        if (gameFinished) return;
        generateDrop();
        moveDrops(dt);
        manageColision();
        managePlayerInput(dt);

        player.update(dt);

        for (Enemy enemy : enemies) {
            enemy.update(dt);
        }

        /*if (items.size == 0 && totalCoins > 0) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game));
        }*/

        if (player.getY() < -player.getHeight()) {
            R.getMusic("music").stop();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen(game));
        }

        if (player.getLives() <= 0) {
            R.getMusic("music").stop();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen(game));
        }

    }

}
