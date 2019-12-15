package dashboard.entities.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.User;
import dashboard.entities.base.BaseEntity;
import dashboard.entities.post.PostType;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "post",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_title", columnNames = "title"),
                @UniqueConstraint(name = "UK_slugTitle", columnNames = "slug_title")

        })
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "updateDate", "deleleDate"},
        allowGetters = true)
public class Post extends BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    @JsonProperty("post_id")
    private Long postId;

    @NotBlank(message = "{validation.title.notBlank}")
    @Size(min = 2, message = "{validation.minLength}")
    @Size(max = 50, message = "{validation.maxLength}")
    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Size(min = 2, message = "{validation.minLength}")
    @Size(max = 50, message = "{validation.maxLength}")
    @Column(name = "slug_name")
    @JsonProperty("slugName")
    private String slugName;

    @Column(name = "content")
    @JsonProperty("content")
    private String content;

    @Column(name = "image")
    @JsonProperty("image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "post_type_id")
    private PostType postType;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "{validation.status.notBlank}")
    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @OneToMany(mappedBy = "postTagIdentity.post",cascade = CascadeType.PERSIST)
    private Set<PostTag> postTags;

    @Transient
    private String[] tags;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty("postType")
    public Map<String,String> getPostType() {
        Map<String, String> map = new HashMap<>();
        map.put("postTypeId", String.valueOf(postType.getPostTypeId()));
        map.put("name", postType.getName());
        map.put("slugName", postType.getName());
        return map;
    }

    @JsonProperty("postType")
    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("user")
    public Map<String, String> getUser() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(user.getUserId()));
        map.put("name", user.getName());

        return map;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
