package com.gorkhon.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GorkhonGame extends ApplicationAdapter {
	SpriteBatch batch;

	OrthographicCamera camera;

	Texture tvirin;
	Texture artemiy;
	Texture swevery;
	Texture white_whip;
	Texture ashen_swish;
	Texture plague;
	Texture bg;
	Texture shmowder;
	Texture panacea;

	Music bgmusic;

	Sound taketwyre;
	Sound infected;
	Sound cured;
	Sound createtincture;

	Rectangle haruspex;

	Array<Drop> fallings;

	long lastDropTime;

	int white_whip_count=0;
	int ashen_swish_count=0;
	int swevery_count=0;
	int tincturesmade=0;

	int immunity = 100;
	int infection = 0;

	String text = "Тинктур сварено: " + tincturesmade;

	@Override
	public void create() {
		batch = new SpriteBatch();
		tvirin = new Texture("twyrine.png");
		artemiy = new Texture("Haruspex.png");
		swevery = new Texture("savyur.png");
		white_whip = new Texture("plet.png");
		ashen_swish = new Texture("sech.png");
		plague = new Texture("plague.png");
		panacea = new Texture("panacea.png");
		shmowder = new Texture("shmowder.png");
		bg = new Texture("bg.jpg");

		taketwyre = Gdx.audio.newSound(Gdx.files.internal("taketwyre.mp3"));
		infected = Gdx.audio.newSound(Gdx.files.internal("infected.mp3"));
		cured = Gdx.audio.newSound(Gdx.files.internal("cured.mp3"));
		createtincture = Gdx.audio.newSound(Gdx.files.internal("createtincture.mp3"));

		bgmusic = Gdx.audio.newMusic(Gdx.files.internal("shaman.mp3"));
		bgmusic.setLooping(true);
		bgmusic.setVolume(0.1F);
		bgmusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1850, 950);

		haruspex = new Rectangle();
		haruspex.x = 1850/2 - 145/2;
		haruspex.y = 0;
		haruspex.width=145;
		haruspex.height=180;

		fallings = new Array<>();
		spawnSmth();
	}

	@Override
	public void render () {
		camera.update();
		ScreenUtils.clear(1, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(bg, 0, 0);
		batch.draw(artemiy, haruspex.x, haruspex.y);
		for (Drop drop : fallings){
			String type = drop.getType();
			if (type.equals("twyrine")){
			batch.draw(tvirin, drop.x, drop.y);
			}
			if (type.equals("plague")){
				batch.draw(plague, drop.x, drop.y);
			}
			if (type.equals("shmowder")){
				batch.draw(shmowder, drop.x, drop.y);
			}
			if (type.equals("panacea")){
				batch.draw(panacea, drop.x, drop.y);
			}
			if (type.equals("swevery")){
				batch.draw(swevery, drop.x, drop.y);
			}
			if (type.equals("white_whip")){
				batch.draw(white_whip, drop.x, drop.y);
			}
			if (type.equals("ashen_swish")){
				batch.draw(ashen_swish, drop.x, drop.y);
			}
		}
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) haruspex.x -= 400 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) haruspex.x += 400 * Gdx.graphics.getDeltaTime();

		if(haruspex.x < 0) haruspex.x = 0;
		if(haruspex.x > 1850 - 145) haruspex.x = 1850 - 145;

		if(TimeUtils.nanoTime() - lastDropTime > 2100000000) spawnSmth();

		Iterator<Drop> iter = fallings.iterator();
		while (iter.hasNext()) {
			Drop drop = iter.next();
			String type = drop.getType();
			if (type.equals("plague")){
				drop.y -= (250 + MathUtils.random(1, 150)) * Gdx.graphics.getDeltaTime();
			} else drop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(drop.y + 200 < 0) iter.remove();
			if(drop.overlaps(haruspex)){
				switch (type) {
					case "twyrine":
						tincturesmade++;
						createtincture.play();
						break;
					case "plague":
						infected.play();
						if (immunity > 0) {
							if (immunity <= 20) {
								immunity = 0;
							} else immunity -= 20;
						} else infection += 20;
						break;
					case "shmowder":
						cured.play();
						infection = 0;
						break;
					case "panacea":
						cured.play(10);
						infection = 0;
						immunity += 50;
						if (immunity > 100) {
							immunity = 100;
						}
						break;
					case "swevery":
						taketwyre.play();
						swevery_count++;
						break;
					case "white_whip":
						taketwyre.play();
						white_whip_count++;
						break;
					case "ashen_swish":
						taketwyre.play();
						ashen_swish_count++;
						break;
				}
				if (ashen_swish_count >= 3 && white_whip_count >= 3 && swevery_count >= 3){
					createtincture.play();
					tincturesmade++;
					ashen_swish_count -= 3;
					white_whip_count -= 3;
					swevery_count -= 3;
				}
				iter.remove();
			}
		}
		if (infection >= 100){ //gameover
			dispose();
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		tvirin.dispose();
	}

	@Override
	public void resize(int width, int height){
		super.resize(width, height);
	}

	@Override
	public void pause(){
		super.pause();
	}

	private void spawnSmth(){
		Drop smth = new Drop();
		int i = MathUtils.random(1, 100);
		if (i > 97){
			smth.setType("panacea");
		} else if (i > 95) {
			smth.setType("shmowder");
		} else if (i > 30) {
			smth.setType("plague");
		} else if (i > 20) {
			smth.setType("swevery");
		} else if (i > 10) {
			smth.setType("white_whip");
		} else if (i > 1) {
			smth.setType("ashen_swish");
		} else {
			smth.setType("twyrine");
		}
		smth.x = MathUtils.random(0, 1650);
		smth.y = 950 - 200;
		smth.width = 200;
		smth.height = 200;
		fallings.add(smth);
		lastDropTime = TimeUtils.nanoTime();
	}
}
