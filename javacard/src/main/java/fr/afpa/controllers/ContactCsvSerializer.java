package fr.afpa.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import fr.afpa.models.Contact;
import fr.afpa.serializers.Serializer;

public class ContactCsvSerializer implements Serializer<Contact> {

    //ressource: https://www.geeksforgeeks.org/convert-arraylist-to-comma-separated-string-in-java/
    @Override
    public void saveList(String filePath, ArrayList<Contact> contacts) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.append("First Name,Last Name,Gender,Birthday,Pseudo,Address,Number,Pro Number,Email,Linkedin,GitHub\n");
            
            // Write data
            for (Contact contact : contacts) {
                writer.append(contact.getFirstName()).append(',')
                      .append(contact.getLastName()).append(',')
                      .append(contact.getGender()).append(',')
                      .append(contact.getBirthDate() != null ? contact.getBirthDate().toString() : "").append(',')
                      .append(contact.getNickname()).append(',')
                      .append(contact.getAddress()).append(',')
                      .append(contact.getPersonalPhoneNumber()).append(',')
                      .append(contact.getProfessionalPhoneNumber()).append(',')
                      .append(contact.getEmailAddress()).append(',')
                      .append(contact.getLinkedinLink()).append(',')
                      .append(contact.getGithubGitlabLink()).append('\n');
            }
        }
    }

    @Override
    public void save(String filePath, Contact contact) throws IOException {
        // Not needed for this case
    }
}