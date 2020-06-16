package at.technikum_wien.if18b072.models;

import javafx.beans.property.SimpleStringProperty;

/**
 * The PhotographerViewModel contains all Simple _ Properties of a PhotographerModel and
 * contains get/set access for the underlying PhotographerModel.
 */
public class PhotographerViewModel {

    private PhotographerModel photographer;

    public SimpleStringProperty photographerEmailProperty;
    public SimpleStringProperty firstNameProperty;
    public SimpleStringProperty lastNameProperty;
    public SimpleStringProperty birthdayProperty;
    public SimpleStringProperty notesProperty;

    public PhotographerViewModel(PhotographerModel photographer) {
        this.photographer = photographer;

        photographerEmailProperty = new SimpleStringProperty();
        firstNameProperty = new SimpleStringProperty();
        lastNameProperty = new SimpleStringProperty();
        birthdayProperty = new SimpleStringProperty();
        notesProperty = new SimpleStringProperty();

        updateProperties();
    }

    public void setPhotographerModel(PhotographerModel photographer) {
        this.photographer = photographer;
    }

    public PhotographerModel getPhotographerModel() {
        return photographer;
    }

    /**
     * This function updates the properties with the current PhotographerModel.
     */
    public void updateProperties() {
        photographerEmailProperty.set(photographer.getPhotographerEmail());
        firstNameProperty.set(photographer.getFirstName());
        lastNameProperty.set(photographer.getLastName());
        birthdayProperty.set(photographer.getBirthday());
        notesProperty.set(photographer.getNotes());
    }

}
