package fr.afpa.serializers;

import java.io.*;
import java.util.ArrayList;

import fr.afpa.models.Contact;

/**
 * A class that serializes and deserializes Contact objects to/from binary format.
 */
public class ContactBinarySerializer implements Serializer<Contact>, Deserializer<Contact> {
    /**
     * Saves a list of Contact objects to a binary file.
     *
     * @param filePath the path of the binary file
     * @param contacts the list of Contact objects to save
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void saveList(String filePath, ArrayList<Contact> contacts) throws IOException { 
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(contacts);
        }
    }

    /**
     * Saves a single Contact object to a binary file.
     *
     * @param filePath the path of the binary file
     * @param contact the Contact object to save
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void save(String filePath, Contact contact) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(contact);
        }
    }

    /**
     * Loads a list of Contact objects from a binary file.
     *
     * @param filePath the path of the binary file
     * @return the list of Contact objects loaded from the file
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    @Override
    public ArrayList<Contact> loadList(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ArrayList<Contact>) in.readObject();
        }
    }

    /**
     * Loads a single Contact object from a binary file.
     *
     * @param filePath the path of the binary file
     * @return the Contact object loaded from the file
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    @Override
    public Contact load(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Contact) in.readObject();
        }
    }
}