/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Nam
 */
public abstract class BaseDAO<T> {
    protected Connection connection;
    public BaseDAO() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String user = "sa";
            String pass = "12345678";
            String url  = "jdbc:sqlserver://localhost:1433;databaseName=RBAC2";
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connect successfully.");
        }
        catch(Exception err) {
            System.out.println("Can't establish connection.");
        }
    }
    
    public abstract int insert(T model);
}
