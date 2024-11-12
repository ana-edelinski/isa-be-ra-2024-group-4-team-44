package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.service.ImageCompressionService;

@RestController
public class ImageCompressionController {

    @Autowired
    private ImageCompressionService imageCompressionService;



    @GetMapping("/test-compress")
    public String testCompressOldImages() {
        imageCompressionService.compressOldImages();
        return "Kompresija završena!";
    }

    @GetMapping("/simulate-old-images")
    public String simulateOldImages() {
        imageCompressionService.simulateOldImages();
        return "Simulacija starosti slika završena!";
    }
}
