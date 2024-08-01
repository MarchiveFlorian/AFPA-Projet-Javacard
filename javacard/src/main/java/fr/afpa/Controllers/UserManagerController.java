package fr.afpa.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.control.SelectionMode;
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
    private TableView<Contact> tableView4columns;

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

        selectManual();
        initComboBoxGender();
        initComboBoxSelectFormat();

        tableView4columns.setItems(contacts); // TO DO initialize

        contacts.addAll(
                new Contact("Chloé", "Boivin", "Female", LocalDate.of(1995, 07, 19), "bulo", "Bordeaux", "0604138029",
                        "", "chloe.boivin@outlook.com",
                        "https://www.linkedin.com/in/chloe-boivin/", "https://github.com/bu-lo"),
                new Contact("Florian", "Marchive", "Male", LocalDate.of(1995, 03, 28), "marchive", "Bordeaux",
                        "0613206966",
                        "", "marchiveflorian@gmail.com", "https://www.linkedin.com/in/florianmarchive/",
                        "https://github.com/MarchiveFlorian"));

        // ***
        // *** TO DO: INITIALIZE WITH CONTACTS ALREADY IN BINARY ***
        // ***

        columnFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        columnLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        columnNumber.setCellValueFactory(cellData -> cellData.getValue().personalPhoneNumberProperty());
        columnMail.setCellValueFactory(cellData -> cellData.getValue().emailAddressProperty());

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

        if (!fName.isEmpty() && !lName.isEmpty() && bDay != null && !tNum.isEmpty()
                && !mail.isEmpty() && !linkd.isEmpty()) { // Are not empty. //&& !gender.isEmpty()
            contacts.add(new Contact(fName, lName, gender, bDay, pseudo, add, tNum, tPNum, mail, linkd, gHub));

            resetForm();
        }
    }

    // NEW BUTTON
    @FXML
    private void newC(ActionEvent e) {

        resetForm();

        tableView4columns.getItems().add(new Contact("NEW", "-", null, null, null, null, "-", null, "-", null, null));
        // TO DO SELECT LINE BLUE ********
    }

    // DELETE BUTTON
    @FXML
    void delete(ActionEvent e) {

        resetForm();
        // Contact selectedContact =
        // tableView4columns.getSelectionModel().getSelectedItem();

        ObservableList<Contact> selectedContacts = tableView4columns.getSelectionModel().getSelectedItems();

        if (!selectedContacts.isEmpty()) {
            // Créer une liste pour stocker les contacts à supprimer
            List<Contact> contactsToRemove = new ArrayList<>(selectedContacts);

            // Supprimer les contacts sélectionnés de la liste principale
            contacts.removeAll(contactsToRemove);
        }
        // if (selectedContact != null) {
        // contacts.remove(selectedContact);
        // }

    }

    // SELECT Multiple elements with CTRL
    @FXML
    private void selectManual() {
        tableView4columns.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    // SELECT ALL ARRAY *****************
    @FXML
    void selectAllArray(ActionEvent e) {
        // tableView4columns.getSelectionModel().selectAll();
    }

    // EXPORT BUTTON
    @FXML
    void export(ActionEvent e) {

        String selectedFormat = comboBoxSelectFormat.getSelectionModel().getSelectedItem();
        
        if (selectedFormat == null) {
            System.out.println("Please select a Format");
        } else {
            switch (selectedFormat) {
                case "vCard":
                    // TO DO Link with VCard Logic
                    System.out.println("Exporting as vCard");
                    break;
                case "JSON":
                    // TO DO Link with JSON Logic
                    System.out.println("Exporting as JSON");
                    break;
                case "CSV":
                    // TO DO Link with CSV Logic
                    System.out.println("Exporting as CSV");
                    break;
                case "QRCode":
                    // TO DO Link with QRCode Logic
                    System.out.println("Exporting as QRCode");
                    break;
            }
        }
    }

}