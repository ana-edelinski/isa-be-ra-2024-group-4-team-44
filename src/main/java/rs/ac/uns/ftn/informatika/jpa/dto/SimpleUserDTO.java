package rs.ac.uns.ftn.informatika.jpa.dto;

public class SimpleUserDTO {
    private Integer id;
    private String username;

    public SimpleUserDTO() {}

    public SimpleUserDTO(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    // GET i SET metode
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}

