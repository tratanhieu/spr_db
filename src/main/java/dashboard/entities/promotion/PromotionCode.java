package dashboard.entities.promotion;

import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(name = "percent")
    @JsonProperty("percent")
    private int percent;

    @Column(name = "quantity")
    @JsonProperty("quantity")
    private int quantity;

    public Long getPromotionCodeId() {
        return promotionCodeId;
    }

    public void setPromotionCodeId(Long promotionCodeId) {
        this.promotionCodeId = promotionCodeId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    @JsonProperty("promotion")
    public Map<String, String> getPromotion() {
        Map<String,String> map = new HashMap<String, String>();
        map.put("promotionId", String.valueOf(promotion.getPromotionId()));
        map.put("promotionName", promotion.getName());
        map.put("startDate", promotion.getFromDate().toString());
        map.put("endDate", promotion.getToDate().toString());

        return map;
    }

    @JsonProperty("promotion")
    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
