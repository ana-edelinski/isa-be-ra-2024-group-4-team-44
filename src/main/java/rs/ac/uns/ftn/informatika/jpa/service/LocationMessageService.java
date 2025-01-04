package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.LocationMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.model.LocationMessage;
import rs.ac.uns.ftn.informatika.jpa.repository.LocationMessageRepository;

import java.util.List;

@Service
public class LocationMessageService {


    private final LocationMessageRepository locationMessageRepository;

    @Autowired
    public LocationMessageService(LocationMessageRepository locationMessageRepository) {
        this.locationMessageRepository = locationMessageRepository;
    }

    public List<LocationMessage> getAllLocationMessages() {
        return locationMessageRepository.findAll();
    }

    public LocationMessageDTO save(LocationMessageDTO locationMessageDto) {
        // Map DTO to Entity
        LocationMessage locationMessage = new LocationMessage();
        locationMessage.setName(locationMessageDto.getName());
        locationMessage.setStreet(locationMessageDto.getStreet());
        locationMessage.setCity(locationMessageDto.getCity());
        locationMessage.setPostalCode(locationMessageDto.getPostalCode());
        locationMessage.setLatitude(locationMessageDto.getLatitude());
        locationMessage.setLongitude(locationMessageDto.getLongitude());

        // Save Entity to Database
        LocationMessage savedLocationMessage = locationMessageRepository.save(locationMessage);

        // Map Entity back to DTO
        return new LocationMessageDTO(
                savedLocationMessage.getId(),
                savedLocationMessage.getName(),
                savedLocationMessage.getStreet(),
                savedLocationMessage.getCity(),
                savedLocationMessage.getPostalCode(),
                savedLocationMessage.getLatitude(),
                savedLocationMessage.getLongitude()
        );
    }
}
