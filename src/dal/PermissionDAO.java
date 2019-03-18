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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Permission;

/**
 *
 * @author Nam
 */
public class PermissionDAO extends BaseDAO<Permission>{

    @Override
    public int insert(Permission model) {
        try {
            String sql = "INSERT INTO Permissions (ObjectID, ActionID)\n" +
                        "VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getObjectID());
            statement.setInt(2, model.getActionID());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return 1;
    }
    
    public int getCurrentID() {
        try{
            String sql = "SELECT IDENT_CURRENT('Permissions') as MaxID";
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
    
    public ArrayList<Permission> all() {
        ArrayList<Permission> permissions = new ArrayList<>();
        try{
            String sql = "SELECT ActionID, ObjectID FROM Permissions";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Permission permission = new Permission();
                permission.setActionID(rs.getInt("ActionID"));
                permission.setObjectID(rs.getInt("ObjectID"));
                permissions.add(permission);
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error get all of Action" + err);
        }
        return permissions;
    }
    
}
