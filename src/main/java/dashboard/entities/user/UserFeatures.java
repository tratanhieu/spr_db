package dashboard.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user_features")
@EntityListeners(AuditingEntityListener.class)
public class UserFeatures extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "features_id")
    @JsonProperty("featuresId")
    private String featuresId;

    @Column(name = "name", unique = true)
    @NotBlank(message = "Name is not null")
    @Size(min = 2, message = "Min length is more than 2")
    @Size(max = 50, message = "Max length is 50")
    @JsonProperty("name")
    private String name;

    @OneToMany(mappedBy = "userGroupFeaturesIdentity.userFeatures", fetch = FetchType.EAGER)
    private Set<UserGroupFeatures> userGroupFeatures;

    public UserFeatures() {
        super();
    }

    public UserFeatures(String featuresId) {
        this.featuresId = featuresId;
    }

    public UserFeatures(String featuresId, String name) {
        this.featuresId = featuresId;
        this.name = name;
    }

    public String getFeaturesId() {
        return featuresId;
    }

    public void setFeaturesId(String featuresId) {
        this.featuresId = featuresId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserGroupFeatures> getUserGroupFeatures() {
        return userGroupFeatures;
    }

    public void setUserGroupFeatures(Set<UserGroupFeatures> userGroupFeatures) {
        this.userGroupFeatures = userGroupFeatures;
    }
}
