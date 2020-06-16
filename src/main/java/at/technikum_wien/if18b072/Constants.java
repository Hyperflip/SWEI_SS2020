package at.technikum_wien.if18b072;

import at.technikum_wien.if18b072.database.IDatabaseService;

/**
 * This class stores the paths to the image and thumbnail folders as well as the current database service.
 */
public final class Constants {
    public static final String IMAGES_PATH_REL = "src/main/resources/at/technikum_wien/if18b072/pictures/";
    public static final String THUMBS_PATH_REL = "src/main/resources/at/technikum_wien/if18b072/thumbnails/";

    public static IDatabaseService DATABASE;
}
