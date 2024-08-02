package fr.afpa.serializers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;

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
     * @throws IOException if an I/O error occurs
     */
    void saveList(String filePath, ObservableList<T> objectsToSave) throws IOException;

    /**
     * Saves a single object to a specified file.
     *
     * @param filePath the path of the file where the object should be saved
     * @param object the object to save
     * @throws IOException if an I/O error occurs
     */
    void save(String filePath, T object) throws IOException;
}