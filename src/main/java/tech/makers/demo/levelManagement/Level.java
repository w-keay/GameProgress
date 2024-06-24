package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.assets.Door;
import tech.makers.demo.player.Player;

import java.util.List;

public class Level {
    private Player player;
    private List<Puzzle> puzzles;
    private Door door;
    private List<Interaction> interactions;
    private boolean completed;

    public Level(Player player, List<Puzzle> puzzles, Door door, List<Interaction> interactions) {
        this.player = player;
        this.puzzles = puzzles;
        this.door = door;
        this.interactions = interactions;
        this.completed = false;
    }

    public void render(GraphicsContext gc) {
        player.render(gc);
        for (Puzzle puzzle : puzzles) {
            puzzle.render(gc);
        }
        for (Interaction interaction : interactions) {
            interaction.render(gc);
        }
        door.render(gc);
    }

    public void update() {
        for (Puzzle puzzle : puzzles) {
            puzzle.checkPlayerInRange(player);
        }
        for (Interaction interaction : interactions) {
            interaction.checkPlayerInRange(player);
        }
        door.checkPlayerInRange(player);

        boolean allPuzzlesSolved = true;
        for (Puzzle puzzle : puzzles) {
            if (!puzzle.isSolved()) {
                allPuzzlesSolved = false;
                door.lock();
                break;
            }
        }
        if (allPuzzlesSolved) {
            door.unlock();
        }
    }

    public void handleInteraction() {
        for (Puzzle puzzle : puzzles) {
            if (puzzle.intersects(player.getX(), player.getY())) {
                puzzle.interact();
            }
        }
        for (Interaction interaction : interactions) {
            if (interaction.isInRange()) {
                interaction.interact();
            }
        }
    }

    public boolean isCompleted() {
        boolean allPuzzlesSolved = true;
        for (Puzzle puzzle : puzzles) {
            if (!puzzle.isSolved()) {
                allPuzzlesSolved = false;
                break;
            }
        }
        return allPuzzlesSolved && door.isOpen();
    }

    public Player getPlayer() {
        return player;
    }

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public Door getDoor() {
        return door;
    }

    public void completeLevel() {
        this.completed = true;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }
}
