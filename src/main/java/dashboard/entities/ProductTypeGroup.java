package dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_type_group")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "updateDate", "deleleDate"},
        allowGetters = true)
public class ProductTypeGroup {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_group_id")
    @JsonProperty("product_type_group_id")
    private  Long productTypeGroupId;

    @Column(name = "name")
    @NotNull(message = "Name is not null ")
    @JsonProperty("name")
    private String name;

    @Column(name = "slug_name")
    @JsonProperty("slug_name")
    private String slugName;


    @OneToMany(fetch = FetchType.LAZY)
    @Column(name = "product_category_id")
    @JsonProperty("product_category_id")
    private ProductCategory productCategoryId;

    @Column( name = "status")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public Long getProductTypeGroupId() {
        return productTypeGroupId;
    }

    public void setProductTypeGroupId(Long productTypeGroupId) {
        this.productTypeGroupId = productTypeGroupId;
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

    public ProductCategory getProductCategory() {
        return productCategoryId;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategoryId = productCategory;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
