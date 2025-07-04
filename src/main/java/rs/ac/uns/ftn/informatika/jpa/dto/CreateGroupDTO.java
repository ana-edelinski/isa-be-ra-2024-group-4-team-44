package rs.ac.uns.ftn.informatika.jpa.dto;

import java.util.List;

public class CreateGroupDTO {
    private String name;
    private List<Integer> memberIds;
    private Integer creatorId;


    public CreateGroupDTO() {}
    public String getName() { return name;}
    public void setName(String name) { this.name = name;}
    public List<Integer> getMemberIds() { return memberIds;}
    public void setMemberIds(List<Integer> memberIds) { this.memberIds = memberIds;}
    public Integer getCreatorId() { return creatorId;}
    public void setCreatorId(Integer creatorId) { this.creatorId = creatorId;}
}
