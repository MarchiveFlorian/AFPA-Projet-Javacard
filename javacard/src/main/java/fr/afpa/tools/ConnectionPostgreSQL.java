package fr.afpa.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgreSQL {
    /**
     * URL de connexion
     */
    private static String url = "jdbc:postgresql://localhost:5432/jdbc";
    /**
     * Nom du user
     */
    private static String user = "postgres";
    /**
     * Mot de passe du user
     */
    private static String passwd = "B@nLgU4qz*9?D~3n83";
    /**
     * Objet Connexion
     */
    private static Connection connect;
    
    /**
     * Méthode qui va nous retourner notre instance
     * et la créer si elle n'existe pas...
     * @return
     */
    public static Connection getInstance(){
        if(connect == null){
            try {
                connect = DriverManager.getConnection(url, user, passwd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }        
        return connect;    
    }

    public static void close(){
        try {
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connect = null;
    }
}
