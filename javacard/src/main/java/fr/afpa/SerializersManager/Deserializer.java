package fr.afpa.SerializersManager;

import java.util.ArrayList;

/**
 * An interface defining methods for deserializing objects.
 *
 * @param <T> the type of objects to be deserialized
 */
public interface Deserializer<T> {
    /**
     * Loads a list of objects from a specified file.
     *
     * @param filePath the path of the file to load the list from
     * @return the list of objects loaded from the file
     */
    ArrayList<T> loadList(String filePath);

    /**
     * Loads a single object from a specified file.
     *
     * @param filePath the path of the file to load the object from
     * @return the object loaded from the file
     */
    T load(String filePath);
}