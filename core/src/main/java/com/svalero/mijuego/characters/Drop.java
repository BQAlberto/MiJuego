package com.svalero.mijuego.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Data;

@Data
public class Drop extends Character{

    public Drop(TextureRegion image, Vector2 position) {
        super(image, position);
    }

}
