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

public class SnakeGameS extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture snake_background_img, apple_img, snake_head_img, snake_body_img;
    private int gameWidth = 512, gameHeight = 512;
    private Rectangle apple_rect;
    private OrthographicCamera camera;
    private Direction direction;
    private int score = 0;
    private Snake snake;
    private float totalDeltaTime = 0.0f;

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
        snake.getSnakeBody().add(new Rectangle(snake_head.x - snake_head.width / 2, snake_head.y, 20, 20));

        drawNewApple();

        direction = Direction.UP;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(snake_background_img, 0, 0);
        for (Rectangle snake_body : snake.getSnakeBody()) {
            batch.draw(snake_body_img, snake_body.x, snake_body.y);
        }
        batch.draw(snake_head_img, snake.getSnakeHead().x, snake.getSnakeHead().y);
        batch.draw(apple_img, apple_rect.x, apple_rect.y);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && direction != Direction.RIGHT)
            direction = Direction.LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && direction != Direction.DOWN)
            direction = Direction.UP;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && direction != Direction.LEFT)
            direction = Direction.RIGHT;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && direction != Direction.UP)
            direction = Direction.DOWN;

        if (checkIfAppleEaten()) {
            addScore();
            drawNewApple();
            addBody();
        }

        if (direction == Direction.LEFT && snake.getSnakeHead().x > 0)
            snakeMove(-1, 0);
        if (direction == Direction.UP && snake.getSnakeHead().y < gameHeight - snake.getSnakeHead().height)
            snakeMove(0, 1);
        if (direction == Direction.RIGHT && snake.getSnakeHead().x < gameWidth - snake.getSnakeHead().width)
            snakeMove(1, 0);
        if (direction == Direction.DOWN && snake.getSnakeHead().y > 0)
            snakeMove(0, -1);
    }

    private void addBody() {
        snake.getSnakeBody().add(new Rectangle(snake.getSnakeHead().x, snake.getSnakeHead().y, 20, 20));
    }

    private void addScore() {
        score += 1;
        Gdx.app.log("SnakeGameS", "Score: " + score);
    }

    private void drawNewApple() {
        apple_rect = new Rectangle();
        apple_rect.width = 32;
        apple_rect.height = 32;
        apple_rect.x = new Random().nextInt(gameWidth - 32);
        apple_rect.y = new Random().nextInt(gameHeight - 32);
    }

    private boolean checkIfAppleEaten() {
        return snake.getSnakeHead().overlaps(apple_rect);
    }

    private void snakeMove(int x, int y) {
        totalDeltaTime += Gdx.graphics.getDeltaTime();

        if(totalDeltaTime<0.1f) return;
        totalDeltaTime = 0.0f;

        snake.getSnakeHead().x += 20 * x;
        snake.getSnakeHead().y += 20 * y;

        if (!snake.getSnakeBody().isEmpty()) {
            snake.getSnakeBody().remove();
            snake.getSnakeBody().add(new Rectangle(snake.getSnakeHead().x - 20 * x ,
                    snake.getSnakeHead().y - 20 * y, 20, 20));
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        snake_background_img.dispose();
        snake_head_img.dispose();
    }
}
