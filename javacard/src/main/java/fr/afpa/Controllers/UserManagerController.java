package fr.afpa.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import fr.afpa.App;
import fr.afpa.Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UserManagerController {

    @FXML
    private Button deleteButton;

    @FXML
    private Button newButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldLastName;

    @FXML
    private TextField textFieldPseudo;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private TextField textFieldNumber;

    @FXML
    private TextField textFieldProNumber;

    @FXML
    private TextField textFieldMail;

    @FXML
    private TextField textFieldLinkedin;

    @FXML
    private TextField textFieldGitHub;

    @FXML
    private ComboBox<String> comboBoxGender;

    @FXML
    private void initComboBoxGender() {
        comboBoxGender.getItems().addAll("Male", "Female", "Non-binary");
    }

    @FXML
    private ComboBox<String> comboBoxSelectFormat;

    @FXML
    private void initComboBoxSelectFormat() {
        comboBoxSelectFormat.getItems().addAll("vCard", "JSON", "CSV", "QRCode");
    }

    @FXML
    private DatePicker datePickerBirthday;

    @FXML
    private CheckBox checkBoxSelectAll;

    @FXML
    private TableView<String> tableView4columns;

    @FXML
    private TableColumn<Contact, String> columnFirstName;

    @FXML
    private TableColumn<Contact, String> columnLastName;

    @FXML
    private TableColumn<Contact, String> columnNumber;

    @FXML
    private TableColumn<Contact, String> columnMail;

    @FXML
    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        tableView4columns.setItems(contacts); // TO DO initialize

        contacts.addAll(
                new Contact("Boivin", "Chloé", "Female", LocalDate.of(1995, 07, 19), "bulo", "Bordeaux", "0604138029",
                        "", "chloe.boivin@outlook.com",
                        "https://www.linkedin.com/in/chloe-boivin/", "https://github.com/bu-lo"),
                new Contact("Marchive", "Florian", "Male", LocalDate.of(1995, 03, 28), "marchive", "Bordeaux",
                        "0613206966",
                        "", "marchiveflorian@gmail.com", "https://www.linkedin.com/in/florianmarchive/",
                        "https://github.com/MarchiveFlorian"));

        // ***
        // *** TO DO: INITIALIZE WITH CONTACTS ALREADY IN BINARY ***
        // ***

        columnFirstName.setCellValueFactory(cellData -> cellData.getValue.getFirstName());
        columnLastName.setCellValueFactory(cellData -> cellData.getValue().getLastName());
        columnNumber.setCellValueFactory(cellData -> cellData.getValue().getPersonalPhoneNumber());
        columnMail.setCellValueFactory(cellData -> cellData.getValue().getEmailAddress());

    }

    /**
     * RESET THE FORM + INIT VERSION
     */
    @FXML
    private void resetForm() {
        // Reset TextFields
        textFieldFirstName.setText("");
        textFieldLastName.setText("");
        textFieldPseudo.setText("");
        textFieldAddress.setText("");
        textFieldNumber.setText("");
        textFieldProNumber.setText("");
        textFieldMail.setText("");
        textFieldLinkedin.setText("");
        textFieldGitHub.setText("");

        // Reset Gender ComboBox
        comboBoxGender.getSelectionModel().clearSelection();
        // comboBoxGender.getSelectionModel().selectFirst(); // select an element

        // Reset DatePicker
        datePickerBirthday.setValue(null);
    }

    // SAVE BUTTON
    @FXML
    private void save(ActionEvent e) {

        String fName = textFieldFirstName.getText();
        String lName = textFieldLastName.getText();
        String gender = comboBoxGender.getValue();
        LocalDate bDay = datePickerBirthday.getValue();
        String pseudo = textFieldPseudo.getText();
        String add = textFieldAddress.getText();
        String tNum = textFieldNumber.getText();
        String tPNum = textFieldProNumber.getText();
        String mail = textFieldMail.getText();
        String linkd = textFieldLinkedin.getText();
        String gHub = textFieldGitHub.getText();

        if (!fName.isEmpty() && !lName.isEmpty() && !gender.isEmpty() && bDay != null && !tNum.isEmpty()
                && !mail.isEmpty() && !linkd.isEmpty()) { // Are not empty.
            contacts.add(new Contact(fName, lName, gender, bDay, pseudo, add, tNum, tPNum, mail, linkd, gHub));

            resetForm();
        }
    }

    // NEW BUTTON
    @FXML
    private void newC(ActionEvent e) {

        resetForm();

        contacts.add(new Contact("", "", "", LocalDate.of(0000, 0, 0), "", "", "", "", "", "", ""));
        // TO DO: New line in tab

    }

    // DELETE BUTTON
    @FXML
    void delete(ActionEvent e) {

        resetForm();
        Contact selectedContact = tableView4columns.getSelectionModel().getSelectedItem();  //TO DO WORKING
        if (selectedContact != null) {
            contacts.remove(selectedContact)};
        
    }

    // SELECT ALL ARRAY
    @FXML
    void selectAllArray(ActionEvent e) {
        tableView4columns.getSelectionModel().selectAll();
    }

    // EXPORT BUTTON
    @FXML
    void export(ActionEvent e) {
        if (comboBoxSelectFormat.getValue() == null) {
            System.out.println("Please select a Format");
        } else {
            // TO DO *** EXPORT ACTIONS ***
        }
    }

}