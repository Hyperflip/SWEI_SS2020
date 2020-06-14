package at.technikum_wien.if18b072.models;

import java.util.Date;

public class PhotographerModel {

    // only has get access, as it's an auto incrementing value in database
    private String photographerEmail;     // PRIMARY key in photographers table
    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private String notes;

    public void setPhotographerEmail(String photographerEmail) {
        this.photographerEmail = photographerEmail;
    }

    public String getPhotographerEmail() {
        return photographerEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
