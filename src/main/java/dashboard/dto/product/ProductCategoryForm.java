package dashboard.dto.product;

import dashboard.enums.EntityStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class ProductCategoryForm {

    private Long productCategoryId;

    private String name;

    private String slugName;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
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
}
