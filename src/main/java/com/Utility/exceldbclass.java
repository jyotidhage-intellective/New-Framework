package com.Utility;

import java.sql.*;

public class exceldbclass {

    public static void main(String []args) throws Exception{
        // variables
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        // Step 1: Loading or
        // registering Oracle JDBC driver class
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        // Step 2: Opening database connection
        try {

            String msAccDB = "C:\\Users\\jdhage\\Desktop\\swTesting\\Project\\newPro\\LatestFreeCRM\\LatestFreeCRM\\Database2.accdb";
            String dbURL = "jdbc:ucanaccess://" + msAccDB;

            // Step 2.A: Create and
            // get connection using DriverManager class
            connection = DriverManager.getConnection(dbURL);

            // Step 2.B: Creating JDBC Statement
            statement = connection.createStatement();

            // Step 2.C: Executing SQL and
            // retrieve data into ResultSet
            resultSet = statement.executeQuery("SELECT * FROM employee where Lname = 'Joshi'");

            System.out.println("ID\tFName\tLname\t\tCity");
            System.out.println("==\t================\t===\t=======");

            // processing returned data and printing into console
            while(resultSet.next()) {
                System.out.println(resultSet.getInt(1) + "\t" +
                        resultSet.getString(2) + "\t" +
                        resultSet.getString(3) + "\t" + resultSet.getString(4));
            }
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {
            // Step 3: Closing database connection
            try {
                if(null != connection) {
                    // cleanup resources, once after processing
                    resultSet.close();
                    statement.close();

                    // and then finally close connection
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
    }

//    public static void main(String []args) throws Exception{
//        Connection conn = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//        conn = getConnection();
//        stmt = conn.createStatement();
//        String query = "select * from [TestCase$]";
//        rs= stmt.executeQuery(query);
//        while (rs.next()){
//            System.out.println(rs.getString("TC_ID"));
//        }
//        rs.close();
//        stmt.close();
//        conn.close();
//    }
//    public static Connection getConnection() throws Exception{
//        String dbUrl = "C:\\Users\\jdhage\\Desktop\\swTesting\\Project\\com.intellective.unityautomation\\src\\main\\resources\\TestData\\TestData.xlsx";
//        String username ="";
//        String password ="";
//        String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
//        String url = "jdbc:odbc:excelDB";
//        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//        return DriverManager.getConnection("jdbc:odbc:man");
//        //Class.forName(driver);
//        //return DriverManager.getConnection(url,username,password);
//    }
}
