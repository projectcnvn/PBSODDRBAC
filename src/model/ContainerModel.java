/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Nam
 */
public class ContainerModel {
    private int containerID;
    private String containerName;
    private ArrayList<ObjectModel> objects;

    public int getContainerID() {
        return containerID;
    }

    public void setContainerID(int containerID) {
        this.containerID = containerID;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public ArrayList<ObjectModel> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<ObjectModel> objects) {
        this.objects = objects;
    }
    
    
    
}
