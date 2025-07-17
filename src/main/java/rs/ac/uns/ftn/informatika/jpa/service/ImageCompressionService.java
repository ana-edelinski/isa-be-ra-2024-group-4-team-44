package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
public class ImageCompressionService {

    @Value("${onlybuns.upload.path}")
    private String imageDirectory;

    private static final int TARGET_WIDTH = 300;


    @Scheduled(cron = "0 0 0 * * ?")
    public void compressOldImages() {
        File imageDir = new File(imageDirectory);


        if (!imageDir.exists() || !imageDir.isDirectory()) {
            System.err.println("Photo directory not found.");
            return;
        }

        File[] images = imageDir.listFiles();
        if (images == null) {
            System.out.println("No photos for compression");
            return;
        }

        LocalDate today = LocalDate.now();

        for (File imageFile : images) {
            if (isEligibleForCompression(imageFile, today)) {
                try {
                    compressImage(imageFile);
                    System.out.println("Kompresovana slika: " + imageFile.getName());
                } catch (IOException e) {
                    System.err.println("Greška pri kompresiji slike: " + imageFile.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isEligibleForCompression(File imageFile, LocalDate today) {
        try {

            Instant lastModifiedInstant = Files.getLastModifiedTime(imageFile.toPath()).toInstant();
            LocalDate lastModifiedDate = lastModifiedInstant.atZone(ZoneId.systemDefault()).toLocalDate();

            // da li je slika stara tačno 30 dana
            long daysOld = ChronoUnit.DAYS.between(lastModifiedDate, today);
            return daysOld == 30;
        } catch (IOException e) {
            System.err.println("Error loading photo: " + imageFile.getName());
            return false;
        }
    }

    private void compressImage(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);

        BufferedImage compressedImage = Scalr.resize(image, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, TARGET_WIDTH);

        ImageIO.write(compressedImage, "jpg", imageFile);
    }

    public void simulateOldImages() {
        File imageDir = new File(imageDirectory);
        File[] images = imageDir.listFiles();

        if (images == null) {
            System.out.println("Nema slika za simulaciju starosti.");
            return;
        }

        for (File imageFile : images) {
            try {
                FileTime modifiedTime = FileTime.from(Instant.now().minus(30, ChronoUnit.DAYS));
                Files.setLastModifiedTime(imageFile.toPath(), modifiedTime);
                System.out.println("Simulirana starost za: " + imageFile.getName());
            } catch (IOException e) {
                System.err.println("Greška pri simulaciji starosti za fajl: " + imageFile.getName());
                e.printStackTrace();
            }
        }
    }
}
