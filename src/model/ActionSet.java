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
public class ActionSet {
    private int actionSetID;
    private String actionSetName;
    private ArrayList<Action> actions;

    public int getActionSetID() {
        return actionSetID;
    }

    public void setActionSetID(int actionSetID) {
        this.actionSetID = actionSetID;
    }

    public String getActionSetName() {
        return actionSetName;
    }

    public void setActionSetName(String actionSetName) {
        this.actionSetName = actionSetName;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }
    
    
}
