package com.gorkhon.mygame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gorkhon.mygame.OnGorkhonGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "On Gorkhon";
		config.width = 1850;
		config.height = 950;
		new LwjglApplication(new OnGorkhonGame(), config);
	}
}
