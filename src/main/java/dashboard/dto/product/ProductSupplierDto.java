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
}
