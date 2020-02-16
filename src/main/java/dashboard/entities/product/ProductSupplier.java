package dashboard.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "updateDate", "deleleDate"},
        allowGetters = true)
public class ProductSupplier extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private Long productSupplierId;

    private String name;

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
}

