package dashboard.dto.product;

import dashboard.entities.base.BaseEntity;
import dashboard.entities.product.ProductSupplierBranch;
import dashboard.enums.EntityStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigInteger;
import java.util.List;

public class ProductSupplierDto extends BaseEntity {

    private BigInteger productSupplierId;

    private String name;

    private List<ProductSupplierBranchDto> productSupplierBranchList;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public BigInteger getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(BigInteger productSupplierId) {
        this.productSupplierId = productSupplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductSupplierBranchDto> getProductSupplierBranchList() {
        return productSupplierBranchList;
    }

    public void setProductSupplierBranchList(List<ProductSupplierBranchDto> productSupplierBranchList) {
        this.productSupplierBranchList = productSupplierBranchList;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
