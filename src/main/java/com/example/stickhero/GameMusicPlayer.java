package com.example.stickhero;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameMusicPlayer {

    private MediaPlayer mediaPlayer;

    public GameMusicPlayer(String musicFilePath) {

        Media media = new Media(getClass().getResource("Music.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);

        // indefinite play
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void play() {
        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
    }
}
