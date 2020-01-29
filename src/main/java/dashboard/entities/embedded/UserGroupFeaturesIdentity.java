package dashboard.entities.embedded;

import dashboard.entities.user.UserFeature;
import dashboard.entities.user.UserGroup;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserGroupFeaturesIdentity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @ManyToOne
    @JoinColumn(name = "user_features_id")
    private UserFeature userFeature;

    public UserGroupFeaturesIdentity() {
    }

    public UserGroupFeaturesIdentity(Long userGroupId, String userFeatureId) {
        userGroup.setUserGroupId(userGroupId);
        userFeature.setFeaturesId(userFeatureId);
    }

    public UserGroupFeaturesIdentity(UserGroup userGroup, UserFeature userFeature) {
        this.userGroup = userGroup;
        this.userFeature = userFeature;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public UserFeature getUserFeature() {
        return userFeature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroupFeaturesIdentity)) return false;
        UserGroupFeaturesIdentity that = (UserGroupFeaturesIdentity) o;
        return Objects.equals(getUserGroup(), that.getUserGroup()) &&
                Objects.equals(getUserFeature(), that.getUserFeature());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserGroup(), getUserFeature());
    }
}
