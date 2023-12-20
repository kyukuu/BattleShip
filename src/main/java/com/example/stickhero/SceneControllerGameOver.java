package com.example.stickhero;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class SceneControllerGameOver {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label scoreLabelFinal;
    @FXML
    private Label highscoreLabel;
    @FXML
    private Label cherryCountFinalLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private Label cherriesTooFew;
    public void updateGame(String score, String cherryCount, String highscore, Image image) throws IOException {
        imageView.setImage(image);
        scoreLabelFinal.setText(score);
        cherryCountFinalLabel.setText(cherryCount);
        highscoreLabel.setText(highscore);

        Player player = Player.getInstance(null , null, null, null, null);
        Player.serializeInt(player.getCherryCount(), "cherryCount.txt");
        player.setCherryCount(Integer.parseInt(cherryCount));
    }


    public Image randomBackground(){
        Random random = new Random();

        // Generating a random number between 1 and 3 (inclusive)
        int randomNumber = random.nextInt(3) + 1;

        // Step 2: Construct the path string based on the random number

        // Step 3: Load the randomly selected Image
        Image image =  new Image(getClass().getResource("background" + randomNumber + ".jpg").toExternalForm());
        return image;
    }



    public void switchToStart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Opening Screen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void animateLabelBonus(Label label, double durationInSeconds) {
        // Set the initial opacity to 0
        label.setOpacity(0);

        double originalYPosition = label.getLayoutY();

        // Create the fade in transition
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Create the fade out transition
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), label);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        // Create translate transition for moving up
        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(durationInSeconds / 2), label);
        moveUp.setByY(-20);

        // Create translate transition for moving down
        TranslateTransition moveDown = new TranslateTransition(Duration.seconds(durationInSeconds / 2), label);
        moveDown.setByY(20);

        // Create a sequential transition to play fade in and move up in sequence
        SequentialTransition fadeInSequence = new SequentialTransition(fadeIn, moveUp);

        // Create a sequential transition to play move down and fade out in sequence
        SequentialTransition fadeOutSequence = new SequentialTransition(moveDown, fadeOut);

        // Combine both sequences
        SequentialTransition combinedSequence = new SequentialTransition(fadeInSequence, fadeOutSequence);

        // Start the animation
        combinedSequence.play();

        // Reset position after animation ends
        combinedSequence.setOnFinished(event -> label.setLayoutY(originalYPosition));
    }



    public void revivePlayer(Event event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Playing.fxml"));

        root = loader.load();
        SceneController sceneControllerPlaying = loader.getController();
        sceneControllerPlaying.resetAfter(imageView.getImage());
        Player player = Player.getInstance(null, null, null, null, null);

        if (player.getCurrentScore() >= 5 && (player.getCherryCount()>= 2)){
            System.out.println(player.getCurrentScore() + "Score");
            GameMusicPlayer musicPlayer = new GameMusicPlayer("Music.mp3");
            musicPlayer.play();
            player.setCherryCount(player.getCherryCount() - 2);
            player.setCurrentScore(player.getCurrentScore() - 5);

            player.getScoreLabel().setText(Integer.toString(player.getCurrentScore()));
            player.getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));

            sceneControllerPlaying.setBackground(imageView.getImage());
            sceneControllerPlaying.resetAfter(imageView.getImage());// Assuming initializeMyScene is the method you want to call

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            animateLabelBonus(cherriesTooFew, 1);
        }
    }

    public void newGame(Event event) throws IOException {
        GameMusicPlayer musicPlayer = new GameMusicPlayer("Music.mp3");
        musicPlayer.play();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Playing.fxml"));
        root = loader.load();
        Player player = Player.getInstance(null, null, null, null, null);
        SceneController sceneControllerPlaying = loader.getController();
        sceneControllerPlaying.initializeMyScene(randomBackground());
        sceneControllerPlaying.getPlayer().setCurrentScore(0);
        player.getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));
        sceneControllerPlaying.getPlayer().getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));
        sceneControllerPlaying.getPlayer().setCherryCount(player.getCherryCount());
        System.out.println(player.getCherryCount() + "cherry count");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toHome(Event event) throws IOException {
        Player player = Player.getInstance(null, null, null, null, null);
        player.setCurrentScore(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Opening Screen.fxml"));
        Parent root = loader.load();

        // Getting the controller for the 'Playing' scene
        SceneControllerOpening openingController = loader.getController();

        openingController.initializeMyScene(); // Assuming initializeMyScene is the method you want to call
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    }

