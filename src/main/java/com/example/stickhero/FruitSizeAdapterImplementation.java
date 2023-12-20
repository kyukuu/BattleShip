package com.example.stickhero;

import com.example.stickhero.FruitSizeAdapter;
import javafx.animation.ScaleTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

// Adapter design pattern
public class FruitSizeAdapterImplementation implements FruitSizeAdapter {
    private Fruit fruit;

    public FruitSizeAdapterImplementation(Fruit fruit) {
        this.fruit = fruit;
    }

    @Override
    public void resizeFruit(ImageView imageView) {
        ScaleTransition increaseSize = new ScaleTransition(Duration.seconds(0.5), imageView);
        increaseSize.setToX(1.5); // Scale to 150%
        increaseSize.setToY(1.5); // Scale to 150%

        // Decrease size to zero
        ScaleTransition decreaseSize = new ScaleTransition(Duration.seconds(0.5), imageView);
        decreaseSize.setToX(0); // Scale to 0%
        decreaseSize.setToY(0); // Scale to 0%

        increaseSize.setOnFinished(event -> {
            decreaseSize.play();
        });

        // Start the animation
        increaseSize.play();

    }

}