package com.hites.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hites.snake.state.GameStateManager;
import com.hites.snake.state.MenuState;

public class SnakeGame  extends ApplicationAdapter {
    public static final int WIDTH = 848;
    public static final int HEIGHT = 412;
    public static final String TITLE = "SnakeGame";

    private SpriteBatch spriteBatch;
    private GameStateManager gameStateManager;

    @Override
    public void create () {
        spriteBatch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        MenuState state = new MenuState(gameStateManager);
        gameStateManager.push(state);

        Gdx.gl.glClearColor(1, 0, 0, 1);
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.render(spriteBatch);
    }

}