package rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Control {
    @RequestMapping("/TMSApp/akash")
    public String show(){
        return "akash";
    }
}
