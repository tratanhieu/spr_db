package dashboard.dto.product;

import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigInteger;

public class ProductSupplierDto extends BaseEntity {

    private BigInteger productSupplier;

    private String name;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public BigInteger getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(BigInteger productSupplier) {
        this.productSupplier = productSupplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
