package dashboard.entities.promotion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dashboard.entities.base.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_promotion")
@EntityListeners(AuditingEntityListener.class)
public class ProductPromotion implements Serializable {
    private static final Long serialVersionUID = 1L;

    @EmbeddedId
    @JsonIgnore
    private ProductPromotion productPromotion;


}
