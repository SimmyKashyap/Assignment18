package com.example.nitinsharma.loginapp;

/**
 * Created by nitin sharma on 07-Dec-18.
 */

public class ItemsBean {
    String title;

    public ItemsBean(String title, String description, String checkBoxStatus) {
        this.title = title;
        this.description = description;
        this.checkBoxStatus = checkBoxStatus;
    }

    String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCheckBoxStatus() {
        return checkBoxStatus;
    }

    public void setCheckBoxStatus(String checkBoxStatus) {
        this.checkBoxStatus = checkBoxStatus;
    }

    String checkBoxStatus;
}
