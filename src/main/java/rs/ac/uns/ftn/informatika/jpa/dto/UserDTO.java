package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.User;
public class UserDTO {

    private Integer id;
    private String email;
    private String username;
    private String name;
    private String password;
    private String confirmPassword;
    private String street;
    private String city;
    private String postalCode;

    public UserDTO(){

    }
    public UserDTO(Integer id, String email, String username, String name, String password, String confirmPassword, String street, String city, String postalCode) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.name = name;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.name = user.getName();
        this.password = user.getPassword();
        this.confirmPassword = user.getPassword();
        if (user.getAddress() != null) {
            this.street = user.getAddress().getStreet();
            this.city = user.getAddress().getCity();
            this.postalCode = user.getAddress().getPostalCode();
        }
    }
    public Integer getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }
    public String getPostalCode() {
        return postalCode;
    }
}
