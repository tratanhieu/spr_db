package dashboard.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dashboard.dto.product.ProductSupplierDto;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

public class ProductSupplier extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private Long productSupplierId;

    private String name;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public ProductSupplier() {};

    public ProductSupplier(ProductSupplierForm productSupplierForm) {
        this.productSupplierId = productSupplierForm.getProductSupplierId();
        this.name = productSupplierForm.getName();
        this.status = productSupplierForm.getStatus();
    }

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

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}

