package dashboard.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.embedded.UserGroupFeaturesIdentity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_group_features")
@EntityListeners(AuditingEntityListener.class)
public class UserGroupFeature implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserGroupFeaturesIdentity userGroupFeaturesIdentity;

    @Column(name = "read_permission")
    @JsonProperty("read")
    private Boolean read;

    @Column(name = "create_permission")
    @JsonProperty("create")
    private Boolean create;

    @Column(name = "update_permission")
    @JsonProperty("update")
    private Boolean update;

    @Column(name = "delete_permission")
    @JsonProperty("delete")
    private Boolean delete;

    @Transient
    @JsonProperty(value = "featureId")
    private String userFeatureId;

    public UserGroupFeature() {
        this.read = false;
        this.create = false;
        this.update = false;
        this.delete = false;
    }

    public UserGroupFeature(Boolean read, Boolean create, Boolean update, Boolean delete) {
        this.read = read;
        this.create = create;
        this.update = update;
        this.delete = delete;
    }

    @JsonIgnore
    public UserGroupFeaturesIdentity getUserGroupFeaturesIdentity() {
        return userGroupFeaturesIdentity;
    }

    @JsonProperty("userGroupFeaturesIdentity")
    public void setUserGroupFeaturesIdentity(UserGroupFeaturesIdentity userGroupFeaturesIdentity) {
        this.userGroupFeaturesIdentity = userGroupFeaturesIdentity;
    }

    public String getUserFeatureId() {
        return userFeatureId;
    }

    public void setUserFeatureId(String featureId) {
        this.userFeatureId = featureId;
    }

    @JsonProperty("featureId")
    public String getUserDFeatureId() {
        return userGroupFeaturesIdentity.getUserFeature().getFeaturesId();
    }

    @JsonProperty(value = "featureName")
    public String getUserFeatureName() {
        return userGroupFeaturesIdentity.getUserFeature().getName();
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getCreate() {
        return create;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
