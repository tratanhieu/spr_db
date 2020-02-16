package dashboard.entities.product;

import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;

import java.io.Serializable;

public class ProductType extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    private  Long productTypeId;

    private String name;

    private String slugName;

    private Long productCategoryId;

    private Long productTypeGroupId;

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

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
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
