package dashboard.entities.product;

import dashboard.dto.product.ProductCategoryForm;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;

import java.io.Serializable;

public class ProductCategory extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Long productCategoryId;

    private String name;

    private String slugName;

    private EntityStatus status;

    public ProductCategory() {}

	public ProductCategory(ProductCategoryForm form) {
		this.name = form.getName();
		this.slugName = form.getSlugName();
		this.status = form.getStatus();
	}

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
