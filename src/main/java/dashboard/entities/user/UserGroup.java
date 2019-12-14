package dashboard.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_group",
        uniqueConstraints = {
        @UniqueConstraint(name="UK_name", columnNames = "name"),
})
@EntityListeners(AuditingEntityListener.class)
public class UserGroup extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_group_id")
    @JsonProperty("userGroupId")
    private Long userGroupId;

    @Column(name = "name", unique = true)
    @NotBlank(message = "Name is not null")
    @Size(min = 2, message = "Min length is more than 2")
    @Size(max = 50, message = "Max length is 50")
    @JsonProperty("name")
    private String name;

    @Column(name = "status")
    @NotNull(message = "{validation.status.notBlank}")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @OneToMany(mappedBy = "userGroupFeaturesIdentity.userGroup",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<UserGroupFeatures> userGroupFeatures;

    public UserGroup() {
        super();
    }

    public UserGroup(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public UserGroup(Long userGroupId, String name, EntityStatus status) {
        this.userGroupId = userGroupId;
        this.name = name;
        this.status = status;
    }

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

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public Set<UserGroupFeatures> getUserGroupFeatures() {
        return userGroupFeatures;
    }

    public void setUserGroupFeatures(Set<UserGroupFeatures> userGroupFeatures) {
        this.userGroupFeatures = userGroupFeatures;
    }
}
