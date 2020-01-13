package dashboard.entities.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.dto.post.PostTypeDto;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Entity
@SqlResultSetMapping(
        name="listPostTypeMapping",
        classes={
                @ConstructorResult(
                        targetClass= PostTypeDto.class,
                        columns={
                                @ColumnResult(name="post_type_id", type = BigInteger.class),
                                @ColumnResult(name="name"),
                                @ColumnResult(name="slug_name"),
                                @ColumnResult(name="totalPost", type = BigInteger.class),
                                @ColumnResult(name="status"),
                                @ColumnResult(name="create_date", type = Date.class),
                                @ColumnResult(name="update_date", type = Date.class),
                                @ColumnResult(name="delete_date", type = Date.class)
                        }
                )
        }
)
@Table( name = "post_type",
        uniqueConstraints = {
        @UniqueConstraint(name = "Uk_name", columnNames = "name"),
        @UniqueConstraint(name = "UK_slugName", columnNames = "slug_name") }
)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "updateDate", "deleteDate"},
        allowGetters = true)
public class PostType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_type_id")
    private Long  postTypeId;

    @NotNull(message = "Name is not null")
    @Column(name = "name")
    private String name;

    @Column(name = "slug_name")
    private String slugName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public PostType() {
        super();
    }

    public PostType(Long postTypeId) {
        this.postTypeId = postTypeId;
    }

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

    public void clone(PostType postType) {
        this.name = postType.name;
        this.slugName = postType.slugName;
        this.status = postType.status;
    }
}