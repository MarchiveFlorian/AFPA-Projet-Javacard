package fr.afpa.controllers;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import fr.afpa.models.Contact;
import fr.afpa.tools.ConnectionPostgreSQL;
import fr.afpa.controllers.ContactQRcodeSerializer;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    private TextField researchField;

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
        comboBoxSelectFormat.getItems().addAll("vCard", "JSON", "CSV", "QRCode (1 contact)");
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
    private FilteredList<Contact> filteredList;

    @FXML
    private HBox hBoxForm;

    // DateTimeFormatter LocalDate -> String
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {

        hBoxForm.setVisible(false);
        selectManual();
        initComboBoxGender();
        initComboBoxSelectFormat();

        // tableView4columns.setItems(contacts); //done later

        // ChangeListener listening to selection
        tableView4columns.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            hBoxForm.setVisible(true);
            updateForm(newValue);
        });

        // contacts.addAll(
        // new Contact("Chloé", "Boivin", "Female", LocalDate.of(1995, 07, 19), "bulo",
        // "Bordeaux", "0604138029",
        // "", "chloe.boivin@outlook.com",
        // "https://www.linkedin.com/in/chloe-boivin/", "https://github.com/bu-lo"),
        // new Contact("Florian", "Marchive", "Male", LocalDate.of(1995, 03, 28),
        // "marchive", "Bordeaux",
        // "0613206966",
        // "", "marchiveflorian@gmail.com",
        // "https://www.linkedin.com/in/florianmarchive/",
        // "https://github.com/MarchiveFlorian"));

        // Load contacts from binary file
        // try {
        // ContactBinarySerializer binarySerializer = new ContactBinarySerializer();
        // ArrayList<Contact> loadedContacts =
        // binarySerializer.loadList("contacts.bin");
        // contacts.addAll(loadedContacts);
        // } catch (IOException | ClassNotFoundException e) {
        // System.out.println("Failed to load contacts from binary file: " +
        // e.getMessage());
        // }

        // Load contacts from database
        try {
            ContactDAO dao = new ContactDAO();
            ArrayList<Contact> loadedContacts = dao.getAll();
            contacts.addAll(loadedContacts);
        } catch (Exception e) {
            System.out.println("Failed to load contacts from database: " + e.getMessage());
        }

        // TableView link
        columnFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        columnLastName.setCellValueFactory(cellData -> {
            if (cellData.getValue().getLastName().equals("Required")) {
                return new SimpleStringProperty("-");
            } else {
                return cellData.getValue().lastNameProperty();
            }
        });
        columnNumber.setCellValueFactory(cellData -> {
            if (cellData.getValue().getPersonalPhoneNumber().equals("Required")) {
                return new SimpleStringProperty("-");
            } else {
                return cellData.getValue().personalPhoneNumberProperty();
            }
        });
        columnMail.setCellValueFactory(cellData -> {
            if (cellData.getValue().getEmailAddress().equals("Required")) {
                return new SimpleStringProperty("-");
            } else {
                return cellData.getValue().emailAddressProperty();
            }
        });

        // Ajoute un écouteur pour gérer l'événement de fermeture de la fenêtre
        Platform.runLater(() -> {
            Stage stage = (Stage) tableView4columns.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                try {
                    // Tente de sauvegarder les contacts
                    saveContactsOnClose();
                } catch (Exception e) {
                    System.out.println("Échec de la sauvegarde des contacts : " + e.getMessage());
                    event.consume(); // Empêche la fermeture si la sauvegarde échoue
                    return; // Quitte la méthode si la sauvegarde échoue
                }

                // Ferme la connexion à la base de données après la sauvegarde
                ConnectionPostgreSQL.close();
            });
        });

        // COMBOBOX SELECT FORMAT MOUSE EVENT
        comboBoxSelectFormat.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            exportButton.getStyleClass().remove("button-error");
            exportButton.getStyleClass().remove("button-success");
        });

        // RESEARCH FUNCTION

        // Creation of the Filtered list
        filteredList = new FilteredList<>(contacts, c -> true);
        tableView4columns.setItems(filteredList);

        // Listener on the TextField researchField
        researchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = researchField.getText();
            if (searchText.isEmpty()) {
                filteredList.setPredicate(c -> true);
            } else {
                filteredList.setPredicate(createPredicate());
            }
        });

        // After setting up the TableView and contacts, select the first contact
        Platform.runLater(() -> {
            // Remove focus from the search field
            researchField.requestFocus(); // Requests focus on the search field
            researchField.getParent().requestFocus(); // Moves focus away from the search field
        });

    }

    private Predicate<Contact> createPredicate() {
        return contact -> {
            String searchText = researchField.getText();
            String lowerCaseFilter = searchText.toLowerCase();

            return contact.getFirstName() != null && contact.getFirstName().toLowerCase().contains(lowerCaseFilter) ||
                    contact.getFirstName() != null && contact.getLastName().toLowerCase().contains(lowerCaseFilter) ||
                    contact.getGender() != null && contact.getGender().toLowerCase().equals(lowerCaseFilter) ||
                    contact.getBirthDate() != null
                            && contact.getBirthDate().format(DATE_FORMATTER).contains(lowerCaseFilter)
                    ||
                    contact.getNickname() != null && contact.getNickname().toLowerCase().contains(lowerCaseFilter) ||
                    contact.getAddress() != null && contact.getAddress().toLowerCase().contains(lowerCaseFilter) ||
                    contact.getPersonalPhoneNumber() != null
                            && contact.getPersonalPhoneNumber().toLowerCase().contains(lowerCaseFilter)
                    ||
                    contact.getProfessionalPhoneNumber() != null
                            && contact.getProfessionalPhoneNumber().toLowerCase().contains(lowerCaseFilter)
                    ||
                    contact.getEmailAddress() != null
                            && contact.getEmailAddress().toLowerCase().contains(lowerCaseFilter)
                    ||
                    contact.getLinkedinLink() != null
                            && contact.getLinkedinLink().toLowerCase().contains(lowerCaseFilter)
                    ||
                    contact.getGithubGitlabLink() != null
                            && contact.getGithubGitlabLink().toLowerCase().contains(lowerCaseFilter);
        };
    }

    /**
     * Saves the contacts to a binary file when the application is closed.
     */
    // private void saveContactsOnClose() {
    // try {
    // ContactBinarySerializer binarySerializer = new ContactBinarySerializer();
    // binarySerializer.clearFile("contacts.bin");
    // binarySerializer.saveList("contacts.bin", new ArrayList<>(contacts));
    // System.out.println("Contacts saved successfully on close.");
    // } catch (IOException e) {
    // System.out.println("Failed to save contacts on close: " + e.getMessage());
    // }
    // }

    // Save the contacts to database when application is closed.
    private void saveContactsOnClose() {
        try {
            // Sauvegarde des contacts en base de données
            ContactDAO dao = new ContactDAO();
    
            // // Clear de la table pour éviter les doublons
            // dao.clearTable();
    
            // Appel de la méthode insert pour sauvegarder les contacts
            boolean success = dao.insert(new ArrayList<>(contacts));
    
            if (success) {
                System.out.println("Contacts saved successfully on close.");
            } else {
                System.out.println("No contacts were saved on close.");
            }
        } catch (Exception e) {
            System.out.println("Failed to save contacts on close: " + e.getMessage());
            e.printStackTrace();
        }
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
        comboBoxGender.setPromptText("Required - Select gender");

        // Reset DatePicker
        datePickerBirthday.setValue(null);

        // Reset field styles
        resetFieldStyles();
    }

    /**
     * UPDATE THE FORM
     */
    @FXML
    private void updateForm(Contact contact) {
        if (contact != null) {

            // Reset field styles
            resetFieldStyles();

            textFieldFirstName.setText(contact.getFirstName());
            textFieldLastName.setText(contact.getLastName());
            comboBoxGender.setValue(contact.getGender());
            datePickerBirthday.setValue(contact.getBirthDate());
            textFieldPseudo.setText(contact.getNickname());
            textFieldAddress.setText(contact.getAddress());
            textFieldNumber.setText(contact.getPersonalPhoneNumber());
            textFieldProNumber.setText(contact.getProfessionalPhoneNumber());
            textFieldMail.setText(contact.getEmailAddress());
            textFieldLinkedin.setText(contact.getLinkedinLink());
            textFieldGitHub.setText(contact.getGithubGitlabLink());
        }
    }

    // SAVE BUTTON
    @FXML
    private void save(ActionEvent e) {

        // Get values of the formulary
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

        // Get selected contact
        Contact selectedContact = tableView4columns.getSelectionModel().getSelectedItem();

        if (selectedContact == null) {
            System.out.println("No contact selected. Cannot save changes.");
            return; // Exit the method early
        }

        boolean valid = true;

        if (fName.isEmpty()) {
            textFieldFirstName.getStyleClass().add("text-field-error");
            valid = false;
        } else if (fName.equals("NEW")) {
            textFieldFirstName.getStyleClass().add("text-field-error");
            valid = false;
        }

        if (lName.isEmpty()) {
            textFieldLastName.getStyleClass().add("text-field-error");
            valid = false;
        } else if (lName.equals("Required")) {
            textFieldLastName.getStyleClass().add("text-field-error");
            valid = false;
        }

        if (gender == null || gender.isEmpty()) {
            comboBoxGender.getStyleClass().add("combo-box-error");
            valid = false;
        } else if (comboBoxGender.getSelectionModel().isEmpty()) {
            comboBoxGender.getStyleClass().add("combo-box-error");
            valid = false;
        }

        if (bDay == null) {
            datePickerBirthday.getStyleClass().add("date-picker-error");
            valid = false;
        } else if (bDay.getYear() == Year.now().getValue()) {
            datePickerBirthday.getStyleClass().add("date-picker-error");
            valid = false;
        }

        if (tNum.isEmpty()) {
            textFieldNumber.getStyleClass().add("text-field-error");
            valid = false;
        } else if (!Pattern.matches("^0\\d{9}$", tNum)) {
            textFieldNumber.getStyleClass().add("text-field-error");
            valid = false;
        }

        if (mail.isEmpty()) {
            textFieldMail.getStyleClass().add("text-field-error");
            valid = false;
        } else if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", mail)) {
            textFieldMail.getStyleClass().add("text-field-error");
            valid = false;
        }

        if (linkd.isEmpty()) {
            textFieldLinkedin.getStyleClass().add("text-field-error");
            valid = false;
        } else if (!Pattern.matches(
                "^(https?:\\/\\/)?(www\\.)?linkedin\\.com\\/(in|company|school)\\/[a-zA-Z0-9_-]+\\/?$", linkd)) {
            textFieldLinkedin.getStyleClass().add("text-field-error");
            valid = false;
        }

        if (valid) {
            selectedContact.setFirstName(fName);
            selectedContact.setLastName(lName);
            selectedContact.setGender(gender);
            selectedContact.setBirthDate(bDay);
            selectedContact.setNickname(pseudo);
            selectedContact.setAddress(add);
            selectedContact.setPersonalPhoneNumber(tNum);
            selectedContact.setProfessionalPhoneNumber(tPNum);
            selectedContact.setEmailAddress(mail);
            selectedContact.setLinkedinLink(linkd);
            selectedContact.setGithubGitlabLink(gHub);

            resetForm();
            hBoxForm.setVisible(false);

            resetFieldStyles();
        } else {
            System.out.println("Form contains errors. Please correct them.");
        }
    }

    // RESET RED ERROR
    @FXML
    private void resetFieldStyles() {
        textFieldFirstName.getStyleClass().remove("text-field-error");
        textFieldLastName.getStyleClass().remove("text-field-error");
        comboBoxGender.getStyleClass().remove("combo-box-error");
        datePickerBirthday.getStyleClass().remove("date-picker-error");
        textFieldNumber.getStyleClass().remove("text-field-error");
        textFieldMail.getStyleClass().remove("text-field-error");
        textFieldLinkedin.getStyleClass().remove("text-field-error");
    }

    // NEW BUTTON
    @FXML
    private void newC(ActionEvent e) {

        resetForm();
        comboBoxGender.getSelectionModel().clearSelection();
        comboBoxGender.setPromptText("Required - Select gender");
        hBoxForm.setVisible(true);

        Contact newContact = new Contact("NEW", "Required", "Required", LocalDate.now(), "-", "-", "Required", "-",
                "Required", "Required", "-");

        // Add new contact to the original (non-filtered) list
        contacts.add(newContact);

        // Refresh the TableView
        tableView4columns.refresh();

        // Clear selection -> not to have many selected items
        tableView4columns.getSelectionModel().clearSelection();

        // Force the TableView to update the selection
        Platform.runLater(() -> {
            tableView4columns.getSelectionModel().select(newContact);
        });

    }

    // DELETE BUTTON
    @FXML
    void delete(ActionEvent e) {

        ObservableList<Contact> selectedContacts = tableView4columns.getSelectionModel().getSelectedItems();

        if (!selectedContacts.isEmpty()) {
            // New list to stock contacts to delete
            List<Contact> contactsToRemove = new ArrayList<>(selectedContacts);
            contacts.removeAll(contactsToRemove);
        }

        resetForm();
        checkBoxSelectAll.setSelected(false);
        hBoxForm.setVisible(false);
    }

    // SELECT MULTIPLE ELEMENTS WITH "CTRL"
    @FXML
    private void selectManual() {
        tableView4columns.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    // SELECT ALL ARRAY
    @FXML
    void selectAllArray(ActionEvent e) {
        if (checkBoxSelectAll.isSelected()) {
            tableView4columns.getSelectionModel().selectAll();
        } else {
            tableView4columns.getSelectionModel().clearSelection();
        }
    }

    // EXPORT BUTTON
    @FXML
    void export(ActionEvent e) {

        String selectedFormat = comboBoxSelectFormat.getSelectionModel().getSelectedItem();

        // Convert ObservableList to ArrayList
        ObservableList<Contact> selectedContacts = tableView4columns.getSelectionModel().getSelectedItems();
        ArrayList<Contact> contactsList = new ArrayList<>(selectedContacts);

        if (selectedFormat == null) {
            System.out.println("Please select a Format");
            exportButton.getStyleClass().add("button-error");
        } else {
            switch (selectedFormat) {
                case "vCard":
                    // TO DO Link with VCard Logic
                    try {
                        ContactVCardSerializer vCardSerializer = new ContactVCardSerializer();

                        // Save contacts using the created instances

                        // Chemin = dossier utilisateur + ".javacard"
                        // Pour retrouver le chemin aboslu vers le dosssier utilsiateur :
                        // System.getProperty("user.home");

                        vCardSerializer.saveList("contacts.vcf", contactsList);
                        System.out.println("Contacts exported successfully in vCard format.");

                    } catch (IOException ex) {
                        System.out.println("Failed to export contacts in vCard format: " + ex.getMessage());
                        exportButton.getStyleClass().add("button-error");
                    }

                    System.out.println("Exporting as vCard");
                    exportButton.getStyleClass().add("button-success");
                    break;

                case "JSON":
                    try {
                        ContactJsonSerializer jsonSerializer = new ContactJsonSerializer();

                        // Save contacts using the created instances
                        jsonSerializer.saveList("contacts.json", contactsList);
                        System.out.println("Contacts exported successfully in Json format.");

                    } catch (IOException ex) {
                        System.out.println("Failed to export contacts in Json format: " + ex.getMessage());
                        exportButton.getStyleClass().add("button-error");
                    }
                    System.out.println("Exporting as JSON");
                    exportButton.getStyleClass().add("button-success");
                    break;

                case "CSV":
                    try {
                        ContactCsvSerializer csvSerializer = new ContactCsvSerializer();
                        csvSerializer.saveList("contacts.csv", contactsList);
                        System.out.println("Contacts exported successfully in CSV format.");
                    } catch (IOException ex) {
                        System.out.println("Failed to export contacts in CSV format: " + ex.getMessage());
                        exportButton.getStyleClass().add("button-error");
                    }
                    System.out.println("Exporting as CSV");
                    exportButton.getStyleClass().add("button-success");
                    break;

                case "QRCode (1 contact)":
                    try {
                        ContactVCardSerializer vCardSerializer = new ContactVCardSerializer();

                        // Save contacts using the created instances
                        vCardSerializer.saveList("contactsQRcode.vcf", contactsList);

                        // The path to the vCard file
                        String vcardFilePath = "contactsQRcode.vcf";

                        // Read the contents of the vCard file
                        String vcardString = ContactQRcodeSerializer.readFileAsString(vcardFilePath);

                        // The path where the QR code image will be saved
                        String qrCodeImagePath = "QRcode.png";

                        // Encoding charset
                        String charset = "UTF-8";

                        // Creating a map to define the error correction level of the QR code
                        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
                        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

                        // Call the createQR method to create and save the QR code as a PNG file
                        ContactQRcodeSerializer.createQR(vcardString, qrCodeImagePath, charset, hashMap, 200, 200);
                        System.out.println("QR Code Generated!!!");

                        // If QR code generation is successful, add success style to export button
                        exportButton.getStyleClass().add("button-success");

                        // Display QRCode
                        DisplayQRCode.showImage("QRCode.png");

                    } catch (WriterException | IOException ex) {
                        // If there is an exception, print the error message and add error style to
                        // export button
                        System.out.println("Failed to export contacts in QRCode format: " + ex.getMessage());
                        exportButton.getStyleClass().add("button-error");
                    }
                    System.out.println("Exporting as QRCode");
                    break;
            }
        }
    }
}