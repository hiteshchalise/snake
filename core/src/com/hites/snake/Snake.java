package com.hites.snake;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Snake {

    Rectangle snakeHead;
    LinkedList<Rectangle> snakeBody;

    public Snake(Rectangle snakeHead, LinkedList<Rectangle> snakeBody){
        this.snakeHead = snakeHead;
        this.snakeBody = snakeBody;
    }
}
