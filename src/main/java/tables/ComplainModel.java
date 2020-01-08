package tables;

import java.io.Serializable;

public class ComplainModel implements Serializable {
    private int complainId;
    private  Vehicle vehicle;
    private Owner owner;;
    private Offence offence;;

    public ComplainModel(int complainId, Vehicle vehicle, Owner owner, Offence offence) {
        this.complainId = complainId;
        this.vehicle = vehicle;
        this.owner = owner;
        this.offence = offence;
    }

    public ComplainModel(){}

    public int getComplainId() {
        return complainId;
    }

    public void setComplainId(int complainId) {
        this.complainId = complainId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Offence getOffence() {
        return offence;
    }

    public void setOffence(Offence offence) {
        this.offence = offence;
    }
}
