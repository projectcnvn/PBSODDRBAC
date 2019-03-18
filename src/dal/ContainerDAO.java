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
import model.ContainerModel;
import model.ObjectModel;

/**
 *
 * @author Nam
 */
public class ContainerDAO extends BaseDAO<ContainerModel>{

    @Override
    public int insert(ContainerModel model) {
        try{
            String sql = "INSERT INTO ObjectContainers (ContainerName)\n"
                    + " VALUES (?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, model.getContainerName());
            statement.execute();
            
            for (ObjectModel object : model.getObjects()) {
                insertToContainerDetail(object);
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error insert to ObjectContainer" + err);
            return 0;
        }
        return 1;
    }
    
    public int insertToContainerDetail(ObjectModel model) {
        try{
            String sql = "INSERT INTO ObjectContainerDetails (ObjectID, ContainerID)\n"
                    + " VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getObjectID());
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
            String sql = "SELECT IDENT_CURRENT('ObjectContainers') as MaxID";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                return rs.getInt("MaxID");
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error getCurrentID of ObjectContainer" + err);
            return 0;
        }
        return 0;
    }
    
    public ArrayList<ContainerModel> all() {
        ArrayList<ContainerModel> containers = new ArrayList<>();
        try{
            String sql = "SELECT ContainerID, ContainerName FROM ObjectContainers";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                ContainerModel container = new ContainerModel();
                container.setContainerID(rs.getInt("ContainerID"));
                container.setContainerName(rs.getString("ContainerName"));
                containers.add(container);
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error get all of ContainerModel" + err);
        }
        return containers;
    }
    public ArrayList<String> getAllObjectNameInContainer(String name) {
        ArrayList<String> objectName = new ArrayList<>();
        try{
            String sql = "SELECT o.ObjectName \n" +
                "FROM ObjectContainerDetails as ocd, Objects as o, ObjectContainers as oc\n" +
                "WHERE oc.ContainerID = ocd.ContainerID AND ocd.ObjectID = o.ObjectID AND oc.ContainerName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                objectName.add(rs.getString("ObjectName"));
            }
        }
        catch(SQLException err){
            System.out.println("Ops! Error get all of ContainerModel" + err);
        }
        return objectName;
    }
}
