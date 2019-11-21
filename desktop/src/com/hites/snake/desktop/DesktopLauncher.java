package com.hites.snake.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hites.snake.SnakeGame;
import com.hites.snake.SnakeGameS;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = SnakeGame.TITLE;
		config.width = SnakeGame.WIDTH;
		config.height = SnakeGame.HEIGHT;
		new LwjglApplication(new SnakeGame(), config);
	}
}
