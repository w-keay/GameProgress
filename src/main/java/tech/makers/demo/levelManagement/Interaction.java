package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tech.makers.demo.player.Player;

public abstract class Interaction {
    protected double x;
    protected double y;
    protected Image image;
    protected boolean inRange;

    public Interaction(double x, double y, String imagePath) {
        this.x = x;
        this.y = y;
        this.image = new Image(getClass().getResource(imagePath).toExternalForm());
        this.inRange = false;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, 48, 48); // Adjust size as needed
    }

    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
        inRange = distance < 70;
    }

    public abstract void interact();

    public boolean isInRange() {
        return inRange;
    }

    // Add these getter methods
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
