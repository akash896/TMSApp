package tables;

import connection.MysqlCon;

import java.io.Serializable;
import java.sql.*;

public class Owner implements Serializable {
    private int id;
    private String ownerName;
    private String ownerAddress;

    public Owner() {
    }

    public Owner(int id, String ownerName, String ownerAddress) {
        this.id = id;
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", ownerName='" + ownerName + '\'' +
                ", ownerAddress='" + ownerAddress + '\'' +
                '}';
    }

    public static String newOwner(Owner owner){
        ResultSet rs = null;
        int candidateId = 0;

        String sql = "INSERT INTO owner(id,owner_name,owner_address) "
                + "VALUES(?,?,?)";

        try (Connection conn = MysqlCon.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            // set parameters for statement
            pstmt.setInt(1, owner.id);
            pstmt.setString(2, owner.ownerName);
            pstmt.setString(3, owner.ownerAddress);

            int rowAffected = pstmt.executeUpdate();
            if(rowAffected!=0)
                return "New Owner created";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Owner creation failed";
    }



    public Owner getOwnerDetails(int id) {
        String query = "SELECT * FROM owner where id = '" + id + "'";
        Owner o = null;
        try {
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery(query);
            if (rs == null) {
                System.out.println("no owner by given iid");
                return null;
            }
            System.out.println("The owner details are: ");
            while (rs.next()) {
                int i = rs.getInt("id");
                String name = rs.getString("owner_name");
                String address = rs.getString("owner_address");
                o = new Owner(i, name, address);
                /*System.out.println("ID: " + i);
                System.out.println(", Name: " + name);
                System.out.println(", Address: " + address);
                o = new Owner(i, name, address); */
                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    public String updateOwnerDetails(Owner owner) {
        try {
            String query = "update owner set owner_name = ?, owner_address = ? where id = ?";
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            preparedStmt.setString(1, owner.ownerName);
            preparedStmt.setString(2, owner.ownerAddress);
            preparedStmt.setInt(3, owner.id);
            // execute the java preparedstatement
            if(preparedStmt.executeUpdate()!=0)
                return "Owner Update successfull";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Update unsuccessfull";
    }

    public static String deleteOwner(int id) {
        String query = "delete from owner where id = ?";
        try {
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, id);
            if (preparedStmt.executeUpdate() !=0)
                return "owner deleted ";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "owner deletion failed";
    }
}
