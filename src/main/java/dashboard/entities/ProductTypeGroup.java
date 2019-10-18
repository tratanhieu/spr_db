package dashboard.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
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

@Entity
@Table(name = "product_type_group")
@EntityListeners(AuditingEntityListener.class)
public class ProductTypeGroup extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_group_id")
    @JsonProperty("product_type_group_id")
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
    @JsonProperty("slug_name")
    private String slugName;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @Column( name = "status")
    @NotNull(message = "Status is not null")
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

    @JsonProperty("product_category")
    public Map<String, String> getProductCategory() {
        Map<String, String> map = new HashMap<>();
        map.put("product_category_id", String.valueOf(productCategory.getProductCategoryId()));
        map.put("name", productCategory.getName());
        map.put("slug_name", productCategory.getSlugName());
        return map;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
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