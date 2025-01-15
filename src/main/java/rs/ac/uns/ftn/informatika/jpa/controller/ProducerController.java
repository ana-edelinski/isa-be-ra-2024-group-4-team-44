package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.LocationMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.model.LocationMessage;
import rs.ac.uns.ftn.informatika.jpa.model.Producer;
import rs.ac.uns.ftn.informatika.jpa.service.LocationMessageService;

@RestController
@RequestMapping(value = "api")
public class ProducerController {

    @Autowired
    private Producer producer;
    @Autowired
    private LocationMessageService locationMessageService;

    @PostMapping(value="/{queue}", consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<String> sendMessage(@PathVariable("queue") String queue, @RequestBody LocationMessageDTO message) {
        locationMessageService.save(message);
        producer.sendTo(queue, message);
        return ResponseEntity.ok().build();
    }


}
