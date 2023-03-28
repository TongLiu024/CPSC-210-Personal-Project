package ui;

public interface Updatable {

    //MODIFIES: this
    //EFFECT: updates the list of the combobox (wildlifeList, donorList) based on the most recent status of conservationSite of the
    //mainFrame
    //Reference: https://stackoverflow.com/questions/4620295/dynamically-change-jcombobox
    void refreshComboBoxOptions();
}
