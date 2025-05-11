package com.svalero.mijuego;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.svalero.mijuego.characters.Drop;
import com.svalero.mijuego.characters.Player;
import com.svalero.mijuego.manager.ConfigurationManager;
import com.svalero.mijuego.manager.R;
import com.svalero.mijuego.screen.GameScreen;
import com.svalero.mijuego.screen.MainMenuScreen;
import com.svalero.mijuego.screen.SplashScreen;
import lombok.Getter;

import static com.svalero.mijuego.util.Constants.DROP_SPEED;
import static com.svalero.mijuego.util.Constants.TIME_BETWEEN_DROPS;

public class MiJuego extends Game {

    public boolean pause;

    public MiJuego() {
        this.pause = false;
    }

    @Getter
    private SpriteBatch batch;
    @Getter
    private BitmapFont font;

    @Override
    public void create() {
        ConfigurationManager.loadPreferences();
        setScreen(new SplashScreen(this));
        batch = new SpriteBatch();
        font = new BitmapFont();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
