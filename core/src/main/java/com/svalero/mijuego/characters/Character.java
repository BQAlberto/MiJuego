package com.svalero.mijuego.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.svalero.mijuego.manager.LevelManager;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.svalero.mijuego.util.Constants.*;

@Data
@NoArgsConstructor
public abstract class Character {

    protected TextureRegion currentFrame;
    protected Vector2 position;
    protected Rectangle rect;
    protected Vector2 velocity;
    protected boolean isJumping;

    public Character(TextureRegion image, Vector2 position) {
        this.position = position;
        this.currentFrame = image;

        initialize();
    }

    public Character(TextureRegion image) {
        position = Vector2.Zero;
        this.currentFrame = image;

        initialize();
    }

    private void initialize() {
        rect = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        velocity = new Vector2();
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return currentFrame.getRegionWidth();
    }

    public float getHeight() {
        return currentFrame.getRegionHeight();
    }

    public void move(float x, float y) {
        position.x += x;
        position.y += y;
        rect.setPosition(position);
    }

    public void checkCollisions(float dt) {
        // Salto y caida
        move(0, velocity.y * dt);
        /*if (position.y < (TILE_HEIGHT * 1)) {
            position.y = TILE_HEIGHT * 1;
            isJumping = false;
        }*/


        velocity.y  -= GRAVITY;
        if (velocity.y < -PLAYER_JUMPING_SPEED)
            velocity.y = -PLAYER_JUMPING_SPEED;

        int startX, endX, startY, endY;
        // Colisiones en eje Y
        if (velocity.y > 0)
            startY = endY = (int) (position.y + rect.getHeight() + velocity.y);
        else
            startY = endY = (int) (position.y + velocity.y);

        startX = (int) position.x;
        endX = (int) (position.x + rect.getWidth());

        Array<Rectangle> tiles = LevelManager.getCollisionTiles(startX, endX, startY, endY);

        rect.y += velocity.y;
        for (Rectangle tileRect : tiles) {
            if (rect.overlaps(tileRect)) {
                if (velocity.y > 0) {
                    position.y = tileRect.y - rect.getHeight();
                } else {
                    position.y = tileRect.y + TILE_HEIGHT;
                    isJumping = false;
                }

                velocity.y = 0;
                break;
            }
        }
        rect.y = position.y;


        // Colisiones en el eje X
        if(velocity.x > 0)
            startX = endX = (int) (position.x + rect.getWidth() + velocity.x);
        else
            startX = endX = (int) (position.x + velocity.x);

        startY = (int) position.y;
        endY = (int) (position.y + rect.getHeight());

        tiles.clear();
        tiles = LevelManager.getCollisionTiles(startX, endX, startY, endY);

        rect.x += velocity.x;
        for (Rectangle rectTile : tiles) {
            if (rect.overlaps(rectTile)) {
                if (velocity.x > 0) {
                    position.x = rectTile.x - rect.getWidth();
                } else {
                    position.x = rectTile.x + rectTile.getWidth();
                }
                velocity.x = 0;
                break;
            }
        }
        rect.x = position.x;
    }

    public void jump() {
        if (!isJumping) {
            velocity.y = PLAYER_JUMPING_SPEED;
            isJumping = true;
        }
    }
}
