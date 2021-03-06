package dashboard.dto.post;

import dashboard.constants.PatternConstants;
import dashboard.dto.TagDto;
import dashboard.entities.post.PostType;
import dashboard.enums.EntityStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

public class PostForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long postId;

    @Size(min = 6, message = "Min length is more than 6 character")
    @Size(max = 60, message = "Max length is less than 60 character")
    private String name;

    private String slugName;

    @NotNull(message = "Post image is not empty")
//    @Pattern(regexp = PatternConstants.REGEX_BASE64_CHECK, message = "Image not valid")
    private String image;

    @Size(max = 255, message = "Max character is 255")
    private String description;

    @Size(min = 128, message = "Min character is 128")
    private String content;

    private Date publishDate;

    private String[] tags;

    @NotNull(message = "Post type is not empty")
    private Long postTypeId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Long getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(Long postTypeId) {
        this.postTypeId = postTypeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
