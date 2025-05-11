package com.svalero.mijuego.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.Data;

import static com.svalero.mijuego.util.Constants.ENEMY_RUNNING_SPEED;

@Data
public class Enemy extends Character {

    private int power;

    public Enemy(TextureRegion image, Vector2 position) {
        super(image, position);
    }

    public void update(float dt) {
        checkCollisions(dt);

        move(-ENEMY_RUNNING_SPEED * dt, 0);
    }

}
