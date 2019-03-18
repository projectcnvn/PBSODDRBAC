/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author Nam
 */
public class UserDAO extends BaseDAO<User>{

    @Override
    public int insert(User model) {
        try{
            String sql = "INSERT INTO Users (Username, Password, FromDay, ToDay, FromTime, ToTime, IpAddress)\n"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, model.getUsername());
            statement.setString(2, model.getPassword());
            statement.setString(3, model.getFromDay());
            statement.setString(4, model.getToDay());
            statement.setString(5, model.getFromTime());
            statement.setString(6, model.getToTime());
            statement.setString(7, model.getIpAddress());
            statement.execute();
        }
        catch(SQLException err){
            System.out.println("Ops! " + err);
            return 0;
        }
        return 1;
    }
    
    public User getByUsername(String username) {
        try{
            String sql = "SELECT * FROM Users\n"
                    + "WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(rs.getString("password"));
                user.setFromDay(rs.getString("FromDay"));
                user.setToDay(rs.getString("ToDay"));
                user.setFromTime(rs.getString("FromTime"));
                user.setToTime(rs.getString("ToTime"));
                user.setIpAddress(rs.getString("IpAddress"));
                return user;
            }
        }
        catch(SQLException err){
            System.out.println("Ops! " + err);
        }
        return null;
    }
}
