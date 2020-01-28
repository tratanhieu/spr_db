package dashboard.entities.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dashboard.dto.post.PostForm;
import dashboard.dto.post.PostDto;
import dashboard.entities.base.BaseEntity;
import dashboard.entities.user.User;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

@Entity
@SqlResultSetMapping(
        name="listPostMapping",
        classes={
                @ConstructorResult(
                        targetClass= PostDto.class,
                        columns={
                                @ColumnResult(name="post_id", type = BigInteger.class),
                                @ColumnResult(name="name"),
                                @ColumnResult(name="slug_name"),
                                @ColumnResult(name="description"),
                                @ColumnResult(name="content"),
                                @ColumnResult(name="tags"),
                                @ColumnResult(name="image"),
                                @ColumnResult(name="postTypeName"),
                                @ColumnResult(name="author"),
                                @ColumnResult(name="status"),
                                @ColumnResult(name="create_date", type = Date.class),
                                @ColumnResult(name="update_date", type = Date.class),
                                @ColumnResult(name="delete_date", type = Date.class)
                        }
                )
        }
)
@Table(name = "post",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_name ", columnNames = "name"),
                @UniqueConstraint(name = "UK_slugName", columnNames = "slug_name")

        })
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "updateDate", "deleleDate"},
        allowGetters = true)
public class Post extends BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "name")
    private String name;

    @Column(name = "slug_name")
    private String slugName;

    @Column(name = "content", columnDefinition="TEXT", length = 1024)
    private String content;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "post_type_id")
    private PostType postType;

    @Column(name = "description")
    private String description;

    @Column(name = "publish_date")
    private Date publishDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @OneToMany(mappedBy = "postTagIdentity.post",cascade = CascadeType.PERSIST)
    private Set<PostTag> postTags;

    public Post() {
        super();
    }

    public Post(Long postId) {
        this.postId = postId;
    }

    public Post(Long postTypeId, Long userId) {
        this.postType = new PostType(postTypeId);
        this.user = new User(userId);
    }

    public Post(PostForm postForm) {
        this.name = postForm.getName();
        this.slugName = postForm.getSlugName();
        this.image = postForm.getImage();
        this.description = postForm.getDescription();
        this.content = postForm.getContent();
        this.publishDate = postForm.getPublishDate();
        this.postType = new PostType(postForm.getPostTypeId());
        this.user = new User(postForm.getUserId());
        this.status = postForm.getStatus();
    }

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

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public PostType getPostType() {
        return this.postType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setUser(Long userId) {
        this.user.setUserId(userId);
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public Set<PostTag> getPostTags() {
        return postTags;
    }

    public void setPostTags(Set<PostTag> postTags) {
        this.postTags = postTags;
    }
}
