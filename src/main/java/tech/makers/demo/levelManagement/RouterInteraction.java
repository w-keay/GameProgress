package tech.makers.demo.levelManagement;

import javafx.scene.control.ChoiceDialog;
import tech.makers.demo.player.Player;

import java.util.Arrays;
import java.util.Optional;

public class RouterInteraction extends Interaction {
    private ComputerInteraction computer;

    public RouterInteraction(double x, double y, String imagePath, ComputerInteraction computer) {
        super(x, y, imagePath);
        this.computer = computer;
    }

    @Override
    public void interact() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("on", Arrays.asList("on", "off"));
        dialog.setTitle("Router Control");
        dialog.setHeaderText("Would you like to turn the router on or off?");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(choice -> {
            if (choice.equals("on")) {
                computer.setRouterOn(true);
                System.out.println("The router is now on. The computer is also on.");
            } else {
                computer.setRouterOn(false);
                System.out.println("The router is now off. The computer is also off.");
            }
        });
    }
}
