/**
 * Created by johan on 10.09.2017.
 */

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class Start {
    //works.
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309vcb4";

    //credential
    static final String USER = "dbu309vcb4";
    static final String PWD = "qCaVEG0Z";
    private static Map<String, String> name = new HashMap<String,String>();
    public static void main(String[] args){
        Connection conn = null;
        Statement stmt = null;

        try{
            //register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Driver myDriver = new oracle.jdbc.driver.OracleDriver();
            //open connection
            System.out.println("Connecting db");
            conn = DriverManager.getConnection(DB_URL, USER, PWD);

            //execute query
            stmt = conn.createStatement();

            //extract data
            extractData(stmt);

            //update id
            //updateData(stmt);
            deleteDate(stmt);
            //
            System.out.println("\n\n");
            extractData(stmt);
            //clean up
            stmt.close();
            conn.close();

        }catch (SQLDataException se){
            System.err.println("SQL EXCEPTION");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("OTHERTYPE OF EXCEPTION EXCEPTION");

            e.printStackTrace();
        }finally{
            //close all resources
            try{
                if(stmt!=null){
                    stmt.close();
                }
            }catch(SQLException se){
                se.printStackTrace();
            }
            try{
                if(conn!= null){
                    conn.close();
                }
            }catch(SQLException se){
                se.printStackTrace();
            }//end 2nd try
        }//end outer try
        System.out.println("Finished lets see our results..");

        System.out.println(name.toString());
    }


    //delete a mean user.
    private static void deleteDate(Statement stmt) throws SQLException{
        String sql = "delete from Student where id = 2";
        stmt.execute(sql);
    }


    private static void updateData(Statement stmt) throws SQLException{
        String sql = "Update Student set id = id+2 where id % 2 = 0";
        stmt.execute(sql);

    }
    private static void extractData(Statement stmt) throws SQLException {
        String sql = "SELECT first, last, id from Student";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            String first = rs.getString("first");
            String last = rs.getString("last");
            System.out.println(first +" "+ last+ " : " +(rs.getString("id")));

            String id =(rs.getString("id").toString());
            //store values in a map
            name.put(id,first+ " " +last);

        }
        rs.close();
        return;
    }


}

