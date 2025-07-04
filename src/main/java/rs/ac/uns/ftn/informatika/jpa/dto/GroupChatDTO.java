package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.GroupChat;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class GroupChatDTO {
    private Integer id;
    private String name;
    private List<Integer> memberIds;

    public GroupChatDTO() {}

    public GroupChatDTO(GroupChat group) {
        this.id = group.getId();
        this.name = group.getName();
        this.memberIds = group.getMemberIds();
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

    public List<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Integer> memberIds) {
        this.memberIds = memberIds;
    }
}

