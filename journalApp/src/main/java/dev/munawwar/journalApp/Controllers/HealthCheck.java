package net.engineeringdigest.journalApp.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/hlth-chk")
    public String healthCheck(){
        return "health is ok...";
    }
}
