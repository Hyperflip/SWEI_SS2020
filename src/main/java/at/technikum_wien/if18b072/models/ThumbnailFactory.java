package at.technikum_wien.if18b072.models;

import at.technikum_wien.if18b072.models.ThumbnailModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static at.technikum_wien.if18b072.Constants.*;

/**
 * This class handles the creation of thumbnails.
 */
public class ThumbnailFactory {

    /**
     * This function returns a new ThumbnailModel. It either tries to locate an existing thumbnail
     * or calls the function to create a new one, based on the input path of the original picture.
     * @param inputPath
     * @return
     */
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

    /**
     * This function creates a new thumbnail with size 0.25 of the original picture, given
     * the original picture path.
     * @param inputPath
     * @param outputPath
     * @param extension
     * @throws Exception
     */
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
