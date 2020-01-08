package tables;

import connection.MysqlCon;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Complain implements Serializable {
    private int complainId;
    private int vehicleId;
    private int offenceId;
    private String status;

    public Complain(){}

    public Complain(int complainId, int vehicleId, int offenceId, String status) {
        this.complainId = complainId;
        this.vehicleId = vehicleId;
        this.offenceId = offenceId;
        this.status = status;
    }

    public int getComplainId() {
        return complainId;
    }

    public void setComplainId(int complainId) {
        this.complainId = complainId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getOffenceId() {
        return offenceId;
    }

    public void setOffenceId(int offenceId) {
        this.offenceId = offenceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Complain{" +
                "complainId=" + complainId +
                ", vehicleId=" + vehicleId +
                ", offenceId=" + offenceId +
                ", status='" + status + '\'' +
                '}';
    }

    public static String newComplain(Complain complain) throws SQLException {
            String query = " insert into complain(complain_id, vehicle_id, offence_id, status)" + " values(?, ?, ?, ?)";
            PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, complain.complainId);
            preparedStmt.setInt(2, complain.vehicleId);
            preparedStmt.setInt(3, complain.offenceId);
            preparedStmt.setString(4, complain.status);

            if (preparedStmt.executeUpdate() != 0)
                return "new complain created ";
            else
                return "complain creation failed";
        }

        public ComplainModel getComplainDetails(int id) throws SQLException {
            String query = "SELECT * FROM complain where complain_id = '" + id + "'";
            ComplainModel cm = null;
            Owner owner= null;
            Vehicle vehicle = null;
            Offence offence = null;
            int owid, veid, ofid, offi, coid = id;
            String owna, owad, vety, ofna, ofst;
            try {
                PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
                ResultSet rs = preparedStmt.executeQuery(query);
                if (rs == null)
                    return null;
                rs.next();
                //System.out.println("The Complain details are: ");
                    //int i = rs.getInt("complain_id");
                    veid = rs.getInt("vehicle_id");
                    ofid = rs.getInt("offence_id");

                //System.out.println("Complain Id: "+i);

                query = "SELECT * FROM vehicle where vehicle_id = '" + veid + "'";

                    preparedStmt = MysqlCon.getConnection().prepareStatement(query);
                    rs = preparedStmt.executeQuery(query);
                    if (rs == null)
                        vehicle = null;
                    //System.out.println("The vehicle details are: ");
                    rs.next();
                    //System.out.println("vehicle id: "+rs.getInt("vehicle_id"));
                    owid = rs.getInt("owner_id");
                //System.out.println("OwnerId: "+ owid);
                    vety = rs.getString("vehicle_type");
                    vehicle = new Vehicle(veid, owid, vety);
                    //System.out.println("Owner details are :");
                    query = "SELECT * FROM owner where id = '" + owid + "'";

                        preparedStmt = MysqlCon.getConnection().prepareStatement(query);
                        rs = preparedStmt.executeQuery(query);
                        if (rs == null)
                            owner = null;
                        rs.next();
                        //System.out.println("The owner details are: ");
                        //System.out.println(rs.getInt("id"));
                        owna = rs.getString("owner_name");
                        owad = rs.getString("owner_address");
                        owner = new Owner(owid, owna, owad);

                //System.out.println("offence details are :");
                query = "SELECT * FROM offence where offence_id = '" + ofid + "'";
                preparedStmt = MysqlCon.getConnection().prepareStatement(query);
                rs = preparedStmt.executeQuery(query);
                if (rs == null)
                    offence = null;
                rs.next();
                //System.out.println("The offence details are: ");

                    //i = rs.getInt("offence_id");
                    ofna = rs.getString("offence_name");
                    offi = rs.getInt("offence_fine");
                    offence = new Offence(ofid, ofna, offi);
                    /*System.out.println("OffenceID: " + i);
                        System.out.println(", OffenceName: " + name);
                        System.out.println(", OffenceFine: " + fine);*/
                        cm = new ComplainModel(coid, vehicle, owner, offence);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return cm;
        }

        public String updateComplainDetails(Complain complain) {
            try {
                String query = "update complain set vehicle_id = ?, offence_id = ?, status = ? where complain_id = ?";
                PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
                preparedStmt.setInt(1, complain.vehicleId);
                preparedStmt.setInt(2, complain.offenceId);
                preparedStmt.setString(3, complain.status);
                preparedStmt.setInt(4, complain.complainId);
                // execute the java preparedstatement
                if(preparedStmt.executeUpdate()!=0)
                    return "Offence updated ";
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Offence updation failed";
        }

        public String payFine(int id) {
            try {
                String query = "update complain set status = ? where complain_id = ?";
                PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
                preparedStmt.setString(1, "Paid");
                preparedStmt.setInt(2, id);
                // execute the java preparedstatement
                if(preparedStmt.executeUpdate()!=0)
                    return "Fine paid ";
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Fine payment failed ";
        }

        public static String deleteComplain(int id) {
            String query = "delete from complain where complain_id = ?";
            try {
                PreparedStatement preparedStmt = MysqlCon.getConnection().prepareStatement(query);
                preparedStmt.setInt(1, id);
                if (preparedStmt.executeUpdate() != 0)
                    return "complain deleted ";
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "complain deletion failed";
        }
    }

