package com.example.amrish.project3_a1;

/**
 * Created by Amrish on 28-Oct-17.
 */
public final class Constants {

    /**
     * Names of the landmarks
     */
    private static final String[] LANDMARK_NAMES = new String[]{
            "Willis Tower", "Museum of Science and Industry", "Field Museum of Natural History", "Navy Pier", "John Hancock Centre", "Shedd Aquarium"};

    /**
     * Return the array of landmark names
     *
     * @return String[]
     */
    public static String[] getLandmarkNames() {
        return LANDMARK_NAMES;
    }


    /*
    Websites of the landmarks
     */
    private static final String[] LANDMARK_WEBSITES = new String[]{
            "http://www.willistower.com/", "https://www.msichicago.org/", "https://www.fieldmuseum.org/", "https://navypier.org/", "http://johnhancockcenterchicago.com/", "https://www.sheddaquarium.org/"};

    /**
     * Return the array of landmark websites
     *
     * @return String[]
     */
    public static String[] getLandmarkWebsites() {
        return LANDMARK_WEBSITES;
    }
}
