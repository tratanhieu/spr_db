package dashboard.dto.product;

import dashboard.entities.product.ProductSupplierBranch;
import dashboard.enums.EntityStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class ProductSupplierForm {

    private Long productSupplierId;

    private String name;

    private ProductSupplierBranchForm[] productSupplierBranch;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public Long getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(Long productSupplierId) {
        this.productSupplierId = productSupplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductSupplierBranchForm[] getProductSupplierBranch() {
        return productSupplierBranch;
    }

    public void setProductSupplierBranch(ProductSupplierBranchForm[] productSupplierBranch) {
        this.productSupplierBranch = productSupplierBranch;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
