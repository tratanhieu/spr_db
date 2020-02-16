package dashboard.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.product.ProductTypeGroup;
import dashboard.enums.EntityStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductTypeForm {

    private  Long productTypeId;


    @NotBlank(message = "Name is not null")
    @Size(min = 2, message = "Do dai phai lon hon 2")
    @Size(max = 50, message = "Max of name is 50")
    private String name;

    @Size(min = 2, message = "Min of slugname is 2")
    @Size(max = 50, message = "Max of slugname is 50")
    private String slugName;

    private Long productTypeGroupId;

    @NotNull(message = "{validation.status.notBlank}")
    @Enumerated(EnumType.STRING)
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

    public Long getProductTypeGroupId() {
        return productTypeGroupId;
    }

    public void setProductTypeGroupId(Long productTypeGroupId) {
        this.productTypeGroupId = productTypeGroupId;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
