package com.example.stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Game extends Application {
    public Image randomBackground(){
        Random random = new Random();

        // Generating a random number between 1 and 3 (inclusive)
        int randomNumber = random.nextInt(3) + 1;

        // Step 2: Construct the path string based on the random number

        // Step 3: Load the randomly selected Image
        Image image =  new Image(getClass().getResource("background" + randomNumber + ".jpg").toExternalForm());
        return image;
    }

    private void showLoadingScreen(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Game.class.getResource("Loading.fxml"));
        Scene loadingScene = new Scene(loader.load());
        SceneControllerLoading loadingController = loader.getController();
        // Start the loading animation
        loadingController.initialize();
        stage.setScene(loadingScene);
        stage.setTitle("Loading Screen");
        stage.show();


        // Simulate loading tasks (replace this with your actual loading logic)
        Thread loadingThread = new Thread(() -> {
            try {
                Thread.sleep(3000); // Simulate a 3-second loading time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // After loading, transition to the main game screen
            javafx.application.Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("Opening Screen.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    SceneControllerOpening openingController = fxmlLoader.getController();
                    openingController.setBackground(randomBackground());
                    scene.getRoot().setFocusTraversable(true);
                    String css = Objects.requireNonNull(this.getClass().getResource("stickHero.css")).toExternalForm();
                    scene.getStylesheets().add(css);
                    stage.setTitle("Stick Hero Game");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        // Start the loading thread
        loadingThread.start();
    }
    @Override
    public void start(Stage stage) throws IOException {
        showLoadingScreen(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}