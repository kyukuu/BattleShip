package com.example.stickhero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class SceneControllerOpening {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button playButton;
    @FXML
    private ImageView backgroundImageView;

    public ImageView getBackgroundImageView() {
        return backgroundImageView;
    }

    public void setBackgroundImageView(ImageView backgroundImageView) {
        this.backgroundImageView = backgroundImageView;
    }

    public Image randomBackground() {
        Random random = new Random();

        // Generating a random number between 1 and 3 (inclusive)
        int randomNumber = random.nextInt(3) + 1;

        // Step 2: Construct the path string based on the random number

        // Step 3: Load the randomly selected Image
        Image image = new Image(getClass().getResource("background" + randomNumber + ".jpg").toExternalForm());
        return image;
    }

    public void initializeMyScene() {
        Player player = Player.getInstance(null, null, null, null, null);

        playButton.setOnAction(event -> {
            try {
                GameMusicPlayer musicPlayer = new GameMusicPlayer("Music.mp3");
                musicPlayer.play();
                switchToPlaying(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        playButton.setOnAction(event -> {
            try {
                loadGame(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        playButton.setOnAction(event -> {
            try {
                loadGame(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        player.setCherryCount(Player.deserializeInt("cherryCount.txt"));
        player.setHighScore(Player.deserializeInt("highscore.txt"));
    }

    public void setBackground(Image image) {
        this.backgroundImageView.setImage(image);
    }

    public void loadGame(ActionEvent event) throws IOException {
        GameMusicPlayer musicPlayer = new GameMusicPlayer("Music.mp3");
        musicPlayer.play();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Playing.fxml"));
        Parent root = loader.load();
        // Getting the controller for the 'Playing' scene
        SceneController playingController = loader.getController();
        // Now you can call a method on the playingController
        playingController.initializeMyScene(backgroundImageView.getImage());
        Player player = Player.getInstance(null, null, null, null, null);
        player.setCurrentScore(Player.deserializeInt("currentScore.txt"));
        player.setCherryCount(Player.deserializeInt("cherryCount.txt"));
        player.setHighScore(Player.deserializeInt("highscore.txt"));
        player.getScoreLabel().setText(Integer.toString(player.getCurrentScore()));
        player.getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));
        // Assuming initializeMyScene is the method you want to call
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchToPlaying(ActionEvent event) throws IOException {
        GameMusicPlayer musicPlayer = new GameMusicPlayer("Music.mp3");
        musicPlayer.play();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Playing.fxml"));
        Parent root = loader.load();
        // Getting the controller for the 'Playing' scene
        SceneController playingController = loader.getController();
        // Now you can call a method on the playingController
        Player player = Player.getInstance(null, null, null, null, null);
        playingController.initializeMyScene(backgroundImageView.getImage()); // Assuming initializeMyScene is the method you want to call
        player.setCherryCount(Player.deserializeInt("cherryCount.txt"));
        player.setHighScore(Player.deserializeInt("highscore.txt"));
        //   player.setCurrentScore(0);
        player.getScoreLabel().setText("0");
//        System.out.println(player.getCurrentScore());
        player.getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

