package com.svalero.mijuego.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.Data;

@Data
public class Coin extends Item {

    public Coin(TextureRegion image, Vector2 position) {
        super(image, position);
    }

}
