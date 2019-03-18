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

/**
 *
 * @author Nam
 */
public class ActionDAO extends BaseDAO<Action>{

    @Override
    public int insert(Action model) {
        try{
            String sql = "INSERT INTO Actions (ActionName)\n"
                    + " VALUES (?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, model.getActionName());
            statement.execute();
            
            if (model.getConflictedAction() != null) {
                sql = "INSERT INTO ConflictedActions (ActionID1, ActionID2)\n"
                    + " VALUES (?, ?);";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, getCurrentID());
                statement.setInt(2, model.getConflictedAction().getActionID());
                statement.execute();
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error insert to Action" + err);
            return 0;
        }
        return 1;
    }
    
    public int getCurrentID() {
        try{
            String sql = "SELECT IDENT_CURRENT('Actions') as MaxID";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                return rs.getInt("MaxID");
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error getCurrentID of Action" + err);
            return 0;
        }
        return 0;
    }
    
    public Action getByName(String name) {
        try{
            String sql = "SELECT ActionId, ActionName FROM Actions\n"
                    + "WHERE ActionName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Action action = new Action();
                action.setActionID(rs.getInt("ActionID"));
                action.setActionName(rs.getString("ActionName"));
                return action;
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error getByName of Action" + err);
        }
        return null;
    }
    
    public Action getByID(int id) {
        try{
            String sql = "SELECT ActionId, ActionName FROM Actions\n"
                    + "WHERE ActionID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Action action = new Action();
                action.setActionID(rs.getInt("ActionID"));
                action.setActionName(rs.getString("ActionName"));
                return action;
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error getByName of Action" + err);
        }
        return null;
    }
    
    public ArrayList<Action> all() {
        ArrayList<Action> actions = new ArrayList<>();
        try{
            String sql = "SELECT ActionId, ActionName FROM Actions";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Action action = new Action();
                action.setActionID(rs.getInt("ActionID"));
                action.setActionName(rs.getString("ActionName"));
                actions.add(action);
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error get all of Action" + err);
        }
        return actions;
    }
    public boolean isConflicted(Action act1, Action act2) {
        try{
            String sql = "SELECT ActionID1, ActionID2 FROM ConflictedActions\n"
                    + "WHERE (ActionID1 = ? AND ActionID2 = ?) OR (ActionID1 = ? AND ActionID2 = ?) ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, act1.getActionID());
            statement.setInt(2, act2.getActionID());
            statement.setInt(3, act2.getActionID());
            statement.setInt(4, act1.getActionID());
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                return true;
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error get all of Action" + err);
        }
        return false;
    }
}
