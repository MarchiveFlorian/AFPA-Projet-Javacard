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
        // if (lastName == null || firstName == null || gender == null || address == null || personalPhoneNumber == null || emailAddress == null) {
        //     throw new IllegalArgumentException("Required fields cannot be null");
        // }

        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.gender = new SimpleStringProperty(gender);
        this.address = new SimpleStringProperty(address);
        this.personalPhoneNumber = new SimpleStringProperty(personalPhoneNumber);
        this.emailAddress = new SimpleStringProperty(emailAddress);
        setEmailAddress(emailAddress);  // Use setter to validate email

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
        // if (linkedinLink != null && !Pattern.matches("^(https?:\\/\\/)?(www\\.)?(linkedin)\\.com\\/[a-zA-Z0-9_-]+\\/?$", linkedinLink)) {
        //     throw new IllegalArgumentException("Invalid Linkedin link");
        // }
        this.linkedinLink.set(linkedinLink);
    }

    public StringProperty linkedinLinkProperty() {
        return linkedinLink;
    }

    public String getGithubGitlabLink() {
        return githubGitlabLink.get();
    }

    public void setGithubGitlabLink(String githubGitlabLink) {
        // if (githubGitlabLink != null && !Pattern.matches("^(https?:\\/\\/)?(www\\.)?(github|gitlab)\\.com\\/[a-zA-Z0-9_-]+\\/?$", githubGitlabLink)) {
        //     throw new IllegalArgumentException("Invalid Github or Gitlab link");
        // }
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
        // if (lastName == null) {
        //     throw new IllegalArgumentException("Last name cannot be null");
        // }
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        // if (firstName == null) {
        //     throw new IllegalArgumentException("First name cannot be null");
        // }
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        // if (gender == null) {
        //     throw new IllegalArgumentException("Gender cannot be null");
        // }
        this.gender.set(gender);
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        // if (address == null) {
        //     throw new IllegalArgumentException("Address cannot be null");
        // }
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getPersonalPhoneNumber() {
        return personalPhoneNumber.get();
    }

    public void setPersonalPhoneNumber(String personalPhoneNumber) {
        // if (personalPhoneNumber == null) {
        //     throw new IllegalArgumentException("Personal phone number cannot be null");
        // }
        this.personalPhoneNumber.set(personalPhoneNumber);
    }

    public StringProperty personalPhoneNumberProperty() {
        return personalPhoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public void setEmailAddress(String emailAddress) {
        // if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", emailAddress)) {
        //     throw new IllegalArgumentException("Invalid email address");
        // }
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

    public static class SerializableContact implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String firstName;
        private final String lastName;
        private final String gender;
        private final LocalDate birthDate;
        private final String pseudo;
        private final String address;
        private final String personalPhoneNumber;
        private final String professionalPhoneNumber;
        private final String emailAddress;
        private final String linkedinLink;
        private final String githubGitlabLink;

        public SerializableContact(Contact contact) {
            this.firstName = contact.getFirstName();
            this.lastName = contact.getLastName();
            this.gender = contact.getGender();
            this.birthDate = contact.getBirthDate();
            this.pseudo = contact.getNickname();
            this.address = contact.getAddress();
            this.personalPhoneNumber = contact.getPersonalPhoneNumber();
            this.professionalPhoneNumber = contact.getProfessionalPhoneNumber();
            this.emailAddress = contact.getEmailAddress();
            this.linkedinLink = contact.getLinkedinLink();
            this.githubGitlabLink = contact.getGithubGitlabLink();
        }

        public Contact toContact() {
            return new Contact(
                firstName, lastName, gender, birthDate, pseudo, address, personalPhoneNumber, 
                professionalPhoneNumber, emailAddress, linkedinLink, githubGitlabLink
            );
        }
    }

    public SerializableContact toSerializableContact() {
        return new SerializableContact(this);
    }
}