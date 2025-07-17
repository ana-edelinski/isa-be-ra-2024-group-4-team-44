package rs.ac.uns.ftn.informatika.jpa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${onlybuns.upload.path}")
    private String uploadPath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Dozvoli sve rute
                .allowedOrigins("http://localhost:4200") // Dozvoli frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Dozvoli sve metode
                .allowedHeaders("*") // Dozvoli sve zaglavlja
                .allowCredentials(true); // Omogući slanje kolačića
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static images with Cache-Control
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///" + uploadPath)
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
    }
}
