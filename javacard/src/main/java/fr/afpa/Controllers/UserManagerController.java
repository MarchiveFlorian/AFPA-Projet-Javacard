package fr.afpa.Controllers;

import java.io.IOException;
import java.time.LocalDate;

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
        comboBoxSelectFormat.getItems().addAll("vCard","JSON","CSV","QRCode");
    }

    @FXML
    private DatePicker datePickerBirthday;

    @FXML
    private CheckBox checkBoxSelectAll;

    @FXML
    private TableView tableView4columns;

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

        contacts.add(new Contact("Boivin", "Chloé", "Female", "19/07/1995", "bulo", "Bordeaux", "0604138029", "07",
                "https://www.linkedin.com/in/chloe-boivin/", "https://github.com/bu-lo"));
        contacts.add(new Contact("Marchive", "Florian", "Male", "28/03/1995", "marchive", "Bordeaux", "0613206966",
                "07", "https://www.linkedin.com/in/florianmarchive/", "https://github.com/MarchiveFlorian"));

        // *** + INITIALIZE WITH CONTACTS ALREADY IN BINARY **********

        // ADD NEW LINE
        tableView4columns.setItems(contacts);

        columnFirstName.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
        columnLastName.setCellValueFactory(cellData -> cellData.getValue().getLastName());
        columnNumber.setCellValueFactory(cellData -> cellData.getValue().getPersonalPhoneNumber());
        columnMail.setCellValueFactory(cellData -> cellData.getValue().getEmailAddress());

    }

    // SAVE BUTTON
    @FXML
    private void save(ActionEvent e) {

        String fName = textFieldFirstName.getText();
        String lName = textFieldLastName.getText();
        String gender = comboBoxGender.getValue(); // ---------------
        LocalDate bDay = datePickerBirthday.getValue(); // --------------------
        String pseudo = textFieldPseudo.getText();
        String add = textFieldAddress.getText();
        String tNum = textFieldNumber.getText();
        String tPNum = textFieldProNumber.getText();
        String mail = textFieldMail.getText();
        String linkd = textFieldLinkedin.getText();
        String gHub = textFieldGitHub.getText();

        if (!fName.isEmpty() && !lName.isEmpty() && !gender.isEmpty() && bDay != null && !tNum.isEmpty()
                && !mail.isEmpty() && !linkd.isEmpty()) { // ARE NOT EMPTY
            contacts.add(new Contact(fName, lName, gender, bDay, pseudo, add, tNum, tPNum, mail, linkd,gHub));

            // textFieldFirstName.clear();
            // textFieldLastName.clear();
            // comboBoxGender.clear(); // ------------ => REMETTRE ETAT INITIAL
            // datePickerBirthday.clear(); // --------------
            // textFieldPseudo.clear();
            // textFieldAddress.clear();
            // textFieldNumber.clear();
            // textFieldProNumber.clear();
            // textFieldMail.clear();
            // textFieldLinkedin.clear();
            // textFieldGitHub.clear();

        }
    }

    // NEW BUTTON

    @FXML
    private void newC (ActionEvent e)
    {

        // textFieldFirstName.clear();
        // textFieldLastName.clear();
        // comboBoxGender.clear();
        // datePickerBirthday.gclear(); => REMETTRE ETAT INITIAL
        // textFieldPseudo.getclear();
        // textFieldAddress.getclear();
        // textFieldNumber.getclear();
        // textFieldProNumber.getclear();
        // textFieldMail.getclear();
        // textFieldLinkedin.getclear();
        // textFieldGitHub.getclear();

        contacts.add(new Contact("", "", "", "")); // NEW LINE IN TAB

    }

    // DELETE BUTTON

    @FXML
    void delete(ActionEvent e) {
        textFieldFirstName.clear();
        textFieldLastName.clear();
        comboBoxGender.clear();
        datePickerBirthday.clear(); 
        textFieldPseudo.clear();
        textFieldAddress.clear();
        textFieldNumber.clear();
        textFieldProNumber.clear();
        textFieldMail.clear();
        textFieldLinkedin.clear();
        textFieldGitHub.clear();

        // + FORMULAIRE ETAT INITIAL

        contacts.remove(contact<Contact>); // REMOVE THE LINE IN TAB
    }

    // SELECT ALL ARRAY

    @FXML
    void selectAllArray(ActionEvent e) {
        // ****************************
    }

    // EXPORT BUTTON

    @FXML void export (ActionEvent e){
        if (comboBoxSelectFormat.isEmpty()){
            System.out.println("Please select a Format");
        } else {
            // EXPORT ACTIONS ****************
        }
    }


}