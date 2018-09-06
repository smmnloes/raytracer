package gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class RenderView {
    ImageView imageView;

    public void drawImage(int[][] imageBuffer) {
        int width = imageBuffer[0].length;
        int height = imageBuffer.length;

        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelWriter.setArgb(x, y, imageBuffer[y][x]);
            }
        }

        this.imageView.setImage(writableImage);
    }

    public Scene getRenderViewScene () {
        Group group = new Group();
        imageView = new ImageView();
        group.getChildren().add(imageView);
        return new Scene(group);

    }

}
