package dashboard.entities.promotion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.PromotionStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "promotion",
        uniqueConstraints = {
            @UniqueConstraint(name = "UK_name", columnNames = "name")
        })
@EntityListeners(AuditingEntityListener.class)
public class Promotion extends BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "promotion_id")
    @JsonProperty("promotionId")
    private Long promotionId;

    @NotBlank(message = "name is not blank")
    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "percent")
    @JsonProperty("percent")
    private String percent;

    @Column(name = "from_date")
    @JsonProperty("fromDate")
    private Date fromDate;

    @Column(name = "to_date")
    @JsonProperty("toDate")
    private Date toDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PromotionStatus status;

    @OneToOne(mappedBy = "promotion", cascade = CascadeType.ALL)
    private PromotionCode promotionCode;

    @OneToMany(mappedBy = "productPromotionIdentity.postId")
    private Set<ProductPromotion> productPromotions;

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public PromotionStatus getStatus() {
        return status;
    }

    public void setStatus(PromotionStatus status) {
        this.status = status;
    }

    @JsonProperty("promotionCode")
    public PromotionCode getPromotionCode() {
        return promotionCode;
    }


    public void setPromotionCode(PromotionCode promotionCode) {
        this.promotionCode = promotionCode;
    }

    @JsonProperty("product")
    public Set<ProductPromotion> getProductPromotions() {
        return productPromotions;
    }


    public void setProductPromotions(Set<ProductPromotion> productPromotions) {
        this.productPromotions = productPromotions;
    }
}
