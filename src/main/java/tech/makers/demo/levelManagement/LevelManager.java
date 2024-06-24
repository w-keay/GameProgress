package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.EscapeRoomGame;
import tech.makers.demo.levelManagement.levels.Level1;

public class LevelManager {
    private GraphicsContext gc;
    private EscapeRoomGame game;
    private Level currentLevel;
    private int currentLevelNumber;

    public LevelManager(GraphicsContext gc, EscapeRoomGame game) {
        this.gc = gc;
        this.game = game;
        this.currentLevelNumber = 1;
        loadLevel(currentLevelNumber);
    }

    public void loadLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                currentLevel = new Level1();
                break;
            case 2:
                break;
            // Add more cases for additional levels
        }
        currentLevelNumber = levelNumber;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void render() {
        currentLevel.render(gc);
    }

    public void update() {
        currentLevel.update();
    }

    public void loadNextLevel() {
        loadLevel(currentLevelNumber + 1);
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public void completeLevel() {
    }


    public void getHelperCharacter() {
        ;
    }
}
