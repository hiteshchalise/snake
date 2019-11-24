package com.hites.snake.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.hites.snake.Direction;
import com.hites.snake.Snake;
import com.hites.snake.SnakeGame;

import java.util.LinkedList;
import java.util.Random;

public class PlayState extends State {

    private final Texture snakeBodyImg;
    private final Snake snake;
    private final Texture appleImg;
    private final Texture backgroundImg;
    private final Texture snakeHeadImgRight;
    private final Texture snakeHeadImgDown;
    private final Texture snakeHeadImgLeft;
    private final Texture snakeHeadImgUp;
    private Direction direction;
    private Rectangle apple_rect;
    private float totalDeltaTime;
    private int score;

    PlayState(GameStateManager gsm) {
        super(gsm);
        backgroundImg = new Texture("snake_background.png");
        snakeHeadImgRight = new Texture("snake_head_right.png");
        snakeHeadImgDown = new Texture("snake_head_down.png");
        snakeHeadImgLeft = new Texture("snake_head_left.png");
        snakeHeadImgUp = new Texture("snake_head_up.png");
        snakeBodyImg = new Texture("snake_body.png");
        appleImg = new Texture("apple.png");

        Rectangle snake_head = new Rectangle(SnakeGame.WIDTH/2, SnakeGame.HEIGHT/2, 32, 32);
        snake = new Snake(snake_head, new LinkedList<Rectangle>());
        snake.getSnakeBody().add(new Rectangle(snake_head.x - snake_head.width / 2, snake_head.y, 20, 20));

        drawNewApple();

        direction = Direction.NONE;

        Gdx.gl.glClearColor(1, 0, 0, 1);
    }

    private void drawNewApple() {
        apple_rect = new Rectangle();
        apple_rect.width = 32;
        apple_rect.height = 32;
        apple_rect.x = new Random().nextInt(SnakeGame.WIDTH - 32);
        apple_rect.y = new Random().nextInt(SnakeGame.HEIGHT - 32);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && direction !=Direction.RIGHT)
            direction =Direction.LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && direction !=Direction.DOWN)
            direction =Direction.UP;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && direction !=Direction.LEFT)
            direction =Direction.RIGHT;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && direction !=Direction.UP)
            direction =Direction.DOWN;
    }

    @Override
    public void update(float dt) {
        handleInput();
        if (checkIfAppleEaten()) {
            addScore();
            drawNewApple();
            addBody();
        }
        if (direction == Direction.LEFT && snake.getSnakeHead().x > 0)
            snakeMove(-1, 0, dt);
        if (direction == Direction.UP && snake.getSnakeHead().y < SnakeGame.HEIGHT - snake.getSnakeHead().height)
            snakeMove(0, 1, dt);
        if (direction == Direction.RIGHT && snake.getSnakeHead().x < SnakeGame.WIDTH - snake.getSnakeHead().width)
            snakeMove(1, 0, dt);
        if (direction == Direction.DOWN && snake.getSnakeHead().y > 0)
            snakeMove(0, -1, dt);
    }

    private void addBody() {
        snake.getSnakeBody().add(new Rectangle(snake.getSnakeHead().x, snake.getSnakeHead().y, 20, 20));
    }

    private void addScore() {
        score += 1;
        Gdx.app.log("SnakeGameS", "Score: " + score);
    }

    private boolean checkIfAppleEaten() {
        return snake.getSnakeHead().overlaps(apple_rect);
    }

    private void snakeMove(int x, int y, float dt) {
        totalDeltaTime += dt;
        if(totalDeltaTime <0.1f) return;
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
    public void render(SpriteBatch sb) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(backgroundImg, 0, 0);
        for (Rectangle snake_body : snake.getSnakeBody()) {
            sb.draw(snakeBodyImg, snake_body.x, snake_body.y);
        }
        switch (direction){
            case UP:
                sb.draw(snakeHeadImgUp, snake.getSnakeHead().x, snake.getSnakeHead().y);
                break;
            case DOWN:
                sb.draw(snakeHeadImgDown, snake.getSnakeHead().x, snake.getSnakeHead().y);
                break;
            case RIGHT:
                sb.draw(snakeHeadImgRight, snake.getSnakeHead().x, snake.getSnakeHead().y);
                break;
            case LEFT:
                sb.draw(snakeHeadImgLeft, snake.getSnakeHead().x, snake.getSnakeHead().y);
                break;
        }
        sb.draw(appleImg, apple_rect.x, apple_rect.y);
        sb.end();
    }

    @Override
    public void dispose() {
        snakeHeadImgRight.dispose();
        snakeHeadImgUp.dispose();
        snakeHeadImgDown.dispose();
        snakeHeadImgLeft.dispose();
        backgroundImg.dispose();
        snakeBodyImg.dispose();
    }
}
