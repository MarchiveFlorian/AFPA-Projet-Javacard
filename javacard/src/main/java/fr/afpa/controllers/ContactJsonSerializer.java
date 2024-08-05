package fr.afpa.controllers;

import fr.afpa.models.Contact;
import fr.afpa.serializers.Serializer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContactJsonSerializer implements Serializer<Contact> {

    /**
     * Converts a Contact object to a JSONObject.
     * @param contact the Contact object to convert
     * @return the resulting JSONObject
     */
    private JSONArray contactToJson(Contact contact) {
        JSONArray jsonArray = new JSONArray();
    
        // Créez un objet JSON pour chaque paire clé-valeur
        JSONObject firstNameObject = new JSONObject();
        firstNameObject.put("Key", "FirstName");
        firstNameObject.put("Value", contact.getFirstName());
        jsonArray.add(firstNameObject);
    
        JSONObject lastNameObject = new JSONObject();
        lastNameObject.put("Key", "LastName");
        lastNameObject.put("Value", contact.getLastName());
        jsonArray.add(lastNameObject);
    
        JSONObject genderObject = new JSONObject();
        genderObject.put("Key", "Gender");
        genderObject.put("Value", contact.getGender());
        jsonArray.add(genderObject);
    
        if (contact.getBirthDate() != null) {
            LocalDate birthDate = contact.getBirthDate();
            String birthDateString = birthDate.toString(); // Format ISO 8601
            JSONObject birthDateObject = new JSONObject();
            birthDateObject.put("Key", "BirthDate");
            birthDateObject.put("Value", birthDateString);
            jsonArray.add(birthDateObject);
        }
    
        JSONObject pseudoObject = new JSONObject();
        pseudoObject.put("Key", "Pseudo");
        pseudoObject.put("Value", contact.getNickname());
        jsonArray.add(pseudoObject);
    
        JSONObject addressObject = new JSONObject();
        addressObject.put("Key", "Address");
        addressObject.put("Value", contact.getAddress());
        jsonArray.add(addressObject);
    
        JSONObject personalPhoneNumberObject = new JSONObject();
        personalPhoneNumberObject.put("Key", "PersonalPhoneNumber");
        personalPhoneNumberObject.put("Value", contact.getPersonalPhoneNumber());
        jsonArray.add(personalPhoneNumberObject);
    
        JSONObject professionalPhoneNumberObject = new JSONObject();
        professionalPhoneNumberObject.put("Key", "ProfessionalPhoneNumber");
        professionalPhoneNumberObject.put("Value", contact.getProfessionalPhoneNumber());
        jsonArray.add(professionalPhoneNumberObject);
    
        JSONObject emailAddressObject = new JSONObject();
        emailAddressObject.put("Key", "EmailAddress");
        emailAddressObject.put("Value", contact.getEmailAddress());
        jsonArray.add(emailAddressObject);
    
        JSONObject linkedinLinkObject = new JSONObject();
        linkedinLinkObject.put("Key", "LinkedinLink");
        linkedinLinkObject.put("Value", contact.getLinkedinLink());
        jsonArray.add(linkedinLinkObject);
    
        JSONObject githubGitlabLinkObject = new JSONObject();
        githubGitlabLinkObject.put("Key", "GithubGitlabLink");
        githubGitlabLinkObject.put("Value", contact.getGithubGitlabLink());
        jsonArray.add(githubGitlabLinkObject);
    
        return jsonArray;
    }

    /**
     * Saves a single Contact object to a specified file as JSON.
     * 
     * @param filePath the path of the file where the JSON should be saved
     * @param contact the Contact object to save
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void save(String filePath, Contact contact) throws IOException {
        JSONArray jsonArray = contactToJson(contact);
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toJSONString());
        }
    }

    /**
     * Saves a list of Contact objects to a specified file as JSON.
     * 
     * @param filePath the path of the file where the JSON should be saved
     * @param contacts the list of Contact objects to save
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void saveList(String filePath, ArrayList<Contact> contacts) throws IOException {
        JSONArray jsonArray = new JSONArray();

        for (Contact contact : contacts) {
            JSONArray contactJsonArray = contactToJson(contact);
            jsonArray.add(contactJsonArray);
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toJSONString());
        }
    }
}