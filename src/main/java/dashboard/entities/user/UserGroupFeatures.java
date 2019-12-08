package dashboard.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import dashboard.entities.embedded.UserGroupFeaturesId;
import dashboard.enums.FeaturesStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@IdClass(UserGroupFeaturesId.class)
@Table(name = "user_group_features")
@EntityListeners(AuditingEntityListener.class)
public class UserGroupFeatures extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @Id
    @ManyToOne
    @JoinColumn(name = "features_id")
    private UserFeatures userFeatures;

    @Column(name = "read_permission")
    @JsonProperty("read")
    @Enumerated(EnumType.STRING)
    private FeaturesStatus read;

    @Column(name = "create_permission")
    @JsonProperty("create")
    @Enumerated(EnumType.STRING)
    private FeaturesStatus create;

    @Column(name = "update_permission")
    @JsonProperty("update")
    @Enumerated(EnumType.STRING)
    private FeaturesStatus update;

    @Column(name = "delete_permission")
    @JsonProperty("delete")
    @Enumerated(EnumType.STRING)
    private FeaturesStatus delete;

    public UserGroupFeatures() {
        super();
    }

    public UserGroupFeatures(FeaturesStatus read, FeaturesStatus create, FeaturesStatus update, FeaturesStatus delete) {
        this.read = read;
        this.create = create;
        this.update = update;
        this.delete = delete;
    }

    public Map<String, String> getUserGroup() {
        Map<String, String> map = new HashMap<>();
        map.put("userGroupId", String.valueOf(userGroup.getUserGroupId()));
        map.put("name", userGroup.getName());
        return map;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Map<String, String> getUserFeatures() {
        Map<String, String> map = new HashMap<>();
        map.put("featuresId", userFeatures.getFeaturesId());
        map.put("name", userFeatures.getName());
        return map;
    }

    public void setUserFeatures(UserFeatures userFeatures) {
        this.userFeatures = userFeatures;
    }

    public FeaturesStatus getRead() {
        return read;
    }

    public void setRead(FeaturesStatus read) {
        this.read = read;
    }

    public FeaturesStatus getCreate() {
        return create;
    }

    public void setCreate(FeaturesStatus create) {
        this.create = create;
    }

    public FeaturesStatus getUpdate() {
        return update;
    }

    public void setUpdate(FeaturesStatus update) {
        this.update = update;
    }

    public FeaturesStatus getDelete() {
        return delete;
    }

    public void setDelete(FeaturesStatus delete) {
        this.delete = delete;
    }
}
