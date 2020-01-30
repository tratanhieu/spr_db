package dashboard.dto.user;

import dashboard.enums.EntityStatus;

import java.util.List;

public class UserGroupDto {
    private Long userGroupId;

    private String name;

    private int totalUser;

    private List<UserGroupFeatureDto> userGroupFeatures;

    private EntityStatus status;

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }

    public List<UserGroupFeatureDto> getUserGroupFeatures() {
        return userGroupFeatures;
    }

    public void setUserGroupFeatures(List<UserGroupFeatureDto> userGroupFeatures) {
        this.userGroupFeatures = userGroupFeatures;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
