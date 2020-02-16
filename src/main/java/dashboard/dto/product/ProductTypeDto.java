package dashboard.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.dto.BaseEntityDto;
import dashboard.entities.product.ProductTypeGroup;
import dashboard.enums.EntityStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductTypeDto extends BaseEntityDto {

    private  Long productTypeId;

    private String name;

    private String slugName;

    private String productTypeGroupName;

    private EntityStatus status;

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

    public String getProductTypeGroupName() {
        return productTypeGroupName;
    }

    public void setProductTypeGroupName(String productTypeGroupName) {
        this.productTypeGroupName = productTypeGroupName;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
