package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;


public class EddieInteraction extends Interaction {
    private ComputerInteraction computerInteraction;
    private final String defaultMessage = "Hi there! You need to turn on the router to start your PC!.";
    private Image idleSpriteSheet;
    private Image[] idleRightFrames;
    private Image[] idleUpFrames;
    private Image[] idleLeftFrames;
    private Image[] idleDownFrames;
    private String direction = "down";
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final int FRAME_DURATION = 150; // Frame duration in milliseconds

    public EddieInteraction(double x, double y, String imagePath, ComputerInteraction computerInteraction) {
        super(x, y, imagePath);
        this.computerInteraction = computerInteraction;

        // Load frames for the idle animation
        this.idleSpriteSheet = new Image(getClass().getResource(imagePath).toExternalForm());
        this.idleRightFrames = loadFrames(idleSpriteSheet, 0);
        this.idleUpFrames = loadFrames(idleSpriteSheet, 6);
        this.idleLeftFrames = loadFrames(idleSpriteSheet, 12);
        this.idleDownFrames = loadFrames(idleSpriteSheet, 18);
    }

    private Image[] loadFrames(Image spriteSheet, int startFrame) {
        Image[] frames = new Image[6];
        int spriteWidth = 16;
        int spriteHeight = 32;
        for (int i = 0; i < 6; i++) {
            int x = (startFrame + i) * spriteWidth;
            int y = 0; // Since the image is a single row
            frames[i] = new WritableImage(spriteSheet.getPixelReader(), x, y, spriteWidth, spriteHeight);
        }
        return frames;
    }

    @Override
    public void render(GraphicsContext gc) {
        Image[] currentFrames;
        switch (direction) {
            case "up":
                currentFrames = idleUpFrames;
                break;
            case "down":
                currentFrames = idleDownFrames;
                break;
            case "left":
                currentFrames = idleLeftFrames;
                break;
            case "right":
                currentFrames = idleRightFrames;
                break;
            default:
                currentFrames = idleDownFrames;
        }
        Image currentImage = currentFrames[currentFrame];
        gc.drawImage(currentImage, getX(), getY(), 25, 50);
    }


    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= FRAME_DURATION) {
            currentFrame = (currentFrame + 1) % 6;
            lastFrameTime = currentTime;
        }
    }

    @Override
    public void interact() {
        if (computerInteraction.isRouterOn()) {
            showAlert("Eddie: Here's the Wi-Fi password you need: " + computerInteraction.getWifiPassword());
        } else {
            showAlert(defaultMessage);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tip");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
