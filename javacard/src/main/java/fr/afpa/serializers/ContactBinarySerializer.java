package fr.afpa.serializers;

import java.io.*;
import java.util.ArrayList;
import fr.afpa.models.Contact;

public class ContactBinarySerializer implements Serializer<Contact>, Deserializer<Contact> {

    /**
     * Saves a list of Contact objects to a specified file.
     * 
     * @param filePath the path of the file where the contacts should be saved
     * @param contacts the list of Contact objects to save
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void saveList(String filePath, ArrayList<Contact> contacts) throws IOException {
        // Convert each Contact object to its serializable form
        ArrayList<Contact.SerializableContact> serializableContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            serializableContacts.add(contact.toSerializableContact());
        }
        // Write the list of SerializableContact objects to the specified file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(serializableContacts);
        }
    }

    /**
     * Saves a single Contact object to a specified file.
     * 
     * @param filePath the path of the file where the contact should be saved
     * @param contact the Contact object to save
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void save(String filePath, Contact contact) throws IOException {
        // Write the SerializableContact object to the specified file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(contact.toSerializableContact());
        }
    }

    /**
     * Loads a list of Contact objects from a specified file.
     * 
     * @param filePath the path of the file from which the contacts should be loaded
     * @return the list of loaded Contact objects
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    @Override
    public ArrayList<Contact> loadList(String filePath) throws IOException, ClassNotFoundException {
        // Read the list of SerializableContact objects from the specified file
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            ArrayList<Contact.SerializableContact> serializableContacts = (ArrayList<Contact.SerializableContact>) in.readObject();
            // Convert each SerializableContact object back to a Contact object
            ArrayList<Contact> contacts = new ArrayList<>();
            for (Contact.SerializableContact serializableContact : serializableContacts) {
                contacts.add(serializableContact.toContact());
            }
            return contacts;
        }
    }

    /**
     * Loads a single Contact object from a specified file.
     * 
     * @param filePath the path of the file from which the contact should be loaded
     * @return the loaded Contact object
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    @Override
    public Contact load(String filePath) throws IOException, ClassNotFoundException {
        // Read the SerializableContact object from the specified file
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            Contact.SerializableContact serializableContact = (Contact.SerializableContact) in.readObject();
            return serializableContact.toContact();
        }
    }

    /**
     * Clears the contents of the specified file.
     * 
     * @param fileName the path of the file to clear
     * @throws IOException if an I/O error occurs
     */
    public void clearFile(String fileName) throws IOException {
        // Opening the file in write mode without adding any content clears it
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            // The file is cleared by this operation
        }
    }
}