package rs.ac.uns.ftn.informatika.jpa.dto;

public class UserInfoDTO {
    private String name;
    private String surname;
    private String email;
    private int postCount;
    private int followingCount;

    public UserInfoDTO(String name, String surname, String email, int postCount, int followingCount) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.postCount = postCount;
        this.followingCount = followingCount;
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

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

}
