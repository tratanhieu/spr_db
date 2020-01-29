package dashboard.dto.user;

import dashboard.entities.user.UserGroupFeature;
import dashboard.enums.EntityStatus;

import java.util.List;

public class UserGroupForm {
    private Long userGroupId;

    private String name;

    private List<UserGroupFeature> userGroupFeatures;

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

    public List<UserGroupFeature> getUserGroupFeatures() {
        return userGroupFeatures;
    }

    public void setUserGroupFeatures(List<UserGroupFeature> userGroupFeatures) {
        this.userGroupFeatures = userGroupFeatures;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
