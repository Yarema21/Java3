import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

 public class SnakeGame extends Application {
     private long lastKeyPressTime;
     private static final int GAME_SPEED = 300; // milliseconds
     private static final long KEY_PRESS_INTERVAL = 125;
     SnakeBoard snakeBoard = new SnakeBoard();
     Snake snake = snakeBoard.getSnake();
     Canvas canvas = snakeBoard.getCanvas();
     @Override
     public void start(Stage primaryStage) {
         StackPane root = new StackPane();
         root.getChildren().add(canvas);

         Scene scene = new Scene(root);
         scene.setOnKeyPressed(event -> {
             long currentTime = System.currentTimeMillis();
             if (currentTime - lastKeyPressTime > KEY_PRESS_INTERVAL) {
                 lastKeyPressTime = currentTime;
                 if (event.getCode() == KeyCode.UP && snake.getDirection() != Snake.Direction.DOWN) {
                     snake.direction = Snake.Direction.UP;
                 } else if (event.getCode() == KeyCode.DOWN && snake.getDirection() != Snake.Direction.UP) {
                     snake.direction = Snake.Direction.DOWN;
                 } else if (event.getCode() == KeyCode.LEFT && snake.getDirection() != Snake.Direction.RIGHT) {
                     snake.direction = Snake.Direction.LEFT;
                 } else if (event.getCode() == KeyCode.RIGHT && snake.getDirection() != Snake.Direction.LEFT) {
                     snake.direction = Snake.Direction.RIGHT;
                 }
                 if (event.getCode() == KeyCode.W && snake.getDirectionPlayer2() != Snake.Direction.DOWN) {
                     snake.directionPlayer2 = Snake.Direction.UP;
                 } else if (event.getCode() == KeyCode.S && snake.getDirectionPlayer2() != Snake.Direction.UP) {
                     snake.directionPlayer2 = Snake.Direction.DOWN;
                 } else if (event.getCode() == KeyCode.A && snake.getDirectionPlayer2() != Snake.Direction.RIGHT) {
                     snake.directionPlayer2 = Snake.Direction.LEFT;
                 } else if (event.getCode() == KeyCode.D && snake.getDirectionPlayer2() != Snake.Direction.LEFT) {
                     snake.directionPlayer2 = Snake.Direction.RIGHT;
                 }
             }
         });
         primaryStage.setTitle("Snake Game");
         primaryStage.setScene(scene);
         primaryStage.show();

         snakeBoard.initializeGame();
         startGameLoop(canvas.getGraphicsContext2D());
     }

     private void startGameLoop(GraphicsContext gc) {
         new Thread(() -> {
             while (snakeBoard.gameActive) {
                 try {
                     Thread.sleep(GAME_SPEED);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }

                 snakeBoard.updateGame();
                 snakeBoard.drawGame(gc);
             }
         }).start();
     }

     public static void main(String[] args) {
         launch(args);
     }
 }
