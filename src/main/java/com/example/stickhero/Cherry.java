package com.example.stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Cherry extends Fruit {
    public static final String CHERRY_IMAGE_PATH = "cherry.png"; // Replace with actual image path
    public static final int CHERRY_SIZE = 20;
    public static final int Y_OFFSET_FROM_BOTTOM = 110;
    private boolean isReaped = false;
    private final AnchorPane anchorPane;

    // Constructor
    public Cherry(AnchorPane anchorPane) {

        super(new Image(Cherry.class.getResourceAsStream(CHERRY_IMAGE_PATH)));
        this.setFitWidth(CHERRY_SIZE);
        this.setFitHeight(CHERRY_SIZE);
        this.anchorPane = anchorPane;
    }

    public static Cherry spawnCherry(double minX, Rectangle platform, AnchorPane anchorPane) {
        Random random = new Random();
        Cherry cherry = null;
        double maxX = platform.getX() - 20;
        minX = minX + 1000;


        // Decide randomly whether to spawn a cherry
        // random.nextBoolean()

        if (maxX - minX > 30) {
            if (true) {

                cherry = new Cherry(anchorPane);

                // Generate a random x position within the provided bounds
                double xPos = minX + (maxX - minX) * random.nextDouble();
                // Set the position of the cherry
                cherry.setLayoutX(xPos);
                double yPos = anchorPane.getHeight() - Y_OFFSET_FROM_BOTTOM - CHERRY_SIZE;
                cherry.setLayoutY(yPos);

            }
        }


        return cherry;
    }

    public boolean isReaped() {
        return isReaped;
    }

    public void setReaped(boolean reaped) {
        isReaped = reaped;
    }

    // Method to possibly spawn a cherry at a random x position and fixed y position
    public void moveOut() {
        // Create a TranslateTransition for the cherry
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), this);
        transition.setByX(-1000); // Move leftwards by 1000 units on the x axis

        // Optional: You can add an onFinished event handler if you want to do something after the animation
        // transition.setOnFinished(event -> {
        //     // Actions to perform after moving out
        // });

        // Start the transition
        transition.play();
    }

    public void moveCherry() {
        // Create a TranslateTransition for the cherry
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), this);
        transition.setByX(-1000); // Move leftwards by 1000 units on the x axis
        transition.play();
    }
}