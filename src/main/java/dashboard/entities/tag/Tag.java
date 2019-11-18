package dashboard.entities.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import dashboard.entities.post.PostTag;
import dashboard.entities.product.ProductTag;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tag")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "updateDate", "deleleDate"},
        allowGetters = true)
public class Tag extends BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @Column(name = "slug_name")
    @JsonProperty("slug_name")
    private String slugName;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<PostTag> postTags;

    @OneToMany(mappedBy = "productTag", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<ProductTag> productTags;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PostTag> getPostTags() {
        return postTags;
    }

    public void setPostTags(Set<PostTag> postTags) {
        this.postTags = postTags;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
