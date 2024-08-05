package fr.afpa.models; 

import java.io.Serializable;
import java.time.LocalDate;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty gender;
    private final ObjectProperty<LocalDate> birthDate;
    private final StringProperty pseudo;
    private final StringProperty address;
    private final StringProperty personalPhoneNumber;
    private final StringProperty professionalPhoneNumber;
    private final StringProperty emailAddress;
    private final StringProperty linkedinLink;
    private final StringProperty githubGitlabLink;

    // Constructor for required attributes
    public Contact(String firstName, String lastName, String gender, LocalDate birthDate, String pseudo, String address, String personalPhoneNumber, String professionalPhoneNumber,  String emailAddress, String linkedinLink, String githubGitlabLink) {

        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.gender = new SimpleStringProperty(gender);
        this.address = new SimpleStringProperty(address);
        this.personalPhoneNumber = new SimpleStringProperty(personalPhoneNumber);
        this.emailAddress = new SimpleStringProperty(emailAddress);

        this.birthDate = new SimpleObjectProperty<>(birthDate);
        this.pseudo = new SimpleStringProperty(pseudo);
        this.professionalPhoneNumber = new SimpleStringProperty(professionalPhoneNumber);
        this.linkedinLink = new SimpleStringProperty(linkedinLink);
        this.githubGitlabLink = new SimpleStringProperty(githubGitlabLink);
    }

    // Getters and setters for optional attributes
    public LocalDate getBirthDate() {
        return birthDate.get();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate.set(birthDate);
    }

    public ObjectProperty<LocalDate> birthDateProperty() {
        return birthDate;
    }

    public String getNickname() {
        return pseudo.get();
    }

    public void setNickname(String pseudo) {
        this.pseudo.set(pseudo);
    }

    public StringProperty pseudoProperty() {
        return pseudo;
    }

    public String getProfessionalPhoneNumber() {
        return professionalPhoneNumber.get();
    }

    public void setProfessionalPhoneNumber(String professionalPhoneNumber) {
        this.professionalPhoneNumber.set(professionalPhoneNumber);
    }

    public StringProperty professionalPhoneNumberProperty() {
        return professionalPhoneNumber;
    }

    public String getLinkedinLink() {
        return linkedinLink.get();
    }

    public void setLinkedinLink(String linkedinLink) {
        this.linkedinLink.set(linkedinLink);
    }

    public StringProperty linkedinLinkProperty() {
        return linkedinLink;
    }

    public String getGithubGitlabLink() {
        return githubGitlabLink.get();
    }

    public void setGithubGitlabLink(String githubGitlabLink) {
        this.githubGitlabLink.set(githubGitlabLink);
    }

    public StringProperty githubGitlabLinkProperty() {
        return githubGitlabLink;
    }

    // Getters and setters for required attributes
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getPersonalPhoneNumber() {
        return personalPhoneNumber.get();
    }

    public void setPersonalPhoneNumber(String personalPhoneNumber) {
        this.personalPhoneNumber.set(personalPhoneNumber);
    }

    public StringProperty personalPhoneNumberProperty() {
        return personalPhoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }

    public StringProperty emailAddressProperty() {
        return emailAddress;
    }

    // Method to display contact information
    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName.get() + '\'' +
                ", lastName='" + lastName.get() + '\'' +
                ", gender='" + gender.get() + '\'' +
                ", birthDate='" + birthDate.get() + '\'' +
                ", pseudo='" + pseudo.get() + '\'' +
                ", address='" + address.get() + '\'' +
                ", personalPhoneNumber='" + personalPhoneNumber.get() + '\'' +
                ", professionalPhoneNumber='" + professionalPhoneNumber.get() + '\'' +
                ", emailAddress='" + emailAddress.get() + '\'' +
                ", linkedinLink='" + linkedinLink.get() + '\'' +
                ", githubGitlabLink='" + githubGitlabLink.get() + '\'' +
                '}';
    }
}