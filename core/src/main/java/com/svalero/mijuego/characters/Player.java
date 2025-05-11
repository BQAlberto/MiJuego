package com.svalero.mijuego.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.svalero.mijuego.manager.LevelManager;
import com.svalero.mijuego.manager.R;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.svalero.mijuego.util.Constants.*;

@Data
public class Player extends Character {

    public enum State {
        RIGHT, LEFT, GUY_IDLE, JUMPING;
    }

    private int score;
    private int lives;
    private int currentLevel;

    private Animation<TextureRegion> rightAnimation, leftAnimation;
    public State state;
    private float stateTime;

    public Player (TextureRegion image) {
        super(image);
        score = 0;
        lives = 3;
        currentLevel = 1;
        isJumping = false;

        rightAnimation = new Animation<>(0.15f, R.getRegions("guy_run_right"));
        leftAnimation = new Animation<>(0.15f, R.getRegions("guy_run_left"));

        currentFrame = R.getTexture("guy_idle");
        state = State.GUY_IDLE;

        setPosition(new Vector2(0, TILE_HEIGHT * 1));
        rect.setPosition(position.x, position.y);
    }

    public void update (float dt) {
        stateTime += dt;

        // Animaciones
        switch (state) {

            case RIGHT:
                currentFrame = rightAnimation.getKeyFrame(stateTime, true);
                break;
            case LEFT:
                currentFrame = leftAnimation.getKeyFrame(stateTime, true);
                break;
            case GUY_IDLE:
                currentFrame = R.getTexture("guy_idle");
        }

        checkCollisions(dt);
    }

    public void takeDrop() {
        lives--;
    }

    public void taketurtle() {
        lives--;
    }

    public void addScore (int value) {
        score += value;
    }

    public void increaseLife() {
        this.lives++;
    }


}
