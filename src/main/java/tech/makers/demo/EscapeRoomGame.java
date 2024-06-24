package tech.makers.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tech.makers.demo.assets.Sound;
import tech.makers.demo.gui.HomeScreen;
import tech.makers.demo.gui.LevelCompletionScreen;
import tech.makers.demo.levelManagement.Level;
import tech.makers.demo.Tile.TileManager;
import tech.makers.demo.levelManagement.LevelManager;
import tech.makers.demo.levelManagement.Puzzle;
import tech.makers.demo.player.Player;

import java.util.List;

public class EscapeRoomGame extends Application {
    private LevelManager levelManager;
    private TileManager tileManager;
    private Image[] moneyImages;
    private Sound sound = new Sound();
    private Stage primaryStage;
    private AnimationTimer gameLoop;
    private Canvas canvas;
    private GraphicsContext gc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        HomeScreen homeScreen = new HomeScreen(primaryStage, this);
        primaryStage.setTitle("Escape Room Game");
        primaryStage.setScene(homeScreen.getScene());
        primaryStage.show();
    }

    public void startGame(Stage primaryStage) {
        canvas = new Canvas(768, 576);
        gc = canvas.getGraphicsContext2D();

        levelManager = new LevelManager(gc, this);

        // Initialize the images before loading the level
        moneyImages = new Image[]{
                new Image(getClass().getResource("/sprites/money 1.png").toString()),
                new Image(getClass().getResource("/sprites/money 2.png").toString()),
                new Image(getClass().getResource("/sprites/money 3.png").toString())
        };

        loadLevel(1); // Start with level 1

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        setSceneControls(scene);

        primaryStage.setTitle("Escape Room Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        sound.setFile(0);
        sound.setVolume(-10.0f);
        sound.play();
        sound.loop();

        startGameLoop();
    }

    private void setSceneControls(Scene scene) {
        scene.setOnKeyPressed(event -> {
            Level currentLevel = levelManager.getCurrentLevel();
            Player player = currentLevel.getPlayer();
            List<Puzzle> puzzles = currentLevel.getPuzzles();
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) player.moveUp(puzzles, currentLevel.getDoor());
            if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) player.moveDown(puzzles, currentLevel.getDoor());
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) player.moveLeft(puzzles, currentLevel.getDoor());
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) player.moveRight(puzzles, currentLevel.getDoor());
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.E) {
                currentLevel.handleInteraction();
            }
        });

        scene.setOnKeyReleased(event -> {
            Level currentLevel = levelManager.getCurrentLevel();
            Player player = currentLevel.getPlayer();

            // Reset player state to idle when movement keys are released
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W ||
                    event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S ||
                    event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A ||
                    event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                player.stopMoving();
            }
        });
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                tileManager.renderTiles(gc);
                levelManager.render();
                levelManager.update();
            }
        };
        gameLoop.start();
    }

    public void playMusic(int i) {
        System.out.println("Playing music track: " + i);
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(int i) {
        sound.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public void completeLevel() {
        gameLoop.stop(); // Stop the game loop before showing the level completion screen
        showLevelCompletionScreen();
    }

    public void showLevelCompletionScreen() {
        LevelCompletionScreen levelCompletionScreen = new LevelCompletionScreen(primaryStage, this);
        levelCompletionScreen.show();
    }

    public void startNextLevel() {
        levelManager.loadNextLevel();
        loadLevel(levelManager.getCurrentLevelNumber());
        setupNextLevel(); // Initialize the game for the next level
        gameLoop.start();
    }

    public void loadLevel(int levelNumber) {
        tileManager = new TileManager("/tiles/StoneTile.png");
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        setSceneControls(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setupNextLevel() {
        Level currentLevel = levelManager.getCurrentLevel();
        Player player = currentLevel.getPlayer();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        setSceneControls(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}