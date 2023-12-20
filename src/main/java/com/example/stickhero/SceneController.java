package com.example.stickhero;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class SceneController {
    public Stage stage;
    Scene scene;
    Parent root;

    @FXML
    public Button growButton;
    @FXML
    public ImageView heroImageView;
    @FXML
    ImageView myBackground;
    @FXML
    Rectangle myStick;
    @FXML
    Rectangle myPlatform;
    @FXML
    Label myBonusLabel;
    @FXML
    Rectangle myPlatformCurrent;
    @FXML
    Label scoreLabel;
    @FXML
    Label messageLabel;
    @FXML
    Label perfectLabel;
    @FXML
    Button flipButton;
    @FXML
    Rectangle bonusBox;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Label cherryCount;
    Hero hero;
    Stick stick;
    Platform platform;
    Background background;


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private Player player;
    Platform currentPlatform;

    public void setBackground(Image image){
        this.myBackground.setImage(image);
    }

    public void saveGame(Event event) throws IOException {
        Player player = Player.getInstance(null, null, null, null, null);
        Player.serializeInt(player.getCurrentScore(), "currentScore.txt");
        Player.serializeInt(player.getHighScore(), "highscore.txt");
        Player.serializeInt(player.getCherryCount(), "cherryCount.txt");
        Parent root = FXMLLoader.load(getClass().getResource("Opening Screen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }



    public Rectangle spawnPlatform() {
        Random random = new Random();

        // Create a new rectangle with the specified criteria
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(140);
        rectangle.setWidth(random.nextInt(151) + 30); // Width between 20 and 170
        rectangle.setFill(Color.web("#131313")); // Hex color code #131313

        // Generate a random X position so that the leftmost point of x is always greater than 120
        double maxWidth = 355 - rectangle.getWidth();
        double minLeftMostX = 120; // Minimum value for the leftmost point of x
        double xPos = (random.nextDouble()) * (maxWidth - minLeftMostX) + minLeftMostX;
        rectangle.setX(xPos); // Adding 1000 to animate in from the outside

        rectangle.setY(267); // layout y set of the original game rectangle.
        // Add the rectangle to the provided Pane
        anchorPane.getChildren().add(rectangle);
        return rectangle;
    }

    public Rectangle spawnBonusBox(Rectangle baseRectangle) {
        // Create a new rectangle for the bonus box
        Rectangle bonusBox = new Rectangle();

        // Set the size of the bonus box (adjust these values as needed)
        double boxWidth = 8;
        double boxHeight = 6;
        bonusBox.setWidth(boxWidth);
        bonusBox.setHeight(boxHeight);

        // Calculate the X position to place the bonus box at the top center of the baseRectangle
        double bonusBoxX = baseRectangle.getX() + (baseRectangle.getWidth() - boxWidth) / 2;

        // Set the Y position to place the bonus box just above the baseRectangle
        double bonusBoxY = baseRectangle.getY() - boxHeight + boxHeight;

        // Set the position of the bonus box
        bonusBox.setX(bonusBoxX);
        bonusBox.setY(bonusBoxY);

        // Set the color or other properties of the bonus box as needed
        bonusBox.setFill(Color.web("#ff0202")); // Change the color as desired
        anchorPane.getChildren().add(bonusBox);
        return bonusBox;
    }


    public void initializeMyScene(Image imagebackground) {
        GameObjects gameObjectsContainer = new GameObjects();
        flipButton.setOnAction(event -> hero.toggleFlip());
        Rectangle tempPlatform = spawnPlatform();
        platform = new Platform(tempPlatform, spawnBonusBox(tempPlatform), anchorPane);
        platform.setCoordinateX(platform.getRectangle().getX());
        currentPlatform = new Platform(myPlatformCurrent, null, anchorPane);
        myBackground.setImage(imagebackground);
        background = new Background(myBackground);

        player = Player.getInstance(myBonusLabel, scoreLabel, messageLabel, perfectLabel, cherryCount);
        player.setMessageLabel(messageLabel);
        player.setScoreLabel(scoreLabel);
        player.setBonusLabel(myBonusLabel);
        player.setCherryCountLabel(cherryCount);
        player.setPerfectLabel(perfectLabel);

        stick = new Stick(myStick, hero, platform, background, currentPlatform, anchorPane, growButton);
        hero = new Hero(heroImageView, flipButton, platform, currentPlatform, stick, anchorPane, growButton, background);
        stick = new Stick(myStick, hero, platform, background, currentPlatform, anchorPane, growButton);
        growButton.setOnMousePressed(e -> stick.startExtending());
        growButton.setOnMouseReleased(e -> stick.stopExtending());
        player.setCherryCount(Player.deserializeInt("cherryCount.txt"));
        player.setHighScore(Player.deserializeInt("highscore.txt"));
    }

    public void resetAfter(Image imagebackground) {
        GameObjects gameObjectsContainer = new GameObjects();
        flipButton.setOnAction(event -> hero.toggleFlip());
        Rectangle tempPlatform = spawnPlatform();
        platform = new Platform(tempPlatform, spawnBonusBox(tempPlatform), anchorPane);
        platform.setCoordinateX(platform.getRectangle().getX());
        currentPlatform = new Platform(myPlatformCurrent, null, anchorPane);
        myBackground.setImage(imagebackground);
        background = new Background(myBackground);

        player = Player.getInstance(myBonusLabel, scoreLabel, messageLabel, perfectLabel, cherryCount);
        player.setMessageLabel(messageLabel);
        player.setScoreLabel(scoreLabel);
        player.setBonusLabel(myBonusLabel);
        player.setCherryCountLabel(cherryCount);
        player.setPerfectLabel(perfectLabel);

        stick = new Stick(myStick, hero, platform, background, currentPlatform, anchorPane, growButton);
        hero = new Hero(heroImageView, flipButton, platform, currentPlatform, stick, anchorPane, growButton, background);
        stick = new Stick(myStick, hero, platform, background, currentPlatform, anchorPane, growButton);
        growButton.setOnMousePressed(e -> stick.startExtending());
        growButton.setOnMouseReleased(e -> stick.stopExtending());
        player.setCherryCount(Player.deserializeInt("cherryCount.txt"));
        player.setHighScore(Player.deserializeInt("highscore.txt"));
    }

    public void switchToStart(ActionEvent event) throws IOException {

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchTogameOver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Game Over.fxml"));

        Parent root = loader.load();

        // Getting the controller for the 'Game Over' scene
        SceneControllerGameOver gameOverController = loader.getController();

        gameOverController.updateGame(player.getScoreLabel().getText(), player.getCherryCountLabel().getText(), Integer.toString(player.getHighScore()), myBackground.getImage());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

