package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.model.LocationMessage;
import rs.ac.uns.ftn.informatika.jpa.service.LocationMessageService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value ="/api/message", produces = MediaType.APPLICATION_JSON_VALUE)

public class LocationMessageController {

    @Autowired
    private LocationMessageService locationMessageService;

    @GetMapping("/locations")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public List<LocationMessage> getAllLocationMessages() {
        return locationMessageService.getAllLocationMessages();
    }
}
