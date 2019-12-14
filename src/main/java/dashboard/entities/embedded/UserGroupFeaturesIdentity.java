package dashboard.entities.embedded;

import dashboard.entities.User;
import dashboard.entities.user.UserFeatures;
import dashboard.entities.user.UserGroup;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserGroupFeaturesIdentity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @ManyToOne
    @JoinColumn(name = "user_features_id")
    private UserFeatures userFeatures;

    public UserGroupFeaturesIdentity() {
    }

    public UserGroupFeaturesIdentity(Long userGroupId, String userFeatureId) {
        userGroup.setUserGroupId(userGroupId);
        userFeatures.setFeaturesId(userFeatureId);
    }

    public UserGroupFeaturesIdentity(UserGroup userGroup, UserFeatures userFeatures) {
        this.userGroup = userGroup;
        this.userFeatures = userFeatures;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public UserFeatures getUserFeatures() {
        return userFeatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroupFeaturesIdentity)) return false;
        UserGroupFeaturesIdentity that = (UserGroupFeaturesIdentity) o;
        return Objects.equals(getUserGroup(), that.getUserGroup()) &&
                Objects.equals(getUserFeatures(), that.getUserFeatures());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserGroup(), getUserFeatures());
    }
}
