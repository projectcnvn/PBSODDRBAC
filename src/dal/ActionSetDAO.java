/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Action;
import model.ActionSet;

/**
 *
 * @author Nam
 */
public class ActionSetDAO extends BaseDAO<ActionSet>{

    @Override
    public int insert(ActionSet model) {
        try{
            String sql = "INSERT INTO ActionSets (ActionSetName)\n"
                    + " VALUES (?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, model.getActionSetName());
            statement.execute();
            
            for (Action action : model.getActions()) {
                insertToSetDetail(action);
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error insert to ActionSet" + err);
            return 0;
        }
        return 1;
    }
    
    public int insertToSetDetail(Action model) {
        try{
            String sql = "INSERT INTO ActionSetDetails (ActionID, ActionSetID)\n"
                    + " VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getActionID());
            statement.setInt(2, getCurrentID());
            statement.execute();
        }
        catch(SQLException err){
            System.out.println("Ops! Error insert to insertToContainerDetail" + err);
            return 0;
        }
        return 1;
    }
    
    public int getCurrentID() {
        try{
            String sql = "SELECT IDENT_CURRENT('ActionSets') as MaxID";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                return rs.getInt("MaxID");
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error getCurrentID of ActionSets" + err);
            return 0;
        }
        return 0;
    }
    
    public ArrayList<ActionSet> all() {
        ArrayList<ActionSet> sets = new ArrayList<>();
        try{
            String sql = "SELECT ActionSetID, ActionSetName FROM ActionSets";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                ActionSet set = new ActionSet();
                set.setActionSetID(rs.getInt("ActionSetID"));
                set.setActionSetName(rs.getString("ActionSetName"));
                sets.add(set);
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error get all of ContainerModel" + err);
        }
        return sets;
    }
    public ArrayList<String> getAllActionNameInSet(String name) {
        ArrayList<String> actionName = new ArrayList<>();
        try{
            String sql = "SELECT a.ActionName \n" +
                "FROM ActionSetDetails as asd, Actions as a, ActionSets as as1\n" +
                "WHERE as1.ActionSetID = asd.ActionSetID AND asd.ActionID = a.ActionID AND as1.ActionSetName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                actionName.add(rs.getString("ActionName"));
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error get all of ContainerModel" + err);
        }
        return actionName;
    }
    
}
