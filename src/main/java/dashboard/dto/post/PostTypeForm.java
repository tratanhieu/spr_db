package dashboard.dto.post;

import dashboard.enums.EntityStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class PostTypeForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long postTypeId;

    @Size(min = 3, message = "Min length is more than 3 character")
    @Size(max = 30, message = "Max length is less than 30 character")
    private String name;

    private String slugName;

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
}
