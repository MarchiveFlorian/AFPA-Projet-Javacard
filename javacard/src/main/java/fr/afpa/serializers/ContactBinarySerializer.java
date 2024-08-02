package fr.afpa.serializers;


import java.io.*;
import java.util.ArrayList;

import fr.afpa.models.Contact;

public class ContactBinarySerializer implements Serializer<Contact>, Deserializer<Contact> {

    @Override
    public void saveList(String filePath, ArrayList<Contact> contacts) throws IOException {
        ArrayList<Contact.SerializableContact> serializableContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            serializableContacts.add(contact.toSerializableContact());
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(serializableContacts);
        }
    }

    @Override
    public void save(String filePath, Contact contact) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(contact.toSerializableContact());
        }
    }

    @Override
    public ArrayList<Contact> loadList(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            ArrayList<Contact.SerializableContact> serializableContacts = (ArrayList<Contact.SerializableContact>) in.readObject();
            ArrayList<Contact> contacts = new ArrayList<>();
            for (Contact.SerializableContact serializableContact : serializableContacts) {
                contacts.add(serializableContact.toContact());
            }
            return contacts;
        }
    }

    @Override
    public Contact load(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            Contact.SerializableContact serializableContact = (Contact.SerializableContact) in.readObject();
            return serializableContact.toContact();
        }
    }
}