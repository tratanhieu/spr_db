package dashboard.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import dashboard.dto.BaseEntityDto;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.util.Date;

public class PostTypeDto extends BaseEntityDto {
    private BigInteger postTypeId;

    private String name;

    private String slugName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigInteger totalPost;

    private String status;

    public PostTypeDto() {}

    public PostTypeDto(
            BigInteger postTypeId,
            String name,
            String slugName,
            BigInteger totalPost,
            String status,
            Date createDate,
            Date updateDate,
            Date deleteDate
    ) {
        super(createDate, updateDate, deleteDate);
        this.postTypeId = postTypeId;
        this.name = name;
        this.slugName = slugName;
        this.totalPost = totalPost;
        this.status = status;
    }

    public BigInteger getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(BigInteger postTypeId) {
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

    public BigInteger getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(BigInteger totalPost) {
        this.totalPost = totalPost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
