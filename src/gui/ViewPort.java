package gui;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ViewPort extends ImageView {

    public void drawImage(int[][] imageBuffer) {
        int width = imageBuffer[0].length;
        int height = imageBuffer.length;

        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                pixelWriter.setArgb(x, y, imageBuffer[x][y]);
            }
        }

        this.setImage(writableImage);
    }

}
