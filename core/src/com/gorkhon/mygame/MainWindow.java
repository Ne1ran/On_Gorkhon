package com.gorkhon.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainWindow implements Screen {

    final OnGorkhonGame game;

    OrthographicCamera camera;

    Texture mwbg;
    Music mwmusic;

    public MainWindow(final OnGorkhonGame gam) {

        game = gam;
        mwbg = new Texture("mwbg.jpg");
        mwmusic = Gdx.audio.newMusic(Gdx.files.internal("twyrin.mp3"));
        mwmusic.setLooping(true);
        mwmusic.setVolume(0.1F);
        mwmusic.play();

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
        game.batch.draw(mwbg, 0, 0);
        game.font.draw(game.batch, "Welcome to the On Gorkhon game. Press Enter to start the game!", 1850/2-200, 950/2+75);
        game.font.draw(game.batch, "You need to create three bottles of tinctures to save Murky. Gather three of each herb to create one.", 1850/2-300, 950/2+50);
        game.font.draw(game.batch, "But the Plague is destroying the Town. And It will try to destroy you.", 1850/2-200, 950/2+25);
        game.font.draw(game.batch, "Collect Panacea to heal up infection and immunity or rare Twyrine to help your immunity.", 1850/2-200, 950/2);
        game.font.draw(game.batch, "Every tincture you make for Murky will also heal some of your immunity.", 1850/2-200, 950/2-25);
        game.font.draw(game.batch, "Beware of Shmowders. They clear infection, but annihilate your health and immunity", 1850/2-200, 950/2-50);
        game.font.draw(game.batch, "Good luck, Menhu.", 1850/2-100, 950/2-75);



        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(new GorkhonGame(game));
            mwmusic.dispose();
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