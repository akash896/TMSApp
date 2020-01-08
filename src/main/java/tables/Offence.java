package tables;

import connection.MysqlCon;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Offence implements Serializable {
    private int offenceId;
    private String offenceName;
    int offenceFine;

    public Offence() {
    }

    public Offence(int offenceId, String offenceName, int offenceFine) {
        this.offenceId = offenceId;
        this.offenceName = offenceName;
        this.offenceFine = offenceFine;
    }

    public int getOffenceId() {
        return offenceId;
    }

    public void setOffenceId(int offenceId) {
        this.offenceId = offenceId;
    }

    public String getOffenceName() {
        return offenceName;
    }

    public void setOffenceName(String offenceName) {
        this.offenceName = offenceName;
    }

    public int getOffenceFine() {
        return offenceFine;
    }

    public void setOffenceFine(int offenceFine) {
        this.offenceFine = offenceFine;
    }

    @Override
    public String toString() {
        return "Offence{" +
                "offenceId=" + offenceId +
                ", offenceName='" + offenceName + '\'' +
                ", offenceFine=" + offenceFine +
                '}';
    }

    public static String newOffence(Offence offence) throws SQLException {
        String query = " insert into offence(offence_id, offence_name, offence_fine)" + " values(?, ?, ?)";
        PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
//        preparedStmt.setInt(1, offence.offenceId);
        preparedStmt.setInt(1, offence.offenceId);
        preparedStmt.setString(2, offence.offenceName);
        preparedStmt.setInt(3, offence.offenceFine);

        if (preparedStmt.executeUpdate() != 0)
            return "new offence created ";
        else
        return "offence creation failed";
    }

    public Offence getOffenceDetails(int id) {
        String query = "SELECT * FROM offence where offence_id = '" + id + "'";
        Offence o = null;
        try {
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery(query);
            if (rs == null)
                System.out.println("No record with given id");

            System.out.println("The offence details are: ");
            while (rs.next()) {
                o = new Offence(rs.getInt("offence_id"), rs.getString("offence_name"), rs.getInt("offence_fine"));
            /*    System.out.println("OffenceID: " + i);
                System.out.println(", OffenceName: " + name);
                System.out.println(", OffenceFine: " + fine);*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    public String updateOffenceDetails(Offence offence) {
        try {
            String query = "update offence set offence_name = ?, offence_fine = ? where offence_id = ?";
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            preparedStmt.setString(1, offence.offenceName);
            preparedStmt.setInt(2, offence.offenceFine);
            preparedStmt.setInt(3, offence.offenceId);
            // execute the java preparedstatement
            if(preparedStmt.executeUpdate()!=0)
                return "Offence updated ";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Offence updation failed";
    }

    public static String deleteOffence(int id) {
        String query = "delete from offence where offence_id = ?";
        try {
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, id);
            if (preparedStmt.executeUpdate() != 0)
                return "offence deleted ";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "offence deletion failed";
    }
}
