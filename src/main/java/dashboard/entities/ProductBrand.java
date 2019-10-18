package dashboard.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "product_brand")
@EntityListeners(AuditingEntityListener.class)
public class ProductBrand extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_brand_id")
    @JsonProperty("product_brand_id")
    private Long productBrandId;

    @NotNull(message = "Name is not null")
    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Slug name is not null")
    @Column(name = "slug_name")
    @JsonProperty("slug_name")
    private String slugName;

    @Column(name = "image")
    @JsonProperty("image")
    private String image;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public Long getProductBrandID() {
        return productBrandId;
    }

    public void setProductBrandID(Long productBrandID) {
        this.productBrandId = productBrandID;
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

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
