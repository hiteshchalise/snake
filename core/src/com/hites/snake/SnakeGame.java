package com.hites.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture snake_background_img, apple_img, snake_head_img, snake_body_img;
    private int gameWidth = 512, gameHeight = 512;
    private Rectangle apple_rect;
    private OrthographicCamera camera;
    private SnakeDirection direction;
    private int score = 0;
    private Snake snake;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 512, 512);

        snake_background_img = new Texture("snake_background.png");
        snake_head_img = new Texture("snake_head.png");
        apple_img = new Texture("apple.png");
        snake_body_img = new Texture("snake_body.png");

        Random random = new Random();

        Rectangle snake_head = new Rectangle(random.nextInt(gameWidth), random.nextInt(gameHeight), 32, 32);
        snake = new Snake(snake_head, new LinkedList<Rectangle>());
        snake.snakeBody.add(new Rectangle(snake_head.x - snake_head.width/2, snake_head.y, 20, 20));

        drawNewApple();

        direction = SnakeDirection.UP;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(snake_background_img, 0, 0);
        for (Rectangle snake_body : snake.snakeBody) {
            batch.draw(snake_body_img, snake_body.x, snake_body.y);
        }
        batch.draw(snake_head_img, snake.snakeHead.x, snake.snakeHead.y);
        batch.draw(apple_img, apple_rect.x, apple_rect.y);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && direction != SnakeDirection.RIGHT)
            direction = SnakeDirection.LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && direction != SnakeDirection.DOWN)
            direction = SnakeDirection.UP;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && direction != SnakeDirection.LEFT)
            direction = SnakeDirection.RIGHT;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && direction != SnakeDirection.UP)
            direction = SnakeDirection.DOWN;

        if (checkIfAppleEaten()) {
            addScore();
            drawNewApple();
            addBody();
        }

        if (direction == SnakeDirection.LEFT && snake.snakeHead.x > 0)
            snakeMove(-1, 0);
        if (direction == SnakeDirection.UP && snake.snakeHead.y < gameHeight - snake.snakeHead.height)
            snakeMove(0, 1);
        if (direction == SnakeDirection.RIGHT && snake.snakeHead.x < gameWidth - snake.snakeHead.width)
            snakeMove(1, 0);
        if (direction == SnakeDirection.DOWN && snake.snakeHead.y > 0)
            snakeMove(0, -1);
    }

    private void addBody() {
        snake.snakeBody.add(new Rectangle(snake.snakeHead.x, snake.snakeHead.y, 20, 20));
    }

    private void addScore() {
        score += 1;
        Gdx.app.log("SnakeGame", "Score: " + score);
    }

    private void drawNewApple() {
        apple_rect = new Rectangle();
        apple_rect.width = 32;
        apple_rect.height = 32;
        apple_rect.x = new Random().nextInt(gameWidth - 32);
        apple_rect.y = new Random().nextInt(gameHeight - 32);
    }

    private boolean checkIfAppleEaten() {
        return snake.snakeHead.overlaps(apple_rect);
    }

    private void snakeMove(int x, int y) {

        if (!snake.snakeBody.isEmpty()) {
            snake.snakeBody.remove();
            snake.snakeBody.add(new Rectangle(snake.snakeHead.x,
                    snake.snakeHead.y, 20, 20));
        }
        snake.snakeHead.x += 3 * x;
        snake.snakeHead.y += 3 * y;
    }

    @Override
    public void dispose() {
        batch.dispose();
        snake_background_img.dispose();
        snake_head_img.dispose();
    }

    private enum SnakeDirection {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
}
