package tech.makers.demo.assets;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tech.makers.demo.player.Player;

import javax.swing.JOptionPane;

public class Router {
    private boolean isOn;
    private Image routerImage;
    private double x, y;

    public Router(String imagePath, double x, double y) {
        this.routerImage = new Image(getClass().getResource(imagePath).toString());
        this.x = x;
        this.y = y;
        this.isOn = false;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(routerImage, x, y, 48, 48);
    }

    public void interact() {
        String message = isOn ? "Would you like to turn the router off?" : "Would you like to turn the router on?";
        int response = JOptionPane.showConfirmDialog(null, message, "Router", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            isOn = !isOn;
            JOptionPane.showMessageDialog(null, isOn ? "Router is now on." : "Router is now off.");
        }
    }

    public boolean isOn() {
        return isOn;
    }

    public boolean isInRange(double playerX, double playerY) {
        double distance = Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2));
        return distance <= 50;
    }

    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2)); // Calculate the distance between player and puzzle
        boolean inRange = distance < 70; // Set the inRange flag if the distance is less than 70
    }
}
