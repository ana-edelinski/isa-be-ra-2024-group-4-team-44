package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.User;
public class UserDTO {

    private Integer id;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;

    UserDTO(){

    }
    UserDTO(Integer id, String email, String username, String password, String confirmPassword) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    UserDTO(User user)
    {
        this(user.getId(), user.getEmail(), user.getUsername(), user.getPassword(), user.getPassword());
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
    public String getPassword() {
        return password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
}
