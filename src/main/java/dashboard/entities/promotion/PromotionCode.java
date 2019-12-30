package dashboard.entities.promotion;

import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "promotion_code")
@EntityListeners(AuditingEntityListener.class)
public class PromotionCode implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "promotion_code_id")
    @JsonProperty("promotionCodeId")
    private Long promotionCodeId;

    @Column(name = "promotion_code")
    @JsonProperty("promotionCode")
    private String promotionCode;

    @OneToOne
    @JoinColumn(name = "promotion_id", referencedColumnName = "promotion_id")
    private Promotion promotion;
}
