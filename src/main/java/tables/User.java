package tables;

import connection.MysqlCon;

import java.io.Serializable;
import java.sql.*;

public class User implements Serializable {
    private String userName;
    private String password;

    public User(){}

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static String newUser(User user){
        ResultSet rs = null;
        int candidateId = 0;
        String query = "SELECT * FROM users where username = '" + user.userName + "'";
        try {
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            rs = preparedStmt.executeQuery(query);
            if (rs != null)
                return "Username already exists ";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "INSERT INTO users(username, password) " + "VALUES(?,?)";
        try (Connection conn = MysqlCon.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            // set parameters for statement
            pstmt.setString(1, user.userName);
            pstmt.setString(2, user.password);

            int rowAffected = pstmt.executeUpdate();
            if(rowAffected!=0)
                return "New User created";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "User creation failed";
    }

    public static String userLogin(User user){
        String query = "SELECT * FROM users where username = '" + user.userName + "'";
        try {
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery(query);
            if (rs == null)
                return "Wrong username ";
            System.out.println("The user details are: ");
            rs.next();
            String p = rs.getString("password");
            if(p.equals(user.password))
                return "Login successfull";
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return "Wrong username or password ";
    }



    public String getUserDetails(String username) {
        String query = "SELECT * FROM users where username = '" + username + "'";

        try {
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery(query);
            if (rs == null)
                return "No record with given id";
            System.out.println("The user details are: ");
                rs.next();
            System.out.println(rs.getString("username"));
            System.out.println(rs.getString("password"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "user details display successfull ";
    }

    public String updateUserDetails(User user) {
        try {
            String query = "update users set password = ? where username = ?";
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            preparedStmt.setString(1, user.password);
            preparedStmt.setString(2, user.userName);
            // execute the java preparedstatement
            if(preparedStmt.executeUpdate()!=0)
                return "Owner Update successfull";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Update unsuccessfull";
    }

    public static String deleteUser(String username) {
        String query = "delete from users where username = ?";
        try {
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            preparedStmt.setString(1, username);
            if (preparedStmt.executeUpdate() !=0)
                return "user deleted ";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "user deletion failed";
    }
}
