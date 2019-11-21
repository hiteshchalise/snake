package com.hites.snake.state;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends State {

    private final Texture background;
    private final Texture snakeHead;
    private final Texture snakeBody;
    private final Texture snakeBodyTurned;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("snake_background.png");
        snakeHead = new Texture("yellow_snake_head.png");
        snakeBody = new Texture("yellow_snake_body.png");
        snakeBodyTurned = new Texture("yellow_snake_body_turned.png");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(snakeHead, 200, 200);
        sb.draw(snakeBody, 168, 200);
        sb.draw(snakeBodyTurned, 136, 200);
        sb.draw(snakeBody, 136, 232);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        snakeHead.dispose();
        snakeBody.dispose();
    }
}
