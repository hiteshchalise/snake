package com.hites.snake.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.hites.snake.SnakeGame;

public class MenuState extends State{
    private final Rectangle playRect;
    Texture background;
    Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("menu_bg.jpg");
        playBtn = new Texture("play_game.png");
        playRect = new Rectangle((SnakeGame.WIDTH / 2) - (playBtn.getWidth() / 2), SnakeGame.HEIGHT/2 , 136, 54);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touch);
            if(playRect.contains(touch.x, touch.y))
                gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(background, 0, 0, SnakeGame.WIDTH, SnakeGame.HEIGHT);
        sb.draw(playBtn, (SnakeGame.WIDTH / 2) - (playBtn.getWidth() / 2), SnakeGame.HEIGHT / 2);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}