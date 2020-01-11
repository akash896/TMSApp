package tables;

import connection.MysqlCon;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle implements Serializable {
    private int vehicleId;
    private int ownerId;
    private String vehicleType;

    public Vehicle(){}

    public Vehicle(int vehicleId, int ownerId, String vehicleType) {
        this.vehicleId = vehicleId;
        this.ownerId = ownerId;
        this.vehicleType = vehicleType;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", ownerId=" + ownerId +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }

    public static String newVehicle(Vehicle vehicle) throws SQLException {
        String query = " insert into vehicle(vehicle_id, owner_id, vehicle_type)" + " values(?, ?, ?)";
        PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
        preparedStmt.setInt(1, vehicle.vehicleId);
        preparedStmt.setInt(2, vehicle.ownerId);
        preparedStmt.setString(3, vehicle.vehicleType);
        if (preparedStmt.executeUpdate() !=0)
            return "new vehicle created ";
        else
            return "new vehicle creation failed";
    }

    public Vehicle getVehicleDetails(int vId) {
        String query = "SELECT * FROM vehicle where vehicle_id = '" + vId +"'";
        Vehicle v = null;
        try {
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery(query);
            if (rs == null) {
                System.out.println("No record with given vehicleId");
                return null;
            }
            System.out.println("The vehicle details are: ");
            while (rs.next()) {
                int vi = rs.getInt("vehicle_id");
                int oi = rs.getInt("owner_id");
                String vt = rs.getString("vehicle_type");
                v = new Vehicle(vi, oi, vt);

                /*System.out.println("VehicleId: " + vi);
                System.out.println(", OwnerId: " + oi);
                System.out.println(", VehicleType: " + vt);
                return new Vehicle(vi, oi, vt); */
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    public String updateVehicleDetails(Vehicle vehicle) {
        try {
            String query = "update vehicle set owner_id = ?, vehicle_type = ? where vehicle_id = ?";
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, vehicle.ownerId);
            preparedStmt.setString(2, vehicle.vehicleType);
            preparedStmt.setInt(3, vehicle.vehicleId);
            // execute the java preparedstatement
            if(preparedStmt.executeUpdate()==0)
                return "No update done ";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Record updated ";
    }

    public static String deleteVehicle(int vId) {
        String query = "delete from vehicle where vehicle_id = ?";
        try {
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, vId);
            if (preparedStmt.executeUpdate() != 0)
                return "vehicle deleted ";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "vehicle deletion failed";
    }

}
