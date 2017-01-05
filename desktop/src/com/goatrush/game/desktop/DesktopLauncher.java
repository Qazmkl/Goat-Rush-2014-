package com.goatrush.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.goatrush.game.GoatRush;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Goat Rush";
		config.useGL30 = false;
		//config.useGL20 =true;
		config.width=2064;
		config.height=1024;
		new LwjglApplication(new GoatRush(), config);
		
	}
}
