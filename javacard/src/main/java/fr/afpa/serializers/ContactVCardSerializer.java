package fr.afpa.serializers;

import ezvcard.VCard;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import fr.afpa.models.Contact;
import javafx.collections.ObservableList;
import ezvcard.property.Email;
import ezvcard.property.Address;
import ezvcard.property.Gender;
import ezvcard.property.Birthday;
import ezvcard.io.text.VCardWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


public class ContactVCardSerializer implements Serializer<Contact> {

    //ressource: //https://code.google.com/archive/p/ez-vcard/wikis/Examples.wiki

    /**
     * Converts a Contact object to a VCard object.
     * 
     * @param contact the Contact object to convert
     * @return the resulting VCard object
     */
    private VCard contactToVCard(Contact contact) {
        VCard vcard = new VCard();

        // Set the structured name (first name and last name)
        StructuredName n = new StructuredName();
        n.setFamily(contact.getLastName());
        n.setGiven(contact.getFirstName());
        vcard.setStructuredName(n);

        // Add the personal phone number
        Telephone personalPhone = new Telephone(contact.getPersonalPhoneNumber());
        vcard.addTelephoneNumber(personalPhone);

        // Add the professional phone number if it's not empty
        if (contact.getProfessionalPhoneNumber() != null && !contact.getProfessionalPhoneNumber().isEmpty()) {
            Telephone professionalPhone = new Telephone(contact.getProfessionalPhoneNumber());
            vcard.addTelephoneNumber(professionalPhone);
        }

        // Add the address
        Address adr = new Address();
        adr.setStreetAddress(contact.getAddress());
        vcard.addAddress(adr);

        // Add the email address
        Email email = new Email(contact.getEmailAddress());
        vcard.addEmail(email);

        // Add the gender
        Gender gender = new Gender(contact.getGender());
        vcard.setGender(gender);

        // Add the birthday if it's not null
        if (contact.getBirthDate() != null) {
            LocalDate birthDate = contact.getBirthDate();
            Date date = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Birthday birthday = new Birthday(date);
            vcard.setBirthday(birthday);
        }

        // Add the nickname if it's not empty
        if (contact.getNickname() != null && !contact.getNickname().isEmpty()) {
            vcard.setNickname(contact.getNickname());
        }

        // Add the LinkedIn URL if it's not empty
        if (contact.getLinkedinLink() != null && !contact.getLinkedinLink().isEmpty()) {
            vcard.addUrl(contact.getLinkedinLink());
        }

        // Add the GitHub/GitLab URL if it's not empty
        if (contact.getGithubGitlabLink() != null && !contact.getGithubGitlabLink().isEmpty()) {
            vcard.addUrl(contact.getGithubGitlabLink());
        }

        return vcard;
    }

    /**
     * Saves a single Contact object to a specified file as a VCard.
     * 
     * @param filePath the path of the file where the VCard should be saved
     * @param contact the Contact object to save
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void save(String filePath, Contact contact) throws IOException {
        VCard vcard = contactToVCard(contact);
        try (FileOutputStream fos = new FileOutputStream(filePath);
             VCardWriter writer = new VCardWriter(fos, ezvcard.VCardVersion.V4_0)) {
            writer.write(vcard);
        }
    }

    /**
     * Saves a list of Contact objects to a specified file as VCards.
     * 
     * @param filePath the path of the file where the VCards should be saved
     * @param contacts the list of Contact objects to save
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void saveList(String filePath, ObservableList<Contact> contacts) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             VCardWriter writer = new VCardWriter(fos, ezvcard.VCardVersion.V4_0)) {
            for (Contact contact : contacts) {
                VCard vcard = contactToVCard(contact);
                writer.write(vcard);
            }
        }
    }
}