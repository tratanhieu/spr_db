package dashboard.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.dto.product.ProductTypeGroupForm;
import dashboard.entities.base.BaseEntity;
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
@Table(name = "product_type_group")
@EntityListeners(AuditingEntityListener.class)
public class ProductTypeGroup extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_group_id")
    @JsonProperty("productTypeGroupId")
    private  Long productTypeGroupId;

    @Column(name = "name", unique = true)
    @NotBlank(message = "Name is not null")
    @Size(min = 2, message = "DDooj dai phai lon hon 2")
    @Size(max = 50, message = "Max of name is 50")
    @JsonProperty("name")
    private String name;

    @Column(name = "slug_name", unique = true)
    @Size(min = 2, message = "Min of slugname is 2")
    @Size(max = 50, message = "Max of slugname is 50")
    @JsonProperty("slugName")
    private String slugName;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "productTypeGroup", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<ProductType> productTypes;

    @Column( name = "status")
    @NotNull(message = "Status is not null")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public ProductTypeGroup(ProductTypeGroupForm productTypeGroupForm) {

        this.productTypeGroupId = productTypeGroupForm.getProductTypeGroupId();
        this.name = productTypeGroupForm.getName();
        this.slugName = productTypeGroupForm.getSlugName();
        this.status = productTypeGroupForm.getStatus();
    }

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

    @JsonProperty("productCategory")
    public Map<String, String> getProductCategory() {
        Map<String, String> map = new HashMap<>();
        map.put("productCategoryId", String.valueOf(productCategory.getProductCategoryId()));
        map.put("name", productCategory.getName());
        map.put("slugName", productCategory.getSlugName());
        return map;
    }

    @JsonProperty("productCategory")
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Set<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(Set<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    // Cách này là cách thứ 2 để hiển thị thông tin của bảng 1
//    @JsonProperty("product_category_name")
//    public String getProductCategoryName() {
//        return productCategory.getName();
//    }
//
//    @JsonProperty("product_category_id")
//    public Long getProductCategoryId() {
//        return productCategory.getProductCategoryId();
//    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}