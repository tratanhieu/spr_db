package dashboard.entities.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table( name = "post_type")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "updateDate", "deleleDate"},
        allowGetters = true)
public class PostType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_type_id")
    @JsonProperty("postTypeId")
    private Long  postTypeId;

    @NotNull(message = "Name is not null")
    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Slug_name is not null")
    @Column(name = "slug_name")
    @JsonProperty("slugName")
    private String slugName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public Long getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(Long postTypeId) {
        this.postTypeId = postTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public Boolean isEquals(PostType postType){
        return (this.name.equals(postType.name)
                && this.slugName.equals(postType.slugName)
                && this.status.equals(postType.status));
    }
}