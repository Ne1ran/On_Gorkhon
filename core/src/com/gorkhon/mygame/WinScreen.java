package com.gorkhon.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class WinScreen implements Screen {
    final OnGorkhonGame game;

    OrthographicCamera camera;

    Texture wsbg;
    Music wsmusic;

    public WinScreen(final OnGorkhonGame game){
        this.game = game;

        wsbg = new Texture("wsbg.jpg");
        wsmusic = Gdx.audio.newMusic(Gdx.files.internal("wsmusic.mp3"));
        wsmusic.setLooping(true);
        wsmusic.setVolume(0.1F);
        wsmusic.play();

        camera = new OrthographicCamera();

        camera.setToOrtho(false, 1850, 950);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(wsbg, 0, 0);
        game.font.draw(game.batch, "You won and saved Murky! Press anywhere to go to the main menu!", 1850/2-200, 950/2);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new MainWindow(game));
            wsmusic.dispose();
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
