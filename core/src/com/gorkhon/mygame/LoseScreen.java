package com.gorkhon.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class LoseScreen implements Screen {
    final OnGorkhonGame game;

    OrthographicCamera camera;

    Texture lsbg;
    Music lsmusic;

    public LoseScreen(final OnGorkhonGame gam){

        game = gam;

        lsbg = new Texture("lsbg.png");
        lsmusic = Gdx.audio.newMusic(Gdx.files.internal("lsmusic.mp3"));
        lsmusic.setLooping(true);
        lsmusic.setVolume(0.1F);
        lsmusic.play();

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
        game.batch.draw(lsbg, 0, 0);
        game.font.draw(game.batch, "You lost... Press anywhere to go to the main menu!", 1850/2-200, 950/2);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new MainWindow(game));
            lsmusic.dispose();
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
