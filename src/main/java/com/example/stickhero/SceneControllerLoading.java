package com.example.stickhero;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class SceneControllerLoading {

    @FXML
    private final Label loadingLabel = new Label();
    @FXML
    private ImageView backgroundImageView;

    // Your existing controller code

    public void initialize() {
        startLoadingTask();
    }

    private void startLoadingTask() {
        Thread loadingThread = new Thread(() -> {
            // Simulate loading tasks
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500); // Simulate loading delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Update the loading message on the JavaFX Application Thread
                int finalI = i;
                javafx.application.Platform.runLater(() -> {
                    loadingLabel.setText("Loading... " + (finalI + 1) + "/10"); // Update the message
                });
            }
            // After loading is complete, you can perform any additional tasks
            // or navigate to another scene
            javafx.application.Platform.runLater(() -> {
                loadingLabel.setText("Loading complete!");
                // Add logic to transition to the next scene or perform other actions
            });
        });

        // Start the loading thread
        loadingThread.start();
    }
}
