package fr.afpa.controllers;

import java.util.ArrayList;

public abstract class DAO<T> {

    public abstract ArrayList<T> getAll();

    /**
     * Permet de récupérer un objet via son ID
     * @param id
     * @return
     */
    public abstract T getById(int id);
    
    /**
     * Permet de créer une entrée dans la base de données
     * par rapport à un objet
     * @param obj
     */
    public abstract boolean insert(ArrayList<T> obj);
    
    /**
     * Permet de mettre à jour les données d'une entrée dans la base 
     * @param int, obj
     */
    public abstract boolean updateById(int id, T obj);
    
    /**
     * Permet la suppression d'une entrée de la base
     * @param id
     */
    public abstract boolean deleteById(int id);
}
