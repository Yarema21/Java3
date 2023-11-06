import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake {

    private int scoreP2;
    private int score;
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    private static final int GRID_SIZE = 20;
    public Direction direction = Direction.RIGHT;
    public List<Position> snake = new ArrayList<>();
    private Position food;
    public Direction directionPlayer2 = Direction.LEFT;
    public List<Position> snakeP2 = new ArrayList<>();

    public Direction getDirection() {
        return direction;
    }
    public Direction getDirectionPlayer2() {
        return directionPlayer2;
    }
    public List<Position> getSnake() {
        return snake;
    }
    public List<Position> getSnakeP2() {
        return snakeP2;
    }
    public int getScore() {
        return score;
    }
    public void  setScore(int score) {
        this.score = score;
    }
    public int getScoreP2() {
        return scoreP2;
    }
    public void setScoreP2(int scoreP2) {
        this.scoreP2 = scoreP2;
    }
    public Position getFood() {
        return food;
    }

    public void spawnFood() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(GRID_SIZE);
            y = random.nextInt(GRID_SIZE);
        } while (isSnakeCollision(x, y));

        food = new Position(x, y);
    }
    private boolean isSnakeCollision(int x, int y) {
        for (Position pos : snake) {
            if (pos.x == x && pos.y == y) {
                return true;
            }
        }
        return false;
    }

    public void moveSnake() {
        Position head = snake.get(0);
        Position newHead;

        switch (direction) {
            case UP:
                newHead = new Position(head.x, (head.y - 1 + GRID_SIZE) % GRID_SIZE);
                break;
            case DOWN:
                newHead = new Position(head.x, (head.y + 1) % GRID_SIZE);
                break;
            case LEFT:
                newHead = new Position((head.x - 1 + GRID_SIZE) % GRID_SIZE, head.y);
                break;
            case RIGHT:
                newHead = new Position((head.x + 1) % GRID_SIZE, head.y);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }

        snake.add(0, newHead);

        if (newHead.x == food.x && newHead.y == food.y) {
            // Snake ate the food, spawn a new food
            score++;
            spawnFood();
        } else {
            // Remove the tail of the snake
            snake.remove(snake.size() - 1);
        }
    }
    public void moveSnakePlayer2() {
        Position head = snakeP2.get(0);
        Position newHead;

        switch (directionPlayer2) {
            case UP:
                newHead = new Position(head.x, (head.y - 1 + GRID_SIZE) % GRID_SIZE);
                break;
            case DOWN:
                newHead = new Position(head.x, (head.y + 1) % GRID_SIZE);
                break;
            case LEFT:
                newHead = new Position((head.x - 1 + GRID_SIZE) % GRID_SIZE, head.y);
                break;
            case RIGHT:
                newHead = new Position((head.x + 1) % GRID_SIZE, head.y);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + directionPlayer2);
        }

        snakeP2.add(0, newHead);

        if (newHead.x == food.x && newHead.y == food.y) {
            // Snake ate the food, spawn a new food
            scoreP2++;
            spawnFood();
        } else {
            // Remove the tail of the snake
            snakeP2.remove(snakeP2.size() - 1);
        }
    }

}