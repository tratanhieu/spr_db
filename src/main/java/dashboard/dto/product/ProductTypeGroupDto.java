package dashboard.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.dto.BaseEntityDto;
import dashboard.entities.base.BaseEntity;
import dashboard.entities.product.ProductCategory;
import dashboard.entities.product.ProductType;
import dashboard.enums.EntityStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class ProductTypeGroupDto extends BaseEntityDto {

    private Long productTypeGroupId;

    private String name;

    private String slugName;

    private String productCategoryName;

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

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
