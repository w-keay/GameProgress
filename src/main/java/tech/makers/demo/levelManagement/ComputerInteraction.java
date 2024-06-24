package tech.makers.demo.levelManagement;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import tech.makers.demo.assets.Door;

import java.util.Optional;

public class ComputerInteraction extends Interaction {
    private boolean isRouterOn;
    private boolean hasPassword;
    private final String wifiPassword = "escape123";
    private Door door;

    public ComputerInteraction(double x, double y, String imagePath, Door door) {
        super(x, y, imagePath);
        this.door = door;
        this.isRouterOn = false;
        this.hasPassword = false;
    }

    public void setRouterOn(boolean routerOn) {
        isRouterOn = routerOn;
    }

    public boolean isRouterOn() {
        return isRouterOn;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    @Override
    public void interact() {
        if (!isRouterOn) {
            showAlert("The computer is off. Turn on the router to power it up.");
        } else if (!hasPassword) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Wi-Fi Password");
            dialog.setHeaderText("Enter the Wi-Fi password:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(password -> {
                if (password.equals(wifiPassword)) {
                    hasPassword = true;
                    door.unlock();
                    showAlert("Password correct! The door is now unlocked.");
                } else {
                    showAlert("Incorrect password. Try again.");
                }
            });
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
