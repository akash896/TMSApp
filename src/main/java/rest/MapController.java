package rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tables.*;

import java.sql.SQLException;

@RestController
public class MapController {
    Owner owner = new Owner();
    Vehicle vehicle = new Vehicle();
    Offence offence = new Offence();
    Complain complain = new Complain();
    User user = new User();


    @RequestMapping("/TMSApp")
    public String welcome() {
        return "Welcome to TMSApp ";
    }

    @PostMapping(path = "/TMSApp/Register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newUser(@RequestBody User user) throws SQLException {
        //System.out.println("In register ");
        return User.newUser(user);
    }

    @PostMapping(path = "/TMSApp/Login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody User user) throws SQLException {
        return User.userLogin(user);
    }

    @PostMapping(path = "/TMSApp/create/Owner", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newOwner(@RequestBody Owner owner) {
        return Owner.newOwner(owner);
    }

    @PostMapping(path = "/TMSApp/create/Vehicle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newVehicle(@RequestBody Vehicle vehicle) throws SQLException {
        return Vehicle.newVehicle(vehicle);
    }

    @PostMapping(path = "/TMSApp/create/Offence", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newOffence(@RequestBody Offence offence) throws SQLException {
        return Offence.newOffence(offence);
    }

    @PostMapping(path = "/TMSApp/create/Complain", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newComplain(@RequestBody Complain complain) throws SQLException {
        return Complain.newComplain(complain);
    }

    @GetMapping(path = "/TMSApp/get/Owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Owner> getOwner(@PathVariable int id) {
        Owner cOwner = owner.getOwnerDetails(id);
        return ResponseEntity.ok(cOwner);
    }

    @GetMapping(path = "/TMSApp/get/Vehicle/{id}")
    public @ResponseBody
    ResponseEntity<Vehicle> getVehicle( @PathVariable String id) {
        Vehicle v = vehicle.getVehicleDetails(Integer.parseInt(id));
        return ResponseEntity.ok(v);
    }

    @GetMapping(path = "/TMSApp/get/Offence/{id}")
    public @ResponseBody
    ResponseEntity<Offence> getOffence(@PathVariable String id) {
        Offence o = offence.getOffenceDetails(Integer.parseInt(id));
        return ResponseEntity.ok(o);
    }

    @GetMapping(path = "/TMSApp/get/Complain/{id}") // Map ONLY POST Requests
    public @ResponseBody
    ResponseEntity<ComplainModel> getComplain(@PathVariable String id) throws SQLException {
        ComplainModel complainModel = complain.getComplainDetails(Integer.parseInt(id));
        return ResponseEntity.ok(complainModel);
    }

    @PutMapping(path = "/TMSApp/update/Owner", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateOwner(@RequestBody Owner owner) {
        return owner.updateOwnerDetails(owner);
    }

    @PutMapping(path = "/TMSApp/update/Vehicle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateOwner(@RequestBody Vehicle vehicle) {
        return vehicle.updateVehicleDetails(vehicle);
    }

    @PutMapping(path = "/TMSApp/update/Offence", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateOwner(@RequestBody Offence offence) {
        return offence.updateOffenceDetails(offence);
    }

    @PutMapping(path = "/TMSApp/update/Complain", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateOwner(@RequestBody Complain complain) {
        return complain.updateComplainDetails(complain);
    }

    @DeleteMapping(path = "/TMSApp/delete/Owner/{id}")
    public  String deleteOwner(@PathVariable String id) {
        return Owner.deleteOwner(Integer.parseInt(id));
    }

    @DeleteMapping(path = "/TMSApp/delete/Vehicle/{id}")
    public  String deleteVehicle(@PathVariable String id) {
        return Vehicle.deleteVehicle(Integer.parseInt(id));
    }

    @DeleteMapping(path = "/TMSApp/delete/Offence/{id}")
    public  String deleteOffence(@PathVariable String id) {
        return Offence.deleteOffence(Integer.parseInt(id));
    }

    @DeleteMapping(path = "/TMSApp/delete/Complain/{id}")
    public  String deleteComplain(@PathVariable String id) {
        return Complain.deleteComplain(Integer.parseInt(id));
    }

    @PutMapping(path = "/TMSApp/pay/Complain/{id}")
    public String payFineComplain(@PathVariable int id){
        return complain.payFine(id);
    }

}// class ends

