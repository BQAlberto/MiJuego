package com.svalero.mijuego.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import lombok.Data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Data
public class R {

    private static String TEXTURE_ATLAS = "mijuegov2.atlas";
    private static String SOUNDS = "sounds";

    private static AssetManager assetManager = new AssetManager();

    public static boolean update() {
        return assetManager.update();
    }

    public static void loadAllResources(){
        assetManager.load(TEXTURE_ATLAS, TextureAtlas.class);
        loadAllSounds();
    }

    private static void loadAllSounds() {
        assetManager.load(SOUNDS + File.separator + "drop.mp3", Sound.class);
        assetManager.load(SOUNDS + File.separator + "music.mp3", Music.class);
    }

    public static Sound getSound(String name) {
        return assetManager.get(SOUNDS + File.separator + name + ".mp3", Sound.class);
    }

    public static Music getMusic(String name) {
        return assetManager.get(SOUNDS + File.separator + name + ".mp3", Music.class);
    }

    public static TextureRegion getTexture(String name){
        return assetManager.get(TEXTURE_ATLAS, TextureAtlas.class).findRegion(name);
    }

    public static Array<TextureAtlas.AtlasRegion> getRegions(String name) {
        return assetManager.get(TEXTURE_ATLAS, TextureAtlas.class).findRegions(name);
    }

    public static void dispose(){
        //TODO Pendiente
    }

}
