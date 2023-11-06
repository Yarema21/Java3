import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class SnakeBoard {
    Canvas canvas = new Canvas(GRID_SIZE * TILE_SIZE, GRID_SIZE * TILE_SIZE);

    private static final int GRID_SIZE = 20;
    private static final int TILE_SIZE = 30;

    public boolean gameActive = true;

    private Snake snake = new Snake();

    public Snake getSnake() {
        return snake;
    }
    public Canvas getCanvas() {
        return canvas;
    }

    private void drawScore(GraphicsContext gc, int colorCode) {
        if (colorCode == 1) {
            String text = "Score: " + snake.getScore();
            gc.setFill(Color.GREEN);
            gc.fillText(text, 10, 20);
        } else if (colorCode == 2) {
            String text = "Score: " + snake.getScoreP2();
            double textWidth = gc.getFont().getSize() * text.length();
            gc.setFill(Color.BLUE);
            double xCoordinate = gc.getCanvas().getWidth() - textWidth - 10;
            gc.fillText(text, xCoordinate, 20);
        }
    }

    private void drawPlayer(GraphicsContext gc) {
        // Draw the snake
        gc.setFill(Color.GREEN);
        List<Position> snakeList1 = snake.getSnake();
        for (Position pos : snakeList1) {
            gc.fillRect(pos.x * TILE_SIZE, pos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    private void drawPlayer2(GraphicsContext gc) {
        // Draw the snake
        gc.setFill(Color.BLUE);
        List<Position> snakeList2 = snake.getSnakeP2();
        for (Position pos : snakeList2) {
            gc.fillRect(pos.x * TILE_SIZE, pos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    public void drawGame(GraphicsContext gc) {
        // Clear the canvas
        gc.clearRect(0, 0, GRID_SIZE * TILE_SIZE, GRID_SIZE * TILE_SIZE);

        // Draw the food
        gc.setFill(Color.RED);
        gc.fillRect(snake.getFood().x * TILE_SIZE, snake.getFood().y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        drawPlayer(gc);
        drawPlayer2(gc);
        drawScore(gc, 1);
        drawScore(gc, 2);

    }

    public void updateGame() {
        snake.moveSnake();
        snake.moveSnakePlayer2();
        checkSnakeCollision();
    }

    public void initializeGame() {
        snake.setScore(0);
        snake.setScoreP2(0);
        snake.getSnake().clear();
        snake.getSnakeP2().clear();
        snake.snake.add(new Position(GRID_SIZE / 2, GRID_SIZE / 2));
        snake.snake.add(new Position(GRID_SIZE / 2 - 1, GRID_SIZE / 2)); // snake size starts with 2 squares
        snake.snakeP2.add(new Position(GRID_SIZE / 2, GRID_SIZE / 2 + 2));
        snake.snakeP2.add(new Position(GRID_SIZE / 2 - 1, GRID_SIZE / 2 + 2));
        snake.spawnFood();
    }

    private void checkSnakeCollision() {
        Position head1 = snake.getSnake().get(0);
        Position head2 = snake.getSnakeP2().get(0);

        // Check if head1 collided with either snake
        for (int i = 0; i < snake.getSnakeP2().size(); i++) {
            if (head1.x == snake.getSnakeP2().get(i).x && head1.y == snake.getSnakeP2().get(i).y) {
                gameActive = false;
                drawGameOver();
                return;
            }
        }


        // Check if head2 collided with either snake
        for (int i = 0; i < snake.getSnake().size(); i++) {
            if (head2.x == snake.getSnake().get(i).x && head2.y == snake.getSnake().get(i).y) {
                gameActive = false;
                drawGameOver();
                return;
            }
        }
    }

    private void drawGameOver() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, GRID_SIZE * TILE_SIZE, GRID_SIZE * TILE_SIZE);

        gc.setFill(Color.WHITE);

        Font font = new Font("Arial", 24);
        gc.setFont(font);

        String gameOverText;
        if (snake.getScore() > snake.getScoreP2()) {
            gameOverText = "P1 WINS --- P1 Score: " + snake.getScore() + " --- P2 Score: " + snake.getScoreP2();
        } else if (snake.getScoreP2() > snake.getScore()) {
            gameOverText = "P2 WINS --- P1 Score: " + snake.getScore() + " --- P2 Score: " + snake.getScoreP2();
        } else {
            gameOverText = "TIE --- P1 Score" + snake.getScore() + " --- P2 Score: " + snake.getScoreP2();
        }

        // Calculated Size of Text
        Text text = new Text(gameOverText);
        text.setFont(font);
        double textWidth = text.getBoundsInLocal().getWidth();
        double textHeight = text.getBoundsInLocal().getHeight();

        double x = (GRID_SIZE * TILE_SIZE - textWidth) / 2;
        double y = (GRID_SIZE * TILE_SIZE - textHeight) / 2;

        gc.fillText(gameOverText, x, y);

        // Waiting before every game
        try {
            Thread.sleep(3000); // 3000 miliseconds or 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameActive = true;
        snake.direction = Snake.Direction.RIGHT;
        snake.directionPlayer2 = Snake.Direction.LEFT;
        initializeGame();
    }
}