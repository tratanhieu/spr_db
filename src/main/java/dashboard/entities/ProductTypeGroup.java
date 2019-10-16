package dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_type_group")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "updateDate", "deleleDate"},
        allowGetters = true)
public class ProductTypeGroup extends BaseEntity {

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

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

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
        return productCategory;
    }

    //    @JsonIgnore
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

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