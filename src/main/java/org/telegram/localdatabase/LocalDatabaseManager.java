/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.telegram.localdatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author n.sokolsky
 */
public class LocalDatabaseManager {
    
    private static volatile LocalDatabaseManager instance;
    private ArrayList<String> usersList = new ArrayList();
    private HashMap<String, Integer> userPoints = new HashMap();

    /**
     * Private constructor (due to Singleton)
     */
    private LocalDatabaseManager() {
        
    }
    
    public void addUser (String username){
        if (!usersList.contains(username)){
            usersList.add(username);
        }
    }
    
    public boolean checkIfUserExists (String username){
        return usersList.contains(username);
    }
    
    public int checkPoints(String username){
        if (!userPoints.containsKey(username)){
            return 0;
        }
        else {
            return userPoints.get(username);
        }
    }
    
    public void addPoints (String username, int points){
        if (!userPoints.containsKey(username)){
            userPoints.put(username, points);
        }
        else {
            userPoints.replace(username, checkPoints(username) + points);
        }
    }

    /**
     * Get Singleton instance
     *
     * @return instance of the class
     */
    public static LocalDatabaseManager getInstance() {
        final LocalDatabaseManager currentInstance;
        if (instance == null) {
            synchronized (LocalDatabaseManager.class) {
                if (instance == null) {
                    instance = new LocalDatabaseManager();
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }
        return currentInstance;
    }
    
}
