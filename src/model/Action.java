/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Nam
 */
public class Action {
    private int actionID;
    private String actionName;
    private Action conflictedAction;

    public Action getConflictedAction() {
        return conflictedAction;
    }

    public void setConflictedAction(Action conflictedAction) {
        this.conflictedAction = conflictedAction;
    }

    public int getActionID() {
        return actionID;
    }

    public void setActionID(int actionID) {
        this.actionID = actionID;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    
    
    
}
