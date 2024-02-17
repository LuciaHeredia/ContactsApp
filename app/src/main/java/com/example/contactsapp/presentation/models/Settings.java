package com.example.contactsapp.presentation.models;

public class Settings {
    private boolean showLastName;
    private boolean showGender;
    private boolean showPhone;
    private boolean showEmail;

    /**
     * Constructor
     */
    public Settings(boolean showLastName, boolean showGender, boolean showPhone, boolean showEmail) {
        this.showLastName = showLastName;
        this.showGender = showGender;
        this.showPhone = showPhone;
        this.showEmail = showEmail;
    }

    /**
     * Empty Constructor
     */
    public Settings() {
    }

    //* Setters *//

    public void setShowLastName(boolean showLastName) {
        this.showLastName = showLastName;
    }

    public void setShowGender(boolean showGender) {
        this.showGender = showGender;
    }

    public void setShowPhone(boolean showPhone) {
        this.showPhone = showPhone;
    }

    public void setShowEmail(boolean showEmail) {
        this.showEmail = showEmail;
    }


    //* Getters *//

    public boolean isShowLastName() {
        return showLastName;
    }

    public boolean isShowGender() {
        return showGender;
    }

    public boolean isShowPhone() {
        return showPhone;
    }

    public boolean isShowEmail() {
        return showEmail;
    }

}
