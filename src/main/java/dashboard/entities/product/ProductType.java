package dashboard.entities.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.product.ProductTypeGroup;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "product_type")
@EntityListeners(AuditingEntityListener.class)
public class ProductType extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_id")
    @JsonProperty("product_type_id")
    private  Long productTypeId;

    @Column(name = "name", unique = true)
    @NotBlank(message = "Name is not null")
    @Size(min = 2, message = "Do dai phai lon hon 2")
    @Size(max = 50, message = "Max of name is 50")
    @JsonProperty("name")
    private String name;

    @Column(name = "slug_name", unique = true)
    @Size(min = 2, message = "Min of slugname is 2")
    @Size(max = 50, message = "Max of slugname is 50")
    @JsonProperty("slug_name")
    private String slugName;

    @ManyToOne
    @JoinColumn(name = "product_type_group_id")
    @JsonProperty("product_type_group_id")
    private ProductTypeGroup productTypeGroup;

    @Column(name = "status")
    @NotNull(message = "{validation.status.notBlank}")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public ProductType() {
        super();
    }

    public ProductType(String name, String slugName, EntityStatus status) {
        this.name = name;
        this.slugName = slugName;
        this.status = status;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
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

    public ProductTypeGroup getProductTypeGroup() {
        return productTypeGroup;
    }

    public void setProductTypeGroup(ProductTypeGroup productTypeGroup) {
        this.productTypeGroup = productTypeGroup;
    }
}
