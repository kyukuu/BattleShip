package com.example.stickhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.example.stickhero.Cherry.CHERRY_IMAGE_PATH;

public class Fruit extends ImageView {
    private static int totalFruits;

    public Fruit(Image image) {
        super(new Image(Cherry.class.getResourceAsStream(CHERRY_IMAGE_PATH)));
    }

    public static int getTotalFruits() {
        return totalFruits;
    }

    public static void setTotalFruits(int totalFruits) {
        Fruit.totalFruits = totalFruits;
    }

    public Fruit getFruit(){
        return null;
    }
    public void spawnFruit(int coordinateX,int coordinateY){}
}
