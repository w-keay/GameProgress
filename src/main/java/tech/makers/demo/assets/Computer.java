package tech.makers.demo.assets;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tech.makers.demo.player.Player;

import javax.swing.JOptionPane;

public class Computer {
    private boolean inRange;
    private boolean isOn;
    private boolean correctPasswordEntered = false;
    private Image computerImage;
    private double x, y;

    public Computer(String imagePath, double x, double y) {
        this.computerImage = new Image(getClass().getResource(imagePath).toString());
        this.x = x;
        this.y = y;
        this.isOn = false;
        this.inRange = false; // Initialize inRange flag as false

    }

    public void render(GraphicsContext gc) {
        gc.drawImage(computerImage, x, y, 48, 48);
    }

    public void interact(boolean routerOn, String wifiPassword) {
        if (!routerOn) {
            JOptionPane.showMessageDialog(null, "The computer is off. Please turn on the router first.");
        } else if (!isOn) {
            isOn = true;
            JOptionPane.showMessageDialog(null, "The computer is now on. Please go to Eddie for the WiFi password.");
        } else {
            String password = JOptionPane.showInputDialog("The computer is on. Please enter the WiFi password:");
            if (password != null && password.equals(wifiPassword)) {
                correctPasswordEntered = true;
                JOptionPane.showMessageDialog(null, "Correct password! The door is now unlocked.");
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.");
            }
        }
    }

    public boolean isOn() {
        return isOn;
    }

    public boolean hasCorrectPassword() {
        return correctPasswordEntered;
    }

    public boolean isInRange(double playerX, double playerY) {
        double distance = Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2));
        return distance <= 50;
    }

    // Method to check if the player is within range of the puzzle
    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2)); // Calculate the distance between player and puzzle
        inRange = distance < 70; // Set the inRange flag if the distance is less than 70
    }
}
