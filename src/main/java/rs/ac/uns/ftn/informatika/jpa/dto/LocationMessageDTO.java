package rs.ac.uns.ftn.informatika.jpa.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class LocationMessageDTO implements Serializable {

    private Integer id;
    private String name;
    private String street;
    private String city;
    private String postalCode;
    private Double latitude;
    private Double longitude;
    public LocationMessageDTO() {
    }
    @JsonCreator
    public LocationMessageDTO(
            @JsonProperty("id") Integer id,
            @JsonProperty("name") String name,
            @JsonProperty("street") String street,
            @JsonProperty("city") String city,
            @JsonProperty("postalCode") String postalCode,
            @JsonProperty("latitude") Double latitude,
            @JsonProperty("longitude")  Double longitude
    ) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

