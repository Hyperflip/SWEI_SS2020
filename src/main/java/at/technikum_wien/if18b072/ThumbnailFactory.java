package at.technikum_wien.if18b072;

import at.technikum_wien.if18b072.models.ThumbnailModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static at.technikum_wien.if18b072.Constants.*;

public class ThumbnailFactory {

    public ThumbnailModel getThumbnailModel(String inputPath) {

        String outputPath = THUMBS_PATH_REL + inputPath.substring(IMAGES_PATH_REL.length());
        String extension = inputPath.substring(inputPath.lastIndexOf(".") + 1);

        // look for thumbnail
        if(!new File(outputPath).exists()) {
            // create new thumbnail if none exists yet
            try {
                createThumbnail(inputPath, outputPath, extension);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // return ThumbnailModel
        return new ThumbnailModel(outputPath, inputPath, extension);
    }

    // creates a thumbnail image scaled by 0.25
    private void createThumbnail(String inputPath, String outputPath, String extension) throws Exception {
        try {
            // create file from input path
            File inputFile = new File(inputPath);
            // create input image
            BufferedImage inputImage = ImageIO.read(inputFile);

            // calculate thumbnail width and height
            int scaledWidth = (int) (inputImage.getWidth() * 0.25);
            int scaledHeight = (int) (inputImage.getHeight() * 0.25);
            // create output image
            BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

            // scale input to output image
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();

            // write thumbnail to output path
            ImageIO.write(outputImage, extension, new File(outputPath));
        }
        catch (Exception e) {
            throw new Exception("Error creating thumbnail");
        }
    }

}
