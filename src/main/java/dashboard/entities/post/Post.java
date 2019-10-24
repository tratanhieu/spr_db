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

@Entity
@Table(name = "post")
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

    @NotBlank(message = "Tiêu đề bài viết không được để trống")
    @Size(min = 2, message = "Độ dài tối thiểu là 2 ký tự")
    @Size(max = 50, message = "Độ dài tối đa là 50 ký tự")
    @Column(name = "title")
    @JsonProperty("title")
    private String title;

    @Size(min = 2, message = "Độ dài tối thiểu là 2 ký tự")
    @Size(max = 50, message = "Độ dài tối đa là 50 ký tự")
    @Column(name = "slug_title")
    @JsonProperty("slug_title")
    private String slugTitle;

    @Size(min = 2, message = "Độ dài tối thiểu là 2 ký tự")
    @Size(max = 50, message = "Độ dài tối đa là 50 ký tự")
    @Column(name = "content")
    @JsonProperty("content")
    private String content;

    @Column(name = "image")
    @JsonProperty("image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "post_type_id")
    private PostType postType;

    @Size(min = 2, message = "Độ dài tối thiểu là 2 ký tự")
    @Size(max = 50, message = "Độ dài tối đa là 50 ký tự")
    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Size(min = 2, message = "Độ dài tối thiểu là 2 ký tự")
    @Size(max = 50, message = "Độ dài tối đa là 50 ký tự")
    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlugTitle() {
        return slugTitle;
    }

    public void setSlugTitle(String slugTitle) {
        this.slugTitle = slugTitle;
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

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
