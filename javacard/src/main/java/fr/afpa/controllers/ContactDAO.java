package fr.afpa.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.afpa.models.Contact;
import fr.afpa.tools.ConnectionPostgreSQL;

public class ContactDAO extends DAO<Contact>{

    public ArrayList<Contact> getAll() {
        ArrayList<Contact> contacts = new ArrayList<>();

        try {
            Connection con = ConnectionPostgreSQL.getInstance();
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM contact");

            while (result.next()) {
                Contact contact = new Contact(
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("gender"),
                        result.getDate("birth_date").toLocalDate(),
                        result.getString("pseudo"),
                        result.getString("address"),
                        result.getString("personal_phone_number"),
                        result.getString("professionnal_phone_number"),
                        result.getString("email"),
                        result.getString("linkedIn_link"),
                        result.getString("git_link"));
                contacts.add(contact);
            }

            result.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contacts;
    }

    public Contact getById(int id) {
        Contact contact = null;

        try {
            Connection con = ConnectionPostgreSQL.getInstance();
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM contact WHERE id = " + id;
            ResultSet result = stmt.executeQuery(query);

            if (result.next()) {
                contact = new Contact(
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("gender"),
                        result.getDate("birth_date").toLocalDate(),
                        result.getString("pseudo"),
                        result.getString("address"),
                        result.getString("personal_phone_number"),
                        result.getString("professionnal_phone_number"),
                        result.getString("email"),
                        result.getString("linkedIn_link"),
                        result.getString("git_link"));
            }

            result.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contact;
    }

    public boolean deleteById(int id) {
        boolean isDeleted = false;

        try {
            Connection con = ConnectionPostgreSQL.getInstance();
            Statement stmt = con.createStatement();
            String query = "DELETE FROM contact WHERE id = " + id;
            int rowsAffected = stmt.executeUpdate(query);

            isDeleted = rowsAffected > 0;

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isDeleted;
    }

    public boolean deleteContactByName(String firstName, String lastName) {
        boolean isDeleted = false;

        try {
            Connection con = ConnectionPostgreSQL.getInstance();
            Statement stmt = con.createStatement();
            String query = "DELETE FROM contact WHERE first_name = '" + firstName + "' AND last_name = '" + lastName
                    + "'";
            int rowsAffected = stmt.executeUpdate(query);

            isDeleted = rowsAffected > 0;

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isDeleted;
    }

    public boolean insert(ArrayList<Contact> contacts) {
        boolean isInserted = false;
    
        try {
            Connection con = ConnectionPostgreSQL.getInstance();
            Statement stmt = con.createStatement();
    
            // Parcours de la liste de contacts
            for (Contact contact : contacts) {
                String query = String.format(
                        "INSERT INTO contact (first_name, last_name, gender, birth_date, pseudo, address, personal_phone_number, professionnal_phone_number, email, linkedIn_link, git_link) "
                                + "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s') "
                                + "ON CONFLICT (first_name, last_name) DO NOTHING",
                        contact.getFirstName(), contact.getLastName(), contact.getGender(), contact.getBirthDate(),
                        contact.getNickname(), contact.getAddress(), contact.getPersonalPhoneNumber(),
                        contact.getProfessionalPhoneNumber(), contact.getEmailAddress(), contact.getLinkedinLink(),
                        contact.getGithubGitlabLink());
    
                // Exécuter l'insertion pour chaque contact
                int rowsAffected = stmt.executeUpdate(query);
                if (rowsAffected > 0) {
                    isInserted = true; // Si au moins un contact est inséré
                }
            }
    
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return isInserted;
    }
    

    public boolean updateById(int id, Contact updatedContact) {
        boolean isUpdated = false;

        try {
            Connection con = ConnectionPostgreSQL.getInstance();
            Statement stmt = con.createStatement();
            String query = String.format(
                    "UPDATE contact SET first_name = '%s', last_name = '%s', gender = '%s', birth_date = '%s', pseudo = '%s', address = '%s', "
                            + "personal_phone_number = '%s', professionnal_phone_number = '%s', email = '%s', linkedIn_link = '%s', git_link = '%s' "
                            + "WHERE id = %d",
                    updatedContact.getFirstName(), updatedContact.getLastName(), updatedContact.getGender(),
                    updatedContact.getBirthDate(), updatedContact.getNickname(), updatedContact.getAddress(),
                    updatedContact.getPersonalPhoneNumber(), updatedContact.getProfessionalPhoneNumber(),
                    updatedContact.getEmailAddress(), updatedContact.getLinkedinLink(), updatedContact.getGithubGitlabLink(), id);

            int rowsAffected = stmt.executeUpdate(query);
            isUpdated = rowsAffected > 0;

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isUpdated;
    }

    public void clearTable() {
        String sql = "TRUNCATE TABLE contact";
        try (Connection con = ConnectionPostgreSQL.getInstance(); // Gestion des ressources
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Table vidée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Échec de la vidange de la table : " + e.getMessage());
        }
    }
    
}
