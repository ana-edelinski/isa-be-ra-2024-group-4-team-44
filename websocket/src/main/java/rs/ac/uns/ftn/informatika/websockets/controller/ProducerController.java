package rs.ac.uns.ftn.informatika.websockets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.websockets.dto.LocationMessageDTO;
import rs.ac.uns.ftn.informatika.websockets.model.Producer;

@RestController
@RequestMapping(value = "api")
public class ProducerController {

    @Autowired
    private Producer producer;

    @PostMapping(value="/{queue}", consumes = "application/json")
    public ResponseEntity<String> sendMessage(@PathVariable("queue") String queue, @RequestBody LocationMessageDTO message) {

        producer.sendTo(queue, message);
        return ResponseEntity.ok().build();
    }


}
