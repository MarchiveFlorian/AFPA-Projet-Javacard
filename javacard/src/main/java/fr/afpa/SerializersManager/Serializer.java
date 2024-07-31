package fr.afpa.SerializersManager;

import java.util.ArrayList;

/**
 * An interface defining methods for serializing objects.
 *
 * @param <T> the type of objects to be serialized
 */
public interface Serializer<T> {
    /**
     * Saves a list of objects to a specified file.
     *
     * @param filePath the path of the file where the list should be saved
     * @param objectsToSave the list of objects to save
     */
    void saveList(String filePath, ArrayList<T> objectsToSave);

    /**
     * Saves a single object to a specified file.
     *
     * @param filePath the path of the file where the object should be saved
     * @param object the object to save
     */
    void save(String filePath, T object) ;
}