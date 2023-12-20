package com.example.stickhero;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Hero extends GameObjects {

    private ImageView imageView;

    public Button getFlipButton() {
        return flipButton;
    }

    private Button flipButton;

    private Platform platform;
    private  Platform currentPlatform;

    public boolean isFlipped = false;

    private AnchorPane anchorPane;

    private Player player = Player.getInstance(null, null, null, null, null);
    private Button growbutton;
    public Hero(ImageView imageView, Button flipButton, Platform platform, Platform currentPlatform, Stick stick, AnchorPane anchorPane,  Button growButton, Background background) {
        this.imageView = imageView;
        this.setCoordinateX((int) imageView.getLayoutX());
        this.setCoordinateY((int) imageView.getLayoutY());
        this.flipButton = flipButton;
        this.platform = platform;
        this.currentPlatform = currentPlatform;
        this.stick = stick;
        this.anchorPane = anchorPane;
        this.growbutton = growButton;
        this.background = background;
    }

    private AnimationTimer collisionChecker = new AnimationTimer() {
        public void handle(long now) {

            if (cherry != null && checkCollisionWithCherry(cherry)) {
                player.getCherryCountLabel().setText(Integer.toString(player.getCherryCount() + 1));
                Fruit fruit = cherry;
                FruitSizeAdapterImplementation sizeAdapter = new FruitSizeAdapterImplementation(fruit);
                sizeAdapter.resizeFruit(new ImageView());
                anchorPane.getChildren().remove(cherry);
                player.setCherryCount(player.getCherryCount() + 1);
                this.stop(); // Stop the timer
            }

        }
    };

    private int physicalLook;
    private int playerCherries;
    private String state;
    private boolean isAlive;

    private Stick stick;

    private Cherry cherry;

    private Background background;

    public ImageView getImageView(){
        return  imageView;
    }


    public void update() {
        double targetX = this.getCoordinateX() - 110;
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), imageView);
        transition.setByX(- targetX - this.imageView.getFitWidth()); // Move by the calculated distance
        transition.play();
        this.setCoordinateX(42);
    }

    //@Override
    public void moveForward(double Location, Background background, Button flipButton, Button growbutton2){ // moves till a specific location.

        collisionChecker.start();
        Image newImage = new Image(getClass().getResource("myHeroMoving.gif").toExternalForm());
        imageView.setImage(newImage);

        double targetX = Location - imageView.getFitWidth() * 0.5;

        background.moveForward(15);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(Location * 0.005), imageView);
        transition.setToX(targetX);

        // Update hero's X coordinate during the animation
        transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            setCoordinateX((int) imageView.getLayoutX() + (int) imageView.getTranslateX());
            setCoordinateY((int) imageView.getLayoutY() + (int) imageView.getTranslateY());
        });

        transition.setOnFinished(event -> {
            // Load the new image to be displayed after the transition

            Image resetImage =    new Image(getClass().getResource("myHero.png").toExternalForm());
            imageView.setImage(resetImage);
            this.setCoordinateX((int) imageView.getLayoutX() + (int) imageView.getTranslateX());
            currentPlatform.moveOut();
            platform.update();
            stick.moveOut();
            this.update();
            Rectangle newPlatform = platform.spawnPlatform();
            Rectangle newBonusBox = platform.spawnBonusBox(newPlatform);
            Cherry cherryOld = cherry;
            Cherry newCherry = Cherry.spawnCherry(120,  newPlatform, anchorPane);
            if (newCherry != null) {
                anchorPane.getChildren().add(newCherry);
                newCherry.moveCherry();
                this.setCherry(newCherry);// This will start the animation
            }
            currentPlatform = platform;
            platform = new Platform(newPlatform, newBonusBox, anchorPane);
            platform.setCoordinateX(newPlatform.getX()-1000);
            platform.moveIn();
            Rectangle newStick = stick.spawnStick();
            stick = new Stick(newStick, this, platform, background, currentPlatform, anchorPane, growbutton);
            stick.setCoordinateX(newStick.getX());
            collisionChecker.stop();
            if (cherryOld != null && !cherryOld.isReaped()){
                cherryOld.moveOut();
            }
            growbutton2.setDisable(false);

        });

        // Play the transition animation
        transition.play();

    }
    public void setCherry(Cherry cherry) {
        this.cherry = cherry;
    }

    public boolean checkCollisionWithCherry(Cherry cherry) {
        // Get bounds of the hero
        double heroX = getCoordinateX();
        double heroY = getCoordinateY();
        double heroWidth = imageView.getFitWidth();
        double heroHeight = imageView.getFitHeight();

        // Get bounds of the cherry

        double cherryX = cherry.getLayoutX() - 1000;
        double cherryY = cherry.getLayoutY();

        double cherrySize = 20; // Assuming CHERRY_SIZE is public or have a getter method

        // Check if the bounds intersect
        if (heroX < cherryX + cherrySize &&
                heroX + heroWidth > cherryX &&
                heroY < cherryY + cherrySize &&
                heroY + heroHeight > cherryY) {
            System.out.println("Collided");
            cherry.setReaped(true);
            return true; // Collision detected

        }

        return false; // No collision
    }



    public void moveForward(double Speed, Stick stick, Background background) { // moves till the end of the stick
        int cherryCountOld = player.getCherryCount();
        collisionChecker.start();
        // Calculate the target X position
        Image resetImage =   new Image(getClass().getResource("myHero.png").toExternalForm());
        // Update the imageView with the new image
        Image newImage =    new Image(getClass().getResource("myHeroMoving.gif").toExternalForm());
        imageView.setImage(newImage);
        background.moveForward(15);
        double targetX = imageView.getLayoutX() + Speed + imageView.getFitWidth() * 0.5;

        // Create a TranslateTransition
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), imageView);

        // Set the target X coordinate for the transition
        transition.setToX(targetX);
        background.moveForward(15);
        transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            setCoordinateX((int) imageView.getLayoutX() + (int) imageView.getTranslateX());
            setCoordinateY((int) imageView.getLayoutY() + (int) imageView.getTranslateY());
        });
        // Set the onFinished event handler for the transition
        transition.setOnFinished(event -> {
            // Load the new image to be displayed after the transition
            imageView.setImage(resetImage);
            // Update the hero's X coordinate
            this.setCoordinateX(targetX);

            // Call checkFallOnPlatform after the animation completes
            try {
                stick.checkFallOnPlatform();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            currentPlatform = platform;
            collisionChecker.stop();
            int cherryCountnew = player.getCherryCount();
            if (cherryCountnew > cherryCountOld){
                player.setCherryCount(player.getCherryCount() - 1);
                player.getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));
            }

        });

        // Play the transition animation
        transition.play();
    }

    //@Override
    public void fallDown() throws IOException {
        double screenHeight = 662; // The height of your game screen, set appropriately
        double fallDurationSeconds = 1.0; // Duration for the hero to fall, adjust as needed

        // Calculate the Y-coordinate at the bottom of the screen, accounting for the hero's height
        double translateY = screenHeight - imageView.getY() - imageView.getFitHeight();

        // Create a TranslateTransition for the imageView of the hero
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(fallDurationSeconds), imageView);

        // Set the amount to translate in the Y direction
        fallTransition.setByY(translateY);

        fallTransition.setOnFinished(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Game Over.fxml"));
                Parent root2 = loader.load();

                // Getting the controller for the 'Game Over' scene
                SceneControllerGameOver GameOverController = loader.getController();

                // Update the game over scene with necessary information
                GameOverController.updateGame(player.getScoreLabel().getText(), player.getCherryCountLabel().getText(), Integer.toString(player.getHighScore()), background.getMybackground().getImage());
                // Get the current stage and set the new scene
                Stage stage = (Stage) imageView.getScene().getWindow();
//                System.out.println(stage + "Hero");
                Scene gameOverScene = new Scene(root2);
                stage.setScene(gameOverScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception as needed
            }
        });
        // Play the animation
        fallTransition.play();
    }

    public void fallDownFlipped() {
        double screenHeight = 662; // The height of your game screen, set appropriately
        double fallDurationSeconds = 1.0; // Duration for the hero to fall, adjust as needed

        // Calculate the Y-coordinate at the bottom of the screen, accounting for the hero's height
        double translateY = screenHeight - imageView.getY() - imageView.getFitHeight();

        // Create a TranslateTransition for the imageView of the hero
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(fallDurationSeconds), imageView);

        // Set the amount to translate in the Y direction
        fallTransition.setByY(translateY);

        // Optional: Add any additional settings or event handlers if needed
        // For example, you might want to trigger some action after the fall is complete
        // fallTransition.setOnFinished(event -> { /* actions to perform after falling */ });

        // Play the animation
        fallTransition.play();
    }

    //@Override
    public void waitForInput() {

    }

    //@Override
    public void flipDown() {
        // Duration for the flipping animation
        imageView.setScaleY(-1); // Negative scale for flipping
        // Move the hero downwards
        imageView.setTranslateY(imageView.getTranslateY() + imageView.getFitHeight());
    }

    //@Override
    public void flipUp() {
        imageView.setScaleY(1); // Reset scale to normal
        // Move the hero back to original position
        imageView.setTranslateY(imageView.getTranslateY() - imageView.getFitHeight());
    }

    public void toggleFlip() {
        if (isFlipped) {
            flipUp();
        } else {
            flipDown();
        }
        isFlipped = !isFlipped; // Toggle the state
    }
}
