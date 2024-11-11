package rs.ac.uns.ftn.informatika.jpa.dto;

public class UserInfoDTO {
    private Integer id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private Integer numberOfPosts;
    private Integer numberOfFollowing;

    public UserInfoDTO(Integer id, String username, String name, String surname, String email, Integer numberOfPosts, Integer numberOfFollowing) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.numberOfPosts = numberOfPosts;
        this.numberOfFollowing = numberOfFollowing;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(Integer numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public Integer getNumberOfFollowing() {
        return numberOfFollowing;
    }

    public void setNumberOfFollowing(Integer numberOfFollowing) {
        this.numberOfFollowing = numberOfFollowing;
    }
}


